package com.jesusfervid.telemetry.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

/** Representa cada línea del cuerpo de una revisión */
@Entity(tableName = "linea_revision")
@Parcelize
data class LineaRevision(
  val id_revision : Long,
  val tipo : String,
  val realizada : Boolean = false,
  val km : Int? = null,
  @PrimaryKey(autoGenerate = true) val id : Long? = null
) : Parcelable

/**
 * Representa la relación 1:N entre Revision y LineaRevision
 */
data class RevisionConLineas (
  @Embedded val revision : Revision,
  @Relation(
    parentColumn = "id",
    entityColumn = "id_revision"
  )
  val lineasRevision : List<LineaRevision>
)
