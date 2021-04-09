package com.jackercito.mareagris.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.PrimaryKey

data class Escuadra(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "nombreEscuadra") val nombreEscuadra: String,
    @ColumnInfo(name = "cantidad") val cantidad: Int,
    //Esta es una idea para tratar una escudra como una aunica unidad
    //Y poner los valores de la unidad de forma grupal
    //P.E.: 5 SM Interceptor como uno solo tendrá 5 tablas de "proceso" iguales, pero se habrá rellenado una vez
    //Hay que macerar esta idea para ver como puede ser la mejor forma de implementarlo
    //Para esto tambien habría que permitir devidir las escuadras en unidades más pequeñas
    //P.E.: 15 SM Interceptor poder dividirlo en 3 escuadras de 5
    @ColumnInfo(name = "entidadUnica") val entidadUnica: Boolean,
    @Embedded val unidad: Unidad,
    val idFkFaccion: Long
) {
    /*private var listaProceso: MutableList<Proceso> = this.rellenarProcesos(cantidad, fechaCompra);

    private fun rellenarProcesos(cantidad: Int, fechaCompra: Date): MutableList<Proceso> {
        var lista = mutableListOf<Proceso>()
        for (i in 1..cantidad) {
            val proceso: Proceso = Proceso(fechaCompra)
            lista.add(proceso)
        }
        return lista;
    }

    private fun completarUnidad(indice: Int, fechaFin: Date) {
        listaProceso[indice].setFecha(fechaFin)
    }

    private fun tiempoCompletadoUnidad(indice: Int, tiempo: Int) {
        listaProceso[indice].setTiempo(tiempo)
    }*/
}