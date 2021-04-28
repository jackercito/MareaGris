package com.jackercito.mareagris.daos

import androidx.room.*
import com.jackercito.mareagris.models.Escuadra
import com.jackercito.mareagris.models.REscuadraProceso
import kotlinx.coroutines.flow.Flow

@Dao
interface EscuadraDao {
    @Query("SELECT * FROM escuadra")
    fun getAllEscuadras(): Flow<List<Escuadra>>

    @Query("SELECT * FROM escuadra WHERE idFkFaccion = :idFkFaccion")
    fun getEscuadrasByFaccion(idFkFaccion: Long): Flow<List<Escuadra>>

    @Query("SELECT * FROM escuadra WHERE nombre = :nombreUnidad")
    fun getEscuadrasByUnidad(nombreUnidad: String): Flow<List<Escuadra>>

    @Query("DELETE FROM escuadra")
    suspend fun deleteAllEscuadras()

    @Update()
    suspend fun updateEscudra(escuadra: Escuadra)

    @Insert
    suspend fun insertAllEscuadras(escuadra: Escuadra): Long

    @Delete
    suspend fun deleteEscuadra(escuadra: Escuadra)

    @Transaction
    @Query("SELECT * FROM escuadra")
    suspend fun getAllEscuadrasRE(): List<REscuadraProceso>

    @Transaction
    @Query("SELECT * FROM escuadra WHERE idFkFaccion = :idFkFaccion")
    fun getEscuadrasWithProcesosByFaccion(idFkFaccion: Long): Flow<List<REscuadraProceso>>

    @Query("SELECT COUNT(uid)  FROM proceso WHERE idFkEscuadra IN (SELECT uid FROM Escuadra WHERE uid = :uid)")
    fun countNumeroDeMinisByEscuadra(uid: Long): Int
}