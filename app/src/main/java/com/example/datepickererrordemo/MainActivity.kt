package com.example.datepickererrordemo

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateUtils
import androidx.annotation.RequiresApi
import com.example.datepickererrordemo.databinding.ActivityMainBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.time.Instant
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    private var datePicker = MaterialDatePicker.Builder.datePicker()
        .setTitleText("Select Date")
        .setSelection(System.currentTimeMillis())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()
    }


    private fun setupListeners(){

        datePicker.addOnPositiveButtonClickListener {
            //Here we just set the value we just picked from the date picker
            binding.txtSelectedDate.setText(datePicker.headerText)

            //Here we get the actual selection, which will be in Millis
            val millis = datePicker.selection ?: -1
            //And our current Millis as well
            val systemMillis = System.currentTimeMillis()

            val difference = millis - systemMillis

            //lets parse those millis, and get a relative Date from the current millis:
            val relativeDate = getRelativeDate(systemMillis, millis)

            //The actual millis picked ARE NOT FROM THE DEVICE'S TIMEZONE BUT GMT+0
            binding.txtProcessedDate.setText(relativeDate)

            //Just a little comparison:
            binding.txtCurrentSystemMillis.text = systemMillis.toString()
            binding.txtSelectedMillis.text = millis.toString()
        }

        binding.btnShowPicker.setOnClickListener {
            datePicker.show(supportFragmentManager, "DATE_PICKER")
        }

    }

    private fun getRelativeDate(
        currentMillis: Long,
        millis : Long
    ) : String =
        DateUtils.getRelativeTimeSpanString(millis,
            currentMillis, 0L,
            DateUtils.FORMAT_ABBREV_RELATIVE )
            .toString()

}