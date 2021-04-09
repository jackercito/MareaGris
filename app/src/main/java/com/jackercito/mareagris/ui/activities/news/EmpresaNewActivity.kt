package com.jackercito.mareagris.ui.activities.news

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jackercito.mareagris.R
import com.jackercito.mareagris.models.Empresa
import java.io.*
import java.util.*

const val LOG_ACTIVITY = "EmpresaNewActivity"

class EmpresaNewActivity : AppCompatActivity() {
    private lateinit var editEmpresaView: EditText
    private lateinit var imageEmpresaView: ImageView
    private lateinit var uriPass: String

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
                val empresa : Empresa = Empresa(0, editEmpresaView.text.toString(), uriPass)
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
            if(selectImage != null){
                //val uri:Uri = saveImageToInternalStorage(selectImage)
                //imageEmpresaView.setImageURI(uri)

                val imageStream: InputStream? = contentResolver.openInputStream(selectImage);
                val selectedImage: Bitmap = BitmapFactory.decodeStream(imageStream);
                val newUri : Uri? = saveImageToInternalStorage(selectedImage)

                imageEmpresaView.setImageURI(newUri)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    // Method to save an image to internal storage
    private fun saveImageToInternalStorage(bitmap:Bitmap):Uri{
        // Get the image from drawable resource as drawable object
        //val drawable = ContextCompat.getDrawable(applicationContext,drawableId)
        // Get the bitmap from drawable object
        //val bitmap = (drawable as BitmapDrawable).bitmap
        // Get the context wrapper instance
        val wrapper = ContextWrapper(applicationContext)
        // Initializing a new file
        // The bellow line return a directory in internal storage
        var file = wrapper.getDir("images", Context.MODE_PRIVATE)

        // Create a file to save the image
        file = File(file, "${UUID.randomUUID()}.jpg")
        try {
            // Get the file output stream
            val stream: OutputStream = FileOutputStream(file)
            // Compress bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            // Flush the stream
            stream.flush()
            // Close stream
            stream.close()
        } catch (e: IOException){ // Catch the exception
            Log.e(LOG_ACTIVITY, e.toString())
            e.printStackTrace()
        }

        // Return the saved image uri
        uriPass = file.absolutePath
        return Uri.parse(file.absolutePath)
    }

    companion object {
        const val EXTRA_REPLY = "com.jackercito.mareagris.empresalistsql.REPLY"
        const val SELECT_PICTURE = 200
    }
}