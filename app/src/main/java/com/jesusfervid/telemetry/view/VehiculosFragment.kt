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
import androidx.recyclerview.widget.LinearLayoutManager
import com.jesusfervid.telemetry.R
import com.jesusfervid.telemetry.adapter.VehiculosAdapter
import com.jesusfervid.telemetry.databinding.FragmentVehiculosBinding
import com.jesusfervid.telemetry.model.Vehiculo
import com.jesusfervid.telemetry.viewmodel.VehiculosViewModel

/**
 * En este [Fragment] se muestra la lista de vehículos del usuario.
 */
class VehiculosFragment : Fragment() {

  private var _binding : FragmentVehiculosBinding? = null

  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  private val viewModel : VehiculosViewModel by activityViewModels()
  lateinit var vehiculosAdapter : VehiculosAdapter

  override fun onCreateView(
    inflater : LayoutInflater, container : ViewGroup?,
    savedInstanceState : Bundle?
  ) : View? {

    _binding = FragmentVehiculosBinding.inflate(inflater, container, false)
    return binding.root

  }

  override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initializeRecyclerView()
    initializeCRUD()

    viewModel.getVehiculos()

    // Usamos un observer para actualizar la lista cuando haya cambios
    viewModel.vehiculosLD.observe(viewLifecycleOwner, Observer<List<Vehiculo>> { vehiculos ->
      vehiculosAdapter.setListaVehiculos(vehiculos)
    })
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  /** Asigna el layoutManager y el adapter al RecyclerView */
  private fun initializeRecyclerView() {
    vehiculosAdapter = VehiculosAdapter()

    with(binding.rvVehiculos) {
      layoutManager = LinearLayoutManager(activity)
      adapter = vehiculosAdapter
    }
  }

  /** Establece Listeners para operaciones de añadir, editar y borrar */
  private fun initializeCRUD() {
    binding.fabNuevoVehiculo.setOnClickListener {
      // Pasamos un null si queremos crear un nuevo item
      val action = VehiculosFragmentDirections.actionEditarVehiculo(null)
      findNavController().navigate(action)
    }

    // Implementamos la interfaz declarada en el Adapter aquí
    vehiculosAdapter.onVehiculoClickListener = object : VehiculosAdapter.OnVehiculoClickListener {
      // Editar item
      override fun onVehiculoClick(vehiculo: Vehiculo?) {
        val action = VehiculosFragmentDirections.actionEditarVehiculo(vehiculo)
        findNavController().navigate(action)
      }

      // Borrar item
      override fun onVehiculoBorrarClick(vehiculo: Vehiculo?) {
        borrarVehiculo(vehiculo!!)
//        viewModel.getVehiculos()
      }
    }
  }

  /** Se invoca al tocar el icono de borrar */
  private fun borrarVehiculo(vehiculo : Vehiculo){
    AlertDialog.Builder(activity as Context)
      .setTitle(android.R.string.dialog_alert_title)
      .setMessage(getString(R.string.confirmacion_borrar))

      // Acción si pulsa Sí
      .setPositiveButton(android.R.string.ok){ v, _ ->
        // Borramos la tarea y cerramos el diálogo
        viewModel.removeVehiculo(vehiculo)
        v.dismiss()
      }

      // Accion si pulsa No
      .setNegativeButton(android.R.string.cancel){ v,_ -> v.dismiss() }
      .setCancelable(false)
      .create()
      .show()
  }
}
