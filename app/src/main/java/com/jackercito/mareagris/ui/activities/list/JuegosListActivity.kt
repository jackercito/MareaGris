package com.jackercito.mareagris.ui.activities.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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

class JuegosListActivity : AppCompatActivity() {
    private val TAG = "JuegosListActivity"
    private val nuevoJuegoActivityRequestCode = 1
    private var currentEmpresaId by Delegates.notNull<Long>()
    private val juegoViewModel: JuegoViewModel by viewModels {
        JuegoViewModelFactory((application as MareaGrisApplication).repositoryJuego)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juegos_list)

        //currentEmpresaId = null

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerviewJuegos)
        val adapter = JuegoListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentEmpresaId = bundle.getLong(EMPRESA_ID)
        }

        val btnFAB : FloatingActionButton = findViewById(R.id.fab)
        btnFAB.setOnClickListener{
            val intent = Intent(this@JuegosListActivity, JuegoNewActivity::class.java)
            startActivityForResult(intent, nuevoJuegoActivityRequestCode)
        }

        currentEmpresaId?.let {
            juegoViewModel.allJuegosByEmpresa(it).observe(this){ juego ->
                juego?.let {
                    adapter.submitList(it)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == nuevoJuegoActivityRequestCode && resultCode == Activity.RESULT_OK){
            data?.getSerializableExtra(JuegoNewActivity.EXTRA_REPLY)?.let {
                var j : Juego = it as Juego
                j.idFkEmpresa = currentEmpresaId
                juegoViewModel.insert(it as Juego)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_image_not_select,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}