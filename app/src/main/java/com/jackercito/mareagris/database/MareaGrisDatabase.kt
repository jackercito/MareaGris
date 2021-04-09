package com.jackercito.mareagris.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jackercito.mareagris.daos.EmpresaDao
import com.jackercito.mareagris.daos.JuegoDao
import com.jackercito.mareagris.models.Empresa
import com.jackercito.mareagris.models.Juego
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Empresa::class, Juego::class], version = 3, exportSchema = false)
abstract class MareaGrisDatabase: RoomDatabase() {
    abstract fun empresaDao(): EmpresaDao
    abstract fun juegoDao(): JuegoDao

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
                    "word_database"
                )
                        .fallbackToDestructiveMigration()
                        //.addCallback(MareaGrisDatabaseCallback(scope))
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
                    populateDatabase(database.empresaDao())
                }
            }
        }

        suspend fun populateDatabase(empresaDao: EmpresaDao){
            //Borrar todo el contenido
            empresaDao.deleteAll()

            //Añadimos ejemplos
            var empresa = Empresa(0, "GWS", null)
            empresaDao.insertAll(empresa)
            var empresa2 = Empresa(0, "FFG", null)
            empresaDao.insertAll(empresa2)
        }
    }
}