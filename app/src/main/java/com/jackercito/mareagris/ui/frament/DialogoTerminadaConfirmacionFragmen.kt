package com.jackercito.mareagris.ui.frament

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.jackercito.mareagris.R


class DialogoTerminadaConfirmacionFragmen : DialogFragment() {
    private lateinit var etPlannedDate : EditText
    private lateinit var etTiempoTransc : EditText

    private lateinit var listener: DialogoTerminadaConfirmacionListener

    interface DialogoTerminadaConfirmacionListener {
        fun onDialogPositiveClick(fecha: Int, tiempo: Int)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = context as DialogoTerminadaConfirmacionListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;
            val view: View = inflater.inflate(R.layout.fragment_dialogo_terminada_confirmacion, null)

            etPlannedDate = view.findViewById(R.id.ti_FechaFinalizado)
            etTiempoTransc = view.findViewById(R.id.ti_TiempoInvertido)

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.aceptar,
                    DialogInterface.OnClickListener { _, _ ->
                        listener.onDialogPositiveClick(etPlannedDate.text.toString().toInt(), etTiempoTransc.text.toString().toInt())
                    })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { _, _ ->
                        listener.onDialogNegativeClick(this)
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    /*private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance { _, year, month, day ->
            // +1 because January is zero
            val cero = if (month + 1 <= 9) "0" else ""
            val selectedDate = "$day/$cero${month + 1}/$year"
            etPlannedDate = findViewById(R.id.ti_FechaFinalizado)
            etPlannedDate.setText(selectedDate)
        }

        newFragment.show(supportFragmentManager, "datePicker")
    }*/
}