package com.jackercito.mareagris.repositories

import androidx.annotation.WorkerThread
import com.jackercito.mareagris.daos.EjercitoDao
import com.jackercito.mareagris.models.Ejercito
import com.jackercito.mareagris.models.REjercitoConteo
import kotlinx.coroutines.flow.Flow

class EjercitoRepository(private val ejercitoDao: EjercitoDao) {
    val allEjercito: Flow<List<Ejercito>> = ejercitoDao.getAllEjercitos()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertEjercito(ejercito: Ejercito){
        ejercitoDao.insertAllEjercitos(ejercito)
    }

    fun allEjercitosByJuego(idFkJuego: Long): Flow<List<Ejercito>>{
        return ejercitoDao.getEjercitoByJuego(idFkJuego)
    }

    fun allEjercitosConConteoByIdJuego(idFkJuego: Long): Flow<List<REjercitoConteo>>{
        return ejercitoDao.allEjercitosConConteoByIdJuego(idFkJuego)
    }
}