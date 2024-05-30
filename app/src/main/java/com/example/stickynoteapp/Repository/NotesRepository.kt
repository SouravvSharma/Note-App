package com.example.stickynoteapp.Repository


import androidx.lifecycle.LiveData
import com.example.stickynoteapp.DAO.NotesKaDao
import com.example.stickynoteapp.Database.NotesDatabase
import com.example.stickynoteapp.model.Notes

class NotesRepository( private val db: NotesKaDao) {

    fun getAllNote():LiveData<List<Notes>> = db.getAllNotes()

    suspend fun insertNote(note:Notes) = db.addNote(note)

    suspend fun deleteNote(note:Notes) = db.deleteNote(note)

    suspend  fun updateNote(note: Notes) = db.updateNote(note)


}