package com.jackercito.mareagris.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jackercito.mareagris.models.Faccion
import com.jackercito.mareagris.models.TuplaConteo
import kotlinx.coroutines.flow.Flow

@Dao
interface FaccionDao {
    @Query("SELECT * FROM faccion")
    fun getAllFacciones(): Flow<List<Faccion>>

    @Query("SELECT * FROM faccion WHERE idFkEjercito = :idFkEjercito")
    fun getFaccionesByEjercito(idFkEjercito: Long): Flow<List<Faccion>>

    @Query("DELETE FROM faccion")
    suspend fun deleteAllFacciones()

    @Insert
    suspend fun insertAllFacciones(vararg faccion: Faccion)

    @Delete
    suspend fun deleteFaccion(faccion: Faccion)

    @Query("SELECT uid, estado  FROM proceso WHERE idFkEscuadra IN (SELECT uid FROM Escuadra WHERE uid = :uid)")
    fun countNumeroDeMinis(uid: Long): Flow<List<TuplaConteo>>
}