package com.crypto.inzynierka

import android.content.ContentValues
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import android.content.Context
import org.json.JSONArray
import org.json.JSONException
import android.util.Log

fun SyncUsers(context: Context) {
    val url = "http://192.168.8.106/get_users.php"
    val dbHelper = DBConnection(context, "cryptoDB", MainViewModel.DB_VERSION)

    val request = StringRequest(
        Request.Method.GET,
        url,
        { response ->
            val db = dbHelper.writableDatabase
            try {
                val usersArray = JSONArray(response) // Oczekiwana odpowiedź to tablica JSON

                db.beginTransaction()
                // Usunięcie istniejących użytkowników
                db.delete("Username", null, null)

                // Iteracja przez dane i wstawianie do lokalnej bazy
                for (i in 0 until usersArray.length()) {
                    val userObject = usersArray.getJSONObject(i)
                    val id = userObject.optInt("ID") // Pobranie ID użytkownika
                    val username = userObject.optString("Username") // Pobranie nazwy użytkownika

                    // Walidacja pobranych danych
                    if (username.isNotEmpty()) {
                        val values = ContentValues().apply {
                            put("ID", id)
                            put("Username", username)
                            put("Flag", 0) // Ustawienie flagi na 0
                        }
                        db.insert("Username", null, values)
                    }
                }

                db.setTransactionSuccessful()
                Log.d("SyncUsers", "Synchronizacja użytkowników zakończona sukcesem.")
            } catch (e: JSONException) {
                Log.e("SyncUsers", "Błąd parsowania JSON: ${e.message}")
            } finally {
                db.endTransaction()
                db.close()
            }
        },
        { error ->
            Log.e("SyncUsers", "Błąd podczas pobierania użytkowników: ${error.message}")
        }
    )

    val requestQueue = Volley.newRequestQueue(context)
    requestQueue.add(request)
}
