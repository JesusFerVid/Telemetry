package com.jesusfervid.telemetry.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.jesusfervid.telemetry.R
import com.jesusfervid.telemetry.databinding.FragmentEditarVehiculoBinding

/**
 * En este [Fragment] podremos editar y guardar un vehiculo nuevo o existente.
 */
class EditarVehiculoFragment : Fragment() {

  private var _binding : FragmentEditarVehiculoBinding? = null

  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater : LayoutInflater, container : ViewGroup?,
    savedInstanceState : Bundle?
  ) : View? {

    _binding = FragmentEditarVehiculoBinding.inflate(inflater, container, false)
    return binding.root

  }

  override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initializeSpTipoVehiculo()

    binding.btGuardar.setOnClickListener {
      // TODO: Guardar
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  /**
   * Crea y asigna el adapter al spinner
   * Asigna también los listeners
   */
  fun initializeSpTipoVehiculo() {
    ArrayAdapter.createFromResource(
      requireContext(),
      R.array.tipos_vehiculo,
      android.R.layout.simple_spinner_item
    ).also {
      it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
      binding.spTipoVehiculo.adapter = it
    }

    binding.spTipoVehiculo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(parent : AdapterView<*>?, view : View, position : Int, id : Long) {}

      override fun onNothingSelected(parent : AdapterView<*>?) {}
    }
  }
}
