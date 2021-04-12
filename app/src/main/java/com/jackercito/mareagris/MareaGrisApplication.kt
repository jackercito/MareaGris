package com.jackercito.mareagris

import android.app.Application
import com.jackercito.mareagris.database.MareaGrisDatabase
import com.jackercito.mareagris.repositories.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MareaGrisApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { MareaGrisDatabase.getDatabase(this, applicationScope) }
    val repositoryEmpresa by lazy { EmpresaRepository(database.empresaDao()) }
    val repositoryJuego by lazy { JuegoRepository(database.juegoDao()) }
    val repositoryEjercito by lazy { EjercitoRepository(database.ejercitoDao()) }
    val repositoryFaccion by lazy { FaccionRespository(database.faccionDao()) }
    val repositoryEscuadra by lazy { EscuadraRepository(database.escuadraDao()) }
    val repositoryProceso by lazy { ProcesoRepository(database.procesoDao()) }
}