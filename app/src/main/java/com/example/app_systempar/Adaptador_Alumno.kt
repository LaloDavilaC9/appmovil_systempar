package com.example.app_systempar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.app_systempar.databinding.ListItemAlumnoBinding

class AlumnosAdapter(private val mContext: Context, private val listaProductos: List<Solicitud>) : ArrayAdapter<Solicitud>(mContext, 0, listaProductos) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {


        //val layout = LayoutInflater.from(mContext).inflate(R.layout.list_item_alumno, parent, false)


        val binding = ListItemAlumnoBinding.inflate(LayoutInflater.from(mContext), parent, false)
        val alumno = listaProductos[position]

        binding.txtNombre.text = alumno.nombre
       // binding.txtCorreo.text = alumno.correo
        binding.txtMateria.text = alumno.materia

        /*layout.txtTitulo.text = producto.titulo
        layout.txtPrecio.text = "${producto.precio}"
        layout.txtDescripcion.text = producto.descripcion
        layout.txtConsola.text = producto.consola
        layout.imageView.setImageResource(producto.imagen)*/

        return binding.root
    }


}


class Solicitud(val nombre:String,val materia: String,val correo: String, val telefono: String) : java.io.Serializable