package com.jesusfervid.telemetry.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jesusfervid.telemetry.R
import com.jesusfervid.telemetry.databinding.FragmentUsuarioBinding

/**
 * "Usuario" no es una entidad como tal, más bien un nombre conveniente.
 * En este Fragment se muestra la lista de vehículos del... Usuario.
 */
class UsuarioFragment : Fragment() {

  private var _binding : FragmentUsuarioBinding? = null

  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater : LayoutInflater, container : ViewGroup?,
    savedInstanceState : Bundle?
  ) : View? {

    _binding = FragmentUsuarioBinding.inflate(inflater, container, false)
    return binding.root

  }

  override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.buttonFirst.setOnClickListener {
      findNavController().navigate(R.id.actionEditar)
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
