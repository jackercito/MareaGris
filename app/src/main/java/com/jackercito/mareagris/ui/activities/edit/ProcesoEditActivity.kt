package com.jackercito.mareagris.ui.activities.edit

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.jackercito.mareagris.R

class ProcesoEditActivity : AppCompatActivity() {
    private lateinit var editCantidadView: EditText
    private lateinit var editTiempoView: EditText
    private lateinit var imageProcesoView: ImageView
    private lateinit var uriPass: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proceso_edit)
    }
}