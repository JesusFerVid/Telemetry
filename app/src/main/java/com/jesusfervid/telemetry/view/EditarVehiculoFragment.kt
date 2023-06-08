package com.jesusfervid.telemetry.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.jesusfervid.telemetry.R
import com.jesusfervid.telemetry.databinding.FragmentEditarVehiculoBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
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

    binding.buttonSecond.setOnClickListener {
      Snackbar.make(it, "Averiado, disculpa las molestias", Snackbar.LENGTH_SHORT)
        .setAnchorView(R.id.fab)
        .show()
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
