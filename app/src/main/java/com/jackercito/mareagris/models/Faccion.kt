package com.jackercito.mareagris.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Faccion (
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "nombreFaccion") val nombreFaccion: String,
    @ColumnInfo(name = "imagen") val imagen: String,
    var idFkEjercito: Long
) : Serializable {
    /*var listaEscuadras = mutableListOf<Escuadra>()

    fun anadirEscuadra(escuadra: Escuadra){
        this.listaEscuadras.add(escuadra)
    }*/
}