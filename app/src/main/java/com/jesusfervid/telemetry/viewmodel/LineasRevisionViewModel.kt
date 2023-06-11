package com.jesusfervid.telemetry.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jesusfervid.telemetry.model.LineaRevision
import com.jesusfervid.telemetry.repository.LineaRevisionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LineasRevisionViewModel (app : Application) : AndroidViewModel(app) {
  val lineasLD = MutableLiveData<List<LineaRevision>>()

  init {
    LineaRevisionRepository(getApplication<Application>().applicationContext)
  }

  // TODO: ¿Actualizar la lista en cada método?

  // Estas funciones llaman al método homónimo del repositorio y actualizan el LiveData.
  // Utilizan corrutinas, para no bloquear el hilo principal.
  fun addLineaRevision(linea : LineaRevision) = viewModelScope.launch(Dispatchers.IO) {
    LineaRevisionRepository.add(linea)
  }

  fun addLineasRevision(lineas : List<LineaRevision>) = viewModelScope.launch(Dispatchers.IO) {
    LineaRevisionRepository.addAll(lineas)
  }

  fun modifyLineaRevision(linea : LineaRevision) = viewModelScope.launch(Dispatchers.IO) {
    LineaRevisionRepository.modify(linea)
  }

  fun removeLineaRevision(linea : LineaRevision) = viewModelScope.launch(Dispatchers.IO) {
    LineaRevisionRepository.remove(linea)
    val nuevaLista = lineasLD.value?.toMutableList()
    nuevaLista?.remove(linea)
    lineasLD.postValue(nuevaLista!!)
  }

  fun getLineasRevision(id : Long) = viewModelScope.launch(Dispatchers.IO) {
    lineasLD.postValue(LineaRevisionRepository.getByIdRevision(id))
  }
}
