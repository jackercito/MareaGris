package com.jackercito.mareagris.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Empresa (
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "nombreEmpresa") val nombreEmpresa: String?
) {
    //var listaJuego : MutableList<Juego> = mutableListOf<Juego>();

    /*fun anadirJuego(juego: Juego){
        this.listaJuego.add(juego)
    }*/
}