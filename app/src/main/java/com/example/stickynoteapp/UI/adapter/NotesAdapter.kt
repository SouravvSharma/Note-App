package com.example.stickynoteapp.UI.adapter

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.stickynoteapp.UI.fragments.HomeFragment
import com.example.stickynoteapp.UI.fragments.HomeFragmentDirections
import com.example.stickynoteapp.databinding.NoteItemBinding
import com.example.stickynoteapp.model.Notes
import com.example.stickynoteapp.viewModel.NotesViewModel


class NotesAdapter(private val context: Context, private var noteList: List<Notes>) :RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    //private var  viewModel = NotesViewModel(Application())


//    private fun showDeleteConfirmationDialog(note: Notes) {
//        AlertDialog.Builder(context)
//            .setTitle("Delete")
//            .setMessage("Do you really want to delete this item?")
//            .setPositiveButton("Yes") { _, _ ->
//                viewModel.deleteNote(note)
//
//            }
//            .setNegativeButton("NO") { dialog, _ ->
//                dialog.dismiss()
//            }
//            .show()
//    }
    fun filteredList(newArrayFilteredList: ArrayList<Notes>) {
        noteList = newArrayFilteredList
        notifyDataSetChanged()

    }



    class NotesViewHolder(val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root) {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            NoteItemBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val data = noteList[position]
        holder.binding.notesTitle.text = data.title
        holder.binding.notesSubtitle.text = data.note
        holder.binding.notesDate.text = data.date


        holder.binding.root.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToEditFragment(data)
            Navigation.findNavController(it).navigate(action)
        }

//        holder.binding.root.setOnLongClickListener {
//            showDeleteConfirmationDialog(data)
//
//            true // indicate event is consumed
//        }
    }
}
