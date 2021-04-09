package com.jackercito.mareagris.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jackercito.mareagris.models.Juego
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
}