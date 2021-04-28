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
    @ColumnInfo(name = "fechaCompra") var fechaCompra: Date?,
    @ColumnInfo(name = "fechaFin") var fechaFin: Date?,
    @ColumnInfo(name = "fehcaInicio") var fehcaInicio: Date?,
    @ColumnInfo(name = "objetivo") var objetivo: Date?,
    @ColumnInfo(name = "tiempo") var tiempo: Int,
    @ColumnInfo(name = "diasTranscurridos") var diasTranscurridos: Int,
    @ColumnInfo(name = "porcentajeCurso") var porcentajeCurso: Double,
    @ColumnInfo(name = "tiempoCurso") var tiempoCurso: Double,
    @ColumnInfo(name = "objetivoCumplido") var objetivoCumplido: Boolean,
    @ColumnInfo(name = "estado") var estado: String,
    @ColumnInfo(name = "comentarios") var comentarios: String,
    @ColumnInfo(name = "foto") var foto: String,
    //Pintado Pro, Normal, Tabletop, Chapado en nuln oil, etc...
    @ColumnInfo(name = "tipoPintado") var tipoPintado: String,
    @ColumnInfo(name = "nombreEscuadra") var nombreEscuadra: String,
    val idFkEscuadra: Long,
): Serializable