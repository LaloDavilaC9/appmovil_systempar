package com.example.app_systempar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Vista_Tutor : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_tutor)
        tabLayout = findViewById(R.id.tab_menu)
        viewPager = findViewById(R.id.contenedor_fragmentos)

        val adapter = MyPagerAdapter(supportFragmentManager, lifecycle)
        adapter.addFragment(Tutor_Solicitud(), "Solicitudes")
        adapter.addFragment(Tutor_Proceso(), "En proceso")
        adapter.addFragment(Tutor_Proximas(), "Próximas")
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = adapter.getTitle(position)
        }.attach()
    }
}
