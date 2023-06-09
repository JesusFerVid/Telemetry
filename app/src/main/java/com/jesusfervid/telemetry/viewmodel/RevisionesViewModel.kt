package com.jesusfervid.telemetry.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jesusfervid.telemetry.model.Revision
import com.jesusfervid.telemetry.repository.RevisionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel para RevisionesFragment, DetalleRevisionFragment y EditarRevisionFragment
 */
class RevisionesViewModel(app : Application) : AndroidViewModel(app) {
  val revisionesLD = MutableLiveData<List<Revision>>()

  init {
    RevisionRepository(getApplication<Application>().applicationContext)
  }

  // Estas funciones llaman al método homónimo del repositorio y actualizan el LiveData.
  // Utilizan corrutinas, para no bloquear el hilo principal.
  fun addRevision(revision : Revision) = viewModelScope.launch(Dispatchers.IO) {
    RevisionRepository.add(revision)
  }

  fun addRevisiones(revisiones : List<Revision>) = viewModelScope.launch(Dispatchers.IO) {
    RevisionRepository.addAll(revisiones)
  }

  fun modifyRevision(revision : Revision) = viewModelScope.launch(Dispatchers.IO) {
    RevisionRepository.modify(revision)
  }

  fun removeRevision(revision : Revision) = viewModelScope.launch(Dispatchers.IO) {
    RevisionRepository.remove(revision)
    val nuevaLista = revisionesLD.value?.toMutableList()
    nuevaLista?.remove(revision)
    revisionesLD.postValue(nuevaLista!!)
  }

  fun getRevisiones(id : Long) = viewModelScope.launch(Dispatchers.IO) {
    revisionesLD.postValue(RevisionRepository.getByIdVehiculo(id))
  }
}
