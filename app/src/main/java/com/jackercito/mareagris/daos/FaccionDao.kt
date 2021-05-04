package com.jackercito.mareagris.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jackercito.mareagris.models.Faccion
import com.jackercito.mareagris.models.RFaccionConteo
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

    @Query("SELECT Faccion.*, (SELECT COUNT(uid) FROM proceso WHERE idFkEscuadra IN (SELECT uid FROM Escuadra WHERE idFkFaccion = Faccion.uid)) as totalEmpresa, (SELECT COUNT(uid)  FROM proceso WHERE estado = 'Finalizado' AND idFkEscuadra IN (SELECT uid FROM Escuadra WHERE idFkFaccion = Faccion.uid)) as pintadas, (SELECT COUNT(uid) FROM proceso) as total, (SELECT COUNT(uid) FROM proceso WHERE estado = 'Finalizado') as pintadasTotal FROM Faccion WHERE idFkEjercito = :idFkEjercito")
    fun allFaccionConConteoByIdJuego(idFkEjercito: Long): Flow<List<RFaccionConteo>>
}