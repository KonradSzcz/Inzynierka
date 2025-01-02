package com.crypto.inzynierka

import android.content.ContentValues
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.crypto.inzynierka.databinding.FragmentLoginBinding

class Login : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var dbHelper: DBConnection
    private var isLoginMode = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        dbHelper = DBConnection(requireContext(), "cryptoDB", MainViewModel.DB_VERSION)



        binding.LoginButton.setOnClickListener {
            isLoginMode = true
            switchToAuthScreen("Zaloguj się", "Zaloguj się")
        }

        binding.RegisterButton.setOnClickListener {
            isLoginMode = false
            switchToAuthScreen("Zarejestruj się", "Zarejestruj się")
        }

        binding.btnAuthAction.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            if (username.isEmpty()) {
                Toast.makeText(requireContext(), "Podaj login", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (isLoginMode) {
                handleLogin(username)
            } else {
                handleRegistration(username)
            }
        }

        binding.btnBack.setOnClickListener {
            switchToWelcomeScreen()
        }

        return binding.root
    }

    private fun switchToAuthScreen(message: String, buttonText: String) {
        binding.linearLayout1.visibility = View.GONE
        binding.authContainer.visibility = View.VISIBLE
        binding.tvAuthMessage.text = message
        binding.btnAuthAction.text = buttonText
    }

    private fun switchToWelcomeScreen() {
        binding.authContainer.visibility = View.GONE
        binding.linearLayout1.visibility = View.VISIBLE
        binding.etUsername.text.clear()
    }

    private fun handleLogin(username: String) {
        if (isUserExists(username)) {
            // Zmiana flagi na 1 po zalogowaniu
            updateFlag(username)

            Toast.makeText(requireContext(), "Zalogowano pomyślnie", Toast.LENGTH_SHORT).show()
            replaceFragment(Home())

            // Odwołanie do elementu w MainActivity
            val mainActivity = activity as MainActivity
            mainActivity.findViewById<FrameLayout>(R.id.frame).visibility = View.VISIBLE

            SyncTests(requireContext(), username)
            SyncResults(requireContext(), username)
        } else {
            Toast.makeText(requireContext(), "Login nie istnieje", Toast.LENGTH_SHORT).show()
        }
    }


    private fun handleRegistration(username: String) {
        if (isUserExists(username)) {
            Toast.makeText(requireContext(), "Login już istnieje", Toast.LENGTH_SHORT).show()
        } else {

            val mainActivity = activity as MainActivity
            mainActivity.findViewById<FrameLayout>(R.id.frame).visibility = View.VISIBLE

            addUser(username)
            sendUserToRemoteDatabase(requireContext(), username)
            Toast.makeText(requireContext(), "Zarejestrowano pomyślnie", Toast.LENGTH_SHORT).show()
            replaceFragment(Home())
        }
    }


    private fun isUserExists(username: String): Boolean {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Username WHERE Username = ?", arrayOf(username))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    private fun addUser(username: String) {
        val db = dbHelper.writableDatabase
        val contentValues = ContentValues().apply {
            put("Username", username)
            put("Flag", 1)
        }
        val result = db.insert("Username", null, contentValues)
        db.close()

        if (result == -1L) {
            Toast.makeText(requireContext(), "Błąd podczas rejestracji użytkownika", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateFlag(username: String) {
        val db = dbHelper.writableDatabase
        val contentValues = ContentValues().apply {
            put("Flag", 1)  // Ustawiamy flagę na 1
        }

        val result = db.update("Username", contentValues, "Username = ?", arrayOf(username))

        if (result == -1) {
            Toast.makeText(requireContext(), "Błąd podczas aktualizacji flagi", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Flaga została zaktualizowana", Toast.LENGTH_SHORT).show()
        }

        db.close()
    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.center, fragment)
            .commit()
    }
}
