package com.example.stickynoteapp.UI.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.stickynoteapp.MainActivity
import com.example.stickynoteapp.R
import com.example.stickynoteapp.databinding.FragmentCreateBinding
import com.example.stickynoteapp.model.Notes
import com.example.stickynoteapp.viewModel.NotesViewModel
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


class CreateFragment : Fragment(R.layout.fragment_create) {

    private val viewModel: NotesViewModel by viewModels()
    private lateinit var binding: FragmentCreateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCreateBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        binding.cFloatingDonebtn.setOnClickListener {
           runBlocking {
               createNote()
           }
        }

        return binding.root
    }

    private suspend fun  createNote() {

        val title = binding.cTitle.text.toString()
        val subTitle = binding.cSubtitle.text.toString()
        val note = binding.cNote.text.toString()


        val currentDateTime = LocalDateTime.now()
        val currentTime = LocalDateTime.now()
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val formattedTime = currentTime.format(timeFormatter)
        val dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.getDefault())
        val formattedDate = currentDateTime.format(dateFormatter)
        val dateTimeString = "$formattedDate $formattedTime"

        val data = Notes(
            null,
            title = title,
            subTitle = subTitle,
            note = note,
            date = dateTimeString
        )
        viewModel.addNote(data)
        Toast.makeText(requireContext(), "Notes saved successfully", Toast.LENGTH_SHORT).show()
        Navigation.findNavController(requireView())
            .navigate(R.id.action_createFragment_to_homeFragment)

    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val activity = activity as? MainActivity
        return when (item.itemId) {

            android.R.id.home -> {
                // Handle back button click here
                activity?.onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}