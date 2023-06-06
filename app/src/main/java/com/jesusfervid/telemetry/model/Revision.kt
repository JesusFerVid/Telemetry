package com.jesusfervid.telemetry.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

/** Representa una revision, de cualquier vehículo */
@Entity(tableName = "revision")
@Parcelize
data class Revision(
  @PrimaryKey(autoGenerate = true)
  val id : Long?,

  val id_vehiculo : Long?,
  val fecha : String,
  val km : Int,
  val kmSiguiente : Int,
  val observaciones: String?
) : Parcelable {
  // Constructor sin id. Lo generará Room.
  @Ignore
  constructor(
    id_vehiculo : Long?,
    fecha : String,
    km : Int,
    kmSiguiente : Int,
    observaciones: String?
  ) : this(null, id_vehiculo, fecha, km, kmSiguiente, observaciones)
}

data class RevisionConLineas (
  @Embedded
  val revision : Revision,

  @Relation(
    parentColumn = "id",
    entityColumn = "id_vehiculo"
  )
  val lineasRevision : List<LineaRevision>
)

