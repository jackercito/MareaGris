package com.jackercito.mareagris.models

import java.util.*

class Escuadra(var nombreEscuadra: String, var cantidad: Int, val unidad: Unidad, private val fechaCompra: Date) {
    private var listaProceso: MutableList<Proceso> = this.rellenarProcesos(cantidad, fechaCompra);

    private fun rellenarProcesos(cantidad: Int, fechaCompra: Date): MutableList<Proceso>{
        var lista = mutableListOf<Proceso>()
        for(i in 1..cantidad){
            val proceso: Proceso = Proceso(fechaCompra)
            lista.add(proceso)
        }
        return lista;
    }

    private fun completarUnidad(indice: Int, fechaFin: Date){
        listaProceso[indice].setFecha(fechaFin)
    }

    private fun tiempoCompletadoUnidad(indice: Int, tiempo: Int){
        listaProceso[indice].setTiempo(tiempo)
    }


}