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
import com.jackercito.mareagris.models.Juego
import com.jackercito.mareagris.ui.activities.news.JuegoNewActivity
import com.jackercito.mareagris.ui.adapter.JuegoListAdapter
import com.jackercito.mareagris.viewmodels.JuegoViewModel
import com.jackercito.mareagris.viewmodels.JuegoViewModelFactory
import kotlin.properties.Delegates

const val JUEGO_ID = "juego id"

class JuegosListActivity : AppCompatActivity() {
    private var currentEmpresaId by Delegates.notNull<Long>()
    private val juegoViewModel: JuegoViewModel by viewModels {
        JuegoViewModelFactory((application as MareaGrisApplication).repositoryJuego)
    }

    private var resultLaunch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK){
            val data : Intent? = result.data
            data?.getSerializableExtra(JuegoNewActivity.EXTRA_REPLY)?.let {
                (it as Juego).idFkEmpresa = currentEmpresaId
                juegoViewModel.insert(it)
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
        setContentView(R.layout.activity_juego_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerviewJuegos)
        val adapter = JuegoListAdapter { juego -> adapterOnClick(juego) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentEmpresaId = bundle.getLong(EMPRESA_ID)
        }

        val btnFAB : FloatingActionButton = findViewById(R.id.fab)
        btnFAB.setOnClickListener{
            val intent = Intent(this@JuegosListActivity, JuegoNewActivity::class.java)
            resultLaunch.launch(intent)
        }

        currentEmpresaId.let {
            juegoViewModel.allJuegosByEmpresa(it).observe(this){ juego ->
                juego?.let { juegos ->
                    adapter.submitList(juegos)
                }
            }
        }
    }

    private fun adapterOnClick(juego: Juego){
        val intent = Intent(this, EjercitoListActivity::class.java)
        intent.putExtra(JUEGO_ID, juego.uid)
        startActivity(intent)
    }
}