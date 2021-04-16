package com.jackercito.mareagris.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jackercito.mareagris.models.Ejercito
import kotlinx.coroutines.flow.Flow

@Dao
interface EjercitoDao {
    @Query("SELECT * FROM ejercito")
    fun getAllEjercitos(): Flow<List<Ejercito>>

    @Query("SELECT * FROM ejercito WHERE idFkJuego = :idFkJuego")
    fun getEjercitoByJuego(idFkJuego: Long): Flow<List<Ejercito>>

    @Query("DELETE FROM ejercito")
    suspend fun deleteAllEjercitos()

    @Insert
    suspend fun insertAllEjercitos(vararg ejercito: Ejercito)

    @Delete
    suspend fun deleteEjercito(ejercito: Ejercito)

    @Query("SELECT COUNT(uid)  FROM proceso WHERE idFkEscuadra IN (SELECT uid FROM Escuadra WHERE idFkFaccion IN (SELECT uid FROM Faccion WHERE uid = :uid))")
    fun countNumeroDeMinis(uid: Long): Int
}