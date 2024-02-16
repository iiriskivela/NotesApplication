package com.example.notesapplication.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.notesapplication.MainActivity

class Notifications(private val context: Context) {

    fun triggerNotification(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context, "channelId")
            //.setSmallIcon(R.drawable.notification_icon)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Phone Lifted Up")
            .setContentText("You lifted up your phone!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)

        try {
            with(NotificationManagerCompat.from(context)) {
                notify(1234, builder.build())
            }
        } catch (e: SecurityException) {
            // Handle SecurityException gracefully
            e.printStackTrace()
        }
    }

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "My Channel"
            val descriptionText = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("channelId", name, importance).apply {
                description = descriptionText
                enableLights(true)
                //lightColor = Color.RED
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun enableNotifications(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = 1
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(context, "channelId")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Notifications Enabled")
            .setContentText("You can now receive notifications!")
            .setContentIntent(pendingIntent) //opens app
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()

        notificationManager.notify(notificationId, notification)
    }

}