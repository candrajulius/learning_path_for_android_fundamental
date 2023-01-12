package com.candra.latihanroomdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.candra.latihanroomdatabase.databinding.ActivityActitivityNoteAddUpdateBinding
import com.candra.latihanroomdatabase.entity.Note
import com.candra.latihanroomdatabase.helper.DataHelper
import com.candra.latihanroomdatabase.viewmodel.NoteAddUpdateViewModel
import com.candra.latihanroomdatabase.viewmodel.ViewModelFactory
import kotlin.reflect.typeOf

class ActitivityNoteAddUpdate : AppCompatActivity() {

    companion object{
        const val EXTRA_NOTE = "extra_note"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    private var isEdit = false
    private var note: Note? = null

    private lateinit var noteAddUpdateViewModel: NoteAddUpdateViewModel

    private lateinit var binding: ActivityActitivityNoteAddUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActitivityNoteAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteAddUpdateViewModel = obtainViewModel(this@ActitivityNoteAddUpdate)

        note = intent.getParcelableExtra(EXTRA_NOTE)

        if (note != null){
            isEdit = true
        }else{
            note = Note()
        }
        val actionBarTitle: String
        val btnTitle: String

        if (isEdit){
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)
            if (note != null){
                note?.let {
                    with(binding){
                        edtTitle.setText(it.title)
                        edtDescription.setText(it.description)
                    }
                }
            }
        }else{
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }

        supportActionBar?.apply {
            title = actionBarTitle
            setDisplayHomeAsUpEnabled(true)
        }

        binding.btnSubmit.text = btnTitle

        with(binding){
            btnSubmit.setOnClickListener {
                val title = edtTitle.text.toString().trim()
                val description = edtDescription.text.toString().trim()

                when{
                    title.isEmpty() -> {
                        edtTitle.error = getString(R.string.empty)
                    }
                    description.isEmpty() -> {
                        edtDescription.error = getString(R.string.empty)
                    }
                    else -> {
                        note?.let {
                            it.title = title
                            it.description = description
                        }
                        if (isEdit){
                            noteAddUpdateViewModel.update(note as Note)
                            showToast(getString(R.string.changed))
                        }else{
                            note.let { note ->
                                note?.date = DataHelper.getCuurentDate()
                            }
                            noteAddUpdateViewModel.insert(note as Note)
                            showToast(getString(R.string.added))
                        }
                        finish()
                    }
                }
            }

        }
    }

    private fun showToast(string: String) {
        Toast.makeText(this,string,Toast.LENGTH_SHORT).show()
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

    private fun showAlertDialog(alertDialogDelete: Int) {
        val isDialogClose = alertDialogDelete == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMesage: String
        if (isDialogClose){
            dialogTitle = getString(R.string.cancel)
            dialogMesage = getString(R.string.message_cancel)
        }else{
            dialogMesage = getString(R.string.message_delete)
            dialogTitle = getString(R.string.delete)
        }

        val alertDialogBuilder = AlertDialog.Builder(this)

        with(alertDialogBuilder){
            setTitle(dialogTitle)
            setMessage(dialogMesage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)){_,_ ->
                if (!isDialogClose){
                    noteAddUpdateViewModel.delete(note as Note)
                    showToast(getString(R.string.deleted))
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)){dialogMesage,_ -> dialogMesage.cancel()}
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

    }


    private fun obtainViewModel(actitivityNoteAddUpdate: ActitivityNoteAddUpdate): NoteAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(actitivityNoteAddUpdate.application)
        return ViewModelProvider(actitivityNoteAddUpdate,factory).get(NoteAddUpdateViewModel::class.java)
    }
}