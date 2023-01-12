package com.candra.alarmmanager

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.candra.alarmmanager.databinding.ActivityMainBinding
import com.candra.alarmmanager.fragment.DatePickerDialogFragment
import com.candra.alarmmanager.fragment.TimePickerFragment
import com.candra.alarmmanager.service.AlarmService
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(),View.OnClickListener,
        DatePickerDialogFragment.DialogDateListener,TimePickerFragment.DialogTimeListener
{


    private var binding: ActivityMainBinding? = null
    private val  alarmReceiver by lazy { AlarmService() }

    companion object{
        private const val DATE_PICKER_TAG = "DatePicker"
        private const val TIME_PICKER_ONCE_TAG = "TimePickerOnce"
        private const val TIME_PICKER_REPEAT_TAG = "TimePickerRepeat"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Listener one time alarm
        binding?.let {
            it.btnOnceDate.setOnClickListener(this)
            it.btnOnceTime.setOnClickListener(this)
            it.btnSetOnceAlarm.setOnClickListener(this)
            it.btnRepeatingTime.setOnClickListener(this)
            it.btnSetRepeatingAlarm.setOnClickListener(this)
            it.btnCancelRepeatingAlarm.setOnClickListener(this)
        }
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btn_once_date -> setOnDate()

            R.id.btn_once_time -> setOnTime()

            R.id.btn_set_once_alarm -> setOnceTimeAlarm()

            R.id.btn_repeating_time -> repeatingAlarm()

            R.id.btn_set_repeating_alarm -> setRepeatingAlarm()

            R.id.btn_cancel_repeating_alarm -> setButtonCancelAlarm()
        }
    }

    private fun setButtonCancelAlarm(){
        alarmReceiver.cancelAlarm(this,AlarmService.TYPE_REPEATING)
    }

    private fun repeatingAlarm(){
        val timePickerFragmentRepeat = TimePickerFragment()
        timePickerFragmentRepeat.show(supportFragmentManager, TIME_PICKER_REPEAT_TAG)
    }

    private fun setRepeatingAlarm(){
        binding?.let {
            val repeatTime =  it.tvRepeatingTime.text.toString()
            val repeatMessage = it.edtRepeatingMessage.text.toString()
            alarmReceiver.setRepeatingAlarm(this,AlarmService.TYPE_REPEATING,repeatTime,repeatMessage)
        }
    }

    private fun setOnDate(){
        val datePickerDialogFragment = DatePickerDialogFragment()
        datePickerDialogFragment.show(supportFragmentManager,DATE_PICKER_TAG)
    }

    private fun setOnTime(){
        val timePickerFragmentOne = TimePickerFragment()
        timePickerFragmentOne.show(supportFragmentManager, TIME_PICKER_ONCE_TAG)
    }

    private fun setOnceTimeAlarm(){
        binding?.let {
            val onceDate = it.tvOnceDate.text.toString()
            val onceTime = it.tvOnceTime.text.toString()
            val onceMessage = it.edtOnceMessage.text.toString()


            alarmReceiver.setOnTimeAlarm(this, AlarmService.TYPE_ONE_TIME
            ,onceDate,onceTime,onceMessage)
        }
    }




    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        // Siapkan date formatternya terlebih dahulu
        val calendr = Calendar.getInstance().apply {
            set(year,month,dayOfMonth)
        }
        val dateFormat = SimpleDateFormat("yyyy-MM-dd",Locale.getDefault())
        binding?.tvOnceDate?.text = dateFormat.format(calendr.time)

    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY,hourOfDay)
            set(Calendar.MINUTE,minute)
        }
        val dateFormat = SimpleDateFormat("HH:mm:ss",Locale.getDefault())

        // Set text dari textview berdasarkan tag
        when(tag){
            TIME_PICKER_ONCE_TAG -> binding?.tvOnceTime?.text = dateFormat.format(calendar.time)
            TIME_PICKER_REPEAT_TAG -> binding?.tvRepeatingTime?.text = dateFormat.format(calendar.time)
            else -> {
                
            }
        }
    }

}