package com.jesusfervid.telemetry.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.jesusfervid.telemetry.databinding.FragmentEditarRevisionBinding


/**
 * En este [Fragment] podremos editar una revisión.
 */
class EditarRevisionFragment : Fragment() {
  private var _binding : FragmentEditarRevisionBinding? = null

  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  // Argumentos
  val args : EditarRevisionFragmentArgs by navArgs()
  val esNuevoItem : Boolean by lazy { args.revision == null }

  override fun onCreateView(
    inflater : LayoutInflater, container : ViewGroup?,
    savedInstanceState : Bundle?
  ) : View? {

    _binding = FragmentEditarRevisionBinding.inflate(inflater, container, false)
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
