package com.jackercito.mareagris.models

import androidx.room.ColumnInfo

data class Unidad (
        @ColumnInfo(name = "nombre") var nombre: String,
        @ColumnInfo(name = "tipoUnidad") var tipoUnidad: String,
        @ColumnInfo(name = "dificultadEstimada") var dificultadEstimada: Int,
        @ColumnInfo(name = "tiempoDistribucionNormal")var tiempoDistribucionNormal: Double = 0.00,
        @ColumnInfo(name = "dificultadDistribuciónNormal")var dificultadDistribuciónNormal: Double = 0.00,
        @ColumnInfo(name = "valorDistribucionNormal")var valorDistribucionNormal: Double = 0.00,
)