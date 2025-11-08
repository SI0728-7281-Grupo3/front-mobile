package com.example.restyle_mobile.business_search.Fragment

import Beans.Business
import Beans.Businesses
import Interface.BusinessService
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.restyle_mobile.AuthInterceptor
import com.example.restyle_mobile.R
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class BusinessProfileFragment : Fragment() {

    private var businessId: Int = 0
    private lateinit var businessService: BusinessService
    private var token: String? = null

    private var businessNameTextView: TextView? = null
    private var businessDescriptionTextView: TextView? = null
    private var businessImageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        businessId = arguments?.getInt("BUSINESS_ID") ?: 0
        token = arguments?.getString("TOKEN")

        // Configurar Retrofit con autenticación si el token está disponible
        token?.let {
            val retrofitWithAuth = provideRetrofitWithAuth(it)
            businessService = retrofitWithAuth.create(BusinessService::class.java)
        } ?: run {
            // Si no hay token, manejar el caso de alguna manera (por ejemplo, mostrar error)
            Toast.makeText(context, "No se encontró el token", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_business_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar las vistas
        businessNameTextView = view.findViewById(R.id.businessCardName)
        businessDescriptionTextView = view.findViewById(R.id.businessCardDescription)
        businessImageView = view.findViewById(R.id.businessCardImage)

        // Cargar el perfil del negocio
        loadBusinessProfile(businessId)
    }

    // Función para crear Retrofit con el token de autenticación
    private fun provideRetrofitWithAuth(token: String): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(token)) // Agregar el token al interceptor
            .build()

        return Retrofit.Builder()
            .baseUrl("https://restyle-backend-v2.fra1.zeabur.app/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        fun newInstance(businessId: Int, token: String): BusinessProfileFragment {
            val fragment = BusinessProfileFragment()
            val args = Bundle()
            args.putInt("BUSINESS_ID", businessId)
            args.putString("TOKEN", token)
            fragment.arguments = args
            return fragment
        }
    }

    private fun loadBusinessProfile(businessId: Int) {
        businessService.getBusinessById(businessId).enqueue(object : Callback<Businesses> {
            override fun onResponse(call: Call<Businesses>, response: Response<Businesses>) {
                val business = response.body()

                if (business != null) {
                    // Mostrar los datos del negocio en las vistas
                    businessNameTextView?.text = business.name
                    businessDescriptionTextView?.text = business.description

                    // Cargar la imagen con Picasso
                    Picasso.get()
                        .load(business.image)
                        .into(businessImageView)
                }
            }

            override fun onFailure(call: Call<Businesses>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}