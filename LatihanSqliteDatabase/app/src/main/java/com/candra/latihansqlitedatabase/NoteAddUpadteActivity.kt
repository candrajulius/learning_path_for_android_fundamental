package com.candra.latihansqlitedatabase

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.candra.latihansqlitedatabase.database.DatabaseContract
import com.candra.latihansqlitedatabase.database.DatabaseContract.NoteColumns.Companion.DATE
import com.candra.latihansqlitedatabase.database.NoteHelper
import com.candra.latihansqlitedatabase.databinding.ActivityNoteAddUpadteBinding
import com.candra.latihansqlitedatabase.entity.Note
import java.text.SimpleDateFormat
import java.util.*

class NoteAddUpadteActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityNoteAddUpadteBinding
    private var isEdit = false
    private var note: Note? = null
    private var position: Int = 0
    private lateinit var noteHelper: NoteHelper

    companion object{
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_POSITION = "extra_position"
        const val RESULT_ADD = 101
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteAddUpadteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteHelper = NoteHelper.getInstance(applicationContext)
        noteHelper.open()

        note = intent.getParcelableExtra(EXTRA_NOTE)
        if (note != null){
            position = intent.getIntExtra(EXTRA_POSITION,0)
            isEdit = true
        }else{
            note = Note()
        }

        binding.btnSubmit.setOnClickListener(this)

        val actionBarTitle: String
        val btnTitle: String

        with(binding){
            if(isEdit){
                actionBarTitle = "Ubah"
                btnTitle = "Update"


                note?.let {
                    edtTitle.setText(it.title)
                    edtDescription.setText(it.description)
                }

            }else{
                actionBarTitle = "Tambah"
                btnTitle = "Simpan"
            }
            setToolbar(actionBarTitle)
            btnSubmit.text = btnTitle
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        if (isEdit){
            menuInflater.inflate(R.menu.menu,menu)
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setToolbar(theme: String){
        supportActionBar?.title = resources.getString(R.string.name_developer)
        supportActionBar?.subtitle = theme
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onClick(itemView: View?) {
        when (itemView?.id) {
            R.id.btn_submit -> {
                with(binding){
                    val title = edtTitle.text.toString().trim()
                    val description = edtDescription.text.toString().trim()
                    if (title.isEmpty()){
                        edtTitle.error = "Field can not be blank"
                        return
                    }
                    note?.title = title
                    note?.description = description

                    val intent = Intent()
                    intent.apply {
                        putExtra(EXTRA_NOTE,note)
                        putExtra(EXTRA_POSITION,position)
                    }

                    // Digunakan untuk membungkus data pada ContentValues
                    val values = ContentValues()
                    //// Create a new map of values, where column names are the keys
                    values.put(DatabaseContract.NoteColumns.TITLE,title)
                    values.put(DatabaseContract.NoteColumns.DESCRIPTION,description)
                    note?.date = getCurrentDate()
                    values.put(DATE,getCurrentDate())

                    if (isEdit){
                        // mengupdate data dari ContentValues kedalam sqlite databaes menggunakan query update
                        val result = noteHelper.update(note?.id.toString(),values)

                        if(result > 0){
                            setResult(RESULT_UPDATE,intent)
                            finish()
                        }else{
                            Toast.makeText(this@NoteAddUpadteActivity,"Gagal mengupdate data",Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        note?.date = getCurrentDate()
                        values.put(DATE,getCurrentDate())
                        // memasukkan data dari ContentValues kedalam sqlite database menggunakan insert
                        val result = noteHelper.insert(values)

                        if (result > 0){
                            note?.id = result.toInt()
                            setResult(RESULT_ADD,intent)
                            finish()
                        }else{
                            Toast.makeText(this@NoteAddUpadteActivity,"Gagal menambah data",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }


    private fun getCurrentDate(): String{
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss",Locale.getDefault())
        val date = Date()

        return dateFormat.format(date)
    }

    private fun showAlertDialog(type: Int){
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String

        if (isDialogClose){
            dialogTitle = "Batal"
            dialogMessage = "Apakah anda ingin membatalkan perubahan pada form?"
        }else{
            dialogMessage = "Apakah anda yakin ingin menghapus item ini?"
            dialogTitle = "Hapus Note"
        }

        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(dialogTitle)
        alertDialog.setMessage(dialogMessage)
            .setCancelable(false)
            .setPositiveButton("Ya"){_,_ ->
                if (isDialogClose){
                    finish()
                }else{
                    val result = noteHelper.deleteById(note?.id.toString()).toLong()
                    if (result > 0){
                        Intent().apply {
                            putExtra(EXTRA_POSITION,position)
                            setResult(RESULT_DELETE,this)
                            finish()
                        }
                    }else{
                        Toast.makeText(this@NoteAddUpadteActivity,"Gagal menghapus data",Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Tidak"){dialog,_ -> dialog.cancel()}
        val alertDialogCreate = alertDialog.create()
        alertDialogCreate.show()
    }
}