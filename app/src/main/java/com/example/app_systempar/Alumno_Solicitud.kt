package com.example.app_systempar

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatSpinner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.w3c.dom.Text
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Alumno_Solicitud.newInstance] factory method to
 * create an instance of this fragment.
 */
class Alumno_Solicitud : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private val met = Metodos()
    private lateinit var rootView: View
    private lateinit var spinnerModalidad: AppCompatSpinner
    private lateinit var spinnerUrgencia: AppCompatSpinner
    private lateinit var spinnerMaterias : AppCompatSpinner
    private lateinit var boton : Button
    private  var materia : String = ""
    private  var urgencia : String = ""
    private  var modalidad : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_alumno__solicitud, container, false)
        spinnerModalidad = rootView.findViewById(R.id.txtin_modalidad)
        spinnerUrgencia = rootView.findViewById(R.id.txtin_urgencia)

        var options = arrayOf("EN LÍNEA", "PRESENCIAL")
        var adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, options)
        spinnerModalidad.setAdapter(adapter)

        options = arrayOf("URGENTE","PUEDE ESPERAR")
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, options)
        spinnerUrgencia.setAdapter(adapter)

        cargarMaterias()

        boton = rootView.findViewById(R.id.btn_Enviar)

        boton.setOnClickListener {
            enviarSolicitud()
        }

        spinnerMaterias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Obtener la opción seleccionada
                materia = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        spinnerModalidad.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Obtener la opción seleccionada
                modalidad = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        spinnerUrgencia.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Obtener la opción seleccionada
                urgencia = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        return rootView
    }


        companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Alumno_Solicitud.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
                Alumno_Solicitud().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    fun enviarSolicitud(){
        val infoAlumno = AlumnoManager.alumnoResponse

        var tema : String = rootView.findViewById<TextView?>(R.id.txtin_tema).text.toString()
        var descripcion : String = rootView.findViewById<TextView?>(R.id.txtin_descripcion).text.toString()
        println("Materia: ${materia}   Tema: ${tema}")
        val jsonObject = JSONObject()
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

        if (infoAlumno != null) {
            val array = infoAlumno.array
            if (array.isNotEmpty()) {
                jsonObject.put("solicitud_fecha",dateFormat.format(calendar.time))
                jsonObject.put("solicitud_urgencia",urgencia[0])
                jsonObject.put("materia_id",dateFormat.format(calendar.time))
                planId = array[0].plan_id
                semestre = array[0].alumno_semestre
                //println("Nombre del alumno: $nombreAlumno")

            }
        }

    }

    fun cargarMaterias() {
        spinnerMaterias = rootView.findViewById(R.id.txtin_materia)

        var planId: Int = 0
        var semestre: Int = 0
        val alumnoResponse = AlumnoManager.alumnoResponse
        if (alumnoResponse != null) {
            val array = alumnoResponse.array
            if (array.isNotEmpty()) {
                planId = array[0].plan_id
                semestre = array[0].alumno_semestre
                //println("Nombre del alumno: $nombreAlumno")

            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call: Response<MateriasResponse> =
                    met.getRetrofit().create(APIService::class.java)
                        .solicitudesMaterias("/solicitudes/${planId}/${semestre}")
                val info = call.body() as MateriasResponse
                val materias = info.array

                val nombresMaterias = materias.map { it.materia_nombre }

                withContext(Dispatchers.Main) {
                    val adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_dropdown_item_1line,
                        nombresMaterias
                    )
                    spinnerMaterias.adapter = adapter
                }

            } catch (e: Exception) {
                println(e)
            }
        }
    }
}