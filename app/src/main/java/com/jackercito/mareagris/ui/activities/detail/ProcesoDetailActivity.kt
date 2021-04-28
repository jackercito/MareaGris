package com.jackercito.mareagris.ui.activities.detail

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.jackercito.mareagris.MareaGrisApplication
import com.jackercito.mareagris.R
import com.jackercito.mareagris.models.Proceso
import com.jackercito.mareagris.ui.activities.list.PROCESO_ID
import com.jackercito.mareagris.ui.frament.DialogoTerminadaConfirmacionFragmen
import com.jackercito.mareagris.viewmodels.EscuadraViewModel
import com.jackercito.mareagris.viewmodels.EscuadraViewModelFactory
import com.jackercito.mareagris.viewmodels.ProcesoViewModel
import com.jackercito.mareagris.viewmodels.ProcesoViewModelFactory
import org.apache.commons.math3.distribution.NormalDistribution
import java.text.SimpleDateFormat
import kotlin.math.sqrt
import kotlin.properties.Delegates

class ProcesoDetailActivity : AppCompatActivity(), DialogoTerminadaConfirmacionFragmen.DialogoTerminadaConfirmacionListener {
    private var currentProcesoId by Delegates.notNull<Long>()
    private val escuadraViewModel: EscuadraViewModel by viewModels {
        EscuadraViewModelFactory((application as MareaGrisApplication).repositoryEscuadra)
    }
    private val procesoViewMode: ProcesoViewModel by viewModels {
        ProcesoViewModelFactory((application as MareaGrisApplication).repositoryProceso)
    }

    private lateinit var proceso: Proceso

    private lateinit var escuadraViewItem : TextView
    private lateinit var fechaCompraViewItem : TextView
    private lateinit var fechaFinViewItem : TextView
    private lateinit var estadoViewItem : TextView
    private lateinit var tiempoViewItem : TextView
    /*private val unidadViewItem : TextView = findViewById(R.id.tv_unidad)*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proceso_detail)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentProcesoId = bundle.getLong(PROCESO_ID)
        }

        procesoViewMode.getProcesoById(currentProcesoId).observe(this){
            Log.d("PROCESO_DETAIL", "GET CURRENT PROCESO")

            proceso = it

            escuadraViewItem = findViewById(R.id.tv_escuadra)!!
            fechaCompraViewItem = findViewById(R.id.tv_fercha_compra)!!
            fechaFinViewItem = findViewById(R.id.tv_fecha_terminado)!!
            estadoViewItem = findViewById(R.id.tv_estado)!!
            tiempoViewItem = findViewById(R.id.tv_tiempo)!!
            //unidadViewItem = findViewById(R.id.tv_unidad)!!

            escuadraViewItem.text = getString(R.string.nombre_sec_escuadra_val, it.nombreEscuadra )
            fechaCompraViewItem.text = getString(R.string.fecha_compra_val, it.fechaCompra )
            fechaFinViewItem.text = getString(R.string.fecha_fin_val, it.fechaFin )
            estadoViewItem.text = getString(R.string.esadoVal, it.estado )
            tiempoViewItem.text = getString(R.string.tiempoVal, it.tiempo )
            /*unidadViewItem.text = getString(R.string.cantidad_unidades, it!!. )*/
        }

        val btnFin : Button = findViewById(R.id.btn_finalizar)
        btnFin.setOnClickListener{
            showNoticeDialog()
        }
    }

    private fun recalcularValores(){
        Log.d("PROCESO_DETAIL", "RECALCULANDO")
        escuadraViewModel.allEscuadrasRE.observe(this) { escuadras ->
            val matriz: ArrayList<Int> = ArrayList()

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

            var completadas = 0
            var dif = 0.00

            for(esc in escuadras){
                for(p in esc.listaProcesos){
                    if(p.estado == "Finalizado"){
                        val probabilidad = normalDistribution.cumulativeProbability(p.tiempo.toDouble())
                        esc.escuadra.unidad.dificultadDistribuciónNormal = probabilidad*10
                        esc.escuadra.unidad.valorDistribucionNormal = probabilidad
                        completadas++
                        dif += (esc.escuadra.unidad.dificultadEstimada)-(probabilidad*10)
                    }
                }
            }

            val mediaError = dif/completadas

            for(esc in escuadras){
                val x = (esc.escuadra.unidad.dificultadEstimada - mediaError) / 10
                esc.escuadra.unidad.tiempoDistribucionNormal = normalDistribution.inverseCumulativeProbability(x)

                Log.d("PROCESO_DETAIL", esc.escuadra.uid.toString())

                escuadraViewModel.updateEscuadra(esc.escuadra)
            }
        }
    }

    private fun showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        val dialog = DialogoTerminadaConfirmacionFragmen()
        dialog.show(supportFragmentManager, "DialogoTerminadaConfirmacionFragmen")
    }

    override fun onDialogPositiveClick(fecha: String, tiempo: Int) {
        //procesoViewMode.getProcesoById(currentProcesoId).observe(this){
        val date = SimpleDateFormat("dd/MM/yyyy").parse(fecha)
        proceso.fechaFin = date
        proceso.tiempo = tiempo
        proceso.estado = "Finalizado"

        procesoViewMode.updateProceso(proceso)

        recalcularValores()
        //}
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        Log.d("DIALOG", "Negative")
    }
}