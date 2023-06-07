package com.jesusfervid.telemetry.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jesusfervid.telemetry.model.LineaRevision

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
