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
    private var currentFaccionId by Delegates.notNull<Long>()
    private val escuadraViewModel: EscuadraViewModel by viewModels{
        EscuadraViewModelFactory((application as MareaGrisApplication).repositoryEscuadra)
    }

    private val procesoViewModel : ProcesoViewModel by viewModels{
        ProcesoViewModelFactory((application as MareaGrisApplication).repositoryProceso)
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK){
            val data: Intent? = result.data

            data?.getSerializableExtra(EscuadraNewActivity.ESCUADRA_REPLY)?.let {
                (it as Escuadra).idFkFaccion = currentFaccionId
                escuadraViewModel.insertEscuadra(it).observe(this){ uid ->
                    data.getStringExtra(EscuadraNewActivity.FECHA)?.let{ fecha ->
                        data.getStringExtra(EscuadraNewActivity.CANTIDAD).let{ cantidad ->
                            val numero = cantidad?.toInt()
                            val date = SimpleDateFormat("dd/MM/yyyy").parse(fecha)

                            for(i in 1..numero!!){
                                val proceso = Proceso(
                                    0, date, null, null, null, 0,
                                    0, 0.00, 0.00, false,
                                    "Comprado", "", "", uid
                                )

                                procesoViewModel.insertProceso(proceso)
                            }
                        }
                    }
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
                escuadra?.let { escuadras -> adapter.submitList(escuadras) }
            }
        }
    }

    private fun adapterOnClick(escuadra: Escuadra) {
        //val intent = Intent(this, EscuadraListActivity::class.java);
        //intent.putExtra(FACCION_ID, faccion.uid)
        //startActivity(intent)
    }
}