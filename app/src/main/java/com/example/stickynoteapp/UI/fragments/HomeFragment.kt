package com.example.stickynoteapp.UI.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.stickynoteapp.R
import com.example.stickynoteapp.UI.adapter.NotesAdapter
import com.example.stickynoteapp.databinding.FragmentHomeBinding
import com.example.stickynoteapp.model.Notes
import com.example.stickynoteapp.viewModel.NotesViewModel
import java.util.Locale


class HomeFragment : Fragment() {

    private var voiceSearchResult: String? = null
    private val searchRec = 102
    private val viewModel: NotesViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private var oldNoteList = arrayListOf<Notes>()
    private lateinit var adapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)

        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.layoutManager = staggeredGridLayoutManager


        //adding all the notes to recycler view
        viewModel.getAllNote().observe(viewLifecycleOwner) { noteList ->
            oldNoteList = noteList as ArrayList<Notes>
            adapter = NotesAdapter(requireContext(), noteList)
            binding.recyclerView.adapter = adapter
        }
        binding.floatingBtn.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_createFragment)
        }
        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == searchRec && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayExtra(RecognizerIntent.EXTRA_RESULTS)

            if (!result.isNullOrEmpty()) {
                val query = result
                voiceSearchResult = query.toString() // Store the voice search result
//                activity?.invalidateOptionsMenu() // Update the options menu

            }
        }
    }
        @Deprecated("Deprecated in Java")
        override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
            inflater.inflate(R.menu.search_menu, menu)


            val itemView = menu.findItem(R.id.search)
            val actionView = itemView.actionView
            val searchView = actionView as SearchView

            searchView.setQuery(voiceSearchResult, true)


            // searchView.queryHint = "Enter Note Title..."
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    //after text submit karne k bad jo text ata h usse search krne k lie ye function use krna hai
                    return false
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    //koi bhi text likhte hi search karne ka function
                    // notesFiltering(query)
                    notesFiltering(query)
                    return true

                }
            })
            super.onCreateOptionsMenu(menu, inflater)
        }

        private fun notesFiltering(query: String?) {
            val newArrayFilteredList = arrayListOf<Notes>()

            for (i in oldNoteList) {

                if (i.title.contains(query!!) || i.note.contains(query)) {
                    newArrayFilteredList.add(i)
                }
            }
            adapter.filteredList(newArrayFilteredList)
        }


        private fun askInputListner() {
            if (!SpeechRecognizer.isRecognitionAvailable(requireContext())) {
                Toast.makeText(requireContext(), "Voice search not Available", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                i.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something!")
                try {
                    startActivityForResult(i, searchRec)
                } catch (e: Exception) {
                    Toast.makeText(
                        requireContext(),
                        "Your device does not support voice search",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }
    }