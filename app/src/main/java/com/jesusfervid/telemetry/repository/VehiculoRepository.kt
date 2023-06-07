package com.jesusfervid.telemetry.repository

import android.app.Application
import android.content.Context
import com.jesusfervid.telemetry.dao.VehiculoDao
import com.jesusfervid.telemetry.db.TelemetryDatabase
import com.jesusfervid.telemetry.model.Vehiculo

/**
 * Los Repository hacen de mediador entre la interfaz y los DAO.
 * Al declararlo como object, se trata de un Singleton.
 */
object VehiculoRepository {
  // Referencia al DAO especifico.
  private lateinit var vehiculoDao : VehiculoDao

  // Contexto para recuperar datos.
  private lateinit var app : Application

  /**
   * Para un Singleton, esto es como su constructor
   * Al recuperar su DAO de la BD, también la crea, si fuese necesario.
   */
  operator fun invoke(context : Context) {
    this.app = context.applicationContext as Application
    this.vehiculoDao = TelemetryDatabase.getInstancia(app).vehiculoDao()
  }

  // Estas funciones se encargan de llamar a la función adecuada en los DAO.
  // Trabajan con corrutinas, para no bloquear el hilo principal.
  suspend fun add(vehiculo : Vehiculo) = vehiculoDao.add(vehiculo)
  suspend fun addAll(vehiculos : List<Vehiculo>) = vehiculoDao.addAll(vehiculos)
  suspend fun modify(vehiculo : Vehiculo) = vehiculoDao.modify(vehiculo)
  suspend fun remove(vehiculo : Vehiculo) = vehiculoDao.remove(vehiculo)
  suspend fun getAll() = vehiculoDao.getAll()
}
