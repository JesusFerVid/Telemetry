package com.jesusfervid.telemetry.repository

import android.app.Application
import android.content.Context
import com.jesusfervid.telemetry.dao.RevisionDao
import com.jesusfervid.telemetry.db.TelemetryDatabase
import com.jesusfervid.telemetry.model.Revision

/**
 * Los Repository hacen de mediador entre la interfaz y los DAO.
 * Al declararlo como object, se trata de un Singleton.
 */
object RevisionRepository {
  // Referencia al DAO especifico.
  private lateinit var revisionDao : RevisionDao

  // Contexto para recuperar datos.
  private lateinit var app : Application

  /**
   * Para un Singleton, esto es como su constructor
   * Al recuperar su DAO de la BD, también la crea, si fuese necesario.
   */
  operator fun invoke(context : Context) {
    this.app = context.applicationContext as Application
    this.revisionDao = TelemetryDatabase.getInstancia(app).revisionDao()
  }

  // Estas funciones se encargan de llamar a la función adecuada en los DAO.
  suspend fun add(revision : Revision) = revisionDao.add(revision)
  suspend fun addAll(revisiones : List<Revision>) = revisionDao.addAll(revisiones)
  suspend fun modify(revision : Revision) = revisionDao.modify(revision)
  suspend fun remove(revision : Revision) = revisionDao.remove(revision)
  suspend fun getAll()  = revisionDao. getAll()
  suspend fun getByIdVehiculo(idVehiculo : Long) = revisionDao.getByIdVehiculo(idVehiculo)
}
