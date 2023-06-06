package com.jesusfervid.telemetry.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/** Representa cada línea del cuerpo de una revisión */
@Entity(tableName = "linea_revision")
@Parcelize
data class LineaRevision(
  @PrimaryKey(autoGenerate = true)
  val id : Long?,

  val id_revision : Long,
  val tipo : String,
  val realizada : Boolean,
  val km : Int?
) : Parcelable {
  @Ignore
  constructor(
    tipo : String,
    id_revision : Long,
    realizada : Boolean = false,
    km : Int? = null
  ) : this(null, id_revision, tipo, realizada, km)
}
