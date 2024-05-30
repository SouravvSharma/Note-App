package com.example.stickynoteapp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.stickynoteapp.DAO.NotesKaDao
import com.example.stickynoteapp.model.Notes

@Database(entities = [Notes::class], version = 1)

abstract class NotesDatabase : RoomDatabase() {

    abstract fun getDao(): NotesKaDao

    companion object {
        @Volatile
        private var INSTANCE: NotesDatabase? = null

        fun getDatabaseInstance(context: Context): NotesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val roomDatabaseInstance = Room.databaseBuilder(
                    context, NotesDatabase::class.java, "Notes.db"
                ).build()
                INSTANCE = roomDatabaseInstance

                return roomDatabaseInstance
            }
        }
    }
}
