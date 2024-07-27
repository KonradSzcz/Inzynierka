package com.crypto.inzynierka

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.crypto.inzynierka.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.main)

        replaceFragment(Home())

        binding.home.setOnClickListener {
                replaceFragment(Home())
                // val intencja3 = Intent(applicationContext, MainActivity::class.java)
                // startActivity(intencja3)
        }
        binding.profile.setOnClickListener{
                replaceFragment(Introduction())

        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.center, fragment)
            .commit()
    }
}
