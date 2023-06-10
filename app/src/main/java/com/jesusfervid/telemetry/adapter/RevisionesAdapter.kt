package com.jesusfervid.telemetry.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jesusfervid.telemetry.R
import com.jesusfervid.telemetry.databinding.ItemRevisionBinding
import com.jesusfervid.telemetry.model.Revision

/**
 * Adapter para la lista de revisiones.
 * Los Adapter hacen de puente entre los datos y la interfaz de una RecyclerView.
 */
class RevisionesAdapter() : RecyclerView.Adapter<RevisionesAdapter.RevisionesViewHolder>() {
  var revisiones: List<Revision>? = null
  var onRevisionClickListener: OnRevisionClickListener? = null

  /** Los ViewHolders representan elementos (Views) individuales dentro de una RecyclerView **/
  inner class RevisionesViewHolder(val binding: ItemRevisionBinding)
    : RecyclerView.ViewHolder(binding.root) {
    init {
      // Click sobre el item (ConstraintLayout)
      binding.root.setOnClickListener {
        val revision = revisiones?.get(this.bindingAdapterPosition)
        onRevisionClickListener?.onRevisionClick(revision)
      }

      // Click sobre el botón borrar
      binding.ivBorrarRevision.setOnClickListener {
        val revision = revisiones?.get(this.bindingAdapterPosition)
        onRevisionClickListener?.onRevisionBorrarClick(revision)
      }
    }
  }

  /** Actualiza la lista y notifica al adaptador para redibujar el ReciclerView */
  fun setListaRevisiones(revisiones: List<Revision>){
    this.revisiones = revisiones
    notifyDataSetChanged()
  }

  /** Rellena (infla) el ViewHolder con todos los elementos y lo devuelve */
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RevisionesViewHolder {
    val binding = ItemRevisionBinding.inflate(
      LayoutInflater.from(parent.context), parent, false
    )
    return RevisionesViewHolder(binding)
  }

  /** Método principal, llamado cada vez que hay que dibujar un item */
  override fun onBindViewHolder(holder: RevisionesViewHolder, position: Int) {
    // Nos pasan la posición del  item a mostrar en el viewHolder
    with(holder) {
      // Obtenemos el contexto para acceder a los recursos de strings.xml
      val context = this.itemView.context
      // Recuperamos el item a mostrar y rellenamos los campos del ViewHolder
      with(revisiones!![position]) {
        val fecha = this.fecha
        binding.tvTituloRevision.text = context.getString(R.string.titulo_revision, fecha)
        binding.tvKmRevision.text = this.km.toString()

        binding.tvKmSiguienteRevision.text = this.kmSiguiente.toString()
      }
    }
  }

  /** Devuelve el número de items en la lista, o 0 si es nula */
  override fun getItemCount(): Int = revisiones?.size?:0

  /** Interfaz que define los listeners para la Revision */
  interface OnRevisionClickListener{
    /** Edita el item  que contiene el ViewHolder */
    fun onRevisionClick(revision : Revision?)

    /** Borra el item que contiene el ViewHolder */
    fun onRevisionBorrarClick(revision : Revision?)
  }
}
