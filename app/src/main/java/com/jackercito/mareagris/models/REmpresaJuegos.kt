package com.jackercito.mareagris.models

import androidx.room.Embedded
import androidx.room.Relation

data class REmpresaJuegos(
    @Embedded val empresa:Empresa,
    @Relation(
        parentColumn = "uid",
        entityColumn = "uid"
    )
    val listaJuegos: List<Juego>
)