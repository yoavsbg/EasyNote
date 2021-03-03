package yoavsabag.easynote.database

import androidx.lifecycle.LiveData
import androidx.room.*
import yoavsabag.easynote.model.Note

@Dao
interface NoteDatabaseDao {

    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Query("SELECT * from notes_table WHERE noteId = :key")
    suspend fun get(key: Long): Note?

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM notes_table ORDER BY date DESC")
    fun getAllNotes(): LiveData<List<Note>>

}