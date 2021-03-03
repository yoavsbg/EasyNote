package yoavsabag.easynote.ui.notelist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import yoavsabag.easynote.database.NoteDatabaseDao
import java.lang.IllegalArgumentException

class NoteListViewModelFactory(private val dataSource: NoteDatabaseDao,
                               private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NoteListViewModel::class.java)) {
            return NoteListViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}