package com.jackercito.mareagris.repositories

import androidx.annotation.WorkerThread
import com.jackercito.mareagris.daos.EmpresaDao
import com.jackercito.mareagris.models.Empresa
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class EmpresaRepository (private val empresaDao: EmpresaDao) {
    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.

    val allEmpresas: Flow<List<Empresa>> = empresaDao.getAll()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(empresa: Empresa){
        empresaDao.insertAll(empresa)
    }
}