package com.candra.latihansqlitedatabase

import android.app.Instrumentation
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.candra.latihansqlitedatabase.NoteAddUpadteActivity.Companion.EXTRA_NOTE
import com.candra.latihansqlitedatabase.NoteAddUpadteActivity.Companion.EXTRA_POSITION
import com.candra.latihansqlitedatabase.NoteAddUpadteActivity.Companion.RESULT_UPDATE
import com.candra.latihansqlitedatabase.adapter.NoteAdapter
import com.candra.latihansqlitedatabase.database.NoteHelper
import com.candra.latihansqlitedatabase.databinding.ActivityMainBinding
import com.candra.latihansqlitedatabase.entity.Note
import com.candra.latihansqlitedatabase.helper.MappingHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainAdapter: NoteAdapter

    companion object{
        const val EXTRA_STATE = "extra_state"
    }

    val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result ->
        if (result.data != null){
            // Akan dipanggil jika request codenya ADD
            when(result.resultCode){
                NoteAddUpadteActivity.RESULT_ADD -> {
                    val note = result.data?.getParcelableExtra<Note>(EXTRA_NOTE)
                    note?.let { mainAdapter.addItem(it) }
                    binding.rvNotes.smoothScrollToPosition(mainAdapter.itemCount - 1)
                    showSnacbarMessage("Satu item berhasil ditambahkan")
                }

                NoteAddUpadteActivity.RESULT_UPDATE -> {
                    val note = result.data?.getParcelableExtra<Note>(EXTRA_NOTE) as Note
                    val position = result?.data?.getIntExtra(NoteAddUpadteActivity.EXTRA_POSITION,0) as Int
                    mainAdapter.updateItem(position,note)
                    binding.rvNotes.smoothScrollToPosition(position)
                    showSnacbarMessage("Satu item berhasil diubah")
                }
                NoteAddUpadteActivity.RESULT_DELETE -> {
                    val position = result?.data?.getIntExtra(EXTRA_POSITION,0) as Int
                    mainAdapter.removeItem(position)
                    showSnacbarMessage("Satu item berhasil dihapus")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar("Notes")

        mainAdapter = NoteAdapter(object: NoteAdapter.OnItemClickCallback{
            override fun onItemClicked(selectedNote: Note?, position: Int?) {
                val intent = Intent(this@MainActivity,NoteAddUpadteActivity::class.java).apply {
                    putExtra(EXTRA_NOTE,selectedNote)
                    putExtra(EXTRA_POSITION,position)
                }
                resultLauncher.launch(intent)
            }
        })

        with(binding){
            rvNotes.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                setHasFixedSize(true)
                adapter = mainAdapter
            }
            fabAdd.setOnClickListener {
                resultLauncher.launch(Intent(this@MainActivity,NoteAddUpadteActivity::class.java))
            }
        }

        if (savedInstanceState == null){
            // proses ambil data
            loadNoteAsync()
        }else{
            val list = savedInstanceState.getParcelableArrayList<Note>(EXTRA_STATE)
            list?.let { mainAdapter.listNotes = it }
        }

        loadNoteAsync()

    }

    private fun setToolbar(name: String){
        supportActionBar?.apply {
            title = resources.getString(R.string.name_developer)
            subtitle = name
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE,mainAdapter.listNotes)
    }

    private fun loadNoteAsync() {
        lifecycleScope.launch(Dispatchers.Main) {
            binding.progressbar.visibility = View.VISIBLE
            val noteHelper = NoteHelper.getInstance(applicationContext)
            noteHelper.open()

            // Disini digunakan untuk membuat sebuah variabel
            // yang dimana si async ini akan berjalan di balik layar
            //
            val defferdnotes = async(Dispatchers.IO){
                val cursor = noteHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }

            binding.progressbar.visibility = View.INVISIBLE

            // Code dibawah ini digunakan untuk mendapatkan sebuah kembalian dari
            // cursor yang telah dibuat di background thread
            val note = defferdnotes.await()

            if (note.size > 0){
                mainAdapter.listNotes = note
            }else{
                mainAdapter.listNotes = ArrayList()
                showSnacbarMessage("Tidak ada data saat ini")
            }
            noteHelper.close()
        }
    }

    private fun showSnacbarMessage(message: String){
        Snackbar.make(binding.rvNotes,message,Snackbar.LENGTH_SHORT).show()
    }

}