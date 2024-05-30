package com.example.stickynoteapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.stickynoteapp.Database.NotesDatabase
import com.example.stickynoteapp.Repository.NotesRepository
import com.example.stickynoteapp.model.Notes
import kotlinx.coroutines.launch


class NotesViewModel(app: Application): AndroidViewModel(app) {

    private val notesRepository: NotesRepository

    init {
        val dao = NotesDatabase.getDatabaseInstance(app).getDao()
        notesRepository = NotesRepository(dao)
    }

    //private val notesRepository: NotesRepository


    fun addNote(notes: Notes) {
        viewModelScope.launch {
            notesRepository.insertNote(notes)
        }

        }
    fun deleteNote(note:Notes){
        viewModelScope.launch {
            notesRepository.deleteNote(note)
        }

        }
    fun updateNote(notes: Notes){
        viewModelScope.launch {
            notesRepository.updateNote(notes)
        }

        }
    fun getAllNote():LiveData<List<Notes>> = notesRepository.getAllNote()
    
}