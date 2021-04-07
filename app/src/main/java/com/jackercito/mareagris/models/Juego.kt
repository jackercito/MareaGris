package com.jackercito.mareagris.models

class Juego (val nombreJuego: String) {
    var listaEjercito = mutableListOf<Ejercito>()

    fun anadirEjercito(ejercito: Ejercito){
        this.listaEjercito.add(ejercito)
    }
}