package com.candra.latihaninjectionandrepository.helper

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateFormatter
{
    fun formatDate(currentDate: String): String? {
        val currentDate = "yyyy-MM-dd'T'hh:mm:ss'Z'"
        val targetFormat = "dd MM yyyy | HH:mm"
        val timezone = "GMT"
        val currentDiff: DateFormat = SimpleDateFormat(currentDate,Locale.getDefault())
        currentDiff.timeZone = TimeZone.getTimeZone(timezone)
        val targetDif: DateFormat = SimpleDateFormat(targetFormat,Locale.getDefault())
        var targetDate: String? = null
        try {
            val date = currentDiff.parse(currentDate)
            date?.let { targetDate = targetDif.format(it) }
        }catch (ex: ParseException){
            ex.printStackTrace()
        }
        return targetDate
    }
}