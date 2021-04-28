package com.jackercito.mareagris.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jackercito.mareagris.models.Juego
import com.jackercito.mareagris.models.REmpresaConteo
import kotlinx.coroutines.flow.Flow

@Dao
interface JuegoDao {
    @Query("SELECT * FROM juego")
    fun getAll(): Flow<List<Juego>>

    @Query("SELECT * FROM juego WHERE idFkEmpresa = :idFkEmpresa")
    fun getJuegosByEmpresa(idFkEmpresa: Long): Flow<List<Juego>>

    @Query("DELETE FROM juego")
    suspend fun deleteAll()

    @Insert
    suspend fun insertAll(vararg juego: Juego)

    @Delete
    suspend fun delete(juego: Juego)

    @Query("SELECT JUEGO.*, (SELECT COUNT(uid) FROM proceso WHERE idFkEscuadra IN (SELECT uid FROM Escuadra WHERE idFkFaccion IN (SELECT uid FROM Faccion WHERE idFkEjercito IN (SELECT uid from Ejercito WHERE idFkJuego IN (SELECT uid FROM Juego WHERE idFkEmpresa = EMPRESA.uid))))) as totalEmpresa, (SELECT COUNT(uid)  FROM proceso WHERE estado = 'Finalizado' AND idFkEscuadra IN (SELECT uid FROM Escuadra WHERE idFkFaccion IN (SELECT uid FROM Faccion WHERE idFkEjercito IN (SELECT uid from Ejercito WHERE idFkJuego IN (SELECT uid FROM Juego WHERE idFkEmpresa = EMPRESA.uid))))) as pintadas, (SELECT COUNT(uid) FROM proceso) as total, (SELECT COUNT(uid) FROM proceso WHERE estado = 'Finalizado') as pintadasTotal   FROM empresa")
    fun getEmpresasConConteo(): Flow<List<REmpresaConteo>>
}