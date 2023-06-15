package com.example.app_systempar

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.app_systempar.databinding.ListItemAlumnoBinding

class AlumnosAdapter(private val mContext: Context, private val listaSolicitudes: List<SolicitudInfo>) : ArrayAdapter<SolicitudInfo>(mContext, 0, listaSolicitudes) {
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

            mContext.startActivity(intent)
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