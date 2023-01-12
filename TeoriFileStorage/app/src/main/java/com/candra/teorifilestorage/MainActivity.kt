package com.candra.teorifilestorage

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import java.io.File

class MainActivity : AppCompatActivity() {

    companion object{
        const val CREATE_FILE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSaveInternal()
    }

    // Menyimpan data secara internal
    private fun setSaveInternal(){
        val fileName = "myFile"
        val fileContents = "Hello World"
        openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(fileContents.toByteArray())
        }
    }

    // Menyimpan data dalam cache
    private fun getTempFile(context: Context,url: String): File{
        val file: File
        try{
            val fileName = Uri.parse(url).lastPathSegment
            file = File.createTempFile(fileName,null,context.cacheDir)
        }catch (e: Exception){
            // Error while creating file
        }
        return file
    }

    /*
    Meyimpan Data di Shared Storage dengan MediaStore
     */
    private fun setSaveToShareStorageWithMediaStore(){

        // Mendapatkan Content Resolver
        val resolver = applicationContext.contentResolver

        // Mencari semua file audio di shared storage
        val photoCollection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        }else{
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        }

        // Membuat ContentValue untuk menambahkan audio baru.
        val newPhotoDetails = ContentValues().apply {
            put(MediaStore.Audio.Media.DISPLAY_NAME,"My Photo.mp3")
        }

        // Eksekusi menambahkan audio baru ke shared storage
        val myPhotoUri = resolver.insert(photoCollection,newPhotoDetails)

    }

    /*
    Menyimpan Data di Shared Storage dengan Storage Access Framework(SAF)
    MediaStore hanya dapat menyimpan dan membaca file berupa media saja. Jika Anda ingin menyimpan tipe file lain, gunakanlah Storage Access Framework (SAF). Mekanisme ini tidak membutuhkan permission apa pun karena sifatnya seperti picker, di mana user terlibat dalam memilih file atau direktori yang akan dimodifikasi.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setDataSharedStorageWithStorageAccessFramework(pickerInitialUri: Uri)
    {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
           addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"
            putExtra(Intent.EXTRA_TITLE,"invoice.pdf")
            putExtra(DocumentsContract.EXTRA_INITIAL_URI,pickerInitialUri)
        }
        startActivityForResult(intent, CREATE_FILE)
    }
}