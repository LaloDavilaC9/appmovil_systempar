package com.example.app_systempar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.AppCompatSpinner

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

    private lateinit var rootView: View
    private lateinit var spinnerModalidad: AppCompatSpinner
    private lateinit var spinnerUrgencia: AppCompatSpinner
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
}