package com.jesusfervid.telemetry.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jesusfervid.telemetry.databinding.FragmentEditarLineaRevisionBinding
import com.jesusfervid.telemetry.model.LineaRevision
import com.jesusfervid.telemetry.viewmodel.LineasRevisionViewModel
import com.jesusfervid.telemetry.viewmodel.RevisionesViewModel

class EditarLineaRevisionFragment : Fragment() {
  private var _binding: FragmentEditarLineaRevisionBinding? = null
  private val binding get() = _binding!!

  // Argumentos
  val args : EditarLineaRevisionFragmentArgs by navArgs()

  private val lineasViewModdel : LineasRevisionViewModel by activityViewModels()
  private val revisionViewModel : RevisionesViewModel by activityViewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentEditarLineaRevisionBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    revisionViewModel.crearNueva = false
    cargarDatos()
    initializeBotones()
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  private fun initializeBotones() {
    binding.btGuardarLineaRevision.setOnClickListener {
      guardarDatos()
    }
  }

  /** Rellena los campos con los datos del item pasado al Fragment */
  private fun cargarDatos() {
    val linea = args.lineaRevision

    binding.tietKmSiguienteLineaRevision.setText(
      when (linea.km) {
        null -> ""
        else -> linea.km.toString()
      }
    )

    binding.ckRealizadaLineaRevision.isChecked = linea.realizada
  }

  /** Guarda el item nuevo o editado */
  private fun guardarDatos() {
    // Recuperamos datos
    val linea = args.lineaRevision
    var kmSiguiente : String?  = binding.tietKmSiguienteLineaRevision.text.toString()
    var realizada : Boolean  = binding.ckRealizadaLineaRevision.isChecked

    // Adaptar datos
    if (kmSiguiente.isNullOrBlank() || kmSiguiente == "0")
      kmSiguiente = null

    // Actualizamos la revisión en la BD
    val nuevaLineaRevision =
      LineaRevision(
        linea.id_revision,
        linea.tipo,
        realizada,
        kmSiguiente?.toInt(),
        linea.id
      )
    lineasViewModdel.modifyLineaRevision(nuevaLineaRevision)

    // Ir atrás
    findNavController().popBackStack()
  }
}
