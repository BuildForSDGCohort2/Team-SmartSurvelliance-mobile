package com.production.smartsurvelliance;

import android.app.Application;
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.AWSDataStorePlugin
import timber.log.Timber

class MyAmplifyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.addPlugin(AWSDataStorePlugin())
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.configure(applicationContext)
            Timber.tag("MyAmplifyApp").i( "Initialized Amplify")
        } catch (error: AmplifyException) {
            Timber.e(error, "Could not initialize Amplify")
        }
    }
}