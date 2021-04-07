package com.jackercito.mareagris.models

class Ejercito (val nombreEjercito: String) {
    var listaFaccion = mutableListOf<Faccion>()

    fun anadirFaccion(faccion: Faccion){
        this.listaFaccion.add(faccion)
    }
}