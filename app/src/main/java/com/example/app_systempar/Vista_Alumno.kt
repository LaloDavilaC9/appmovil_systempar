package com.example.app_systempar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator



class Vista_Alumno : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_alumno)

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