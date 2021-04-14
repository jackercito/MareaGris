package com.jackercito.mareagris.viewmodels

import androidx.lifecycle.*
import com.jackercito.mareagris.models.Escuadra
import com.jackercito.mareagris.repositories.EscuadraRepository
import kotlinx.coroutines.launch

class EscuadraViewModel (private val repository: EscuadraRepository): ViewModel() {
    val allEscuadras : LiveData<List<Escuadra>> =repository.allEscudras.asLiveData()

    fun insertEscuadra(escuadra: Escuadra): LiveData<Long> {
        val liveData = MutableLiveData<Long>()
         viewModelScope.launch {
             liveData.value = repository.insertEscuadra(escuadra)
        }
        return liveData
    }

    fun allEscuadrasByFaccion(uid: Long): LiveData<List<Escuadra>> {
        return repository.allEscuadrasByFaccion(uid).asLiveData()
    }
}

class EscuadraViewModelFactory(private val repository: EscuadraRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(EscuadraViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return EscuadraViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}