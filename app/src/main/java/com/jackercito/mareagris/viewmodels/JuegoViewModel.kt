package com.jackercito.mareagris.viewmodels

import androidx.lifecycle.*
import com.jackercito.mareagris.models.Juego
import com.jackercito.mareagris.repositories.JuegoRepository
import kotlinx.coroutines.launch

class JuegoViewModel (private val repository: JuegoRepository) : ViewModel() {
    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allJuegos: LiveData<List<Juego>> = repository.allJuegos.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(juego: Juego) = viewModelScope.launch{
        repository.insert(juego)
    }

    fun allJuegosByEmpresa(uid: Long): LiveData<List<Juego>> {
        return repository.allJuegosByEmpresa(uid).asLiveData()
    }
}

class JuegoViewModelFactory(private val repository: JuegoRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(JuegoViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return JuegoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}