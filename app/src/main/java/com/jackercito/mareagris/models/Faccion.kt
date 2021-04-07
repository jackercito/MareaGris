package com.jackercito.mareagris.models

class Faccion (val nombreFaccion: String) {
    var listaEscuadras = mutableListOf<Escuadra>()

    fun anadirEscuadra(escuadra: Escuadra){
        this.listaEscuadras.add(escuadra)
    }
}