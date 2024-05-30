package com.example.stickynoteapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController



class MainActivity : AppCompatActivity() {

    //private lateinit var notesViewModel: NotesViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        navController = findNavController(R.id.fragmentContainerView)
        setupActionBarWithNavController(navController)



    }

    override fun onNavigateUp(): Boolean {
        return navController.navigateUp() || super.onNavigateUp()
    }
//    private fun setUpViewModel(){
//        val notesRepository = NotesRepository(NotesDatabase())
//        val viewModelProviderFactory = ViewModelProviderFactory(application,notesRepository)
//        notesViewModel = ViewModelProvider(this ,viewModelProviderFactory)[NotesViewModel::class.java]
//    }

}