package com.example.app_systempar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.app_systempar.databinding.ActivityAlumnoDetallesBinding

class AlumnoDetalles : AppCompatActivity() {
    private lateinit var binding: ActivityAlumnoDetallesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlumnoDetallesBinding.inflate(layoutInflater)
        val view = binding.root
        val solicitudInfo = intent.getSerializableExtra("info") as SolicitudInfo

        if(solicitudInfo.tutor_nombre_completo != null){
            binding.txtInfoTutor.layoutParams.height = 200
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

        binding.txtInfoAsesoria.text = "Materia: ${solicitudInfo.materia_nombre}\n\n" +
                "Tema: ${solicitudInfo.solicitud_tema}\n\n"+
        "Descripción: ${solicitudInfo.solicitud_descripcion}\n\n"+
        "Lugar: ${lugar}\n"+
        "Modalidad: ${modalidad}\n"+
        "Urgencia: ${urgencia}\n"

        binding.btnRegresar.setOnClickListener {
            val intent = Intent(this, Vista_Alumno::class.java)
            startActivity(intent)
        }
        setContentView(view)
    }
}