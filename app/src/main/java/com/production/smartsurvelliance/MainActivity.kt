package com.production.smartsurvelliance

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.auth.AuthChannelEventName
import com.amplifyframework.auth.AuthProvider
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.InitializationStatus
import com.amplifyframework.core.plugin.Plugin
import com.amplifyframework.datastore.AWSDataStorePlugin
import com.amplifyframework.hub.HubChannel
import com.amplifyframework.hub.HubEvent
import com.amplifyframework.predictions.aws.AWSPredictionsPlugin
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }


        /**Fetch Current user session*/
        Amplify.Auth.fetchAuthSession(
            { result -> Timber.i(result.toString()) },
            { error -> Timber.e( error.toString()) }
        )

        /**Initialize the WebUI*/
        Amplify.Auth.signInWithSocialWebUI(
            AuthProvider.facebook(),
            this,
            { result -> Timber.i(result.toString()) },
            { error -> Timber.e( error.toString()) }
        )

        /**Subscribe to event listeners, listen to success or failure  from WebUi*/
        Amplify.Hub.subscribe(HubChannel.AUTH) { hubEvent: HubEvent<*> ->

            Timber.tag("AuthQuickstart").d( "Called")
            when (hubEvent.name) {
                InitializationStatus.SUCCEEDED.toString() -> {
                    Timber.i( "Auth successfully initialized")
                }
                InitializationStatus.FAILED.toString() -> {
                    Timber.i("Auth failed to succeed")
                }
                else -> {
                    when (
                        AuthChannelEventName.valueOf(hubEvent.name)) {
                        AuthChannelEventName.SIGNED_IN -> Timber.i( "Auth just became signed in.")
                        AuthChannelEventName.SIGNED_OUT -> Timber.i( "Auth just became signed out.")
                        AuthChannelEventName.SESSION_EXPIRED -> Timber.i( "Auth session just expired.")
                    }
                }
            }
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
           toolbar.visibility = View.GONE
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
            Timber.d("Home Place")
            Amplify.Auth.handleWebUISignInResponse(data)
        }
    }
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
