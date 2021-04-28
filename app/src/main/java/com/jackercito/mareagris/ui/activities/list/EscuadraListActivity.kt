package com.jackercito.mareagris.ui.activities.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jackercito.mareagris.MareaGrisApplication
import com.jackercito.mareagris.R
import com.jackercito.mareagris.models.Escuadra
import com.jackercito.mareagris.models.Proceso
import com.jackercito.mareagris.models.REscuadraProceso
import com.jackercito.mareagris.ui.activities.news.EscuadraNewActivity
import com.jackercito.mareagris.ui.adapter.EscuadraListAdapter
import com.jackercito.mareagris.viewmodels.EscuadraViewModel
import com.jackercito.mareagris.viewmodels.EscuadraViewModelFactory
import com.jackercito.mareagris.viewmodels.ProcesoViewModel
import com.jackercito.mareagris.viewmodels.ProcesoViewModelFactory
import java.text.SimpleDateFormat
import kotlin.properties.Delegates

const val ESCUADRA_ID = "escuadra id"

class EscuadraListActivity : AppCompatActivity() {
    private lateinit var listaEscuadras : List<REscuadraProceso>
    private var currentFaccionId by Delegates.notNull<Long>()
    private val escuadraViewModel: EscuadraViewModel by viewModels{
        EscuadraViewModelFactory((application as MareaGrisApplication).repositoryEscuadra)
    }

    private val procesoViewModel : ProcesoViewModel by viewModels{
        ProcesoViewModelFactory((application as MareaGrisApplication).repositoryProceso)
    }

    private fun crearEscuadra(elemento: Escuadra, fecha: String, cantidad: Int, nombreEscuadra: String){
        escuadraViewModel.insertEscuadra(elemento).observe(this){ uid ->
            crearProcesos(fecha,uid, cantidad, nombreEscuadra)
        }
    }

    private fun editarEscuadra(escuadra: Escuadra, cantidad: Int){
        escuadra.cantidad = cantidad
        escuadraViewModel.updateEscuadra(escuadra)
    }

    private fun crearProcesos(fecha: String, uid: Long, cantidad: Int, nombreEscuadra: String) {
        val date = SimpleDateFormat("dd/MM/yyyy").parse(fecha)

        for(i in 1..cantidad){
            val proceso = Proceso(
                0, date, null, null, null, 0,
                0, 0.00, 0.00, false,
                "Comprado", "", "", "", nombreEscuadra, uid
            )
            procesoViewModel.insertProceso(proceso)
        }
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK){
            val data: Intent? = result.data

            data?.getSerializableExtra(EscuadraNewActivity.ESCUADRA_REPLY)?.let { escuadra ->
                (escuadra as Escuadra).idFkFaccion = currentFaccionId

                val find = listaEscuadras.find { it.escuadra.unidad.nombre == escuadra.unidad.nombre}
                if(find != null){
                    crearProcesos(data.getStringExtra(EscuadraNewActivity.FECHA)!!, find.escuadra.uid , data.getStringExtra(EscuadraNewActivity.CANTIDAD)!!.toInt(), data.getStringExtra(EscuadraNewActivity.ESCUADRA)!!)
                    editarEscuadra(find.escuadra, find.escuadra.cantidad + data.getStringExtra(EscuadraNewActivity.CANTIDAD)!!.toInt())
                } else {
                    crearEscuadra(escuadra, data.getStringExtra(EscuadraNewActivity.FECHA)!!, data.getStringExtra(EscuadraNewActivity.CANTIDAD)!!.toInt(), data.getStringExtra(EscuadraNewActivity.ESCUADRA)!!)
                }
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_image_not_select,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_escuadra_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerviewEscuadras)
        val adapter = EscuadraListAdapter(this) { escuadra -> adapterOnClick(escuadra) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val bundle: Bundle? = intent.extras
        if(bundle != null){
            currentFaccionId = bundle.getLong(FACCION_ID)
        }

        val btnFab = findViewById<FloatingActionButton>(R.id.fab)
        btnFab.setOnClickListener{
            val intent = Intent(this@EscuadraListActivity, EscuadraNewActivity::class.java)
            resultLauncher.launch(intent)
        }

        currentFaccionId.let {
            escuadraViewModel.allEscuadrasByFaccion(it).observe(this){ escuadra ->
                listaEscuadras = escuadra
                escuadra?.let { escuadras -> adapter.submitList(escuadras) }
            }
        }
    }

    private fun adapterOnClick(escuadra: Escuadra) {
        val intent = Intent(this, ProcesoListActivity::class.java)
        intent.putExtra(ESCUADRA_ID, escuadra.uid)
        startActivity(intent)
    }
}