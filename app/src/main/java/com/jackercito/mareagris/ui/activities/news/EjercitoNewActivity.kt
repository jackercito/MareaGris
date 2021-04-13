package com.jackercito.mareagris.ui.activities.news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jackercito.mareagris.R

class EjercitoNewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercito_new)
    }

    companion object {
        const val EXTRA_REPLY = ""
    }
}