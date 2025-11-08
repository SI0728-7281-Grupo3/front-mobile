package com.example.restyle_mobile

import Fragment.RegisterFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        // Load the RegisterFragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container, RegisterFragment())
                commit()
            }
        }
    }
}

