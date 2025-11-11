package Fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.example.restyle_mobile.Beans.SignUpRequest
import com.example.restyle_mobile.Interface.RestyleApiClient
import com.example.restyle_mobile.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.File

class RegisterFragment : Fragment() {

    private var photoUri: Uri? = null
    private val PICK_IMAGE_REQUEST = 1
    private val placeholderImageUrl = "https://via.placeholder.com/150"
    private val termsAndConditionsText =
        "Acepto los <a href='https://si0728-7281-grupo3.github.io/landingpage/terms.html'>términos y condiciones</a>."

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        val etFullName = view.findViewById<EditText?>(R.id.et_full_name)
        val etEmail = view.findViewById<EditText?>(R.id.et_email)
        val etPassword = view.findViewById<EditText?>(R.id.et_password)
        val etConfirmPassword = view.findViewById<EditText?>(R.id.et_confirm_password)
        // Estos dos pueden no existir en tu layout — los obtenemos como nullable y usamos "" por defecto
        val etPhone = view.findViewById<EditText?>(R.id.et_phone)
        val etDescription = view.findViewById<EditText?>(R.id.et_description)

        val cbIsRemodeler = view.findViewById<CheckBox>(R.id.cb_is_remodeler)
        val btnRegister = view.findViewById<Button>(R.id.btn_register)
        val btnSelectPhoto = view.findViewById<Button>(R.id.btn_upload_photo)
        val ivSelectedPhoto = view.findViewById<ImageView>(R.id.iv_profile_photo)
        val cbTermsAndConditions = view.findViewById<CheckBox>(R.id.cb_terms_conditions)
        val tvAlreadyHaveAccount = view.findViewById<TextView>(R.id.tv_already_have_account)

        val spannableString =
            SpannableString(HtmlCompat.fromHtml(termsAndConditionsText, HtmlCompat.FROM_HTML_MODE_LEGACY))
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val termsIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://mondongodev.github.io/restyle-landing-page/terms.html"))
                startActivity(termsIntent)
            }
        }
        val startIndex = spannableString.indexOf("términos y condiciones")
        val endIndex = startIndex + "términos y condiciones".length
        if (startIndex >= 0) {
            spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        cbTermsAndConditions.text = spannableString
        cbTermsAndConditions.movementMethod = LinkMovementMethod.getInstance()

        btnSelectPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        btnRegister.setOnClickListener {
            val fullName = etFullName?.text?.toString()?.trim() ?: ""
            val email = etEmail?.text?.toString()?.trim() ?: ""
            val password = etPassword?.text?.toString() ?: ""
            val confirmPassword = etConfirmPassword?.text?.toString() ?: ""
            val phone = etPhone?.text?.toString() ?: ""
            val description = etDescription?.text?.toString() ?: ""
            val isRemodeler = cbIsRemodeler.isChecked

            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Completa los campos obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(requireContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!cbTermsAndConditions.isChecked) {
                Toast.makeText(requireContext(), "Debes aceptar los términos y condiciones", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val roles = if (isRemodeler) listOf("ROLE_REMODELER") else listOf("ROLE_CONTRACTOR")
            val photoUrl = placeholderImageUrl
            val username = fullName

            val signUpRequest = SignUpRequest(
                username = username,
                password = password,
                roles = roles,
                email = email,
                firstName = "",
                paternalSurname = "",
                maternalSurname = "",
                description = description,
                phone = phone,
                image = photoUrl
            )

            //Log.d("RegisterFragment", "Rol seleccionado: $roles")

            // Convertir objeto a JSON real
            //val gson = com.google.gson.GsonBuilder().setPrettyPrinting().create()
            //val jsonPayload = gson.toJson(signUpRequest)
            //Log.d("RegisterFragment", "JSON enviado al backend:\n$jsonPayload")

            registerUser(signUpRequest)

            registerUser(signUpRequest)
        }

        tvAlreadyHaveAccount.setOnClickListener {
            val loginFragment = LoginFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, loginFragment)
                .addToBackStack(null)
                .commit()
        }

        return view
    }

    private fun registerUser(request: SignUpRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RestyleApiClient.authService.signUp(request)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "Registro exitoso", Toast.LENGTH_SHORT).show()
                        val loginFragment = LoginFragment()
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, loginFragment)
                            .addToBackStack(null)
                            .commit()
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Toast.makeText(requireContext(), "Error al registrarse: ${response.code()} ${errorBody ?: ""}", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error HTTP: ${e.message}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Fallo de red: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            photoUri = data?.data
            view?.findViewById<ImageView>(R.id.iv_profile_photo)?.setImageURI(photoUri)
        }
    }
}
