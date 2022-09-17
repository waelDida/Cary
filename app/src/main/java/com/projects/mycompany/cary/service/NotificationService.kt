package com.projects.mycompany.cary.service

import android.app.NotificationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.projects.mycompany.cary.ToDoApplication
import com.projects.mycompany.cary.notification.sendNotification

class NotificationService: FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
       /*: Log.d("TAAAAAAAG", "From: ${remoteMessage.from}")

        // TODO: Step 3.5 check messages for data
        // Check if message contains a data payload.
        if(remoteMessage.data.isNotEmpty()){
            Log.d("TAAAAAAAAG", "Message data payload: " + remoteMessage.data)
        }
       /* remoteMessage.data.let {
            Log.d("TAAAAAAAAG", "Message data payload: " + remoteMessage.data)
        }*/

        remoteMessage.notification?.let {
            Log.d("TAAAAAAAAAG", "Message Notification Body: ${it.body}")
            sendNotification(it.body!!)
        }*/
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("TAG","Refreshed token: $token")
    }

  /*  private fun sendNotification(messageBody: String){
        val notificationManager = ContextCompat.getSystemService(applicationContext,NotificationManager::class.java) as NotificationManager
        notificationManager.sendNotification(messageBody,applicationContext)
    }*/

}