package com.crypto.inzynierka

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

fun sendUserToRemoteDatabase(context: Context, username: String) {
    val url = "http://192.168.8.106/register_user.php" 

    val request = object : StringRequest(Request.Method.POST, url,
        { response ->
            println("Odpowiedź z serwera: $response")
        },
        { error ->
            println("Błąd: ${error.message}")
        }) {

        override fun getParams(): MutableMap<String, String> {
            val params = HashMap<String, String>()
            params["username"] = username
            return params
        }
    }

    val requestQueue = Volley.newRequestQueue(context)
    requestQueue.add(request)
}
