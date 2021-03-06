package com.jackercito.mareagris.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Ejercito (
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "nombreEjercito") val nombreEjercito: String,
    @ColumnInfo(name = "imagen") val imagen: String?,
    var idFkJuego: Long
): Serializable {
    /*var listaFaccion = mutableListOf<Faccion>()

    fun anadirFaccion(faccion: Faccion){
        this.listaFaccion.add(faccion)
    }*/
}