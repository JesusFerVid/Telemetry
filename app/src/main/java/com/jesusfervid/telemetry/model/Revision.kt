package com.jesusfervid.telemetry.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Representa una revision, de cualquier vehículo.
 * No es necesario pasar un id, se genera automáticamente
 */
@Entity(
  tableName = "revision",
  foreignKeys = [
    ForeignKey(
      entity = Vehiculo::class,
      parentColumns = ["id"],
      childColumns = ["id_vehiculo"],
      onDelete = ForeignKey.CASCADE
    )
  ]
)
@Parcelize
data class Revision(
  val id_vehiculo : Long,
  val fecha : String,
  val km : Int,
  val kmSiguiente : Int,
  val observaciones: String? = null,
  @PrimaryKey(autoGenerate = true) val id : Long? = null
) : Parcelable
