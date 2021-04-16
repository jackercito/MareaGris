package com.jackercito.mareagris.models

import androidx.room.Embedded
import androidx.room.Relation

class REscuadraProceso (
    @Embedded val escuadra: Escuadra,
    @Relation(
        parentColumn = "uid",
        entityColumn = "idFkEscuadra"
    )
    val listaProcesos: List<Proceso>
)