package com.jesusfervid.telemetry.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.jesusfervid.telemetry.databinding.FragmentDetalleRevisionBinding


/**
 * En este [Fragment] podremos ver todos los datos de una revisión.
 */
// TODO: Seguramente, borrar esta clase
class DetalleRevisionFragment : Fragment() {
  private var _binding : FragmentDetalleRevisionBinding? = null

  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  // Argumentos
  val args : DetalleRevisionFragmentArgs by navArgs()

  override fun onCreateView(
    inflater : LayoutInflater, container : ViewGroup?,
    savedInstanceState : Bundle?
  ) : View? {

    _binding = FragmentDetalleRevisionBinding.inflate(inflater, container, false)
    return binding.root

  }

  override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
    super.onViewCreated(view, savedInstanceState)

  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
