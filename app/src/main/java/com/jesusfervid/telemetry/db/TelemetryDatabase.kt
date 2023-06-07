package com.jesusfervid.telemetry.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jesusfervid.telemetry.model.LineaRevision
import com.jesusfervid.telemetry.model.Revision
import com.jesusfervid.telemetry.model.Vehiculo

/**
 * Esta clase mapea la BD y sus entidades (indicadas en el array entities).
 * Se indica también la versión y si queremos que sea exportada a una carpeta.
 */
@Database(
  entities = [
    Vehiculo::class,
    Revision::class,
    LineaRevision::class,
  ],
  version = 1,
  exportSchema = false,
)
abstract class TelemetryDatabase : RoomDatabase() {
  abstract fun vehiculoDao() : VehiculoDao
  abstract fun revisionDao() : RevisionDao
  abstract fun lineaRevisionDao() :  LineaRevisionDao

  // Creamos un Singleton que sea thread-safe, es decir, que no se permitirá
  // que dos hilos intenten crear una instancia a la vez.
  companion object {
    @Volatile
    private var INSTANCIA : TelemetryDatabase? = null

    fun getInstancia(context : Context) : TelemetryDatabase {
      // El operador ?: (Elvis) devolverá INSTANCE si no es null,
      // y ejecutará el bloque a su derecha en caso contrario.
      return INSTANCIA ?: synchronized(this) {
        val instanciaLocal = Room.databaseBuilder(
          context.applicationContext,
          TelemetryDatabase::class.java,
          "telemetry_db"
        )
          .build()
        INSTANCIA = instanciaLocal
        instanciaLocal
      }
    }
  }
}
