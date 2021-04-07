package com.jackercito.mareagris.viewmodels

import androidx.lifecycle.*
import com.jackercito.mareagris.models.Empresa
import com.jackercito.mareagris.repositories.EmpresaRepository
import kotlinx.coroutines.launch

class EmpresaViewModel (private val repository: EmpresaRepository) : ViewModel() {
    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allEmpresas: LiveData<List<Empresa>> = repository.allEmpresas.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(empresa: Empresa) = viewModelScope.launch{
        repository.insert(empresa)
    }
}

class EmpresaViewModelFactory(private val repository: EmpresaRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(EmpresaViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return EmpresaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}