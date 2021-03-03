package yoavsabag.easynote.ui.addnewnote

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import yoavsabag.easynote.database.NoteDatabaseDao
import yoavsabag.easynote.model.Note
import java.util.*

class AddNewNoteViewModel(private val noteId: Long = 0L,
                          private val database: NoteDatabaseDao) : ViewModel() {

    private val _navigationToNoteList = MutableLiveData<Boolean?>()
    val navigationToNoteList: LiveData<Boolean?> get() = _navigationToNoteList

    private val _preventAddEmptyNote = MutableLiveData<Boolean?>()
    val preventAddEmptyNote: LiveData<Boolean?> get() = _preventAddEmptyNote


    val noteTitle = MutableLiveData<String>("")
    val noteBody = MutableLiveData<String>("")

    private var _newNote: Note = Note()

    init {
        viewModelScope.launch {
            _newNote = database.get(noteId) ?: Note()


            if(_newNote.title.isNotEmpty()){
                noteTitle.value = _newNote.title
            }
            if(_newNote.body.isNotEmpty()){
                noteBody.value = _newNote.body
            }

        }
    }



    fun AddNewNote(){

        if(noteTitle.value != null && noteTitle.value.toString().isNotEmpty() ||
            noteBody.value != null && noteBody.value.toString().isNotEmpty()){
            viewModelScope.launch {



                _newNote.title = noteTitle.value.toString()
                _newNote.body = noteBody.value.toString()

                if(_newNote.noteId != 0L){
                    database.update(_newNote)
                }
                else {
                    database.insert(_newNote)
                }

                _navigationToNoteList.value = true

            }
        }
        else {

        }
    }

    fun doneNavigating() {
        _navigationToNoteList.value = false
    }

}