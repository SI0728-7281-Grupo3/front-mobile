package com.example.restyle_mobile.business_portfolio.Activity

import Adapter.Adapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.restyle_mobile.BottomNavigationHelper
import com.example.restyle_mobile.R
import com.example.restyle_mobile.business_search.Activity.SearchBusinessesActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Portfolio : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var addProjectButton: Button
    private lateinit var NombreEmpresa: TextView
    private lateinit var textoProyectos: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.portfolio_main)

        //Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        window.statusBarColor = ContextCompat.getColor(this, R.color.blue)

        //Navigation Bar
        window.navigationBarColor = ContextCompat.getColor(this, R.color.blue)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        BottomNavigationHelper().setupBottomNavigation(this, bottomNavigationView)


        // Inicialización de vistas
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
        addProjectButton = findViewById(R.id.btnAddProject)
        NombreEmpresa = findViewById(R.id.txtNombreEmpresa)
        textoProyectos = findViewById(R.id.txtProyectos)

        // Configurar ViewPager con el adaptador para manejar las pestañas
        val adapter = Adapter(this)
        viewPager.adapter = adapter

        // Conectar TabLayout con ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = if (position == 0) "Información" else "Proyectos"
        }.attach()

        // Ocultar o mostrar el botón dependiendo de la pestaña seleccionada
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 1) {
                    addProjectButton.visibility = View.VISIBLE
                    NombreEmpresa.visibility = View.VISIBLE
                    textoProyectos.visibility = View.VISIBLE
                } else {
                    addProjectButton.visibility = View.GONE
                    NombreEmpresa.visibility = View.GONE
                    textoProyectos.visibility = View.GONE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        if (tabLayout.selectedTabPosition == 0) {
            addProjectButton.visibility = View.GONE
            NombreEmpresa.visibility = View.GONE
            textoProyectos.visibility = View.GONE
        }
        // Configurar el Intent para navegar a ProjectCreation
        addProjectButton.setOnClickListener {
            val intent = Intent(this, ProjectCreation::class.java)
            startActivity(intent)
        }
    }
}
