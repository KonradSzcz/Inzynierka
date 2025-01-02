package com.crypto.inzynierka

import android.content.ContentValues
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import android.content.Context
import org.json.JSONArray
import org.json.JSONException
import android.util.Log

fun SyncResults(context: Context, username: String) {
    val url = "http://192.168.8.106/get_results.php?username=$username" // Adres URL do pobierania wyników
    val dbHelper = DBConnection(context, "cryptoDB", MainViewModel.DB_VERSION) // Inicjalizacja bazy lokalnej

    val request = StringRequest(
        Request.Method.GET,
        url,
        { response ->
            Log.d("SyncData", "Odpowiedź serwera: $response")

            try {
                // Sprawdzanie, czy odpowiedź nie jest pusta lub zawiera HTML
                if (response.isEmpty() || response.contains("<br")) {
                    Log.e("SyncData", "Błąd: Odpowiedź serwera jest pusta lub zawiera HTML.")
                    return@StringRequest
                }

                // Parsowanie odpowiedzi JSON
                val resultsArray = JSONArray(response)

                // Sprawdzanie, czy są jakiekolwiek wyniki
                if (resultsArray.length() == 0) {
                    Log.d("SyncData", "Brak wyników do synchronizacji.")
                    return@StringRequest // Brak wyników, kończymy synchronizację
                }

                // Otwarcie bazy danych
                val db = dbHelper.writableDatabase

                // Flaga do śledzenia, czy transakcja została rozpoczęta
                var transactionStarted = false

                try {
                    db.beginTransaction() // Rozpoczęcie transakcji
                    transactionStarted = true

                    // Iteracja przez otrzymane wyniki
                    for (i in 0 until resultsArray.length()) {
                        val resultObject = resultsArray.getJSONObject(i)
                        val chapter = resultObject.optString("Chapter")  // Rozdział
                        val test = resultObject.optString("Test")  // Test
                        val result = resultObject.optString("Result")  // Wynik

                        // Walidacja danych przed zapisaniem
                        if (chapter.isNotEmpty() && test.isNotEmpty() && result.isNotEmpty()) {
                            val values = ContentValues().apply {
                                put("Chapter", chapter)  // Rozdział
                                put("Test", test)  // Test
                                put("Result", result)  // Wynik
                            }
                            // Wstawianie danych do lokalnej tabeli
                            db.insert("Results", null, values)
                        }
                    }

                    // Zatwierdzenie transakcji, aby dane zostały zapisane
                    db.setTransactionSuccessful()
                    Log.d("SyncData", "Synchronizacja wyników zakończona sukcesem.")
                } catch (e: Exception) {
                    Log.e("SyncData", "Błąd podczas przetwarzania danych: ${e.message}")
                } finally {
                    // Tylko jeśli transakcja została rozpoczęta, zakończ ją
                    if (transactionStarted) {
                        db.endTransaction()
                    }
                    db.close() // Zamknięcie bazy danych
                }
            } catch (e: JSONException) {
                Log.e("SyncData", "Błąd parsowania JSON: ${e.message}")
            }
        },
        { error ->
            // Obsługa błędu w przypadku problemów z pobraniem danych
            Log.e("SyncData", "Błąd podczas pobierania danych wyników: ${error.message}")
        }
    )

    // Dodawanie zapytania do kolejki Volley
    val requestQueue = Volley.newRequestQueue(context)
    requestQueue.add(request)
}
