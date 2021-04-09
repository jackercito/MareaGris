package com.jackercito.mareagris.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jackercito.mareagris.models.Empresa
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
}