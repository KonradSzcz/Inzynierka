import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import android.content.Context

fun RemoteDBHelper(context: Context, chapter: String, result: String, test: String, table: String) {
    val url = "http://192.168.8.106/sync_data.php"

    val request = object : StringRequest(Request.Method.POST, url,
        { response ->
            // Obsługa odpowiedzi z serwera
            println("Odpowiedź z serwera: $response")
        },
        { error ->
            // Obsługa błędów
            println("Błąd123: ${error.message}")
        }) {

        override fun getParams(): MutableMap<String, String> {
            val params = HashMap<String, String>()
            params["chapter"] = chapter
            params["result"] = result
            params["test"] = test
            params["table"] = table
            return params
        }
    }

    val requestQueue = Volley.newRequestQueue(context)
    requestQueue.add(request)
}
