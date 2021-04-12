package com.jackercito.mareagris.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jackercito.mareagris.models.Escuadra
import kotlinx.coroutines.flow.Flow

@Dao
interface EscuadraDao {
    @Query("SELECT * FROM escuadra")
    fun getAllEscuadras(): Flow<List<Escuadra>>

    @Query("SELECT * FROM escuadra WHERE idFkFaccion = :idFkFaccion")
    fun getEscuadrasByFaccion(idFkFaccion: Long): Flow<List<Escuadra>>

    @Query("DELETE FROM escuadra")
    suspend fun deleteAllEscuadras()

    @Insert
    suspend fun insertAllEscuadras(vararg escuadra: Escuadra)

    @Delete
    suspend fun deleteEscuadra(escuadra: Escuadra)
}