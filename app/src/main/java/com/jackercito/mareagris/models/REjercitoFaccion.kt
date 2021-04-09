package com.jackercito.mareagris.models

import androidx.room.Embedded
import androidx.room.Relation

class REjercitoFaccion (
    @Embedded val ejercito: Ejercito,
    @Relation(
        parentColumn = "uid",
        entityColumn = "uid"
    )
    val listaFacciones: List<Faccion>
)