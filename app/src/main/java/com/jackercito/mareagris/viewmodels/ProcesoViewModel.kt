package com.jackercito.mareagris.viewmodels

import androidx.lifecycle.*
import com.jackercito.mareagris.models.Proceso
import com.jackercito.mareagris.repositories.ProcesoRepository
import kotlinx.coroutines.launch

class ProcesoViewModel(private val repository: ProcesoRepository): ViewModel(){
    val allProcesos : LiveData<List<Proceso>> = repository.allProcesos.asLiveData()

    fun insertProceso(proceso: Proceso) = viewModelScope.launch {
        repository.insertProceso(proceso)
    }

    fun getProcesoById(uid: Long): LiveData<Proceso> {
        return repository.getProcesoById(uid).asLiveData()
    }

    fun allProcesosByEscuadra(uid: Long): LiveData<List<Proceso>> {
        return repository.allProcesosByEscuadra(uid).asLiveData()
    }

    fun updateProceso(proceso: Proceso) = viewModelScope.launch{
        repository.updateProceso(proceso)
    }
}

class ProcesoViewModelFactory(private val repository: ProcesoRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProcesoViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ProcesoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}