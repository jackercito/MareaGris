package com.jackercito.mareagris.ui.activities.news

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.jackercito.mareagris.R
import com.jackercito.mareagris.models.Escuadra
import com.jackercito.mareagris.models.Unidad
import com.jackercito.mareagris.ui.frament.DatePickerFragment

class EscuadraNewActivity : AppCompatActivity() {
    private lateinit var etPlannedDate : EditText
    private lateinit var etNombreEscuadra: EditText
    private lateinit var etNombreUnidad: EditText
    private lateinit var etTipo: AutoCompleteTextView
    private lateinit var etCantidad: EditText
    private lateinit var etDificultad: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_escuadra_new)

        etPlannedDate = findViewById(R.id.ti_fecha_compra)
        etPlannedDate.setOnClickListener{ showDatePickerDialog() }

        etNombreEscuadra = findViewById(R.id.ti_nombre_sec_escuadra)
        etNombreUnidad = findViewById(R.id.ti_nombre_unidad)
        etTipo = findViewById(R.id.ti_tipo_escuadra)
        etCantidad = findViewById(R.id.ti_cantidad)
        etDificultad = findViewById(R.id.ti_dificultad)

        val items = listOf(
            getString(R.string.tipo_tropa),
            getString(R.string.tipo_tanque),
            getString(R.string.tipo_volador)
        )
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        etTipo.setAdapter(adapter)

        val btnCrearEscuadra = findViewById<Button>(R.id.btn_crear_escuadra)
        btnCrearEscuadra.setOnClickListener{
            val replyIntent = Intent()

            if(TextUtils.isEmpty(etNombreUnidad.text)){
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val unidad = Unidad(etNombreUnidad.text.toString(), etTipo.text.toString(), etDificultad.text.toString().toInt(), 0.00, 0.00, 0.00)
                val escuadra = Escuadra(0, etNombreEscuadra.text.toString(), etCantidad.text.toString().toInt(), false, unidad, 0)

                replyIntent.putExtra(ESCUADRA_REPLY, escuadra)
                replyIntent.putExtra(CANTIDAD, etCantidad.text.toString())
                replyIntent.putExtra(FECHA, etPlannedDate.text.toString())

                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance { _, year, month, day ->
            // +1 because January is zero
            val cero = if (month + 1 <= 9) "0" else ""
            val selectedDate = "$day/$cero${month + 1}/$year"
            etPlannedDate = findViewById(R.id.ti_fecha_compra)
            etPlannedDate.setText(selectedDate)
        }

        newFragment.show(supportFragmentManager, "datePicker")
    }

    companion object {
        const val ESCUADRA_REPLY = "com.jackercito.mareagris.empresalistsql.REPLY"
        const val CANTIDAD = "com.jackercito.mareagris.empresalistsql.CANTIDAD"
        const val FECHA = "com.jackercito.mareagris.empresalistsql.FECHA"
    }
}