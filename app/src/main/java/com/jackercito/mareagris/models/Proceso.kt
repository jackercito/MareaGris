package com.jackercito.mareagris.models

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import java.util.*

//Clase con valores unicos de cada miniatura
data class Proceso(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "fechaCompra") val fechaCompra: Date,
    @ColumnInfo(name = "fechaFin") val fechaFin: Date,
    @ColumnInfo(name = "fehcaInicio") val fehcaInicio: Date,
    @ColumnInfo(name = "objetivo") val objetivo: Date,
    @ColumnInfo(name = "tiempo") val tiempo: Int,
    @ColumnInfo(name = "diasTranscurridos") val diasTranscurridos: Int,
    @ColumnInfo(name = "porcentajeCurso") val porcentajeCurso: Double,
    @ColumnInfo(name = "tiempoCurso") val tiempoCurso: Double,
    @ColumnInfo(name = "objetivoCumplido") val objetivoCumplido: Boolean,
    @ColumnInfo(name = "estado") val estado: String,
    @ColumnInfo(name = "comentarios") val comentarios: String,
    @ColumnInfo(name = "foto") val foto: String,
    val idFkEscuadra: Long
) {
    /*private fun setDiasTransucrridos(){
        this.diasTranscurridos = this.fechaFin.time - this.fechaCompra.time
    }

    fun setFecha(fechaFin: Date){
        this.fechaFin = fechaFin
        this.setDiasTransucrridos()
    }

    fun setTiempo(tiempo: Int){
        this.tiempo = tiempo
    }*/
}