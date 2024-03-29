package com.jesusfervid.telemetry.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.jesusfervid.telemetry.R
import com.jesusfervid.telemetry.adapter.LineasRevisionAdapter
import com.jesusfervid.telemetry.databinding.FragmentEditarRevisionBinding
import com.jesusfervid.telemetry.model.LineaRevision
import com.jesusfervid.telemetry.model.Revision
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
    comprobarNuevoItem()
    initializeBotones()

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
    // Click en el botón de editar propiedades
    binding.btEditarRevision.setOnClickListener {
      val action = EditarRevisionFragmentDirections.actionEditarPropiedadesRevision(revisionViewModel.revisionEditando!!)
      findNavController().navigate(action)
    }

    // Implementamos la interfaz de LineasRevisionAdapter
    lineasAdapter.onLineasRevisionClickListener = object : LineasRevisionAdapter.OnLineasRevisionClickListener {
      // Click en la línea de revisión
      override fun onLineaRevisionClick(linea : LineaRevision?) {
        val action = EditarRevisionFragmentDirections.actionEditarLineaRevision(linea!!)
        findNavController().navigate(action)
      }
    }
  }

  /** Comprueba si debemos añadir o editar */
  private fun comprobarNuevoItem() {
    revisionViewModel.revisionEditando = revisionViewModel.revisionEditando ?: args.revision
    if (esNuevoItem){
      (requireActivity() as AppCompatActivity).supportActionBar?.title =
        getString(R.string.nueva_revision_fragment_label)
      // Si volvemos de editar propiedades o líneas, no creamos otra revisión más,
      // sino que cargamos los datos de esa revisión.
      if (revisionViewModel.crearNueva) {
        crearRevision()
      } else {
        cargarRevision()
      }
    } else {
      (requireActivity() as AppCompatActivity).supportActionBar?.title =
        getString(R.string.editar_revision_fragment_label)
      cargarRevision()
    }

  }

  /** Crea (y persiste) una nueva revisión, para modificarla después **/
  private fun crearRevision() {
    revisionViewModel.revisionEditando  = Revision(args.vehiculo?.id!!, "", 0, 0, "")

    binding.tvEditarRevisionFecha.text = ""
    binding.tvEditarRevisionKm.text = ""
    binding.tvEditarRevisionKmSiguiente.text = ""
    binding.tvEditarRevisionObservaciones.text = ""

    revisionViewModel.addRevision(revisionViewModel.revisionEditando!!)
    Thread.sleep(600)
    crearLineasRevision()
  }

  /** Crea (y persiste) las líneas de esta revisión, para ser modificadas después. */
  private fun crearLineasRevision() {
    val tiposRevision : Array<String> = resources.getStringArray(R.array.lr_genericas)
    for (tipo in tiposRevision) {
      lineasViewModel.addLineaRevision(
        LineaRevision(revisionViewModel.newId!!, tipo, false, null)
      )
    }
    Thread.sleep(800)
    lineasViewModel.getLineasRevision(revisionViewModel.newId!!)
  }

  /** Rellena los campos con los datos del item pasado por parámetro */
  private fun cargarRevision() {
    val revision = revisionViewModel.revisionEditando!!

    // Recuperar datos
    binding.tvEditarRevisionFecha.text = revision.fecha
    binding.tvEditarRevisionKm.text = revision.km.toString()
    binding.tvEditarRevisionKmSiguiente.text = revision.kmSiguiente.toString()

    binding.tvEditarRevisionObservaciones.text =
      when (revision.observaciones) {
        null -> ""
        else -> revision.observaciones
      }

    cargarLineasRevision()
  }

  private fun cargarLineasRevision() {
    val id = revisionViewModel.revisionEditando?.id ?: revisionViewModel.newId
    lineasViewModel.getLineasRevision(id!!)
  }
}
