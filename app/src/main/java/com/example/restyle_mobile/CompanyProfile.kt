package com.example.restyle_mobile

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.content.Intent
import android.widget.Button
import com.example.restyle_mobile.R
import com.example.restyle_mobile.EditCompany
import com.example.restyle_mobile.home_screen.Activity.HomeActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class CompanyProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_company_profile)

        //Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //Navigation Bar
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        BottomNavigationHelper().setupBottomNavigation(this, bottomNavigationView)

        // Edit Profile Button
        val editProfileButton: Button = findViewById<Button>(R.id.edit_profile_company_button)
        editProfileButton.setOnClickListener {
            val intent = Intent(this, EditCompany::class.java)
            startActivity(intent)
        }

        // Logout Button
        val logoutButton: Button = findViewById<Button>(R.id.logout_button)
        logoutButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

    }
}