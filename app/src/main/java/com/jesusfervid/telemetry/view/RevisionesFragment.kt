package com.jesusfervid.telemetry.view

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.jesusfervid.telemetry.R
import com.jesusfervid.telemetry.adapter.RevisionesAdapter
import com.jesusfervid.telemetry.databinding.FragmentRevisionesBinding
import com.jesusfervid.telemetry.model.Revision
import com.jesusfervid.telemetry.viewmodel.RevisionesViewModel

/**
 * En este [Fragment] se muestra la lista de revisiones de un vehículo
 */
class RevisionesFragment : Fragment() {
  private var _binding : FragmentRevisionesBinding? = null

  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  // Argumentos
  val args : RevisionesFragmentArgs by navArgs()

  private val viewModel : RevisionesViewModel by activityViewModels()
  lateinit var revisionesAdapter : RevisionesAdapter

  override fun onCreateView(
    inflater : LayoutInflater, container : ViewGroup?,
    savedInstanceState : Bundle?
  ) : View? {

    _binding = FragmentRevisionesBinding.inflate(inflater, container, false)
    return binding.root

  }

  override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initializeRecyclerView()
    initializeCRUD()

    viewModel.getRevisiones(args.vehiculo.id!!)

    // Usamos un observer para actualizar la lista cuando haya cambios
    viewModel.revisionesLD.observe(viewLifecycleOwner, Observer<List<Revision>> { revisiones ->
      revisionesAdapter.setListaRevisiones(revisiones)
    })
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  /** Asigna el layoutManager y el adapter al RecyclerView */
  private fun initializeRecyclerView() {
    revisionesAdapter = RevisionesAdapter()

    with(binding.rvRevisiones) {
      layoutManager = LinearLayoutManager(activity)
      adapter = revisionesAdapter
    }
  }

  /** Establece Listeners para operaciones de añadir, editar y borrar */
  private fun initializeCRUD() {
    binding.fabNuevaRevision.setOnClickListener {
      // Pasamos null como revisión y un vehículo si queremos crear un nuevo item
      val action = RevisionesFragmentDirections.actionEditarRevision(null, args.vehiculo)
      findNavController().navigate(action)
    }

    // Implementamos la interfaz declarada en el Adapter aquí
    revisionesAdapter.onRevisionClickListener = object : RevisionesAdapter.OnRevisionClickListener {
      // Editar item
      override fun onRevisionClick(revision : Revision?) {
        // Pasamos la revisión y null como vehículo para modificar una revisión existente
        val action = RevisionesFragmentDirections.actionEditarRevision(revision, null)
        findNavController().navigate(action)
      }

      // Borrar item
      override fun onRevisionBorrarClick(revision: Revision?) {
        borrarRevision(revision!!)
      }
    }
  }

  /** Se invoca al tocar el icono de borrar */
  private fun borrarRevision(revision : Revision){
    AlertDialog.Builder(activity as Context)
      .setTitle(android.R.string.dialog_alert_title)
      .setMessage(getString(R.string.confirmacion_borrar))

      // Acción si pulsa Sí
      .setPositiveButton(android.R.string.ok){ v, _ ->
        // Borramos la tarea y cerramos el diálogo
        viewModel.removeRevision(revision)
        v.dismiss()
      }

      // Accion si pulsa No
      .setNegativeButton(android.R.string.cancel){ v,_ -> v.dismiss() }
      .setCancelable(false)
      .create()
      .show()
  }
}
