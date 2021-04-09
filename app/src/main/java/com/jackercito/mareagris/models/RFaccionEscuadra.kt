package com.jackercito.mareagris.models

import androidx.room.Embedded
import androidx.room.Relation

data class RFaccionEscuadra (
    @Embedded val faccion: Faccion,
    @Relation(
        parentColumn = "uid",
        entityColumn = "uid"
    )
    val listaEscuadras: List<Escuadra>
)