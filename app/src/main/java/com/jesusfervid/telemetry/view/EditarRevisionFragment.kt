package com.jesusfervid.telemetry.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jesusfervid.telemetry.R
import com.jesusfervid.telemetry.databinding.FragmentEditarRevisionBinding
import com.jesusfervid.telemetry.model.Revision
import com.jesusfervid.telemetry.viewmodel.LineaRevisionViewModel
import com.jesusfervid.telemetry.viewmodel.RevisionesViewModel
import java.util.Calendar


/**
 * En este [Fragment] podremos ver y editar una revisión.
 */
class EditarRevisionFragment : Fragment() {
  private var _binding : FragmentEditarRevisionBinding? = null

  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  // Argumentos
  val args : EditarRevisionFragmentArgs by navArgs()
  val esNuevoItem : Boolean by lazy { args.revision == null }

  private val viewModel : RevisionesViewModel by activityViewModels()
  private val lineasViewModel : LineaRevisionViewModel by activityViewModels()

  private val calendario : Calendar = Calendar.getInstance()

  override fun onCreateView(
    inflater : LayoutInflater, container : ViewGroup?,
    savedInstanceState : Bundle?
  ) : View? {

    _binding = FragmentEditarRevisionBinding.inflate(inflater, container, false)
    return binding.root

  }

  override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initializeCalendario()
    initializeBotones()
    comprobarNuevoItem()
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
      guardarRevision()
    }
  }

  /** Comprueba si debemos añadir o editar */
  private fun comprobarNuevoItem() {
    if (esNuevoItem){
      (requireActivity() as AppCompatActivity).supportActionBar?.title =
        getString(R.string.nueva_revision_fragment_label)
      crearLineasRevision()
    } else {
      (requireActivity() as AppCompatActivity).supportActionBar?.title =
        getString(R.string.editar_revision_fragment_label)
      cargarRevision()
    }

  }

  private fun crearLineasRevision() {
    TODO("Not yet implemented")
  }

  /** Rellena los campos con los datos del item pasado al Fragment */
  private fun cargarRevision() {
    // Recuperar datos
    val vehiculo : Revision = args.vehiculo!!
    val spinner = binding.spTipoRevision
    spinner.setSelection(spinnerIndexOf(spinner, vehiculo.tipo))
    binding.tietNombreRevision.setText(vehiculo.nombre)
    binding.tietModeloRevision.setText(vehiculo.modelo)

    if (vehiculo.anyo != null)
      binding.tietAnyoRevision.setText(vehiculo.anyo.toString())
    else
      binding.tietAnyoRevision.setText("")

    // TODO: Cargar líneas revisión
  }

  // TODO: Diálogo editar línea revisión
  // TODO: Guardar línea revisión

  /** Guarda el item nuevo o editado */
  private fun guardarRevision() {
    // Recuperamos datos
    val tipo  = binding.spTipoRevision.selectedItem.toString()
    val nombre = binding.tietNombreRevision.text.toString()
    var modelo : String?  = binding.tietModeloRevision.text.toString()
    var anyo : String?  = binding.tietAnyoRevision.text.toString()

    // Validación
    if (nombre.isBlank()) {
      Toast.makeText(
        requireContext(),
        getString(R.string.falta_nombre_vehiculo),
        Toast.LENGTH_LONG
      ).show()
      return
    }

    // Adaptar datos
    if (modelo.isNullOrBlank())
      modelo = null

    if (anyo.isNullOrBlank())
      anyo = null

    // Elegimos si añadir o modificar
    if (esNuevoItem) {
      viewModel.addRevision(
        Revision(tipo, nombre, modelo, anyo?.toInt())
      )
    } else {
      val id = args.vehiculo?.id
      viewModel.modifyRevision(
        Revision(tipo, nombre, modelo, anyo?.toInt(), id),
      )
    }

    // Ir atrás
    findNavController().popBackStack()
  }

  /**
   * Permite obtener el índice que ocupa un elemento en un spinner.
   * @param spinner El spinner donde buscar
   * @param buscado El valor a buscar
   * @return El índice del elemento, o 0 si no lo encuentra.
   */
  private fun spinnerIndexOf(spinner : Spinner, buscado : String) : Int {
    val adapter : ArrayAdapter<String> = spinner.adapter as ArrayAdapter<String>
    for (indice in (0..adapter.count)) {
      if (adapter.getItem(indice) == buscado)
        return indice
    }
    return 0
  }
}
