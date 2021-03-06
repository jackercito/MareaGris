package com.jackercito.mareagris.ui.activities.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jackercito.mareagris.MainMenuActivity
import com.jackercito.mareagris.MareaGrisApplication
import com.jackercito.mareagris.R
import com.jackercito.mareagris.models.Empresa
import com.jackercito.mareagris.models.REmpresaConteo
import com.jackercito.mareagris.ui.activities.news.EmpresaNewActivity
import com.jackercito.mareagris.ui.adapter.EmpresaListAdapter
import com.jackercito.mareagris.viewmodels.EmpresaViewModel
import com.jackercito.mareagris.viewmodels.EmpresaViewModelFactory

const val EMPRESA_ID = "empresa id"

class EmpresasListActivity : MainMenuActivity() {
    private val empresaViewModel: EmpresaViewModel by viewModels {
        EmpresaViewModelFactory((application as MareaGrisApplication).repositoryEmpresa)
    }

    private var resultLaunch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if( result.resultCode == Activity.RESULT_OK){
            val data: Intent? = result.data
            data?.getSerializableExtra(EmpresaNewActivity.EXTRA_REPLY)?.let {
                empresaViewModel.insert(it as Empresa)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_image_not_select,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)

        menu.removeItem(R.id.action_fav)
        menu.findItem(R.id.action_settings).subMenu.removeItem(R.id.action_editar)

        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empresas_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = EmpresaListAdapter(this) { empresa -> adapterOnClick(empresa) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        empresaViewModel.allEmpresasConConteo.observe(this) { empresa ->
            // Update the cached copy of the words in the adapter.
            empresa?.let { adapter.submitList(it) }
        }

        val btnFAB : FloatingActionButton = findViewById(R.id.fab)
        btnFAB.setOnClickListener{
            val intent = Intent(this@EmpresasListActivity, EmpresaNewActivity::class.java)
            resultLaunch.launch(intent)
        }
    }

    private fun adapterOnClick(empresa: REmpresaConteo) {
        val intent = Intent(this, JuegosListActivity()::class.java)
        intent.putExtra(EMPRESA_ID, empresa.empresa?.uid)
        startActivity(intent)
    }
}