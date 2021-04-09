package com.jackercito.mareagris.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Empresa (
        @PrimaryKey(autoGenerate = true) val uid: Long,
        @ColumnInfo(name = "nombreEmpresa") val nombreEmpresa: String?,
        @ColumnInfo(name = "imagen") val imagen: String?
): Serializable {
    //var listaJuego : MutableList<Juego> = mutableListOf<Juego>();

        /*fun anadirJuego(juego: Juego){
        this.listaJuego.add(juego)
    }*/
        override fun toString(): String {
                return "Nombre: $nombreEmpresa, Logo: $imagen"
        }
}