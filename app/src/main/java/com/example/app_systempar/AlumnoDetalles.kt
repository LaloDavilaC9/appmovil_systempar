package com.example.app_systempar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.app_systempar.databinding.ActivityAlumnoDetallesBinding
import java.text.SimpleDateFormat
import java.util.*

class AlumnoDetalles : AppCompatActivity() {
    private lateinit var binding: ActivityAlumnoDetallesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlumnoDetallesBinding.inflate(layoutInflater)
        val view = binding.root
        val solicitudInfo = intent.getSerializableExtra("info") as SolicitudInfo
        val viene  = intent.getStringExtra("viene") as String

        if(viene == "tutor"){
            binding.txtTutor.text = "DATOS DEL ALUMNO"
        }

        if(solicitudInfo.tutor_nombre_completo != null){
            binding.txtInfoTutor.layoutParams.height = 175
            binding.txtInfoTutor.text = "Nombre: ${solicitudInfo.tutor_nombre_completo}\n " +
                    "Correo: ${solicitudInfo.alumno_correo}\n" +
                    "Teléfono: ${solicitudInfo.alumno_telefono}\n"
        }
        else{
            binding.txtInfoTutor.layoutParams.height =100
            binding.txtInfoTutor.text = "En espera de tutor"
        }

        var urgencia = if (solicitudInfo.solicitud_urgencia == "U") "Urgente" else "Puede esperar"
        var modalidad = if (solicitudInfo.solicitud_modalidad == "P") "Presencial" else "Virtual"

        var lugar = if (solicitudInfo.solicitud_lugar == null ) "Por definir" else solicitudInfo.solicitud_lugar

        val fechaTexto = solicitudInfo.solicitud_fecha_programacion
        val formatoTexto = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        formatoTexto.timeZone = TimeZone.getTimeZone("UTC")
        val fecha = formatoTexto.parse(fechaTexto)

// Sumar 1 hora a la fecha
        val calendar = Calendar.getInstance()
        calendar.time = fecha
        calendar.add(Calendar.HOUR_OF_DAY, 1)

// Convertir la fecha a la zona horaria deseada
        val formatoFecha = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        formatoFecha.timeZone = TimeZone.getTimeZone("America/Mexico_City")
        val fechaFormateada = formatoFecha.format(calendar.time)



        binding.txtInfoAsesoria.text = "Materia: ${solicitudInfo.materia_nombre}\n\n" +
                "Tema: ${solicitudInfo.solicitud_tema}\n\n"+
        "Descripción: ${solicitudInfo.solicitud_descripcion}\n\n"+
        "Lugar: ${lugar}\n"+
        "Modalidad: ${modalidad}\n"+
        "Urgencia: ${urgencia}\n"+
        "Fecha: ${fechaFormateada}\n"

        binding.btnRegresar.setOnClickListener {
            if(viene=="alumno"){
                val intent = Intent(this, Vista_Alumno::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this, Vista_Tutor::class.java)
                startActivity(intent)
            }
        }
        setContentView(view)
    }
}