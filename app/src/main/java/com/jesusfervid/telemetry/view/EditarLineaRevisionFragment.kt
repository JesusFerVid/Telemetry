package com.jesusfervid.telemetry.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.jesusfervid.telemetry.databinding.FragmentEditarPropiedadesRevisionBinding
import com.jesusfervid.telemetry.viewmodel.RevisionesViewModel

class EditarLineaRevisionFragment : Fragment() {
  private var _binding: FragmentEditarPropiedadesRevisionBinding? = null
  private val binding get() = _binding!!

  // Argumentos
  val args : EditarLineaRevisionFragmentArgs by navArgs()

  private val revisionViewModel : RevisionesViewModel by activityViewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentEditarPropiedadesRevisionBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    revisionViewModel.crearNueva = false

  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
