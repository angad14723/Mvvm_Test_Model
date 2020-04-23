package com.terasoltechnologies.mvvm_test.database

import android.content.Context
import android.os.AsyncTask
import android.provider.ContactsContract
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [Notes::class], version = 1)
abstract class NotesDataBase : RoomDatabase() {


    abstract fun notesDao(): NotesDao

    companion object {

        private var instance: NotesDataBase? = null

        fun getInstance(context: Context, scope: CoroutineScope): NotesDataBase {
            if (instance == null) {
                synchronized(NotesDataBase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NotesDataBase::class.java, "notes_database"
                    ).fallbackToDestructiveMigration()
                        .addCallback(NoteDatabaseCallBack(scope))
                        .build()
                }
            }

            return instance!!
        }

        fun destroyInstance() {
            instance = null
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                PopulateDbAsyncTask(instance)
                    .execute()
            }
        }
    }

    private class NoteDatabaseCallBack(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            instance.let { databse ->
                scope.launch(Dispatchers.IO) {

                    populateDatabase(databse!!.notesDao())
                }
            }
        }

        private fun populateDatabase(notesDao: NotesDao) {

           // notesDao.deleteAllNotes()

            val word = Notes("Hello", "Disc")
          //  notesDao.insertNotes(word)

        }
    }

    class PopulateDbAsyncTask(db: NotesDataBase?) : AsyncTask<Unit, Unit, Unit>() {

        private val notesDao = db?.notesDao()

        override fun doInBackground(vararg params: Unit?) {

            notesDao?.insertNotes(Notes("Title 1", "description 1"))

        }

    }

}