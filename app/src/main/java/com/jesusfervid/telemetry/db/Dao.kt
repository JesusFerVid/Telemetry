package com.jesusfervid.telemetry.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jesusfervid.telemetry.model.LineaRevision
import com.jesusfervid.telemetry.model.Revision
import com.jesusfervid.telemetry.model.Vehiculo


@Dao
interface VehiculoDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun add(vehiculo : Vehiculo) : Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun addAll(vehiculos : List<Vehiculo>)

  @Update
  fun modify(vehiculo : Vehiculo)

  @Delete
  fun remove(vehiculo : Vehiculo)

  @Query("SELECT * FROM vehiculo")
  fun getAll() : List<Vehiculo>
}

@Dao
interface RevisionDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun add(revision : Revision) : Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun addAll(revisiones : List<Revision>)

  @Update
  fun modify(revision : Revision)

  @Delete
  fun remove(revision : Revision)

  @Query("SELECT * FROM revision")
  fun getAll() : List<Revision>

  /**
   * Devuelve todas las revisiones de un vehículo concreto
   * @param idVehiculo El id del vehículo
   */
  @Query("SELECT * FROM revision WHERE id_vehiculo = :idVehiculo")
  fun getByIdVehiculo(idVehiculo : Long) : List<Revision>
}

@Dao
interface LineaRevisionDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun add(linea : LineaRevision) : Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun addAll(lineas : List<LineaRevision>)

  @Update
  fun modify(linea : LineaRevision)

  @Delete
  fun remove(linea : LineaRevision)

  /**
   * Devuelve todas las líneas de una revisión concreta
   * @param idRevision El id de la revisión
   */
  @Query("SELECT * FROM linea_revision WHERE id_revision = :idRevision")
  fun getByIdRevision(idRevision : Long) : List<LineaRevision>
}
