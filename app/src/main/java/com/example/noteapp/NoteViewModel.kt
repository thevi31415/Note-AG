package com.example.noteapp

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class NoteViewModel(application: Application) : ViewModel() {
    private val noteRespository: NoteRespository = NoteRespository(application)
    fun insertNote(note: Note) = viewModelScope.launch {
        noteRespository.insertNote(note)
    }

    fun updatetNote(note: Note) = viewModelScope.launch {
        noteRespository.updatetNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        noteRespository.deleteNote(note)
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Note>> {
        return noteRespository.searchDatabase(searchQuery)
    }

    fun searchDatabaseAll(searchQuery: String): LiveData<List<Note>> {
        return noteRespository.searchDatabaseAll(searchQuery)
    }

    /*fun swipeGesture(context: Context, recyclerView: RecyclerView){
        val swipeGesture = object :SwipeGesture(context){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val frompos = viewHolder.adapterPosition
                val topos = target.adapterPosition
                Collections.swap()
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                TODO("Not yet implemented")
            }

        }
    }*/
    fun getAllNote(): LiveData<List<Note>> = noteRespository.getAllNote()
    class NoteViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NoteViewModel(application) as T
            }
            throw  IllegalAccessException("Unable construct viewModel")
        }
    }
}