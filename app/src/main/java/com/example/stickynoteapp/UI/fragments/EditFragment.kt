package com.example.stickynoteapp.UI.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.stickynoteapp.MainActivity
import com.example.stickynoteapp.R
import com.example.stickynoteapp.databinding.FragmentEditBinding
import com.example.stickynoteapp.model.Notes
import com.example.stickynoteapp.viewModel.NotesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


class EditFragment : Fragment() {
    private val viewModel: NotesViewModel by viewModels()
    private val oldNote by navArgs<EditFragmentArgs>()
    private lateinit var binding:FragmentEditBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentEditBinding.inflate(layoutInflater,container,false)
        setHasOptionsMenu(true)

        binding.eNTitle.setText(oldNote.data.title)
        binding.eNsubTitle.setText(oldNote.data.subTitle)
        binding.eNote.setText(oldNote.data.note)

        

        binding.eNFloatingDonebtn.setOnClickListener {
            runBlocking {
                updateNote(it)
            }

        }
        return binding.root
    }


    private suspend fun updateNote(it: View?) {

        val title = binding.eNTitle.text.toString()
        val subTitle = binding.eNsubTitle.text.toString()
        val note = binding.eNote.text.toString()

        val currentDateTime = LocalDateTime.now()
        val currentTime = LocalDateTime.now()
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val formattedTime = currentTime.format(timeFormatter)
        val dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.getDefault())
        val formattedDate = currentDateTime.format(dateFormatter)
        val dateTimeString = "$formattedDate , $formattedTime"
        val data = Notes(
            oldNote.data.id,
            title = title,
            subTitle = subTitle,
            note = note,
            date = dateTimeString)
            viewModel.updateNote(data)


        Toast.makeText(requireContext(), "Notes saved successfully", Toast.LENGTH_SHORT).show()
        Navigation.findNavController(requireView()).navigate(R.id.action_editFragment_to_homeFragment)


    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.remove ){
            val bottomSheet = BottomSheetDialog(requireContext())
            bottomSheet.setContentView(R.layout.delete_alert_box)
            bottomSheet.show()

            val textNo= bottomSheet.findViewById<TextView>(R.id.noText)
            val textYes = bottomSheet.findViewById<TextView>(R.id.yesText)

            textNo?.setOnClickListener {
                bottomSheet.dismiss()
            }
            textYes?.setOnClickListener {
               runBlocking {
                   viewModel.deleteNote(oldNote.data)
               }
                bottomSheet.dismiss()
                Navigation.findNavController(requireView()).navigate(R.id.action_editFragment_to_homeFragment)
            }

        }
        val activity = activity as? MainActivity

        return when(item.itemId){
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}