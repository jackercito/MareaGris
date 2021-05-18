package com.jackercito.mareagris.ui.activities.cronometro

import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jackercito.mareagris.R

class ActivityCronometro : AppCompatActivity() {
    private lateinit var relojTextView: TextView;
    private lateinit var startButton: Button;
    private lateinit var stopButton: Button;
    private lateinit var restartButton: Button

    private lateinit var cronometro: Chronometer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cronometro)

        relojTextView = findViewById(R.id.tvReloj)
        startButton = findViewById(R.id.btnInicio)
        stopButton = findViewById(R.id.btnStop)
        restartButton = findViewById(R.id.btnRestart)

        startButton.setOnClickListener{
            iniciarCronometro()
        }

        stopButton.setOnClickListener{
            detenerCronometro()
        }

        restartButton.setOnClickListener {
            reiniciarCronometro()
        }
    }

    private fun iniciarCronometro(){
       cronometro.start()
    }

    private fun detenerCronometro(){

    }

    private fun reiniciarCronometro(){

    }
}