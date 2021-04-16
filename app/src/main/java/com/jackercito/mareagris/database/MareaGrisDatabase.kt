package com.jackercito.mareagris.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jackercito.mareagris.daos.*
import com.jackercito.mareagris.models.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Empresa::class, Juego::class, Ejercito::class, Faccion::class, Escuadra::class, Proceso::class], version = 8, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MareaGrisDatabase: RoomDatabase() {
    abstract fun empresaDao(): EmpresaDao
    abstract fun juegoDao(): JuegoDao
    abstract fun ejercitoDao(): EjercitoDao
    abstract fun faccionDao(): FaccionDao
    abstract fun escuadraDao(): EscuadraDao
    abstract fun procesoDao(): ProcesoDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: MareaGrisDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): MareaGrisDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MareaGrisDatabase::class.java,
                    "marea_gris_database"
                )
                        .fallbackToDestructiveMigration()
                        .addCallback(MareaGrisDatabaseCallback(scope))
                        .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class MareaGrisDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    //database.empresaDao().deleteAll()
                    //database.juegoDao().deleteAll()
                    //database.ejercitoDao().deleteAllEjercitos()
                    //database.faccionDao().deleteAllFacciones()
                    //database.escuadraDao().deleteAllEscuadras()
                    //database.procesoDao().deleteAllProcesos()
                }
            }
        }
    }
}