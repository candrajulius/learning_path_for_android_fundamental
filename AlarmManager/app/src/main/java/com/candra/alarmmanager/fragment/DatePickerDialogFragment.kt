package com.candra.alarmmanager.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerDialogFragment: DialogFragment(),DatePickerDialog.OnDateSetListener {

    private var mListener: DialogDateListener? = null


    // Kode dibawah ini digunakan untuk mengkaitkan dengan activity pemanggil
    // hanya sekali di panggil dalam fragment
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as DialogDateListener?
    }

    override fun onDetach() {
        super.onDetach()
        if (mListener != null){
            mListener = null
        }
    }
    //------------------------------------------------------------------------//

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
       Calendar.getInstance().apply {
            val year = get(Calendar.YEAR)
            val month = get(Calendar.MONTH)
            val date = get(Calendar.DATE)

            return DatePickerDialog(activity as Context,this@DatePickerDialogFragment,year,month,date)
        }
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        mListener?.onDialogDateSet(tag,year,month,dayOfMonth)
    }

    interface DialogDateListener{
        fun onDialogDateSet(tag: String?,year: Int,month: Int,dayOfMonth: Int)
    }
}