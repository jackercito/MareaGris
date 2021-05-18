package com.jackercito.mareagris

import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity


open class MainMenuActivity: AppCompatActivity() {
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_home -> {
                val homeIntent = Intent(this, MainActivity::class.java)
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(homeIntent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    /*private fun addElementToFavMenu(){

    }*/
}