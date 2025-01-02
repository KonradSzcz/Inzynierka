package com.crypto.inzynierka

import RemoteDBHelper
import RemoteNotification
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.crypto.inzynierka.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var dbHelper: DBConnection
    private val Vm by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.main)

        dbHelper = DBConnection(this, "cryptoDB", MainViewModel.DB_VERSION)

        saveLoginDate()

        displayUsernameTable()

        scheduleNotification()


        if (isUserLoggedIn()) {
            replaceFragment(Home())
        } else {
            SyncUsers(this)
            replaceFragment(Login())
            binding.frame.visibility = View.GONE
        }

        if (isInternetAvailable(this)) {
            syncAllResults()
            RemoteNotification(this)
        }

        binding.home.setOnClickListener {
            replaceFragment(Home())
        }
        binding.profile.setOnClickListener {
            replaceFragment(Profile())
        }
        binding.notifications.setOnClickListener {
            replaceFragment(Notification())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.center, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            super.onBackPressed()
        } else {
            finish()
        }
    }


    private fun saveLoginDate() {
        val currentTime = System.currentTimeMillis()

        val db = dbHelper.writableDatabase

        val contentValues = ContentValues().apply {
            put("LoginDate", currentTime)
        }

        val rowsAffected = db.update("Login", contentValues, "ID = 1", null)

        if (rowsAffected == 0) {
            db.insert("Login", null, contentValues)
        }

        // Pobierz ostatnią datę logowania
        val cursor: Cursor = db.rawQuery("SELECT LoginDate FROM Login WHERE ID = 1", null)
        var lastLoginDate: Long = 0
        if (cursor.moveToFirst()) {
            lastLoginDate = cursor.getLong(0)
            Log.d("LoginDate", "Last login date: $lastLoginDate")
        } else {
            Log.d("LoginDate", "No record found for ID = 1.")
        }

        cursor.close()
        db.close()

    }

    private fun scheduleNotification() {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT LoginDate FROM Login WHERE ID = 1", null)
        var lastLoginDate: Long = 0

        if (cursor.moveToFirst()) {
            lastLoginDate = cursor.getLong(0)
            Log.d("LoginDate", "Last login date: $lastLoginDate")
        } else {
            Log.d("LoginDate", "No record found for ID = 1.")
            cursor.close()
            db.close()
            return
        }

        cursor.close()
        db.close()

        val notificationTime = lastLoginDate + 24 * 60 * 60 * 1000 // 24 hours in milliseconds
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, NotificationReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                notificationTime,
                pendingIntent
            )
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, notificationTime, pendingIntent)
        }
    }



    fun syncAllResults() {
        val db = dbHelper.readableDatabase

        // Pobierz nazwę użytkownika, gdzie Flag = 1
        val cursorUser = db.rawQuery("SELECT Username FROM Username WHERE Flag = 1 LIMIT 1", null)
        var username: String? = null
        if (cursorUser.moveToFirst()) {
            username = cursorUser.getString(cursorUser.getColumnIndexOrThrow("Username"))
        }
        cursorUser.close()

        if (username == null) {
            Log.e("syncAllResults", "Brak aktywnego użytkownika z Flag = 1")
            db.close()
            return
        }

        Log.d("syncAllResults", "Synchronizacja dla użytkownika: $username")

        // Synchronizacja tabeli Results
        val cursorResults: Cursor = db.rawQuery("SELECT * FROM Results", null)
        if (cursorResults.moveToFirst()) {
            do {
                val chapter: String =
                    cursorResults.getString(cursorResults.getColumnIndexOrThrow("Chapter"))
                val result: String =
                    cursorResults.getString(cursorResults.getColumnIndexOrThrow("Result"))

                RemoteDBHelper(this, username, chapter, result, "", "Results")
            } while (cursorResults.moveToNext())
        }
        cursorResults.close()

        // Synchronizacja tabeli Tests
        val cursorTests: Cursor = db.rawQuery("SELECT * FROM Tests", null)
        if (cursorTests.moveToFirst()) {
            do {
                val chapter: String =
                    cursorTests.getString(cursorTests.getColumnIndexOrThrow("Chapter"))
                val test: String = cursorTests.getString(cursorTests.getColumnIndexOrThrow("Test"))
                val result: String =
                    cursorTests.getString(cursorTests.getColumnIndexOrThrow("Result"))

                RemoteDBHelper(this, username, chapter, result, test, "Tests")
            } while (cursorTests.moveToNext())
        }
        cursorTests.close()

        db.close()
    }


    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }


    private fun isUserLoggedIn(): Boolean {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT Flag FROM Username WHERE Flag = 1 LIMIT 1", null)
        val isLoggedIn = cursor.moveToFirst() && cursor.getInt(0) == 1
        cursor.close()
        db.close()
        return isLoggedIn
    }

    fun displayUsernameTable() {
        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery("SELECT * FROM Tests", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"))
                val chapter = cursor.getString(cursor.getColumnIndexOrThrow("Chapter"))
                val test = cursor.getString(cursor.getColumnIndexOrThrow("Test"))
                val result = cursor.getString(cursor.getColumnIndexOrThrow("Result"))

                Log.d("TestsTable", "ID: $id, Chapter: $chapter, Test: $test, Result: $result")
            } while (cursor.moveToNext())
        } else {
            Log.d("TestsTable", "Tabela 'Tests' jest pusta.")
        }

        cursor.close()
        db.close()
    }
}
