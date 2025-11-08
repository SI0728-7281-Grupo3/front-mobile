package com.example.restyle_mobile.business_search.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.restyle_mobile.R

class BusinessPortfolioFragment : Fragment() {

    private var businessId: Int = 0
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        businessId = arguments?.getInt("BUSINESS_ID") ?: 0
        token = arguments?.getString("TOKEN")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_business_portfolio, container, false)

        loadBusinessPortoflio(businessId)
        return view
    }

    companion object {
        fun newInstance(businessId: Int, token: String): BusinessPortfolioFragment {
            val fragment = BusinessPortfolioFragment()
            val args = Bundle()
            args.putInt("BUSINESS_ID", businessId)
            args.putString("TOKEN", token)
            fragment.arguments = args
            return fragment
        }
    }

    private fun loadBusinessPortoflio(businessId: Int) {

    }
}