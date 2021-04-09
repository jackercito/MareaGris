package com.jackercito.mareagris.ui.activities.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jackercito.mareagris.MareaGrisApplication
import com.jackercito.mareagris.R
import com.jackercito.mareagris.models.Empresa
import com.jackercito.mareagris.ui.activities.news.EmpresaNewActivity
import com.jackercito.mareagris.ui.adapter.EmpresaListAdapter
import com.jackercito.mareagris.viewmodels.EmpresaViewModel
import com.jackercito.mareagris.viewmodels.EmpresaViewModelFactory

const val LOG_ACTIVITY_2 = "EmpresasListActivity"

class EmpresasListActivity : AppCompatActivity() {
    private val nuevaEmpresaActivityRequestCode = 1
    private val empresaViewModel: EmpresaViewModel by viewModels {
        EmpresaViewModelFactory((application as MareaGrisApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empresas_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = EmpresaListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        empresaViewModel.allEmpresas.observe(this) { empresa ->
            // Update the cached copy of the words in the adapter.
            empresa?.let { adapter.submitList(it) }
        }

        val btnFAB : FloatingActionButton = findViewById(R.id.fab)
        btnFAB.setOnClickListener{
            val intent = Intent(this@EmpresasListActivity, EmpresaNewActivity::class.java)
            startActivityForResult(intent, nuevaEmpresaActivityRequestCode)
        }

        val linearLayout : LinearLayout = findViewById(R.id.ll_empresa)
        linearLayout.setOnClickListener{
            val intent = Intent(this@EmpresasListActivity, JuegosListActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == nuevaEmpresaActivityRequestCode && resultCode == Activity.RESULT_OK){
            data?.getSerializableExtra(EmpresaNewActivity.EXTRA_REPLY)?.let {
                //val empresa = Empresa(0, it, null)
                Log.d(LOG_ACTIVITY_2, "hi!")
                Log.d(LOG_ACTIVITY_2, it.toString())
                Log.d(LOG_ACTIVITY_2, (it as Empresa).toString())
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
}