package com.terasoltechnologies.mvvm_test.repository

import android.os.AsyncTask
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.terasoltechnologies.mvvm_test.database.Notes
import com.terasoltechnologies.mvvm_test.database.NotesDao

class NotesRepository(private val notesDao: NotesDao) {

    val allNotes: LiveData<List<Notes>> = notesDao.getAllNotes()

   /* fun insertNotes(notes: Notes) {
        InsertNotesAsyncTask(notesDao).execute(notes)
    }*/

    fun deleteAllNotes() {
        DeleteAllNotesAsyncTask(
            notesDao
        ).execute()
    }

   /* fun getAllNotes(): LiveData<List<Notes>> {
        return allNotes
    }*/

    @WorkerThread
    suspend fun insert(notes: Notes) {
        notesDao.insertNotes(notes)
    }


    private class InsertNotesAsyncTask(val noteDao: NotesDao) : AsyncTask<Notes, Unit, Unit>() {

        override fun doInBackground(vararg notes: Notes?) {
            noteDao.insertNotes(notes[0]!!)
        }
    }


    private class DeleteAllNotesAsyncTask(val noteDao: NotesDao) : AsyncTask<Unit, Unit, Unit>() {

        override fun doInBackground(vararg p0: Unit?) {
            noteDao.deleteAllNotes()
        }
    }


}