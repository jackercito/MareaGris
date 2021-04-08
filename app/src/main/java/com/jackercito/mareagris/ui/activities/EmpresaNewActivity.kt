package com.jackercito.mareagris.ui.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jackercito.mareagris.R
import com.jackercito.mareagris.models.Empresa

class EmpresaNewActivity : AppCompatActivity() {
    private lateinit var editEmpresaView: EditText
    private lateinit var imageEmpresaView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empresas_new)

        editEmpresaView = findViewById(R.id.edt_nueva_empresa)
        imageEmpresaView = findViewById(R.id.IVPreviewImage)

        val btnSelectImg = findViewById<Button>(R.id.btn_selec_img)
        btnSelectImg.setOnClickListener{
            val imageIntent = Intent()

            imageIntent.type = "image/*"
            imageIntent.action = Intent.ACTION_GET_CONTENT

            startActivityForResult(Intent.createChooser(imageIntent, "Seleccionar Imagen"), SELECT_PICTURE)
        }

        val btnCrearEmpresa = findViewById<Button>(R.id.btn_crear_empresa)
        btnCrearEmpresa.setOnClickListener {
            val replyIntent = Intent()

            if (TextUtils.isEmpty(editEmpresaView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val nombreEmpresa = editEmpresaView.text.toString()
                val imageEmpresa = editEmpresaView.text.toString()
                val empresa : Empresa = Empresa(0, editEmpresaView.text.toString(), "imageEmpresaView")
                replyIntent.putExtra(EXTRA_REPLY, empresa)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == SELECT_PICTURE && resultCode == Activity.RESULT_OK){
            val selectImage : Uri? = data?.data;
            if(selectImage != null)
                imageEmpresaView.setImageURI(selectImage)
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.jackercito.mareagris.empresalistsql.REPLY"
        const val SELECT_PICTURE = 200
    }
}