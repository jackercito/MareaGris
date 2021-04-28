package com.jackercito.mareagris.daos

import androidx.room.*
import com.jackercito.mareagris.models.Proceso
import kotlinx.coroutines.flow.Flow

@Dao
interface ProcesoDao {
    @Query("SELECT * FROM proceso")
    fun getAllProcesos(): Flow<List<Proceso>>

    @Query("SELECT * FROM proceso WHERE idFkEscuadra = :idFkEscuadra")
    fun getAllProcesosByEscuadra(idFkEscuadra: Long): Flow<List<Proceso>>

    @Query("SELECT * FROM proceso WHERE uid = :uid")
    fun getProcesoById(uid: Long): Flow<Proceso>

    @Query("DELETE FROM proceso")
    suspend fun deleteAllProcesos()

    @Update
    suspend fun updateProceso(proceso: Proceso)

    @Insert
    suspend fun insertAllProcesos(vararg proceso: Proceso)

    @Delete
    suspend fun deleteProceso(proceso: Proceso)

}