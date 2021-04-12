package com.jackercito.mareagris.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jackercito.mareagris.models.Proceso
import kotlinx.coroutines.flow.Flow

@Dao
interface ProcesoDao {
    @Query("SELECT * FROM proceso")
    fun getAllProcesos(): Flow<List<Proceso>>

    @Query("SELECT * FROM proceso WHERE idFkEscuadra = :idFkEscuadra")
    fun getAllProcesosByEscuadra(idFkEscuadra: Long): Flow<List<Proceso>>

    @Query("DELETE FROM proceso")
    suspend fun deleteAllProcesos()

    @Insert
    suspend fun insertAllProcesos(vararg proceso: Proceso)

    @Delete
    suspend fun deleteProceso(proceso: Proceso)

}