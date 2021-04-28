package com.jackercito.mareagris.repositories

import androidx.annotation.WorkerThread
import com.jackercito.mareagris.daos.FaccionDao
import com.jackercito.mareagris.models.Faccion
import com.jackercito.mareagris.models.TuplaConteo
import kotlinx.coroutines.flow.Flow

class FaccionRespository (private val faccionDao: FaccionDao) {
    val allFaccion : Flow<List<Faccion>> = faccionDao.getAllFacciones()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertarFaccion(faccion: Faccion){
        faccionDao.insertAllFacciones(faccion)
    }

    fun allFaccionesByEjercito(idFkEjercito: Long): Flow<List<Faccion>> {
        return faccionDao.getFaccionesByEjercito(idFkEjercito)
    }

    fun countAllMinisByIdFaccion(uidFaccion : Long) : Flow<List<TuplaConteo>> {
        return faccionDao.countNumeroDeMinis(uidFaccion)
    }
}