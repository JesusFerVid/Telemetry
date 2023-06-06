package com.jesusfervid.telemetry.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "vehiculo")
@Parcelize
data class Vehiculo(
 val tipo : String,
 val nombre : String = tipo,
 val modelo : String? = null,
 val anyo : Int? = null,
  @PrimaryKey(autoGenerate = true) val id : Long? = null,
) : Parcelable
