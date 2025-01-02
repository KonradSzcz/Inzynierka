package com.crypto.inzynierka

import android.content.ContentValues
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import android.content.Context
import org.json.JSONArray
import org.json.JSONException
import android.util.Log

fun SyncTests(context: Context, username: String) {
    val url = "http://192.168.8.106/get_tests.php?username=$username"
    val dbHelper = DBConnection(context, "cryptoDB", MainViewModel.DB_VERSION)

    val request = StringRequest(
        Request.Method.GET,
        url,
        { response ->
            val db = dbHelper.writableDatabase
            try {
                val testsArray = JSONArray(response) // Oczekiwana odpowiedź to tablica JSON

                db.beginTransaction()
                // Można usunąć istniejące dane, jeśli jest to wymagane
                // db.delete("Tests", null, null)

                // Iteracja przez dane i wstawianie do lokalnej bazy
                for (i in 0 until testsArray.length()) {
                    val testObject = testsArray.getJSONObject(i)
                    val chapter = testObject.optString("Chapter") // Pobranie nazwy rozdziału, np. "chapter1"
                    val test = testObject.optString("Test") // Pobranie numeru testu, np. "4"
                    val result = testObject.optString("Result") // Pobranie wyniku testu, np. "1"

                    // Walidacja pobranych danych
                    if (test.isNotEmpty() && chapter.isNotEmpty() && result.isNotEmpty()) {
                        val values = ContentValues().apply {
                            put("Chapter", chapter) // Nazwa rozdziału
                            put("Test", test) // Test
                            put("Result", result) // Wynik testu
                        }
                        // Wstawianie danych do lokalnej tabeli
                        db.insert("Tests", null, values)
                    }
                }

                db.setTransactionSuccessful()
                Log.d("SyncData", "Synchronizacja danych zakończona sukcesem.")
            } catch (e: JSONException) {
                Log.e("SyncData", "Błąd parsowania JSON: ${e.message}")
            } finally {
                db.endTransaction()
                db.close()
            }
        },
        { error ->
            Log.e("SyncData", "Błąd podczas pobierania danych testów: ${error.message}")
        }
    )

    val requestQueue = Volley.newRequestQueue(context)
    requestQueue.add(request)
}
