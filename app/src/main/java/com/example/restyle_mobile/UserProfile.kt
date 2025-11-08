package com.example.restyle_mobile

import Persistence.UserHelper
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class UserProfile : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dbHelper: UserHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_profile)

        // Retrieve userId from SharedPreferences
        sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", null)

        if (userId != null) {
            // Fetch user data using UserHelper
            dbHelper = UserHelper(this)
            val user = dbHelper.getUserDataById(userId)

            // Display user data in UI
            val tvUserName = findViewById<TextView>(R.id.tvUserName)
            tvUserName.text = user?.fullName
        }

        // Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Navigation Bar
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        BottomNavigationHelper().setupBottomNavigation(this, bottomNavigationView)
    }
}
