package com.jackercito.mareagris.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ejercito (
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "nombreEjercito") val nombreEjercito: String,
    val idFkJuego: Long
) {
    /*var listaFaccion = mutableListOf<Faccion>()

    fun anadirFaccion(faccion: Faccion){
        this.listaFaccion.add(faccion)
    }*/
}