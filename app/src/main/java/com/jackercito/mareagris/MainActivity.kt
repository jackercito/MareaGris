package com.jackercito.mareagris

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jackercito.mareagris.models.Empresa
import com.jackercito.mareagris.models.Escuadra
import com.jackercito.mareagris.models.Unidad
import com.jackercito.mareagris.ui.adapter.EmpresaListAdapter
import java.util.*

const val ACTIVITY = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var unidad = Unidad("nombre", "tipo1", 5)
        var escuadra = Escuadra("Escuadra1", 5, unidad, Date(System.currentTimeMillis()))
        var empresa = Empresa(1, "GWS")

        Log.d(ACTIVITY, escuadra.toString())
        Log.d(ACTIVITY, escuadra.unidad.toString())

        /*val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-marea-gris"
        ).build()*/

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = EmpresaListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //db.empresaDao().insertAll(empresa)

        //val listaEmpresas = db.empresaDao().getAll()
        //Log.d(ACTIVITY, listaEmpresas.toString())

        /*val btnCambiarActivity: Button = findViewById(R.id.button)
        btnCambiarActivity.setOnClickListener {
            val intent = Intent(this, RellenarFicha::class.java).apply { }
            startActivity(intent)
        }*/
    }

}