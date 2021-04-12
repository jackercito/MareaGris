package com.jackercito.mareagris.viewmodels

import androidx.lifecycle.*
import com.jackercito.mareagris.models.Faccion
import com.jackercito.mareagris.repositories.FaccionRespository
import kotlinx.coroutines.launch

class FaccionViewModel (private val repository: FaccionRespository): ViewModel() {
    val allFacciones: LiveData<List<Faccion>> = repository.allFaccion.asLiveData()

    fun insertFaccion(faccion: Faccion) = viewModelScope.launch {
        repository.insertarFaccion(faccion)
    }

    fun allFaccionesByEjercito(uid: Long): LiveData<List<Faccion>> {
        return repository.allFaccionesByEjercito(uid).asLiveData()
    }
}

class FaccionViewModelFactory(private val repository: FaccionRespository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) : T {
        if(modelClass.isAssignableFrom(FaccionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FaccionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}