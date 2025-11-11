package Fragment

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
import com.example.restyle_mobile.Interface.RestyleApiClient
import com.example.restyle_mobile.Beans.SignInRequest
import com.example.restyle_mobile.business_portfolio.Activity.Portfolio
import com.example.restyle_mobile.business_search.Activity.SearchBusinessesActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val etEmail = view.findViewById<EditText>(R.id.et_email)
        val etPassword = view.findViewById<EditText>(R.id.et_password)
        val btnLogin = view.findViewById<Button>(R.id.btn_login)
        val tvRecoverPassword = view.findViewById<TextView>(R.id.tv_recover_password)
        val tvRegisterPrompt = view.findViewById<TextView>(R.id.tv_register_prompt)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loginUser(email, password)
        }

        tvRecoverPassword.setOnClickListener {
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

    private fun loginUser(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RestyleApiClient.authService.signIn(SignInRequest(email, password))

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT).show()

                            // Guardar sesión (token + id de usuario)
                            val prefs = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE)
                            prefs.edit()
                                .putInt("userId", data.id)
                                .putString("username", data.username)
                                .putString("token", data.token)
                                .apply()

                            // 🔹 Si tu backend devuelve roles o tipo de usuario, puedes decidir a dónde redirigir aquí
                            val intent = Intent(requireContext(), SearchBusinessesActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Invalid credentials (${response.code()})", Toast.LENGTH_SHORT).show()
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Connection error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}