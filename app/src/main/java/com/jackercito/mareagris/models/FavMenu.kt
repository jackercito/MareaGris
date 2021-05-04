package com.jackercito.mareagris.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class FavMenu (
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "tabla") val tabla: String,
    @ColumnInfo(name = "ruta") val ruta: String,
    @ColumnInfo(name = "idFkTabla") val idFkTabla: String,
) : Serializable {}