package com.example.app_systempar

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import com.example.app_systempar.databinding.ListItemTutorBinding
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class Adaptador_Tutor(private val mContext: Context, private val listaProductos: List<SolicitudInfo>,private val texto : String) : ArrayAdapter<SolicitudInfo>(mContext, 0, listaProductos) {
    private val met = Metodos()
    private var tutorSolicitudListener: TutorSolicitudListener? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {


        //val layout = LayoutInflater.from(mContext).inflate(R.layout.list_item_alumno, parent, false)


        val binding = ListItemTutorBinding.inflate(LayoutInflater.from(mContext), parent, false)
        val alumno = listaProductos[position]

        binding.txtNombre.text = alumno.tutor_nombre_completo
        //binding.txtCo
        binding.txtMateria.text = alumno.materia_nombre

        if(texto == "PRÓXIMAS"){
            //binding.btnConfirmar.text = "PROGRAMAR"
        }
        else{
            binding.btnConfirmar.text = texto
        }
        if(texto == "EN PROCESO"){
            binding.btnConfirmar.isVisible = true
            binding.btnConfirmar.text = "PROGRAMAR"
        }

        binding.btnDetalles.setOnClickListener {
            val intent = Intent(mContext, AlumnoDetalles::class.java)
            intent.putExtra("info", alumno)
            intent.putExtra("viene","tutor")
            mContext.startActivity(intent)
        }

        binding.btnConfirmar.setOnClickListener {
            val infoAlumno = AlumnoManager.alumnoResponse
            if(binding.btnConfirmar.text == "ACEPTAR"){

                val jsonObject = JSONObject()
                if (infoAlumno != null) {
                    val array = infoAlumno.array
                    if (array.isNotEmpty()) {
                        jsonObject.put("tutor_id", array[0].tutor_id)
                        jsonObject.put("solicitud_id", alumno.solicitud_id)
                        //Se convierte el objeto Json a String
                        var jsonString = jsonObject.toString()
                        //println("DATOS: " + jsonString)


                        val requestBody = jsonString.toRequestBody("application/json".toMediaTypeOrNull())
                        val retrofit = met.getRetrofit()


                        val service = retrofit.create(APIService::class.java)
                        CoroutineScope(Dispatchers.IO).launch {

                            val response = service.aceptarSolicitud(requestBody)
                            withContext(Dispatchers.Main) {
                                //El siguiente IF controla si se pudo conectar a la API o no
                                if (response.isSuccessful) {
                                    // Convert raw JSON to pretty JSON using GSON library
                                    val gson = GsonBuilder().setPrettyPrinting().create()
                                    val prettyJson = gson.toJson(
                                        JsonParser.parseString(
                                            response.body()?.string()
                                        )
                                    )
                                    //tutorSolicitudListener?.onSolicitudActualizada()

                                    Log.d("Pretty Printed JSON :", prettyJson)
                                    println("LOGRADO")
                                    val intent = Intent(mContext,Vista_Tutor::class.java)
                                    mContext.startActivity(intent)
                                    //Toast.makeText(requireContext(), "¡Solicitud realizada!", Toast.LENGTH_LONG).show()
                                    //Limpiando datos
                                    //var elemento = rootView.findViewById<TextView?>(R.id.txtin_tema)
                                    //elemento.text = ""
                                    //elemento = rootView.findViewById<TextView?>(R.id.txtin_descripcion)
                                    //elemento.text = ""
                                }
                            }
                        }
                    }
                }
            }
            else if(binding.btnConfirmar.text == "PROGRAMAR"){
                val intent = Intent(mContext,Programar_Solicitud::class.java)
                intent.putExtra("info",alumno)
                mContext.startActivity(intent)
            }

        }

        binding.btnCancelar.setOnClickListener {
            val infoAlumno = AlumnoManager.alumnoResponse
            val jsonObject = JSONObject()
            if (infoAlumno != null) {
                val array = infoAlumno.array
                if (array.isNotEmpty()) {
                    jsonObject.put("quien", "tutor")
                    jsonObject.put("solicitud_id", alumno.solicitud_id)
                    //Se convierte el objeto Json a String
                    var jsonString = jsonObject.toString()

                    val requestBody = jsonString.toRequestBody("application/json".toMediaTypeOrNull())
                    val retrofit = met.getRetrofit()


                    val service = retrofit.create(APIService::class.java)
                    CoroutineScope(Dispatchers.IO).launch {

                        val response = service.cancelarSolicitud(requestBody)
                        withContext(Dispatchers.Main) {
                            //El siguiente IF controla si se pudo conectar a la API o no
                            if (response.isSuccessful) {
                                // Convert raw JSON to pretty JSON using GSON library
                                val gson = GsonBuilder().setPrettyPrinting().create()
                                val prettyJson = gson.toJson(
                                    JsonParser.parseString(
                                        response.body()?.string()
                                    )
                                )
                                val intent = Intent(mContext,Vista_Tutor::class.java)
                                mContext.startActivity(intent)

                            }
                        }
                    }
                }
            }
        }

        /*layout.txtTitulo.text = producto.titulo
        layout.txtPrecio.text = "${producto.precio}"
        layout.txtDescripcion.text = producto.descripcion
        layout.txtConsola.text = producto.consola
        layout.imageView.setImageResource(producto.imagen)*/

        return binding.root
    }


    //val layout = LayoutInflater.from(mContext).inflate(R.layout.list_item_alumno, parent, false)


}