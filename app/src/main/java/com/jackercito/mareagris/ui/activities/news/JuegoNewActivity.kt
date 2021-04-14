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
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.jackercito.mareagris.R
import com.jackercito.mareagris.models.Juego
import java.io.*
import java.util.*

class JuegoNewActivity : AppCompatActivity() {
    private lateinit var edtJuegoView: EditText
    private lateinit var imageJuegoView: ImageView
    private lateinit var uriPass: String

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK){
            val data: Intent? = result.data
            val selectImage : Uri? = data?.data;
            if(selectImage != null){
                val imageStream: InputStream? = contentResolver.openInputStream(selectImage);
                val selectedImage: Bitmap = BitmapFactory.decodeStream(imageStream);
                val newUri : Uri? = saveImageToInternalStorage(selectedImage)

                imageJuegoView.setImageURI(newUri)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego_new)

        edtJuegoView = findViewById(R.id.edt_nuevo_juego)
        imageJuegoView = findViewById(R.id.IVPreviewImage)

        val btnSelectImage  = findViewById<Button>(R.id.btn_selec_img)
        btnSelectImage.setOnClickListener{
            val imageIntent = Intent()

            imageIntent.type = "image/*"
            imageIntent.action = Intent.ACTION_GET_CONTENT

            resultLauncher.launch(Intent.createChooser(imageIntent, "Seleccionar una imagen"))
        }

        val btnCreateJuego = findViewById<Button>(R.id.btn_crear_juego)
        btnCreateJuego.setOnClickListener{
            val replyIntent = Intent()

            if(TextUtils.isEmpty(edtJuegoView.text)){
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val juego: Juego = Juego(0, edtJuegoView.text.toString(), uriPass, 0)
                replyIntent.putExtra(EXTRA_REPLY, juego)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    //Para ver más sobre esta funcion ir a EmpresaNewActivity
    private fun saveImageToInternalStorage(bitmap: Bitmap): Uri{
        val wrapper = ContextWrapper(applicationContext)
        var file = wrapper.getDir("images", Context.MODE_PRIVATE)

        file = File(file, "${UUID.randomUUID()}.jpg")
        try{
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException){
            e.printStackTrace()
        }

        uriPass = file.absolutePath
        return Uri.parse(file.absolutePath)
    }

    companion object {
        const val EXTRA_REPLY = "com.jackercito.mareagris.empresalistsql.REPLY"
        const val SELECT_PICTURE = 200
    }
}