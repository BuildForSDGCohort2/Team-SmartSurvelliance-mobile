package com.production.smartsurvelliance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreen : AppCompatActivity() {

    //Splash screen timer
    private val SPLASH_TIME_OUT = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ss_splash_screen_activity)

        Handler().postDelayed(object:Runnable {
            /*
           * Showing splash screen with a timer. This will be useful when you
           * want to show case your app ic_logo / company
           */
          override fun run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                val homeIntent = Intent(this@SplashScreen, MainActivity::class.java)
                startActivity(homeIntent)
                // close this activity
                finish()
            }
        }, SPLASH_TIME_OUT.toLong())
    }
}