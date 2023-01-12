package com.candra.latihansqlitedatabase.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.candra.latihansqlitedatabase.database.DatabaseContract.NoteColumns.Companion.TABLE_NAME
import com.candra.latihansqlitedatabase.database.DatabaseContract.NoteColumns.Companion._ID
import java.sql.SQLException

class NoteHelper(context: Context)
{
    private var databaseHelper: DatabaseHelper = DatabaseHelper(context)

    private lateinit var database: SQLiteDatabase

    companion object{
        private const val DATABASE_TABLE = TABLE_NAME

        private var INSTANCE: NoteHelper? = null

        // Single Pattern
        fun getInstance(context: Context): NoteHelper =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: NoteHelper(context)
            }
    }

    @Throws(SQLException::class)
    fun open(){
        //  Gets the data repository in write mode
        database = databaseHelper.writableDatabase
    }

    fun close(){
        databaseHelper.close()

        if (database.isOpen){
            database.close()
        }
    }

    fun queryAll(): Cursor{
        return database.query(
            DATABASE_TABLE,   // Table yang digunakan
            null,     // Kolom yang ingin ditampilkan
            null,     // Kolom yang akan kita seleksi untuk kebutuhan filter dalam klausa WHERE
            null,  // Nilai pembanding untuk klausa WHERE untuk proses seleksi
            null,     // apakah akan dilakukan pengelompokan hasil (group), null untuk tidak
            null,      // apakah dilakukan proses filter berdasarkan hasil pengelompokan, null untuk tidak
            "$_ID ASC" // Urutan hasil yang diharapkan
        )
    }

    fun queryById(id: String): Cursor{
       return database.query(
           DATABASE_TABLE,
           null,
           "$_ID = ?",
           arrayOf(id),
           null,
           null,
           null,
           null
       )
    }

    fun insert(values: ContentValues?): Long{
        return database.insert(DATABASE_TABLE,null,values)
    }

    fun update(id: String,values: ContentValues?): Int{
        return database.update(DATABASE_TABLE,values,"$_ID = ?", arrayOf(id))
    }

    fun deleteById(id: String): Int = database.delete(DATABASE_TABLE,"$_ID = '$id'",null)
}