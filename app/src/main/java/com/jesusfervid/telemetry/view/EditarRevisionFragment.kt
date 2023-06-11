package com.jesusfervid.telemetry.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.jesusfervid.telemetry.R
import com.jesusfervid.telemetry.adapter.LineasRevisionAdapter
import com.jesusfervid.telemetry.databinding.FragmentEditarRevisionBinding
import com.jesusfervid.telemetry.model.LineaRevision
import com.jesusfervid.telemetry.model.Revision
import com.jesusfervid.telemetry.viewmodel.EditarRevisionDialogFragment
import com.jesusfervid.telemetry.viewmodel.LineasRevisionViewModel
import com.jesusfervid.telemetry.viewmodel.RevisionesViewModel


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

  private val revisionViewModel : RevisionesViewModel by activityViewModels()
  private val lineasViewModel : LineasRevisionViewModel by activityViewModels()

  lateinit var lineasAdapter : LineasRevisionAdapter

  // Una copia de la revisión que estamos editando, para pasarla entre métodos y fragments
  private lateinit var revisionEditando : Revision

  override fun onCreateView(
    inflater : LayoutInflater,
    container : ViewGroup?,
    savedInstanceState : Bundle?
  ) : View? {

    _binding = FragmentEditarRevisionBinding.inflate(inflater, container, false)
    return binding.root

  }

  override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initializeRecyclerView()
    initializeBotones()
    comprobarNuevoItem()

    // Usamos un observer para actualizar la lista cuando haya cambios
    lineasViewModel.lineasLD.observe(viewLifecycleOwner, Observer<List<LineaRevision>> { revisiones ->
      lineasAdapter.setListaLineasRevision(revisiones)
    })
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  /** Asigna el layoutManager y el adapter al RecyclerView */
  private fun initializeRecyclerView() {
    lineasAdapter = LineasRevisionAdapter()

    with(binding.rvLineasRevision) {
      layoutManager = LinearLayoutManager(activity)
      adapter = lineasAdapter
    }
  }

  private fun initializeBotones() {
    binding.btEditarRevision.setOnClickListener {
      EditarRevisionDialogFragment(revisionEditando)
        .show(childFragmentManager, "editar_revision_dialog")
    }
  }

  /** Comprueba si debemos añadir o editar */
  private fun comprobarNuevoItem() {
    if (esNuevoItem){
      (requireActivity() as AppCompatActivity).supportActionBar?.title =
        getString(R.string.nueva_revision_fragment_label)
      crearRevision()
      // TODO: Invocar Dialog
    } else {
      (requireActivity() as AppCompatActivity).supportActionBar?.title =
        getString(R.string.editar_revision_fragment_label)
      cargarRevision()
    }

  }

  /** Crea (y persiste) una nueva revisión, para modificarla después **/
  private fun crearRevision() {
    revisionEditando  = Revision(args.vehiculo?.id!!, "", 0, 0, "")

    binding.tvEditarRevisionFecha.text = ""
    binding.tvEditarRevisionKm.text = ""
    binding.tvEditarRevisionKmSiguiente.text = ""
    binding.tvEditarRevisionObservaciones.text = ""

    revisionViewModel.addRevision(revisionEditando)
    crearLineasRevision()
  }

  /** Crea (y persiste) las líneas de esta revisión, para ser modificadas después. */
  private fun crearLineasRevision() {
    Thread.sleep(1000)
    val tiposRevision : Array<String> = resources.getStringArray(R.array.lr_genericas)
    for (tipo in tiposRevision) {
      lineasViewModel.addLineaRevision(
        LineaRevision(revisionViewModel.newId!!, tipo, false, null)
      )
    }
  }

  /** Rellena los campos con los datos del item pasado al Fragment */
  private fun cargarRevision() {
    // Recuperar datos
    revisionEditando = args.revision!!
    binding.tvEditarRevisionFecha.text = revisionEditando.fecha
    binding.tvEditarRevisionKm.text = revisionEditando.km.toString()
    binding.tvEditarRevisionKmSiguiente.text = revisionEditando.kmSiguiente.toString()

    binding.tvEditarRevisionObservaciones.text =
      when (revisionEditando.observaciones) {
        null -> ""
        else -> revisionEditando.observaciones
      }

    cargarLineasRevision()
  }

  private fun cargarLineasRevision() {
    lineasViewModel.getLineasRevision(revisionEditando.id!!)
  }

  // TODO: Diálogo editar línea revisión
  // TODO: Guardar línea revisión

  // TODO: ¿Quitar botón de guardado en este Fragment?
}
