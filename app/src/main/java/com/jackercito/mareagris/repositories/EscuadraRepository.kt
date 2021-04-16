package com.jackercito.mareagris.repositories

import androidx.annotation.WorkerThread
import com.jackercito.mareagris.daos.EscuadraDao
import com.jackercito.mareagris.models.Escuadra
import com.jackercito.mareagris.models.REscuadraProceso
import kotlinx.coroutines.flow.Flow

class EscuadraRepository(private val escuadraDao: EscuadraDao) {
    val allEscuadras: Flow<List<Escuadra>> = escuadraDao.getAllEscuadras()
    //val allEscuadras: Flow<List<REscuadraProceso>> = escuadraDao.getAllEscuadrasWithProcesos()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertEscuadra(escuadra: Escuadra) : Long {
        return escuadraDao.insertAllEscuadras(escuadra)
    }

    fun allEscuadrasByFaccion(idFkEscuadra: Long): Flow<List<Escuadra>>{
        return escuadraDao.getEscuadrasByFaccion(idFkEscuadra)
    }

    fun allEscuadrasWithProcesoByFaccion(idFkEscuadra: Long): Flow<List<REscuadraProceso>>{
        return escuadraDao.getEscuadrasWithProcesosByFaccion(idFkEscuadra)
    }

    fun contarMiniaturasPorTablaRelacional(idFk : Long, tabla : String): Int {
        return when (tabla) {
            "Ejercito" -> escuadraDao.countNumeroDeMinisByEscuadra(idFk)
            "Faccion" -> escuadraDao.countNumeroDeMinisByEscuadra(idFk)
            "Juego" -> escuadraDao.countNumeroDeMinisByEscuadra(idFk)
            "Empresa" -> escuadraDao.countNumeroDeMinisByEscuadra(idFk)
            else -> escuadraDao.countNumeroDeMinisByEscuadra(idFk)
        }
    }
}