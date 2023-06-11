package com.jesusfervid.telemetry.viewmodel

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.jesusfervid.telemetry.R
import com.jesusfervid.telemetry.databinding.DialogEditarRevisionBinding
import com.jesusfervid.telemetry.model.Revision
import java.util.Calendar

/**
 * Edita las propiedades principales de una Revision (las líneas van aparte)
 */
class EditarRevisionDialogFragment(private val revision : Revision) : DialogFragment() {
  private var _binding: DialogEditarRevisionBinding? = null
  private val binding get() = _binding!!

  private val calendario : Calendar = Calendar.getInstance()

  private val revisionViewModel : RevisionesViewModel by activityViewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = DialogEditarRevisionBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initializeCalendario()
    initializeBotones()
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    // TODO: Comentario aquí
//    return Dialog(requireContext(), theme)
    val dialog = super.onCreateDialog(savedInstanceState)
    dialog.setTitle(getString(R.string.editar_revision))
    return dialog
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  /** Crea un calendario para el cuadro de fecha */
  private fun initializeCalendario() {
    binding.tietFechaRevision.setOnClickListener {
      val dia = calendario.get(Calendar.DAY_OF_MONTH)
      val mes = calendario.get(Calendar.MONTH)
      val anyo = calendario.get(Calendar.YEAR)

      // Pasamos un contexto, un listener y el año, mes y día
      // El listener tiene como parámetros: view (ignorada), año, mes y día
      val dialogoFecha = DatePickerDialog(requireContext(), { _, a, m, d ->
        // Usamos una cadena formateada
        val fecha = "$d/${m + 1}/$a"
        binding.tietFechaRevision.setText(fecha)
      },
        anyo, mes, dia
      )
      // Como con cualquier diálogo, no olvidemos el crucial paso final: mostrarlo
      dialogoFecha.show()
    }
  }

  private fun initializeBotones() {
    binding.btGuardarRevision.setOnClickListener {
      guardarDatos()
      dismiss()
    }
  }

  /** Rellena los campos con los datos del item pasado al Fragment */
  private fun cargarDatos() {
    binding.tietFechaRevision.setText(revision.fecha)
    binding.tietKmRevision.setText(revision.km.toString())
    binding.tietKmSiguienteRevision.setText(revision.kmSiguiente.toString())

    binding.tietObservacionesRevision.setText(
      when (revision.observaciones) {
        null -> ""
        else -> revision.observaciones
      }
    )
  }

  /** Guarda el item nuevo o editado */
  private fun guardarDatos() {
    // Recuperamos datos
    val fecha : String  = binding.tietFechaRevision.text.toString()
    val km : String = binding.tietKmRevision.text.toString()
    var kmSiguiente : String  = binding.tietKmSiguienteRevision.text.toString()
    var observaciones : String?  = binding.tietObservacionesRevision.text.toString()

    // Validación
    if (fecha.isBlank() || km.isBlank() || kmSiguiente.isBlank()) {
      Toast.makeText(
        requireContext(),
        getString(R.string.faltan_campos_obligatorios),
        Toast.LENGTH_LONG
      ).show()
      return
    }

    // Adaptar datos
    if (observaciones.isNullOrBlank())
      observaciones = null

    // Actualizamos la revisión en la BD
    revisionViewModel.modifyRevision(
      Revision(
        revision.id_vehiculo,
        fecha,
        km.toInt(),
        kmSiguiente.toInt(),
        observaciones?.toString(),
        revision.id
      )
    )
  }
}
