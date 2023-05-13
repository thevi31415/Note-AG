package com.example.noteapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "note_table")
class Note(
    @ColumnInfo(name = "title_col")
    var title: String = " ",
    @ColumnInfo(name = "description_col")
    var description: String = "",
    @ColumnInfo(name = "description_text_col")
    var description_text: String = "",
    @ColumnInfo(name = "edittime")
    var timeedit: String = "",
    @ColumnInfo(name="color_col")
    var color: String ="",
    @ColumnInfo(name = "all_note")
    var allnote: String = "",
    @ColumnInfo(name = "du_phong")
    var duphong: String = "0"


) : Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id_col")
    var id: Int = 0;
}