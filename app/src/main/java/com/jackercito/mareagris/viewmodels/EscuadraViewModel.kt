package com.jackercito.mareagris.viewmodels

import androidx.lifecycle.*
import com.jackercito.mareagris.models.Escuadra
import com.jackercito.mareagris.models.REscuadraProceso
import com.jackercito.mareagris.repositories.EscuadraRepository
import kotlinx.coroutines.launch

class EscuadraViewModel (private val repository: EscuadraRepository): ViewModel() {
    val allEscuadras : LiveData<List<Escuadra>> =repository.allEscuadras.asLiveData()

    fun insertEscuadra(escuadra: Escuadra): LiveData<Long> {
        val liveData = MutableLiveData<Long>()
         viewModelScope.launch {
             liveData.value = repository.insertEscuadra(escuadra)
        }
        return liveData
    }

    fun updateEscuadra(escuadra: Escuadra) = viewModelScope.launch{
        repository.updateEscudra(escuadra)
    }

    fun allEscuadrasByUnidad(nombreUnidad: String): LiveData<List<Escuadra>> {
        return repository.allEscuadrasByUnidad(nombreUnidad).asLiveData()
    }

    fun allEscuadrasByFaccion(uid: Long): LiveData<List<REscuadraProceso>> {
        return repository.allEscuadrasWithProcesoByFaccion(uid).asLiveData()
    }

    fun contarMinis(idFk: Long, tabla: String) : Int{
        return repository.contarMiniaturasPorTablaRelacional(idFk, tabla)
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