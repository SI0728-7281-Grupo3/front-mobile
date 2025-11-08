package Fragment

import Persistence.UserHelper
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.restyle_mobile.R
import com.example.restyle_mobile.business_portfolio.Activity.Portfolio
import com.example.restyle_mobile.business_search.Activity.SearchBusinessesActivity

class LoginFragment : Fragment() {

    lateinit var dbHelper: UserHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        dbHelper = UserHelper(requireContext())

        val etEmail = view.findViewById<EditText>(R.id.et_email)
        val etPassword = view.findViewById<EditText>(R.id.et_password)
        val btnLogin = view.findViewById<Button>(R.id.btn_login)
        val tvRecoverPassword = view.findViewById<TextView>(R.id.tv_recover_password)
        val tvRegisterPrompt = view.findViewById<TextView>(R.id.tv_register_prompt)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            val (isLoginSuccessful, isRemodeler, userId) = dbHelper.checkUserWithId(email, password)

            if (isLoginSuccessful) {
                Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT).show()

                // Save user session in SharedPreferences
                val sharedPreferences = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("userId", userId) // Store user ID in session
                editor.apply()

                // Check if the user is a remodeler or not and act accordingly
                val intent = if (isRemodeler == true) {
                    Intent(requireContext(), Portfolio::class.java)
                } else {
                    Intent(requireContext(), SearchBusinessesActivity::class.java)
                }
                startActivity(intent)

                requireActivity().finish()
            } else {
                Toast.makeText(requireContext(), "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }

        tvRecoverPassword.setOnClickListener {
            // Navigate to RecoverPasswordFragment
            val recoverFragment = RecoverPasswordFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, recoverFragment)
                .addToBackStack(null)
                .commit()
        }

        tvRegisterPrompt.setOnClickListener {
            val registerFragment = RegisterFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, registerFragment)
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}
