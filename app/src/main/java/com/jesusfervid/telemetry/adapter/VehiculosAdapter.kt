package com.jesusfervid.telemetry.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jesusfervid.telemetry.R
import com.jesusfervid.telemetry.databinding.ItemVehiculoBinding
import com.jesusfervid.telemetry.model.Vehiculo

/**
 * Adapter para la lista de vehiculos.
 * Los Adapter hacen de puente entre los datos y la interfaz de una RecyclerView.
 */
class VehiculosAdapter() : RecyclerView.Adapter<VehiculosAdapter.VehiculosViewHolder>() {
  var vehiculos: List<Vehiculo>? = null
  var onVehiculoClickListener: OnVehiculoClickListener? = null

  /** Los ViewHolders representan elementos (Views) individuales dentro de una RecyclerView **/
  inner class VehiculosViewHolder(val binding: ItemVehiculoBinding)
    : RecyclerView.ViewHolder(binding.root) {
    init {
      // Click sobre el item (ConstraintLayout)
      binding.root.setOnClickListener {
        val vehiculo = vehiculos?.get(this.bindingAdapterPosition)
        onVehiculoClickListener?.onVehiculoClick(vehiculo)
      }

      // Click sobre el botón editar
      binding.ivEditarVehiculo.setOnClickListener {
        val vehiculo = vehiculos?.get(this.bindingAdapterPosition)
        onVehiculoClickListener?.onVehiculoEditarClick(vehiculo)
      }

      // Click sobre el botón borrar
      binding.ivBorrarVehiculo.setOnClickListener {
        val vehiculo = vehiculos?.get(this.bindingAdapterPosition)
        onVehiculoClickListener?.onVehiculoBorrarClick(vehiculo)
      }
    }
  }

  /** Actualiza la lista y notifica al adaptador para redibujar el ReciclerView */
  fun setListaVehiculos(vehiculos: List<Vehiculo>){
    this.vehiculos = vehiculos
    notifyDataSetChanged()
  }

  /** Rellena (infla) el ViewHolder con todos los elementos y lo devuelve */
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehiculosViewHolder {
    val binding = ItemVehiculoBinding.inflate(
      LayoutInflater.from(parent.context), parent, false
    )
    return VehiculosViewHolder(binding)
  }

  /** Método principal, llamado cada vez que hay que dibujar un item */
  override fun onBindViewHolder(holder: VehiculosViewHolder, position: Int) {
    // Nos pasan la posición del  item a mostrar en el viewHolder
    with(holder) {
      // Obtenemos el contexto para acceder a los recursos de strings.xml
      val context = this.itemView.context
      // Recuperamos el item a mostrar y rellenamos los campos del ViewHolder
      with(vehiculos!![position]) {
        // Establecemos el icono según el tipo
        binding.ivTipoVehiculo.setImageResource(
          when (this.tipo) {
            context.getString(R.string.coche) -> R.drawable.coche
            context.getString(R.string.moto) -> R.drawable.motocicleta
            context.getString(R.string.ciclomotor) -> R.drawable.ciclomotor
            context.getString(R.string.furgoneta) -> R.drawable.furgoneta
            context.getString(R.string.camion) -> R.drawable.camion
            else -> R.drawable.coche
          }
        )

        binding.tvNombreVehiculo.text = this.nombre
        binding.tvDescripcionVehiculo.text = formarDescripcion(this)
      }
    }
  }

  /** Devuelve el número de items en la lista, o 0 si es nula */
  override fun getItemCount(): Int = vehiculos?.size?:0

  /** Interfaz que define los listeners para la Vehiculo */
  interface OnVehiculoClickListener{
    /** Navega al detalle del item */
    fun onVehiculoClick(vehiculo : Vehiculo?)

    /** Edita el item  que contiene el ViewHolder */
    fun onVehiculoEditarClick(vehiculo : Vehiculo?)

    /** Borra el item que contiene el ViewHolder */
    fun onVehiculoBorrarClick(vehiculo : Vehiculo?)
  }

  /**
   * Función auxiliar que permite formar la descripción de un vehículo, es decir,
   * la línea mostrada bajo su nombre en la lista, a partir de sus campos, según cuales haya.
   * Si no hay información, mostrará simplemente el tipo de vehículo.
   * @param vehiculo El vehículo a procesar.
   * @return La descripción formada, como [String]
   */
  private fun formarDescripcion(vehiculo : Vehiculo) : String {
    var descripcion : String = ""
    // Si se ha indicado el modelo de coche
    if (!vehiculo.modelo.isNullOrBlank()) {
      descripcion =
        if (vehiculo.anyo != null)
          "${vehiculo.modelo} (${vehiculo.anyo})"
        else
          vehiculo.modelo
    } else {
      descripcion =
        if (vehiculo.anyo != null)
          vehiculo.anyo.toString()
        else
        vehiculo.tipo
    }
    return descripcion
  }
}
