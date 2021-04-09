package com.jackercito.mareagris.models

import androidx.room.Embedded
import androidx.room.Relation

data class RJuegoEjercito (
    @Embedded val juego: Juego,
    @Relation(
        parentColumn = "uid",
        entityColumn = "uid"
    )
    val listaFacciones: List<Ejercito>
)