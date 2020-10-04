package com.production.smartsurvelliance.services

import VerificationData
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.RingtoneManager
import android.os.Build
import android.provider.MediaStore.Images.Media.getBitmap
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModel
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.amplifyframework.api.ApiException
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.User
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.production.smartsurvelliance.MainActivity
import com.production.smartsurvelliance.R
import com.production.smartsurvelliance.helper.KEY_MESSAGE
import com.production.smartsurvelliance.helper.KEY_NEW_VISITOR_FILTER
import kotlinx.coroutines.*
import timber.log.Timber
import java.net.UnknownHostException

class FirebaseMessagingService : FirebaseMessagingService() {
    private lateinit var localBroadCastManager: LocalBroadcastManager
    val gson = Gson()

    override fun onCreate() {
        super.onCreate()
        localBroadCastManager = LocalBroadcastManager.getInstance(this)
    }


    override fun onNewToken(token: String) {

        sendTokenToServer(token)
    }

    private fun sendTokenToServer(token: String) {
        Timber.d("Sending Token to Server")
//        updatePhoneIdInDataStore(token)
        Timber.d(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)


        remoteMessage.notification?.let {
            val responseBody = remoteMessage.notification?.let { it.body }?:"Payment Feedback"
            val responseTitle = remoteMessage.notification?.let { it.title }?:"Check Payment Status"

//            sendFCMNotification(this, responseBody)
            val imageUrl = gson.fromJson(responseBody, VerificationData::class.java)


//                val restaurant:Restaurant = gson.fromJson(restaurantString,Restaurant::class.java)
                val intent = Intent(KEY_NEW_VISITOR_FILTER)
                intent.putExtra("IMAGE_URL", imageUrl.imageUrl)
                localBroadCastManager.sendBroadcast(intent)
        }
    }

/*
    private fun updatePhoneIdInDataStore(newToken: String) {


        val currentUser = Amplify.Auth.currentUser

        val user = User.builder()
            .userId(currentUser.username).phoneId(mutableListOf(newToken))
            .build()


        Amplify.API.mutate(
            ModelMutation.create(user),
            { response -> Timber.d("Added Todo with id: " + response.getData().getId()) },
            { error: ApiException? -> Timber.d("Create failed ${error}") })

        Amplify.DataStore.observe(
            User::class.java,
            { Log.i("MyAmplifyApp", "Observation began.") },
            { Log.i("MyAmplifyApp", "Post: ${it.item()}") },
            { Log.e("MyAmplifyApp", "Observation failed.", it) },
            { Log.i("MyAmplifyApp", "Observation complete.") }
        )
    }
*/




/*
    private  fun sendFCMNotification(context: Context, messageBody: String) {

        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(context, 0 */
/* Request code *//*
, intent,
            PendingIntent.FLAG_ONE_SHOT)

        val drawable = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.resources.getDrawable(R.drawable.ic_notifications_black_24dp, context.theme)
        } else {
            context.resources.getDrawable(R.drawable.ic_notifications_black_24dp)
        }

//        val mBitmap = getBitmap(drawable)

        val channelId = context.getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setContentTitle("Payment Feedback")
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 */
/* ID of notification *//*
, notificationBuilder.build())
    }
*/


}
