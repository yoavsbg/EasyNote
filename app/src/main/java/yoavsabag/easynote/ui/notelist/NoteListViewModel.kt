package yoavsabag.easynote.ui.notelist

import android.app.Application
import android.view.View
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import yoavsabag.easynote.database.NoteDatabaseDao
import yoavsabag.easynote.model.Note

class NoteListViewModel(val database: NoteDatabaseDao, application: Application) : AndroidViewModel(application) {

    private val _navigationToAddNewNote = MutableLiveData<Long?>()
    val navigationToAddNewNote: LiveData<Long?> get() = _navigationToAddNewNote


    val notes = database.getAllNotes()

    val showNoNotes = Transformations.map(notes) {
        it?.isEmpty()
    }

    fun onNewNote() {
        _navigationToAddNewNote.value = 0
    }

    fun doneNavigation() {
        _navigationToAddNewNote.value = null
    }

    fun onNoteClicked(noteId: Long) {
        _navigationToAddNewNote.value = noteId
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            database.delete(note)
        }
    }

}