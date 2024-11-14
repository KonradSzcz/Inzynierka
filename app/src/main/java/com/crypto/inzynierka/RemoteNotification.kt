import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import android.content.Context
import android.util.Log
import com.crypto.inzynierka.NotificationReceiver

fun RemoteNotification(context: Context) {
    val url = "http://192.168.8.106/notification.php"

    val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null,
        { response: JSONArray ->
            // Odpowiedź
            Log.d("JSONResponse", response.toString())

            // Obsługa odpowiedzi JSON
            for (i in 0 until response.length()) {
                val notification = response.getJSONObject(i)
                val title = notification.getString("Title")

                val content = if (notification.has("Content")) {
                    notification.getString("Content")
                } else {
                    "Brak wiadomości"
                }

                NotificationReceiver().sendRemoteNotification(context, title, content)
            }
        },
        { error ->
            // Odpowiedź
            Log.d("VolleyError", "Błąd przy pobieraniu powiadomień: ${error.message}")
            if (error.networkResponse != null) {
                Log.d("VolleyError", "Status Code: ${error.networkResponse.statusCode}")
                Log.d("VolleyError", "Data: ${String(error.networkResponse.data)}")
            }
        }
    )

    val requestQueue = Volley.newRequestQueue(context)
    requestQueue.add(jsonArrayRequest)
}
