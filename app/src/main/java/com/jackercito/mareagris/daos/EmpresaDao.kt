package com.jackercito.mareagris.daos

import androidx.room.*
import com.jackercito.mareagris.models.Empresa
import com.jackercito.mareagris.models.REmpresaConteo
import com.jackercito.mareagris.models.REmpresaJuegos
import kotlinx.coroutines.flow.Flow

@Dao
interface EmpresaDao {
    @Query("SELECT * FROM empresa")
    fun getAll(): Flow<List<Empresa>>

    @Query("SELECT * FROM empresa WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): Flow<List<Empresa>>

    @Query("SELECT * FROM empresa WHERE nombreEmpresa LIKE :nombreEmpresa LIMIT 1")
    fun findByNameEmpresa(nombreEmpresa: String): Flow<Empresa>

    @Insert
    suspend fun insertAll(vararg empresa: Empresa)

    @Delete
    suspend fun delete(empresa: Empresa)

    @Query("DELETE FROM empresa")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM empresa")
    fun getEmpresasWhithJuegos(): Flow<List<REmpresaJuegos>>

    @Query("SELECT EMPRESA.*, (SELECT COUNT(uid) FROM proceso WHERE idFkEscuadra IN (SELECT uid FROM Escuadra WHERE idFkFaccion IN (SELECT uid FROM Faccion WHERE idFkEjercito IN (SELECT uid from Ejercito WHERE idFkJuego IN (SELECT uid FROM Juego WHERE idFkEmpresa = EMPRESA.uid))))) as totalEmpresa, (SELECT COUNT(uid)  FROM proceso WHERE estado = 'Finalizado' AND idFkEscuadra IN (SELECT uid FROM Escuadra WHERE idFkFaccion IN (SELECT uid FROM Faccion WHERE idFkEjercito IN (SELECT uid from Ejercito WHERE idFkJuego IN (SELECT uid FROM Juego WHERE idFkEmpresa = EMPRESA.uid))))) as pintadas, (SELECT COUNT(uid) FROM proceso) as total, (SELECT COUNT(uid) FROM proceso WHERE estado = 'Finalizado') as pintadasTotal   FROM empresa")
    fun getEmpresasConConteo(): Flow<List<REmpresaConteo>>

    /*@Query("SELECT COUNT(uid)  FROM proceso WHERE estado = :estado AND idFkEscuadra IN (SELECT uid FROM Escuadra WHERE idFkFaccion IN (SELECT uid FROM Faccion WHERE idFkEjercito IN (SELECT uid from Ejercito WHERE idFkJuego IN (SELECT uid FROM Juego WHERE idFkEmpresa IN (SELECT uid FROM Empresa WHERE uid = :uid)))))")
    fun countNumeroDeMinisConEstado(estado : String, uid: Long): Int

    @Query("SELECT COUNT(uid) FROM proceso WHERE idFkEscuadra IN (SELECT uid FROM Escuadra WHERE idFkFaccion IN (SELECT uid FROM Faccion WHERE idFkEjercito IN (SELECT uid from Ejercito WHERE idFkJuego IN (SELECT uid FROM Juego WHERE idFkEmpresa IN (SELECT uid FROM Empresa WHERE uid = :uid)))))")
    fun countNumeroDeMinis(uid: Long): Int*/
}