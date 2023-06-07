package com.jesusfervid.telemetry.repository

import android.app.Application
import android.content.Context
import com.jesusfervid.telemetry.dao.LineaRevisionDao
import com.jesusfervid.telemetry.db.TelemetryDatabase
import com.jesusfervid.telemetry.model.LineaRevision

/**
 * Los Repository hacen de mediador entre la interfaz y los DAO.
 * Al declararlo como object, se trata de un Singleton.
 */
object LineaRevisionRepository {
  // Referencia al DAO especifico.
  private lateinit var lineaRevisionDao : LineaRevisionDao

  // Contexto para recuperar datos.
  private lateinit var app : Application

  /**
   * Para un Singleton, esto es como su constructor
   * Al recuperar su DAO de la BD, también la crea, si fuese necesario.
   */
  operator fun invoke(context : Context) {
    this.app = context.applicationContext as Application
    this.lineaRevisionDao = TelemetryDatabase.getInstancia(app).lineaRevisionDao()
  }

  // Estas funciones se encargan de llamar a la función adecuada en los DAO.
  // Trabajan con corrutinas, para no bloquear el hilo principal.
  suspend fun add(linea : LineaRevision) = lineaRevisionDao.add(linea)
  suspend fun addAll(lineas : List<LineaRevision>) = lineaRevisionDao. addAll(lineas)
  suspend fun modify(linea : LineaRevision) = lineaRevisionDao.modify(linea)
  suspend fun remove(linea : LineaRevision) = lineaRevisionDao.remove(linea)
  suspend fun getByIdRevision(idRevision : Long) = lineaRevisionDao.getByIdRevision(idRevision)
}
