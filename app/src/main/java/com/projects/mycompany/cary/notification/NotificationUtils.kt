package com.projects.mycompany.cary.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.activities.MainActivity

private const val NOTIFICATION_ID = 0
private const val REQUEST_CODE = 0

fun NotificationManager.sendNotification(message: String, applicationContext: Context){

    // Create the content intent for the notification
    val contentIntent = Intent(applicationContext,MainActivity::class.java)
    contentIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    val pendingContentIntent = PendingIntent.getActivity(
        applicationContext,
        REQUEST_CODE,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    // Build the notification
    val builder = NotificationCompat.Builder(applicationContext,applicationContext.getString(R.string.msg_notification_channel_id))
        .setSmallIcon(R.drawable.ic_notification)
        .setContentTitle(applicationContext.getString(R.string.msg_notification_title))
        .setContentText(message)
        .setContentIntent(pendingContentIntent)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    // Call the notification
    notify(NOTIFICATION_ID,builder.build())


}