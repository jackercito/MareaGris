package com.jackercito.mareagris.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Juego (
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "nombreJuego") val nombreJuego: String,
    @ColumnInfo(name = "imagen") val imagen: String,
    val idFkEmpresa: Long
): Serializable {
    /*var listaEjercito = mutableListOf<Ejercito>()

    fun anadirEjercito(ejercito: Ejercito){
        this.listaEjercito.add(ejercito)
    }*/
}