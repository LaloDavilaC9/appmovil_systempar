package com.example.app_systempar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.app_systempar.databinding.FragmentAlumnoProcesoBinding
import com.example.app_systempar.databinding.FragmentAlumnoProximasBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Alumno_Proximas.newInstance] factory method to
 * create an instance of this fragment.
 */
class Alumno_Proximas : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentAlumnoProximasBinding
    private val met = Metodos()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAlumnoProximasBinding.inflate(inflater, container, false)

        //val alumno = Solicitud("EDUARDO DÁVILA CAMPOS","ÁLGEBRA","laloquera@gmail.com","449-920-5022")
        //val alumno2 = Solicitud("EDUARDO DÁVILA CAMPOS","ESTRUCTURA DE DATOS","laloquera@gmail.com","449-920-5022")
        //val alumno3 = Solicitud("EDUARDO DÁVILA CAMPOS","CÁLCULO INTEGRAL","laloquera@gmail.com","449-920-5022")
       // val listaAlumnos = listOf(alumno,alumno2,alumno3)
        //val adapter = AlumnosAdapter(requireContext(),listaAlumnos)
        //binding.alumnos.adapter = adapter
        //return inflater.inflate(R.layout.fragment_alumno__proceso, container, false)
        cargarSolicitudesProximas()
        return binding.root
        //return inflater.inflate(R.layout.fragment_alumno__proximas, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Alumno_Proximas.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Alumno_Proximas().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun cargarSolicitudesProximas(){
        var id : Int = 0
        val alumnoResponse = AlumnoManager.alumnoResponse
        if (alumnoResponse != null) {
            val array = alumnoResponse.array
            if (array.isNotEmpty()) {
                id = array[0].alumno_id

                //println("Nombre del alumno: $nombreAlumno")

            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call: Response<SolicitudResponse> =
                    met.getRetrofit().create(APIService::class.java)
                        .solicitudesProximasAlumno("/proximasAlumno/${id}")
                val info = call.body() as SolicitudResponse
                var solicitudes : List<SolicitudInfo> = info.array
                println("LLEGA BIEN")
                withContext(Dispatchers.Main) {
                    val adapter = AlumnosAdapter(requireContext(),solicitudes)
                    binding.alumnos.adapter = adapter
                    binding.txtAviso.isVisible = if (solicitudes.size==0) true else false

                }

            } catch (e: Exception) {
                println("LLEGA MAl")
                println(e)
            }
        }
    }
}