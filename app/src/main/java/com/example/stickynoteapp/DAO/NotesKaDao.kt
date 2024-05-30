package com.example.stickynoteapp.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.stickynoteapp.model.Notes

@Dao
interface NotesKaDao {

    @Query("SELECT * FROM Notes ORDER BY id DESC")
    fun getAllNotes():LiveData<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun addNote(notes:Notes)

    @Delete
    suspend  fun deleteNote(note: Notes)

    @Update
    suspend fun updateNote(notes: Notes)

}