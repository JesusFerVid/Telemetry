package com.jesusfervid.telemetry.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jesusfervid.telemetry.R
import com.jesusfervid.telemetry.databinding.ItemLineaRevisionBinding
import com.jesusfervid.telemetry.model.LineaRevision

/**
 * Adapter para las líneas de una revisión.
 * Los Adapter hacen de puente entre los datos y la interfaz de una RecyclerView.
 */
class LineasRevisionAdapter() : RecyclerView.Adapter<LineasRevisionAdapter.LineasRevisionViewHolder>() {
  var lineas: List<LineaRevision>? = null
  var onLineasRevisionClickListener: OnLineasRevisionClickListener? = null

  /** Los ViewHolders representan elementos (Views) individuales dentro de una RecyclerView **/
  inner class LineasRevisionViewHolder(val binding: ItemLineaRevisionBinding)
    : RecyclerView.ViewHolder(binding.root) {
    init {
      // Click sobre el item (ConstraintLayout)
      binding.root.setOnClickListener {
        val linea = lineas?.get(this.bindingAdapterPosition)
        onLineasRevisionClickListener?.onLineaRevisionClick(linea)
      }
    }
  }

  /** Actualiza la lista y notifica al adaptador para redibujar el ReciclerView */
  fun setListaLineasRevision(lineas: List<LineaRevision>){
    this.lineas = lineas
    notifyDataSetChanged()
  }

  /** Rellena (infla) el ViewHolder con todos los elementos y lo devuelve */
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineasRevisionViewHolder {
    val binding = ItemLineaRevisionBinding.inflate(
      LayoutInflater.from(parent.context), parent, false
    )
    return LineasRevisionViewHolder(binding)
  }

  /** Método principal, llamado cada vez que hay que dibujar un item */
  override fun onBindViewHolder(holder: LineasRevisionViewHolder, position: Int) {
    // Nos pasan la posición del  item a mostrar en el viewHolder
    with(holder) {
      // Obtenemos el contexto para acceder a los recursos de strings.xml
      val context = this.itemView.context
      // Recuperamos el item a mostrar y rellenamos los campos del ViewHolder
      with(lineas!![position]) {
        binding.tvTipoLineaRevision.text = this.tipo;

        // Establecemos la imagen y el texto según el estado de realización
        if (this.realizada) {
          binding.ivRealizadaLineaRevision.setImageResource(R.drawable.realizada)
          binding.tvRealizadaLineaRevision.text = context.getString(R.string.LineaRevision_realizada)
          binding.tvRealizadaLineaRevision.setTextColor(context.getColor(R.color.realizada))
        } else {
          binding.ivRealizadaLineaRevision.setImageResource(R.drawable.no_realizada)
          binding.tvRealizadaLineaRevision.text = context.getString(R.string.LineaRevision_no_realizada)
          binding.tvRealizadaLineaRevision.setTextColor(context.getColor(R.color.no_realizada))
        }

        // No vamos a mostrar null, mostraremos que no se ha indicado
        binding.tvSiguienteLineaRevision.text = when (this.km) {
          null -> context.getString(R.string.km_no_indicados)
          else -> this.km.toString()
        }
      }
    }
  }

  /** Devuelve el número de items en la lista, o 0 si es nula */
  override fun getItemCount(): Int = lineas?.size?:0

  /** Interfaz que define los listeners para la Revision */
  interface OnLineasRevisionClickListener{
    /** Edita el item  que contiene el ViewHolder */
    fun onLineaRevisionClick(linea : LineaRevision?)
  }
}
