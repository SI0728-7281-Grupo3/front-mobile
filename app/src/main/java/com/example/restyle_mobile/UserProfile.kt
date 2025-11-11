package com.example.restyle_mobile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.restyle_mobile.home_screen.Activity.HomeActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class UserProfile : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_profile)

        // Obtener datos guardados en SharedPreferences
        val prefs = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val username = prefs.getString("username", "Usuario desconocido")
        val userId = prefs.getInt("userId", -1)
        val token = prefs.getString("token", null)
        val email = prefs.getString("email", "correo@ejemplo.com")
        val phone = prefs.getString("phone", "Sin número")
        val description = prefs.getString("description", "Sin descripción disponible")

        // Vincular vistas
        val tvUserName = findViewById<TextView>(R.id.tvUserName)
        val tvEmail = findViewById<TextView>(R.id.tvEmail)
        val tvPhoneNumber = findViewById<TextView>(R.id.tvPhoneNumber)
        val tvAbout = findViewById<TextView>(R.id.tvAbout)
        val ivUserImage = findViewById<ImageView>(R.id.toolbar_client)

        // Mostrar datos del usuario
        tvUserName.text = username
        tvEmail.text = email
        tvPhoneNumber.text = phone
        tvAbout.text = description

        // Cargar imagen (temporalmente un placeholder)
        Glide.with(this)
            .load("https://via.placeholder.com/150")
            .circleCrop()
            .into(ivUserImage)

        // Configurar Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Configurar Bottom Navigation
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        BottomNavigationHelper().setupBottomNavigation(this, bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.nav_profile

        // Logout Button
        val logoutButton: Button = findViewById<Button>(R.id.logout_button)
        logoutButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}

