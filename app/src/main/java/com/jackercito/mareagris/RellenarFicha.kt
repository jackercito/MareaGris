package com.jackercito.mareagris

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.jackercito.mareagris.ui.frament.DatePickerFragment

const val ACTIVITY2 = "RellenarFichaActivity"

class RellenarFicha : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rellenar_ficha)

        Log.d(ACTIVITY2, "activity_rellenar_ficha")

        val etPlannedDate: EditText = findViewById(R.id.fecha_edit_text)
        etPlannedDate.setOnClickListener{ showDatePickerDialog() }
    }

    private fun showDatePickerDialog() {
        Log.d(ACTIVITY2, "showDatePickerDialogBeforeOpen")

        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // +1 because January is zero
            val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
            val etPlannedDate: EditText = findViewById(R.id.fecha_edit_text)
            etPlannedDate.setText(selectedDate)
        })

        newFragment.show(supportFragmentManager, "datePicker")
    }
}