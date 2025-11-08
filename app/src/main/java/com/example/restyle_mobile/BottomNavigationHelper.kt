package com.example.restyle_mobile

import android.app.Activity
import android.content.Intent
import com.example.restyle_mobile.business_portfolio.Activity.Portfolio
import com.example.restyle_mobile.business_search.Activity.SearchBusinessesActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationHelper {
    fun setupBottomNavigation(activity: Activity, bottomNavigationView: BottomNavigationView) {
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    if (activity !is SearchBusinessesActivity) {
                        val intent = Intent(activity, SearchBusinessesActivity::class.java)
                        activity.startActivity(intent)
                    }
                    true
                }
                R.id.nav_projects -> {
                    if (activity !is SearchBusinessesActivity) {
                        val intent = Intent(activity, SearchBusinessesActivity::class.java)
                        activity.startActivity(intent)
                    }
                    true
                }
                R.id.nav_businesses -> {
                    if (activity !is SearchBusinessesActivity) {
                        val intent = Intent(activity, SearchBusinessesActivity::class.java)
                        activity.startActivity(intent)
                    }
                    true
                }
                R.id.nav_portfolios -> {
                    if (activity !is Portfolio) {
                        val intent = Intent(activity, Portfolio::class.java)
                        activity.startActivity(intent)
                    }
                    true
                }
                R.id.nav_profile -> {
                    if (activity !is CompanyProfile) {
                        val intent = Intent(activity, CompanyProfile::class.java)
                        activity.startActivity(intent)
                    }
                    true
                }
                else -> false
            }
        }
    }
}