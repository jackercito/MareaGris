package com.jackercito.mareagris.models

data class Unidad (
        var nombre: String,
        var tipo_unidad: String,
        var dificultad_estimada: Int,

        //Distribución normal
        var tiempo_distribucion_normal: Double = 0.00,
        var dificultad_distribución_normal: Double = 0.00,
        var valor_distribucion_normal: Double = 0.00,
)