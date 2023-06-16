package com.example.app_systempar

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import com.example.app_systempar.databinding.ListItemTutorBinding

class Adaptador_Tutor(private val mContext: Context, private val listaProductos: List<SolicitudInfo>,private val texto : String) : ArrayAdapter<SolicitudInfo>(mContext, 0, listaProductos) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {


        //val layout = LayoutInflater.from(mContext).inflate(R.layout.list_item_alumno, parent, false)


        val binding = ListItemTutorBinding.inflate(LayoutInflater.from(mContext), parent, false)
        val alumno = listaProductos[position]

        binding.txtNombre.text = alumno.tutor_nombre_completo
        //binding.txtCo
        binding.txtMateria.text = alumno.materia_nombre

        if(texto == "PRÓXIMAS"){
            binding.btnConfirmar.isVisible = false
        }
        else{
            binding.btnConfirmar.text = texto
        }

        binding.btnDetalles.setOnClickListener {
            val intent = Intent(mContext, AlumnoDetalles::class.java)
            intent.putExtra("info", alumno)
            intent.putExtra("viene","tutor")

            mContext.startActivity(intent)
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