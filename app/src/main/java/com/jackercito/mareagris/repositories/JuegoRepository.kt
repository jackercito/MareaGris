package com.jackercito.mareagris.repositories

import androidx.annotation.WorkerThread
import com.jackercito.mareagris.daos.JuegoDao
import com.jackercito.mareagris.models.Juego
import kotlinx.coroutines.flow.Flow

class JuegoRepository (private val juegoDao: JuegoDao) {
    val allJuegos: Flow<List<Juego>> = juegoDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(juego: Juego){
        juegoDao.insertAll(juego)
    }
}