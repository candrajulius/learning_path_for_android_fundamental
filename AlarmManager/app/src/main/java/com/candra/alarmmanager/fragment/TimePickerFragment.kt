package com.candra.alarmmanager.fragment

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.ReceiverCallNotAllowedException
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment: DialogFragment(),TimePickerDialog.OnTimeSetListener {

    private var mListener: DialogTimeListener? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as DialogTimeListener?
    }

    override fun onDetach() {
        super.onDetach()
        if (mListener != null){
            mListener = null
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Calendar.getInstance().apply {
            val hour = get(Calendar.HOUR_OF_DAY)
            val minute = get(Calendar.MINUTE)
            val formatHour24 = true

            return TimePickerDialog(activity,this@TimePickerFragment,hour,minute,formatHour24)
        }
    }

    // Digunakan untuk memanggil jam dan minute pada dialog datetime
    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        mListener?.onDialogTimeSet(tag,hourOfDay = hourOfDay,minute = minute )
    }

    interface DialogTimeListener{
        fun onDialogTimeSet(tag: String?,hourOfDay: Int,minute: Int)
    }
}