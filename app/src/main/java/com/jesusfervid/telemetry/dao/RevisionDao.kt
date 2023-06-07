package com.jesusfervid.telemetry.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jesusfervid.telemetry.model.Revision

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
