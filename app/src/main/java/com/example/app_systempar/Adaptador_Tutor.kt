package com.example.app_systempar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import com.example.app_systempar.databinding.ListItemTutorBinding

class Adaptador_Tutor(private val mContext: Context, private val listaProductos: List<Solicitud>,private val texto : String) : ArrayAdapter<Solicitud>(mContext, 0, listaProductos) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {


        //val layout = LayoutInflater.from(mContext).inflate(R.layout.list_item_alumno, parent, false)


        val binding = ListItemTutorBinding.inflate(LayoutInflater.from(mContext), parent, false)
        val alumno = listaProductos[position]

        binding.txtNombre.text = alumno.nombre
        // binding.txtCorreo.text = alumno.correo
        binding.txtMateria.text = alumno.materia

        if(texto == "PROXIMAS"){
            binding.btnConfirmar.isVisible = false
        }
        else{
            binding.btnConfirmar.text = texto
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