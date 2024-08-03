package com.example.stickynoteapp.UI.adapter

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.stickynoteapp.MainActivity
import com.example.stickynoteapp.R
import com.example.stickynoteapp.UI.fragments.HomeFragment
import com.example.stickynoteapp.UI.fragments.HomeFragmentDirections
import com.example.stickynoteapp.databinding.NoteItemBinding
import com.example.stickynoteapp.model.Notes
import com.example.stickynoteapp.viewModel.NotesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class NotesAdapter(private val context: Context, private var noteList: List<Notes>) :RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    private var  viewModel =  NotesViewModel(Application())

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

        holder.binding.root.setOnLongClickListener {
            val bottomSheet = BottomSheetDialog(context)
            bottomSheet.setContentView(R.layout.delete_alert_box)
            bottomSheet.show()
            val textNo = bottomSheet.findViewById<TextView>(R.id.noText)
            val textYes = bottomSheet.findViewById<TextView>(R.id.yesText)

            textNo?.setOnClickListener {
                bottomSheet.dismiss()
            }
            textYes?.setOnClickListener {
                runBlocking{
                    viewModel.deleteNote(data)
                    Toast.makeText(context, "Note deleted successfully", Toast.LENGTH_LONG)
                        .show()
                }
                bottomSheet.dismiss()
            }
            notifyDataSetChanged()
            true // indicate event is consumed
        }
    }


}
