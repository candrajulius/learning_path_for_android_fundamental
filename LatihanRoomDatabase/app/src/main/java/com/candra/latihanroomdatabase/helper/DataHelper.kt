package com.candra.latihanroomdatabase.helper

import java.text.SimpleDateFormat
import java.util.*

object DataHelper {

    fun getCuurentDate(): String{
        val dateFormat = SimpleDateFormat("yyyy/MMMM/dd HH-mm-ss",Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }
}