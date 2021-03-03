package yoavsabag.easynote.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes_table")
data class Note (
    @PrimaryKey(autoGenerate = true)
    var noteId: Long = 0L,
    @ColumnInfo(name = "title")
    var title: String = "",
    @ColumnInfo(name = "body")
    var body: String = "",
    @ColumnInfo(name = "date")
    var date: Long = Calendar.getInstance().timeInMillis,
    @ColumnInfo(name = "background_color")
    var backGroundColorRes: Int = -1
)