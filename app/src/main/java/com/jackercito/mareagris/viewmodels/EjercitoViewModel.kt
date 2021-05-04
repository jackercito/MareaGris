package com.jackercito.mareagris.viewmodels

import androidx.lifecycle.*
import com.jackercito.mareagris.models.Ejercito
import com.jackercito.mareagris.models.REjercitoConteo
import com.jackercito.mareagris.repositories.EjercitoRepository
import kotlinx.coroutines.launch

class EjercitoViewModel(private val repository: EjercitoRepository): ViewModel() {
    val allEjercitos: LiveData<List<Ejercito>> = repository.allEjercito.asLiveData()

    fun insertEjercito(ejercito: Ejercito) = viewModelScope.launch {
        repository.insertEjercito(ejercito)
    }

    fun allEjercitosByJuego(uid: Long): LiveData<List<Ejercito>> {
        return repository.allEjercitosByJuego(uid).asLiveData()
    }

    fun allEjercitosConConteoByIdJuego(uid: Long): LiveData<List<REjercitoConteo>> {
        return repository.allEjercitosConConteoByIdJuego(uid).asLiveData()
    }
}

class EjercitoViewModelFactory(private val repository: EjercitoRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) : T {
        if(modelClass.isAssignableFrom(EjercitoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EjercitoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}