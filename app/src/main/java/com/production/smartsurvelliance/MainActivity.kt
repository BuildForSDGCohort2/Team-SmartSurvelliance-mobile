package com.production.smartsurvelliance

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        VlcOptionsProvider.getInstance().options = VlcOptionsProvider
//            .Builder(this)
//            .setVerbose(true)
//            // See R.array.subtitles_encoding_values
//            .withSubtitleEncoding("KOI8-R")
//            .build()
//
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
           toolbar.visibility = View.GONE
        }


//        val navController = findNavController(R.id.nav_host_fragment)

//        val toolbar = findViewById<Toolbar>(R.id)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

//        val appBarConfiguration = AppBarConfiguration(setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
//
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)

    }
}