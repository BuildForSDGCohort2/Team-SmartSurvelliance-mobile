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
import com.amplifyframework.core.Action
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.Consumer
import com.amplifyframework.core.InitializationStatus
import com.amplifyframework.core.model.query.Where
import com.amplifyframework.datastore.DataStoreException
import com.amplifyframework.datastore.generated.model.Post
import com.amplifyframework.datastore.generated.model.Todo
import com.amplifyframework.datastore.generated.model.UserDetail
import com.amplifyframework.datastore.generated.model.UserDetails
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
            val imageUrl = intent?.getStringExtra("IMAGE_URL")

            buzz(BuzzType.NOTIFICATION.pattern, this@MainActivity)
            Timber.d(imageUrl)

            Toast.makeText(context, imageUrl, Toast.LENGTH_LONG).show()

            if(imageUrl != null) {
                displayDialog(imageUrl)
            } else {
                displayDialog("")
            }

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


//        Amplify.DataStore.clear(
//            { Timber.d("How ied end") },
//            { Timber.e("Error while clearing cache") })
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            toolbar.visibility = View.GONE
//        }


        /**Load Image from image_url if Exist from bundle*/
        val imageUrl = intent.getStringExtra("image_url")
        Timber.d("Home $imageUrl")

        if(imageUrl != null) {
            displayDialog(imageUrl)
        }

        /**Fetch Current user session*/

        try {
            Timber.d("Getting Current User")

                if(Amplify.Auth.currentUser != null) {
                    Timber.d(Amplify.Auth.currentUser.toString())

//                    retriveCurrentToken()
                } else {
                    Timber.d("Current does not exist User")
                    loginUi()

                }


        }
        catch(e: Exception) {
            Timber.d("Current User does not exist");
            e.printStackTrace()
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

//        val text = intent.getStringExtra("image_url")
//        Timber.d("HomeRe $text")

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
                //var token = task.result?.token
                val token = task.result?.token
                Timber.d("Retrieve $token")

                updatePhoneIdInDataStore(token!!)

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
                run {
                    Timber.tag("AuthQuickstart SignIn").i(
                        result.toString()
                    )

                    retriveCurrentToken()
                }



            },
            { error: AuthException ->
                Timber.tag("AuthQuickstartE").e(
                    error.toString()
                )

                error.cause?.let {
                    if (it.message == "user cancelled") {
                        loginUi()
                    }
                }
            }
        )
    }

/*
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
*/

    private fun updatePhoneIdInDataStore(newToken: String) {
        try {

            val currentUser = Amplify.Auth.currentUser.username

            Amplify.DataStore.query(
                UserDetail::class.java, Where.matches(UserDetail.USER_ID.eq(currentUser)),
                { matches ->
                    if (matches.hasNext()) {
                        //Instance present
                        val original = matches.next()

                        Log.i("MyAmplifyApp", original.toString())
                        //Add fcmRegistration token if not preset
                        if(!original.fcmRegistrationId.contains(newToken)){
                            val currentFCMTokens = original.fcmRegistrationId
                            currentFCMTokens.add(newToken)

                            //Adde unique user ids to the edited user object
                            val edited = original.copyOfBuilder()
                                .fcmRegistrationId(currentFCMTokens.toSet().toMutableList())
                                .build()

                            Amplify.DataStore.save(edited,
                                { Log.i("MyAmplifyApp", "Updated a user.${it}") },
                                { Log.e("MyAmplifyApp", "Update failed.", it) }
                            )
                        }

                    } else {
                        //Create new object in Data store if not created

                        val user = UserDetail.builder()
                            .userId(currentUser)
                            .fcmRegistrationId(mutableListOf(newToken))
                            .build()

                        Amplify.DataStore.save(user,
                            { Log.i("MyAmplifyApp", "Created a user.${it}", ) },
                            { Log.e("MyAmplifyApp", "Created failed.", it) }
                        )
                    }
                },
                { Log.e("MyAmplifyApp", "Query failed.", it) }
            )
        } catch (e: Exception) {
            Timber.d("Games type")
            e.printStackTrace()
        }
    }

    private fun displayDialog(imageUrl: String = "") {
        val newFragment = VerifyPictureFragment.newInstance(true)
        val fm = supportFragmentManager.beginTransaction()

        newFragment.arguments = Bundle().apply {
            putString("IMAGE_URL", imageUrl)
        }

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
