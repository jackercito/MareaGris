package com.jackercito.mareagris.ui.activities.list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jackercito.mareagris.MareaGrisApplication
import com.jackercito.mareagris.R
import com.jackercito.mareagris.ui.adapter.JuegoListAdapter
import com.jackercito.mareagris.viewmodels.JuegoViewModel
import com.jackercito.mareagris.viewmodels.JuegoViewModelFactory

class JuegosListActivity : AppCompatActivity() {
    private val nuevoJuegoActivityRequestCode = 1
    private val juegoViewModel: JuegoViewModel by viewModels {
        JuegoViewModelFactory((application as MareaGrisApplication).repositoryJ)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juegos_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerviewJuegos)
        val adapter = JuegoListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        juegoViewModel.allJuegos.observe(this){ juego ->
            juego?.let { adapter.submitList(it) }
        }
    }
}