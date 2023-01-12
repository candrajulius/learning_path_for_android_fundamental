package com.candra.latihanroomdatabase.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.candra.latihanroomdatabase.entity.Note
import com.candra.latihanroomdatabase.repository.NoteRepository

class MainViewModel(
    application: Application
): ViewModel()
{
    private val mNoteRepository: NoteRepository = NoteRepository(application)

    fun getAllNotes(): LiveData<List<Note>> = mNoteRepository.getAllNotes()
}