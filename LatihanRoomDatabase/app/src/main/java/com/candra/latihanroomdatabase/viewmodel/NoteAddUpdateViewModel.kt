package com.candra.latihanroomdatabase.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.candra.latihanroomdatabase.entity.Note
import com.candra.latihanroomdatabase.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteAddUpdateViewModel(
    application: Application
): ViewModel()
{
    private val mNoteRepository: NoteRepository = NoteRepository(application)

    fun insert(note: Note){
        mNoteRepository.insert(note)
    }

    fun delete(note: Note){
        mNoteRepository.delete(note)
    }

    fun update(note: Note){
       mNoteRepository.update(note)
    }

}