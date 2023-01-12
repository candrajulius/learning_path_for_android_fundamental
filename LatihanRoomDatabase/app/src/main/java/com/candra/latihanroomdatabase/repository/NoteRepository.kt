package com.candra.latihanroomdatabase.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.candra.latihanroomdatabase.database.NoteDao
import com.candra.latihanroomdatabase.database.NoteRoomDatabase
import com.candra.latihanroomdatabase.entity.Note
import kotlinx.coroutines.handleCoroutineException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class NoteRepository(
    application: Application
)
{
    private val mNotesDao: NoteDao
    private val excutorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = NoteRoomDatabase.getDatabase(application)
        mNotesDao = db.noteDao()
    }

    fun insert(noteDao: Note){
       excutorService.execute{mNotesDao.insert(noteDao)}
    }

    fun delete(note: Note){
        excutorService.execute{mNotesDao.delete(note)}
    }

    fun update(note: Note){
        excutorService.execute { mNotesDao.update(note) }
    }

    fun getAllNotes(): LiveData<List<Note>> = mNotesDao.getAllNotes()
}