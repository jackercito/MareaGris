package com.jackercito.mareagris.models

import androidx.room.ColumnInfo
import java.io.Serializable

data class Unidad (
        @ColumnInfo(name = "nombre") var nombre: String,
        @ColumnInfo(name = "tipoUnidad") var tipoUnidad: String,
        @ColumnInfo(name = "dificultadEstimada") var dificultadEstimada: Int,
        @ColumnInfo(name = "tiempoDistribucionNormal")var tiempoDistribucionNormal: Double = 0.00,
        @ColumnInfo(name = "dificultadDistribuciÃ³nNormal")var dificultadDistribuciÃ³nNormal: Double = 0.00,
        @ColumnInfo(name = "valorDistribucionNormal")var valorDistribucionNormal: Double = 0.00,
): Serializable