package com.candra.mysharedprefences

import android.content.Context

internal class UserPreference(
    context: Context
)
{
    // membuat key yang digunakan dalam sharedprefence
    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val AGE = "age"
        private const val PHONE_NUMBER = "phone"
        private const val LOVE_MU = "islove"
    }
    // Menginisiasi share prefence dengan key yang telah dibuat.. Ada dua parameter yang harus diisi
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    // Untuk menset data yang ingin digunakan
    fun setUser(value: UserModel) {
        // Memnbuat editor sebagai rekaman simpan data
        val editor = preferences.edit()
        /*
        Code dibawah digunakan untuk meletakkan nilai serta kata kunci dari nilai tersebut
         */
        editor.putString(NAME, value.name)
        editor.putString(EMAIL, value.email)
        editor.putInt(AGE, value.age)
        editor.putString(PHONE_NUMBER, value.phoneNumber)
        editor.putBoolean(LOVE_MU, value.isLove)
        editor.apply()
        //-------------------------------------
    }

    // Untuk mengambil data yang ingin digunakan dengan mengembalikan sebuah niali
    // dari kelas data yang digunakan
    fun getUser(): UserModel {
        // Menginisiasi userModel
        val model = UserModel()
        /*
        mengambil semua data yang telah kita set tadi
         */
        model.name = preferences.getString(NAME, "")
        model.email = preferences.getString(EMAIL, "")
        model.age = preferences.getInt(AGE, 0)
        model.phoneNumber = preferences.getString(PHONE_NUMBER, "")
        model.isLove = preferences.getBoolean(LOVE_MU, false)
        //----------------------------------------------------//

        // Mengemabalikan sebuah nilai dari data tersebut
        return model
    }
}