package com.jackercito.mareagris.repositories

import androidx.annotation.WorkerThread
import com.jackercito.mareagris.daos.ProcesoDao
import com.jackercito.mareagris.models.Proceso
import kotlinx.coroutines.flow.Flow

class ProcesoRepository(private val procesoDao: ProcesoDao) {
    val allProcesos: Flow<List<Proceso>> = procesoDao.getAllProcesos()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertProceso(proceso: Proceso){
        procesoDao.insertAllProcesos(proceso)
    }

    fun getProcesoById(uid: Long): Flow<Proceso> {
        return procesoDao.getProcesoById(uid)
    }

    fun allProcesosByEscuadra(idFkEscuadra: Long): Flow<List<Proceso>>{
        return procesoDao.getAllProcesosByEscuadra(idFkEscuadra)
    }

    suspend fun updateProceso(proceso: Proceso) {
        procesoDao.updateProceso(proceso)
    }
}