package com.jackercito.mareagris

import android.app.Application
import com.jackercito.mareagris.database.MareaGrisDatabase
import com.jackercito.mareagris.repositories.EmpresaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MareaGrisApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { MareaGrisDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { EmpresaRepository(database.empresaDao()) }
}