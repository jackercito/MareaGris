package com.jackercito.mareagris.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity
//Clase con valores unicos de cada miniatura
data class Proceso(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "fechaCompra") val fechaCompra: Date?,
    @ColumnInfo(name = "fechaFin") val fechaFin: Date?,
    @ColumnInfo(name = "fehcaInicio") val fehcaInicio: Date?,
    @ColumnInfo(name = "objetivo") val objetivo: Date?,
    @ColumnInfo(name = "tiempo") val tiempo: Int,
    @ColumnInfo(name = "diasTranscurridos") val diasTranscurridos: Int,
    @ColumnInfo(name = "porcentajeCurso") val porcentajeCurso: Double,
    @ColumnInfo(name = "tiempoCurso") val tiempoCurso: Double,
    @ColumnInfo(name = "objetivoCumplido") val objetivoCumplido: Boolean,
    @ColumnInfo(name = "estado") val estado: String,
    @ColumnInfo(name = "comentarios") val comentarios: String,
    @ColumnInfo(name = "foto") val foto: String,
    //Pintado Pro, Normal, Tabletop, Chapado en nuln oil, etc...
    @ColumnInfo(name = "tipoPintado") val tipoPintado: String,
    val idFkEscuadra: Long,
): Serializable