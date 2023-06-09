package com.jesusfervid.telemetry.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.jesusfervid.telemetry.databinding.FragmentRevisionesBinding

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

  override fun onCreateView(
    inflater : LayoutInflater, container : ViewGroup?,
    savedInstanceState : Bundle?
  ) : View? {

    _binding = FragmentRevisionesBinding.inflate(inflater, container, false)
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
