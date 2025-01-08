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
    val url = "http://192.168.8.106/get_results.php?username=$username"
    val dbHelper = DBConnection(context, "cryptoDB", MainViewModel.DB_VERSION)

    val request = StringRequest(
        Request.Method.GET,
        url,
        { response ->
            Log.d("SyncResults", "Otrzymana odpowiedź serwera: $response")
            val db = dbHelper.writableDatabase
            try {
                val resultsArray = JSONArray(response)
                db.beginTransaction()

                for (i in 0 until resultsArray.length()) {
                    val resultObject = resultsArray.getJSONObject(i)
                    val chapter = resultObject.optString("Chapter")
                    val result = resultObject.optString("Result")

                    if (chapter.isNotEmpty() && result.isNotEmpty()) {
                        val values = ContentValues().apply {
                            put("Chapter", chapter)
                            put("Result", result)
                        }
                        val rowId = db.insert("Results", null, values)
                        if (rowId == -1L) {
                            Log.e("SyncResults", "Błąd wstawiania danych: $values")
                        } else {
                            Log.d("SyncResults", "Dodano dane: ID=$rowId, Chapter=$chapter, Result=$result")
                        }
                    }
                }

                db.setTransactionSuccessful()
                Log.d("SyncResults", "Synchronizacja wyników zakończona sukcesem.")
            } catch (e: JSONException) {
                Log.e("SyncResults", "Błąd parsowania JSON: ${e.message}")
            } finally {
                db.endTransaction()
                db.close()
            }
        },
        { error ->
            Log.e("SyncResults", "Błąd podczas pobierania danych wyników: ${error.message}")
        }
    )

    val requestQueue = Volley.newRequestQueue(context)
    requestQueue.add(request)
}
