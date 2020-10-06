package com.production.smartsurvelliance

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.amazonaws.mobile.client.SignOutOptions
import com.amplifyframework.api.ApiException
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.auth.AuthChannelEventName
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.auth.options.AuthSignOutOptions
import com.amplifyframework.auth.result.AuthSignInResult
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.InitializationStatus
import com.amplifyframework.datastore.generated.model.Visitor
import com.amplifyframework.hub.HubChannel
import com.amplifyframework.hub.HubEvent
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.iid.FirebaseInstanceId
import com.production.smartsurvelliance.helper.BuzzType
import com.production.smartsurvelliance.helper.KEY_MESSAGE
import com.production.smartsurvelliance.helper.KEY_NEW_VISITOR_FILTER
import com.production.smartsurvelliance.helper.buzz
import com.production.smartsurvelliance.ui.dialogs.VerifyPictureFragment
import timber.log.Timber


class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var broadcastReceiver : BroadcastReceiver  =  object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val message = intent?.getStringExtra(KEY_MESSAGE)
            buzz(BuzzType.NOTIFICATION.pattern, this@MainActivity)
            Timber.d(message)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()

            displayDialog()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)

        val navView: NavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        val navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home,
            R.id.nav_dashboard,
            R.id.nav_live_feed,
            R.id.nav_notifications,
            R.id.nav_customer_support,
            R.id.nav_faqs,
            R.id.nav_subscriptions), drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)

        navView.setupWithNavController(navController)

//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            toolbar.visibility = View.GONE
//        }


        val text = intent.getStringExtra("image_url")
        Timber.d("Home $text")

        if(text != null) {
            displayDialog()
        }

        /**Fetch Current user session*/
//        Amplify.Auth.fetchAuthSession(
//            { result -> Timber.tag("FetchSession").i(result.toString()) },
//            { error -> Timber.tag("FetchSession").e(error.toString()) }
//        )

//        saveToDataStore()
//        loginUi()

        Amplify.Auth.signInWithWebUI(
            this,
            { result: AuthSignInResult ->
                Timber.tag("AuthQuickstart").i(
                    result.toString()
                )


            }
        ) { error: AuthException ->
            Timber.tag("AuthQuickstartE").e(
                error.toString()
            )
        }

/*
        Amplify.Auth.signOut(
            AuthSignOutOptions.builder().globalSignOut(true).build(),
            { Log.i("AuthQuickstart", "Signed out globally") },
            { error -> Log.e("AuthQuickstart", error.toString()) }
        )
*/




        try {
            Timber.d(Amplify.Auth.currentUser.toString())
        }
        catch(e: Exception) {
            e.printStackTrace()
        }

        retriveCurrentToken()

        /**Subscribe to event listeners, listen to success or failure  from WebUi*/
        Amplify.Hub.subscribe(HubChannel.AUTH) { hubEvent: HubEvent<*> ->

            Timber.tag("AuthQuickstart").d("Called")
            when (hubEvent.name) {
                InitializationStatus.SUCCEEDED.toString() -> {
                    Timber.i("Auth successfully initialized")
                }

                InitializationStatus.FAILED.toString() -> {
                    Timber.i("Auth failed to succeed")
                }
                else -> {
                    when (
                        AuthChannelEventName.valueOf(hubEvent.name)) {
                        AuthChannelEventName.SIGNED_IN -> Timber.i("Auth just became signed in.")
                        AuthChannelEventName.SIGNED_OUT -> Timber.i("Auth just became signed out.")
                        AuthChannelEventName.SESSION_EXPIRED -> Timber.i("Auth session just expired.")
                    }
                }
            }
        }




    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onStart() {
        super.onStart()
        //Register broadcast receiver
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(broadcastReceiver, IntentFilter(KEY_NEW_VISITOR_FILTER))
    }

    override fun onResume() {
        super.onResume()

        val text = intent.getStringExtra("image_url")
        Timber.d("HomeRe $text")

        registerReceiver(broadcastReceiver, IntentFilter(KEY_NEW_VISITOR_FILTER))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(broadcastReceiver)
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(broadcastReceiver)
    }


    private fun retriveCurrentToken() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Timber.w(task.exception, "getInstanceId failed")
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token
                Timber.d(token)

                // Log and toast
//                val msg = getString(R.string.msg_token_fmt, token)
//                Log.d(TAG, msg)
                Toast.makeText(baseContext, "FireBase Token is ${token}", Toast.LENGTH_SHORT).show()
            })    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
            Timber.d("Home Place")
            Timber.d(data.toString())
            Amplify.Auth.handleWebUISignInResponse(data)
        }
    }

    private fun loginUi() {
        Amplify.Auth.signInWithWebUI(
            this,
            { result: AuthSignInResult ->
                Timber.tag("AuthQuickstart").i(
                    result.toString()
                )


            }
        ) { error: AuthException ->
            Timber.tag("AuthQuickstartE").e(
                error.toString()
            )
        }
    }

    private fun saveToDataStore() {
        val imageUrl = "https://fetch-mc.s3.amazonaws.com/fetchmc/Wasse-picture-B13.jpg"
        val userID = "4b2c559c-d3fb-45f9-8ec9-cd676ea7dda5"
        val name = "wasse"

        val visitor = Visitor.builder()
            .userId(userID)
            .image(imageUrl)
            .name(name)
            .build()



        Amplify.API.mutate(
            ModelMutation.create(visitor),
            { response -> Timber.d("Added Todo with id: " + response.getData().getId()) },
            { error: ApiException? -> Timber.d("Create failed ${error}") })

        Amplify.DataStore.observe(Visitor::class.java,
            { Log.i("MyAmplifyApp", "Observation began.") },
            { Log.i("MyAmplifyApp", "Post: ${it.item()}") },
            { Log.e("MyAmplifyApp", "Observation failed.", it) },
            { Log.i("MyAmplifyApp", "Observation complete.") }
        )
    }

    private fun displayDialog() {
        val newFragment = VerifyPictureFragment.newInstance(true)
        val fm = supportFragmentManager.beginTransaction()

//        newFragment.arguments = Bundle().apply {
//            putString(ARGUMENT__ORDERID, orderId)
//            putString(ARGUMENT__TIME, time)
//            putString(ARGUMENT__RESTAURANT_NAME, restaurantName)
//            putString(ARGUMENT__DISTANCE, distance)
//        }

        if(!newFragment.isVisible) {
            newFragment.show(fm, "Main")
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
