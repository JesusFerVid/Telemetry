package com.jesusfervid.telemetry.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

/**
 * Representa una revision, de cualquier vehículo.
 * No es necesario pasar un id, se genera automáticamente
 */
@Entity(tableName = "revision")
@Parcelize
data class Revision(
  val id_vehiculo : Long,
  val fecha : String,
  val km : Int,
  val kmSiguiente : Int,
  val observaciones: String? = null,
  @PrimaryKey(autoGenerate = true) val id : Long? = null
) : Parcelable

/**
 * Representa la relación 1:N entre Vehiculo y Revision
 */
data class VehiculoConRevisiones (
  @Embedded val vehiculo : Vehiculo,
  @Relation(
    parentColumn = "id",
    entityColumn = "id_vehiculo"
  )
  val revisiones : List<Revision>
)
