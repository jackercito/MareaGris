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
import com.jackercito.mareagris.models.Proceso
import com.jackercito.mareagris.ui.activities.detail.ProcesoDetailActivity
import com.jackercito.mareagris.ui.adapter.ProcesoListAdapter
import com.jackercito.mareagris.viewmodels.ProcesoViewModel
import com.jackercito.mareagris.viewmodels.ProcesoViewModelFactory

const val PROCESO_ID = "proceso id"

class ProcesoListActivity : AppCompatActivity() {
    private val procesoViewModel: ProcesoViewModel by viewModels {
        ProcesoViewModelFactory((application as MareaGrisApplication).repositoryProceso)
    }

    private var resultLaunch =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
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
        setContentView(R.layout.activity_proceso_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerviewProceso)
        val adapter = ProcesoListAdapter(this) { proceso -> adapterOnClick(proceso) }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        procesoViewModel.allProcesos.observe(this) { procesos ->
            procesos?.let {
                adapter.submitList(it)
            }
        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            //val intent = Intent(this@ProcesoListActivity, EscuadraNewActivity)
            //resultLaunch.launch(intent)
        }
    }

    private fun adapterOnClick(proceso: Proceso) {
        val intent = Intent(this, ProcesoDetailActivity()::class.java)
        intent.putExtra(PROCESO_ID, proceso.uid)
        startActivity(intent)
    }
}