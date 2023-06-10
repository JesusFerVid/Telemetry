package com.jesusfervid.telemetry.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jesusfervid.telemetry.R
import com.jesusfervid.telemetry.databinding.FragmentEditarVehiculoBinding
import com.jesusfervid.telemetry.model.Vehiculo
import com.jesusfervid.telemetry.viewmodel.VehiculosViewModel

/**
 * En este [Fragment] podremos editar y guardar un vehiculo nuevo o existente.
 */
class EditarVehiculoFragment : Fragment() {

  private var _binding : FragmentEditarVehiculoBinding? = null

  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  // Argumentos
  val args : EditarVehiculoFragmentArgs by navArgs()
  val esNuevoItem : Boolean by lazy { args.vehiculo == null }

  private val viewModel : VehiculosViewModel by activityViewModels()

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
    initializeBotones()
    comprobarNuevoItem()
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
      override fun onItemSelected(
        parent : AdapterView<*>?, view : View, position : Int, id : Long) {}

      override fun onNothingSelected(parent : AdapterView<*>?) {}
    }
  }

  private fun initializeBotones() {
    binding.btGuardarVehiculo.setOnClickListener {
      guardarVehiculo()
    }
  }

  /** Comprueba si debemos añadir o editar */
  private fun comprobarNuevoItem() {
    (requireActivity() as AppCompatActivity).supportActionBar?.title =
      when (esNuevoItem) {
        true -> getString(R.string.nuevo_vehiculo_fragment_label)
        else -> getString(R.string.editar_vehiculo_fragment_label)
      }
    if (!esNuevoItem)
      cargarVehiculo()
  }

  /** Rellena los campos con los datos del item pasado al Fragment */
  private fun cargarVehiculo() {
    // Recuperar datos
    val vehiculo : Vehiculo = args.vehiculo!!
    val spinner = binding.spTipoVehiculo
    spinner.setSelection(spinnerIndexOf(spinner, vehiculo.tipo))
    binding.tietNombreVehiculo.setText(vehiculo.nombre)
    binding.tietModeloVehiculo.setText(vehiculo.modelo)

    if (vehiculo.anyo != null)
      binding.tietAnyoVehiculo.setText(vehiculo.anyo.toString())
    else
      binding.tietAnyoVehiculo.setText("")
  }

  /** Guarda el item nuevo o editado */
  private fun guardarVehiculo() {
    // Recuperamos datos
    val tipo  = binding.spTipoVehiculo.selectedItem.toString()
    val nombre = binding.tietNombreVehiculo.text.toString()
    var modelo : String?  = binding.tietModeloVehiculo.text.toString()
    var anyo : String?  = binding.tietAnyoVehiculo.text.toString()

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
      viewModel.addVehiculo(
        Vehiculo(tipo, nombre, modelo, anyo?.toInt())
      )
    } else {
      val id = args.vehiculo?.id
      viewModel.modifyVehiculo(
        Vehiculo(tipo, nombre, modelo, anyo?.toInt(), id),
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
