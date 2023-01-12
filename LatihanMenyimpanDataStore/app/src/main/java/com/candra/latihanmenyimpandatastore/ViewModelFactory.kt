package com.candra.latihanmenyimpandatastore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val pref: SettingPreferences
): ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
/*
   Perlu diketahui, kita tidak bisa membuat ViewModel dengan constructor secara langsung. Untuk itu, kita perlu membuat class yang extend ke ViewModelProvider untuk bisa memasukkan constructor ke dalam ViewModel. Berikut adalah kode pada ViewModelFactory:

Dengan ViewModelFactory, Anda dapat memasukkan constructor dengan cara mengirim data ke VIewModelFactory terlebih dahulu, baru setelah itu dikirimkan ke ViewModel pada fungsi create.
*/