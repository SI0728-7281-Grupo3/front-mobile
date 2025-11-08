package Fragment

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

class RecoverPasswordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recover_password, container, false)

        val etEmail = view.findViewById<EditText>(R.id.et_email)
        val btnRecover = view.findViewById<Button>(R.id.btn_recover)
        val tvBackToLogin = view.findViewById<TextView>(R.id.tv_back_to_login)

        btnRecover.setOnClickListener {
            val email = etEmail.text.toString()
            Toast.makeText(requireContext(), "Recovery instructions sent to $email", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        }

        tvBackToLogin.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return view
    }
}
