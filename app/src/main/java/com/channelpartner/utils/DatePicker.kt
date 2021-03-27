package com.channelpartner.utils

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import java.util.*


class MyEditTextDatePicker(context: Context, editTextViewID: Int) :
    View.OnClickListener, OnDateSetListener {
    var _editText: EditText
    private var _day = 0
    private var _month = 0
    private var _birthYear = 0
    private val _context: Context
    override fun onDateSet(
        view: DatePicker,
        year: Int,
        monthOfYear: Int,
        dayOfMonth: Int
    ) {
        _birthYear = year
        _month = monthOfYear
        _day = dayOfMonth
        updateDisplay()
    }

    override fun onClick(v: View?) {
        val calendar: Calendar = Calendar.getInstance()
        val dialog = DatePickerDialog(
            _context, this,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH)+1,
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dialog.getDatePicker().setMaxDate(Date().time);
        dialog.show()
    }

    // updates the date in the birth date EditText
    private fun updateDisplay() {
        _editText.setText(
            StringBuilder() // Month is 0 based so add 1
                .append(_month).append("-").append(_day).append("-").append(_birthYear)
                .append(" ")
        )
    }

    init {
        val act = context as Activity
        _editText = act.findViewById<View>(editTextViewID) as EditText
        _editText.setOnClickListener(this)
        _context = context
    }
}