package com.example.stickynoteapp.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stickynoteapp.Repository.NotesRepository

class ViewModelProviderFactory (val app:Application, private val notesRepository: NotesRepository):
    ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ViewModelProviderFactory(app,notesRepository) as T
    }
}