package com.jackercito.mareagris.repositories

import androidx.annotation.WorkerThread
import com.jackercito.mareagris.daos.EscuadraDao
import com.jackercito.mareagris.models.Escuadra
import kotlinx.coroutines.flow.Flow

class EscuadraRepository(private val escuadraDao: EscuadraDao) {
    val allEscudras: Flow<List<Escuadra>> = escuadraDao.getAllEscuadras()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertEscuadra(escuadra: Escuadra){
        escuadraDao.insertAllEscuadras(escuadra)
    }

    fun allEscuadrasByFaccion(idFkEscuadra: Long): Flow<List<Escuadra>>{
        return escuadraDao.getEscuadrasByFaccion(idFkEscuadra)
    }
}