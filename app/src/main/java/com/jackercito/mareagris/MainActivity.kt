package com.jackercito.mareagris

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import com.jackercito.mareagris.ui.activities.list.EmpresasListActivity
import java.util.*

class MainActivity : MainMenuActivity() {
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)

        menu.removeItem(R.id.action_fav)
        menu.findItem(R.id.action_settings).subMenu.removeItem(R.id.action_editar)
        menu.findItem(R.id.action_settings).subMenu.removeItem(R.id.action_home)

        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn : Button = findViewById(R.id.button)
        btn.setOnClickListener{
            val intent = Intent(this@MainActivity, EmpresasListActivity::class.java)
            startActivity(intent)
        }
    }
}