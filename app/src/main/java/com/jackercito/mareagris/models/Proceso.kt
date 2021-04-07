package com.jackercito.mareagris.models

import java.util.*

//Clase con valores unicos de cada miniatura
class Proceso(private val fechaCompra: Date) {
    private lateinit var fechaFin: Date;
    private var tiempo: Int = 0;
    private var diasTranscurridos: Long = 0;
    private var estado: String = "";
    private var porcentajeCurso: Double = 0.00
    private var tiempoCurso: Double = 0.00
    //Valores en curso
    private lateinit var fehcaInicio: Date
    //Otros
    private lateinit var objetivo: Date
    private var objetivoCumplido: Boolean = false
    private var comentarios: String = ""
    private var foto: String = ""

    private fun setDiasTransucrridos(){
        this.diasTranscurridos = this.fechaFin.time - this.fechaCompra.time
    }

    fun setFecha(fechaFin: Date){
        this.fechaFin = fechaFin
        this.setDiasTransucrridos()
    }

    fun setTiempo(tiempo: Int){
        this.tiempo = tiempo
    }
}