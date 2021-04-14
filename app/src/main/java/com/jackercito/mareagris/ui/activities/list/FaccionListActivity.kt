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
import com.jackercito.mareagris.models.Faccion
import com.jackercito.mareagris.ui.activities.news.FaccionNewActivity
import com.jackercito.mareagris.ui.adapter.FaccionListAdapter
import com.jackercito.mareagris.viewmodels.FaccionViewModel
import com.jackercito.mareagris.viewmodels.FaccionViewModelFactory
import kotlin.properties.Delegates

const val FACCION_ID = "faccion id"

class FaccionListActivity : AppCompatActivity() {
    private var currentEjercito by Delegates.notNull<Long>()
    private val faccionViewModel: FaccionViewModel by viewModels {
        FaccionViewModelFactory((application as MareaGrisApplication).repositoryFaccion)
    }

    private var resultLaunch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK){
            val data : Intent? = result.data
            data?.getSerializableExtra(FaccionNewActivity.EXTRA_REPLY)?.let {
                (it as Faccion).idFkEjercito = currentEjercito
                faccionViewModel.insertFaccion(it)
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
        setContentView(R.layout.activity_faccion_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerviewFacciones)
        val adapter = FaccionListAdapter { faccion -> adapterOnClick(faccion) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentEjercito = bundle.getLong(EJERCITO_ID)
        }

        val btnFab = findViewById<FloatingActionButton>(R.id.fab)
        btnFab.setOnClickListener {
            val intent = Intent(this@FaccionListActivity, FaccionNewActivity::class.java)
            resultLaunch.launch(intent)
        }

        currentEjercito.let {
            faccionViewModel.allFaccionesByEjercito(it).observe(this) { faccion ->
                faccion?.let { facciones ->
                    adapter.submitList(facciones)
                }
            }
        }
    }

    private fun adapterOnClick(faccion: Faccion) {
        val intent = Intent(this, EscuadraListActivity::class.java)
        intent.putExtra(FACCION_ID, faccion.uid)
        startActivity(intent)
    }
}