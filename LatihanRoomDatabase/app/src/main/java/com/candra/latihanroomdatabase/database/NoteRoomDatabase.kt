package com.candra.latihanroomdatabase.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.candra.latihanroomdatabase.entity.Note

@Database(entities = [Note::class],version = 1)
abstract class NoteRoomDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object{
        @Volatile
        private var INSTANCE: NoteRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): NoteRoomDatabase{
            if (INSTANCE == null){
                synchronized(NoteRoomDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    NoteRoomDatabase::class.java,"note_database").build()
                }
            }
            return INSTANCE as NoteRoomDatabase
        }
    }
}
