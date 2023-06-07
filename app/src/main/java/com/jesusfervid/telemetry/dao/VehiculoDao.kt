package com.jesusfervid.telemetry.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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
