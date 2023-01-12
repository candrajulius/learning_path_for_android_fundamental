package com.candra.latihanmenyimpandanmembukafile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.candra.latihanmenyimpandanmembukafile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            buttonNew.setOnClickListener(this@MainActivity)
            buttonOpen.setOnClickListener(this@MainActivity)
            buttonSave.setOnClickListener(this@MainActivity)
        }
    }

    override fun onClick(p0: View?) {
      when(p0?.id){
          R.id.button_new -> newFile()
          R.id.button_open -> showList()
          R.id.button_save -> saveFile()
      }
    }

    private fun saveFile() {
        with(binding){
            when{
                editTitle.text.toString().isEmpty() -> Toast.makeText(this@MainActivity, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show()
                editFile.text.toString().isEmpty() -> Toast.makeText(this@MainActivity, "Kontent harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show()
                else -> {
                    val title = editTitle.text.toString()
                    val text = editFile.text.toString()
                    val fileModel = FileModel()
                    fileModel.also {
                        it.fileName = title
                        it.data = text
                        FileHelper.writeToFile(it,this@MainActivity)
                        Toast.makeText(this@MainActivity,"Saving ${it.fileName} file",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun showList() {
        // Memperoleh semua nama berkas yang ada
        val items = fileList()
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle("Pilih file yang diinginkan")
            setItems(items){ dialog,item -> loadData(items[item].toString())}
            val alert = create()
            alert.show()
        }
    }

    private fun loadData(title: String) {
        val fileModel = FileHelper.readFromFile(this,title)
        with(binding){
            fileModel.also {
                editTitle.setText(it.fileName)
                editFile.setText(it.data)
                Toast.makeText(this@MainActivity,"Loading ${it.fileName} data",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun newFile() {
        with(binding){
            editFile.setText("")
            editTitle.setText("")
            Toast.makeText(this@MainActivity,"Clearing file",Toast.LENGTH_SHORT).show()
        }
    }
}