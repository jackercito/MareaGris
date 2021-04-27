package com.jackercito.mareagris.ui.activities.detail

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.jackercito.mareagris.MareaGrisApplication
import com.jackercito.mareagris.R
import com.jackercito.mareagris.ui.frament.DialogoTerminadaConfirmacionFragmen
import com.jackercito.mareagris.viewmodels.EscuadraViewModel
import com.jackercito.mareagris.viewmodels.EscuadraViewModelFactory
import org.apache.commons.math3.distribution.NormalDistribution
import kotlin.math.sqrt

class ProcesoDetailActivity : AppCompatActivity(), DialogoTerminadaConfirmacionFragmen.DialogoTerminadaConfirmacionListener {
    private val escuadraViewModel: EscuadraViewModel by viewModels {
        EscuadraViewModelFactory((application as MareaGrisApplication).repositoryEscuadra)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proceso_detail)

        val btnFin : Button = findViewById(R.id.btn_finalizar)
        btnFin.setOnClickListener{
            showNoticeDialog()
        }
    }

    private fun recalcularValores(){
        escuadraViewModel.allEscuadrasRE().observe(this) { escuadras ->
            var matriz: ArrayList<Int> = ArrayList()

            for(esc in escuadras){
                for(p in esc.listaProcesos){
                    if(p.tiempo > 0){
                        matriz.add(p.tiempo)
                    }
                }
            }

            val media = matriz.average()
            val desviacion = sqrt(matriz.map { (it - media) * (it - media) }.average())
            val normalDistribution = NormalDistribution(media, desviacion)

            var completadas = 0;
            var dif = 0.00;

            for(esc in escuadras){
                for(p in esc.listaProcesos){
                    if(p.estado == "Finalizado"){
                        val probabilidad = normalDistribution.cumulativeProbability(p.tiempo.toDouble())
                        esc.escuadra.unidad.dificultadDistribuciónNormal = probabilidad*10
                        esc.escuadra.unidad.valorDistribucionNormal = probabilidad
                        completadas++;
                        dif += (esc.escuadra.unidad.dificultadEstimada)-(probabilidad*10)
                    }
                }
            }

            val mediaError = dif/completadas

            for(esc in escuadras){
                val x = (esc.escuadra.unidad.dificultadEstimada - mediaError) / 10
                esc.escuadra.unidad.tiempoDistribucionNormal = normalDistribution.inverseCumulativeProbability(x)

                escuadraViewModel.updateEscuadra(esc.escuadra)
            }
        }
    }

    private fun showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        val dialog = DialogoTerminadaConfirmacionFragmen()
        dialog.show(supportFragmentManager, "DialogoTerminadaConfirmacionFragmen")
    }

    override fun onDialogPositiveClick(fecha: Int, tiempo: Int) {
        Log.d("DIALOG", fecha.toString())
        Log.d("DIALOG", tiempo.toString())
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        Log.d("DIALOG", "Negative")
    }
}