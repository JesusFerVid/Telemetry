package com.jesusfervid.telemetry.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/** Representa cada línea del cuerpo de una revisión */
@Entity(
  tableName = "linea_revision",
  foreignKeys = [
    ForeignKey(
      entity = Revision::class,
      parentColumns = ["id"],
      childColumns = ["id_revision"],
      onDelete = ForeignKey.CASCADE,
    )
  ]
)
@Parcelize
data class LineaRevision(
  val id_revision : Long,
  val tipo : String,
  val realizada : Boolean = false,
  val km : Int? = null,
  @PrimaryKey(autoGenerate = true) val id : Long? = null,
) : Parcelable
