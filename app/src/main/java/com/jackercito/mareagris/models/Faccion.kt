package com.jackercito.mareagris.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Faccion (
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "nombreFaccion") val nombreFaccion: String,
    val idFkEjercito: Long
) {
    /*var listaEscuadras = mutableListOf<Escuadra>()

    fun anadirEscuadra(escuadra: Escuadra){
        this.listaEscuadras.add(escuadra)
    }*/
}