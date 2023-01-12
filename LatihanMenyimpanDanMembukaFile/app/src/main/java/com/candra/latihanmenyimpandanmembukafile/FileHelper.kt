package com.candra.latihanmenyimpandanmembukafile

import android.content.Context
import java.io.File

internal object FileHelper
{
    /*
    Logika sederhana yang dilakukan oleh metode di atas adalah menyimpan data yang bertipekan string ke dalam sebuah berkas pada internal storage. Dengan menggunakan komponen FileOutputStream, Anda dapat menulis data ke dalam berkas menggunakan stream.


     Pada proses inisiasi FileOutputStream, Anda menggunakan metode openFileOutput() untuk membuka berkas sesuai dengan namanya. Jika berkas belum ada, maka berkas tersebut akan secara otomatis dibuatkan. Untuk menggunakan method openFileOutput() Anda harus mengetahui context aplikasi yang menggunakannya. Oleh karena itu, dalam metode ini Anda memberikan inputan parameter context. Setelah berkas dibuka, Anda dapat menulis data menggunakan metode write(data).
     */
    fun writeToFile(fileModel: FileModel,context: Context){
        context.openFileOutput(fileModel.fileName,Context.MODE_PRIVATE).use {
            it.write(fileModel.data?.toByteArray())
        }
    }

   fun readFromFile(context: Context,fileName: String): FileModel{
       val fileModel = FileModel()
       fileModel.fileName = fileName
       fileModel.data = context.openFileInput(fileName).bufferedReader().useLines { it ->
           it.fold(""){ some,text ->
               "$some\n$text"
           }
       }
       return fileModel
   }
}