package com.example.app_systempar

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.app_systempar.databinding.ListItemAlumnoBinding
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class AlumnosAdapter(private val mContext: Context, private val listaSolicitudes: List<SolicitudInfo>) : ArrayAdapter<SolicitudInfo>(mContext, 0, listaSolicitudes) {
    private val met = Metodos()
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {


        //val layout = LayoutInflater.from(mContext).inflate(R.layout.list_item_alumno, parent, false)


        val binding = ListItemAlumnoBinding.inflate(LayoutInflater.from(mContext), parent, false)
        val alumno = listaSolicitudes[position]

        //binding.txtNombre.text = alumno.nombre
       // binding.txtCorreo.text = alumno.correo
        //binding.txtMateria.text = alumno.materia
        if(alumno.tutor_nombre_completo != null){
            binding.txtNombre.text = alumno.tutor_nombre_completo
        }
        else{
            binding.txtNombre.text = "EN ESPERA DE TUTOR"
        }
        binding.txtMateria.text = alumno.materia_nombre

        binding.btnConfirmar.setOnClickListener {
            val intent = Intent(mContext, AlumnoDetalles::class.java)
            intent.putExtra("info", alumno)
            intent.putExtra("viene","alumno")

            mContext.startActivity(intent)
        }

        binding.btnCancelar.setOnClickListener {
            val infoAlumno = AlumnoManager.alumnoResponse
            val jsonObject = JSONObject()
            if (infoAlumno != null) {
                val array = infoAlumno.array
                if (array.isNotEmpty()) {
                    jsonObject.put("quien", "alumno")
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
                                val intent = Intent(mContext,Vista_Alumno::class.java)
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


}


class Solicitud(val nombre:String,val materia: String,val correo: String, val telefono: String) : java.io.Serializable