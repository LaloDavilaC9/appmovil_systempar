package com.example.app_systempar

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.sql.Types.NULL


class Vista_Alumno : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    //private lateinit var binding: ActivityVistaAlumnoBinding
    private lateinit var botonCambio : Button
    private lateinit var botonSalir : Button
    private lateinit var botonActualizar : Button
    private val met = Metodos()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_alumno)


        //binding = ActivityVistaAlumnoB$inding.inflate(layoutInflater)
        //setContentView(binding.root)
        tabLayout = findViewById(R.id.tab_menu)
        viewPager = findViewById(R.id.contenedor_fragmentos)

        val adapter = MyPagerAdapter(supportFragmentManager, lifecycle)
        adapter.addFragment(Alumno_Solicitud(), "Solicitudes")
        adapter.addFragment(Alumno_Proceso(), "En proceso")
        adapter.addFragment(Alumno_Proximas(), "Próximas")
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = adapter.getTitle(position)
        }.attach()

        botonCambio = findViewById(R.id.btn_cambio_vista)
        val infoAlumno = AlumnoManager.alumnoResponse
        val array = infoAlumno?.array
        if(array?.get(0)?.tutor_id == NULL){
            botonCambio.isVisible = false
        }

        botonCambio.setOnClickListener{
            val intent = Intent(this,Vista_Tutor::class.java)
            startActivity(intent)
        }

        botonSalir = findViewById(R.id.btn_cerrarSesion)
        botonSalir.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        botonActualizar  = findViewById(R.id.btn_actualizar)
        botonActualizar.setOnClickListener {
            val intent = Intent(this,Vista_Alumno::class.java)
            startActivity(intent)
        }
    }


}



class MyPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragmentList = arrayListOf<Fragment>()
    private val titleList = arrayListOf<String>()

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        titleList.add(title)
    }

    fun getTitle(position: Int): String {
        return titleList[position]
    }
}