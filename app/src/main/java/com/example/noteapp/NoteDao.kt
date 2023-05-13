package com.example.noteapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("select * from note_table ORDER BY note_id_col DESC")
    fun getAllNote(): LiveData<List<Note>>

    @Query("select * from note_table where title_col like :searchQuery or description_col like :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<Note>>

    @Query("select * from note_table where all_note like :searchQuery")
    fun searchDatabaseAll(searchQuery: String): LiveData<List<Note>>
}