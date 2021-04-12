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

    fun allProcesosByEscuadra(idFkEscuadra: Long){
        procesoDao.getAllProcesosByEscuadra(idFkEscuadra)
    }
}