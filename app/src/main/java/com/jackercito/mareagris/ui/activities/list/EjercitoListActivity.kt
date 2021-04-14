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
import com.jackercito.mareagris.models.Ejercito
import com.jackercito.mareagris.ui.activities.news.EjercitoNewActivity
import com.jackercito.mareagris.ui.adapter.EjercitoListAdapter
import com.jackercito.mareagris.viewmodels.EjercitoViewModel
import com.jackercito.mareagris.viewmodels.EjercitoViewModelFactory
import kotlin.properties.Delegates

const val EJERCITO_ID = "ejercito id"

class EjercitoListActivity : AppCompatActivity() {
    private var currentJuegoId by Delegates.notNull<Long>()
    private val ejercitoViewModel: EjercitoViewModel by viewModels {
        EjercitoViewModelFactory((application as MareaGrisApplication).repositoryEjercito)
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK){
            val data: Intent? = result.data
            data?.getSerializableExtra(EjercitoNewActivity.EXTRA_REPLY)?.let {
                (it as Ejercito).idFkJuego = currentJuegoId
                ejercitoViewModel.insertEjercito(it)
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
        setContentView(R.layout.activity_ejercito_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerviewEjercitos)
        val adapter = EjercitoListAdapter{ ejercito -> adapterOnClick(ejercito) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val bundle : Bundle? = intent.extras
        if(bundle != null){
            currentJuegoId = bundle.getLong(JUEGO_ID)
        }

        val btnFab: FloatingActionButton = findViewById(R.id.fab)
        btnFab.setOnClickListener{
            val intent = Intent(this@EjercitoListActivity, EjercitoNewActivity::class.java )
            resultLauncher.launch(intent)
        }

        currentJuegoId.let {
            ejercitoViewModel.allEjercitosByJuego(it).observe(this){ ejercito ->
                ejercito?.let{ ejercitos ->
                    adapter.submitList(ejercitos)
                }
            }
        }
    }

    private fun adapterOnClick(ejercito: Ejercito) {
        val intent = Intent(this, FaccionListActivity()::class.java)
        intent.putExtra(EJERCITO_ID, ejercito.uid)
        startActivity(intent)
    }
}