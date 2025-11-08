package com.example.restyle_mobile.business_search.Activity

import Beans.Business
import Beans.Businesses
import Interface.BusinessService
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.example.Fragment.DialogLoadingFragment
import com.example.restyle_mobile.AuthInterceptor
import com.example.restyle_mobile.Beans.SignInRequest
import com.example.restyle_mobile.BottomNavigationHelper
import com.example.restyle_mobile.Interface.AuthService
import com.example.restyle_mobile.R
import com.example.restyle_mobile.business_portfolio.Activity.Portfolio
import com.example.restyle_mobile.business_search.Adapter.BusinessProfilePagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BusinessProfileActivity : AppCompatActivity() {

    private lateinit var businessService: BusinessService
    private var tokenA: String? = null

    fun provideRetrofitWithAuth(token: String): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(token))
            .build()

        return Retrofit.Builder()
            .baseUrl("https://restyle-backend-v2.fra1.zeabur.app/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_business_profile)

        //Loading Fragment
        val loadingDialog = DialogLoadingFragment.newInstance()
        loadingDialog.show(supportFragmentManager, DialogLoadingFragment.TAG)

        //Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //Navigation Bar
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        BottomNavigationHelper().setupBottomNavigation(this, bottomNavigationView)

        val businessId = intent.getIntExtra("BUSINESS_ID", -1)

        // Configurar Retrofit para sign-in sin autenticación
        val retrofit = Retrofit.Builder()
            .baseUrl("https://restyle-backend-v2.fra1.zeabur.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Crear AuthService
        val authService = retrofit.create(AuthService::class.java)

        // Realizar el sign-in y obtener el token
        GlobalScope.launch(Dispatchers.IO) {
            val signInRequest = SignInRequest("admin", "admin")
            val response = authService.signIn(signInRequest)

            if (response.isSuccessful) {
                val token = response.body()?.token
                token?.let {
                    // Crear Retrofit con autenticación
                    val retrofitWithAuth = provideRetrofitWithAuth(it)

                    // Inicializar el servicio de negocios con autenticación
                    businessService = retrofitWithAuth.create(BusinessService::class.java)

                    // Obtener los negocios y actualizar la UI
                    getBusinessById(businessId)

                    Handler(Looper.getMainLooper()).postDelayed({
                        // Ocultar el DialogFragment cuando el backend haya respondido
                        loadingDialog.dismiss()
                        // Continuar con el flujo de la aplicación
                    }, 2000)

                    // Actualiza la UI en el hilo principal
                    runOnUiThread {
                        setupViewPager(businessId, it) // Llama a la función para configurar el ViewPager
                    }
                    tokenA = it
                }
            } else {
                println("Error en el sign-in: ${response.errorBody()?.string()}")
            }
        }
    }

    private fun setupViewPager(businessId: Int, token: String) {
        val tabLayout = findViewById<TabLayout>(R.id.business_profile_tab_layout)
        val viewPager = findViewById<ViewPager2>(R.id.business_profile_view_pager)
        val adapter = BusinessProfilePagerAdapter(this, businessId, token)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Perfil"
                1 -> tab.text = "Proyectos"
                2 -> tab.text = "Portafolio"
            }
        }.attach()

        // Añadir un listener para el TabLayout
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null && tab.position == 2) { // Verifica si se seleccionó la pestaña "Portafolio"
                    val intent = Intent(this@BusinessProfileActivity, Portfolio::class.java)
                    startActivity(intent)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // No es necesario implementar
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // No es necesario implementar
            }
        })
    }

    private fun getBusinessById(businessId: Int){
        businessService.getBusinessById(businessId).enqueue(object : Callback<Businesses> {
            override fun onResponse(p0: Call<Businesses>, p1: Response<Businesses>) {
                val business = p1?.body()

                if(business != null){
                    findViewById<TextView>(R.id.businessCardName).text = business.name
                    findViewById<TextView>(R.id.businessCardAddress).text = business.address
                    findViewById<TextView>(R.id.businessCardCity).text = business.city
                    findViewById<TextView>(R.id.businessCardExpertise).text = business.expertise
                }
            }
            override fun onFailure(p0: Call<Businesses>, p1: Throwable) {
                p1.printStackTrace()
            }
        })
    }
}