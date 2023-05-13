package com.example.noteapp

import android.app.Application
import androidx.lifecycle.LiveData


class NoteRespository(app: Application) {
    private val noteDao: NoteDao

    init {
        val noteDatabase: NoteDatabase = NoteDatabase.getInstance(app)
        noteDao = noteDatabase.getNotDao()
    }

    suspend fun insertNote(note: Note) = noteDao.insertNote(note)
    suspend fun updatetNote(note: Note) = noteDao.updateNote(note)
    suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)
    fun getAllNote(): LiveData<List<Note>> = noteDao.getAllNote()
    fun searchDatabase(searchQuery: String): LiveData<List<Note>> {
        return noteDao.searchDatabase(searchQuery)
    }
    fun searchDatabaseAll(searchQuery: String): LiveData<List<Note>> {
        return noteDao.searchDatabaseAll(searchQuery)
    }
}