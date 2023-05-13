package com.example.noteapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun getNotDao(): NoteDao
    companion object {
        @Volatile
        private var instance: NoteDatabase? = null
        fun getInstance(context: Context): NoteDatabase {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(
                        context,
                        NoteDatabase::class.java,
                        "NoteDatabase"
                    ).build()
            }
            return instance!!
        }
    }
}