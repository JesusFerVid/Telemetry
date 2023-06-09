package com.jesusfervid.telemetry.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jesusfervid.telemetry.model.Vehiculo
import com.jesusfervid.telemetry.repository.VehiculoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel para VehiculosFragment y EditarVehiculoFragment
 */
class VehiculosViewModel(app : Application) : AndroidViewModel(app) {
  val vehiculosLD = MutableLiveData<List<Vehiculo>>()

  init {
  	VehiculoRepository(getApplication<Application>().applicationContext)
    getVehiculos()
  }

  // Estas funciones llaman al método homónimo del repositorio y actualizan el LiveData.
  // Utilizan corrutinas, para no bloquear el hilo principal.
  fun addVehiculo(vehiculo : Vehiculo) = viewModelScope.launch(Dispatchers.IO) {
    VehiculoRepository.add(vehiculo)
  }

  fun addVehiculos(vehiculos : List<Vehiculo>) = viewModelScope.launch(Dispatchers.IO) {
    VehiculoRepository.addAll(vehiculos)
  }

  fun modifyVehiculo(vehiculo : Vehiculo) = viewModelScope.launch(Dispatchers.IO) {
    VehiculoRepository.modify(vehiculo)
  }

  fun removeVehiculo(vehiculo : Vehiculo) = viewModelScope.launch(Dispatchers.IO) {
    VehiculoRepository.remove(vehiculo)
  }

  fun getVehiculos() = viewModelScope.launch(Dispatchers.IO) {
    vehiculosLD.postValue(VehiculoRepository.getAll())
  }
}
