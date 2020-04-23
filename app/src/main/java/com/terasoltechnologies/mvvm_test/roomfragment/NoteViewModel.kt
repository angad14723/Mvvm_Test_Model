package com.terasoltechnologies.mvvm_test.roomfragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terasoltechnologies.mvvm_test.database.Notes
import com.terasoltechnologies.mvvm_test.database.NotesDataBase
import com.terasoltechnologies.mvvm_test.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import timber.log.Timber

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NotesRepository

    val allNotes: LiveData<List<Notes>>


    init {
        Timber.d("onStartNoteModel")
        val notesDao = NotesDataBase.getInstance(application, viewModelScope).notesDao()
        repository = NotesRepository(notesDao)
        allNotes = repository.allNotes
    }

    fun insert(notes: Notes) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(notes)
    }

    /*
    fun insert(note: Notes) {
        repository.insertNotes(note)
    }*/

    fun deleteAllNotes() {
        repository.deleteAllNotes()
    }

    /*  fun getAllNotes(): LiveData<List<Notes>> {
          return allNotes
      }*/

    override fun onCleared() {
        super.onCleared()
    }

}
