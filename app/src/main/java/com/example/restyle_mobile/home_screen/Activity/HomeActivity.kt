package com.example.restyle_mobile.home_screen.Activity

import Fragment.LoginFragment
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.restyle_mobile.LoginActivity
import com.example.restyle_mobile.R
import com.example.restyle_mobile.Register
import com.example.restyle_mobile.business_search.Activity.SearchBusinessesActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        //Buttons
        val signupButton: Button = findViewById(R.id.signup_button)
        val loginButton: Button = findViewById(R.id.login_button)

        signupButton.setOnClickListener { //Cambiar por SignupActivity
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener { //Cambiar por LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}