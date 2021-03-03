package yoavsabag.easynote.ui.addnewnote

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import yoavsabag.easynote.database.NoteDatabaseDao
import java.lang.IllegalArgumentException

class AddNewNoteModelFactory(private val noteId: Long,
                            private val dataSource : NoteDatabaseDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AddNewNoteViewModel::class.java)) {
            return AddNewNoteViewModel(noteId, dataSource) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}