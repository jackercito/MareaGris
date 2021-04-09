package com.jackercito.mareagris

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.jackercito.mareagris.ui.activities.list.EmpresasListActivity
import com.jackercito.mareagris.viewmodels.EmpresaViewModel
import com.jackercito.mareagris.viewmodels.EmpresaViewModelFactory
import java.util.*

const val ACTIVITY = "MainActivity"

class MainActivity : AppCompatActivity() {
    private val nuevaEmpresaActivityRequestCode = 1
    private val empresaViewModel: EmpresaViewModel by viewModels {
        EmpresaViewModelFactory((application as MareaGrisApplication).repository)
    }
    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //var unidad = Unidad("nombre", "tipo1", 5)
        //var escuadra = Escuadra("Escuadra1", 5, unidad, Date(System.currentTimeMillis()))
        //var empresa = Empresa(1, "GWS")

        //setupActionBar();

        val btn : Button = findViewById(R.id.button)
        btn.setOnClickListener{
            val intent = Intent(this@MainActivity, EmpresasListActivity::class.java)
            startActivity(intent)
        }
    }

    // this event will enable the back
    // function to the button on press
    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
}