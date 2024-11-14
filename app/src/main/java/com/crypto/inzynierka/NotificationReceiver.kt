package com.crypto.inzynierka

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.core.app.NotificationCompat
import android.util.Log

class NotificationReceiver : BroadcastReceiver() {
    private lateinit var dbHelper: DBConnection

    override fun onReceive(context: Context, intent: Intent) {
        dbHelper = DBConnection(context, "cryptoDB", 13)

        sendNotification(context)
    }

    private fun sendNotification(context: Context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "default",
                "Default Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationTitle = "Powiadomienie"
        val notificationContent = "Minęła 1 minuta od ostatniego zalogowania."

        val notification = NotificationCompat.Builder(context, "default")
            .setSmallIcon(R.drawable.profile_icon)
            .setContentTitle(notificationTitle)
            .setContentText(notificationContent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.notify(1, notification)

        saveNotificationToDatabase(notificationTitle, notificationContent)
    }

    fun sendRemoteNotification(context: Context, title: String, content: String) {
        dbHelper = DBConnection(context, "cryptoDB", 13)

        val channelId = "remote_notifications"

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Remote Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.profile_icon)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)

        saveNotificationToDatabase(title,content)
    }

    fun saveNotificationToDatabase(title: String, content: String) {
        val db: SQLiteDatabase = dbHelper.writableDatabase

        val contentValues = ContentValues().apply {
            put("Title", title)
            put("Content", content)
        }

        val result = db.insert("Notifications", null, contentValues)

        if (result == -1L) {
            Log.d("DB_ERROR", "Wstawienie powiadomienia nie powiodło się.")
        } else {
            Log.d("DB_SUCCESS", "Powiadomienie zapisane w bazie danych.")
        }

        db.close()
    }
}