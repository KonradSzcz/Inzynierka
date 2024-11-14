package com.crypto.inzynierka

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.database.Cursor
import androidx.fragment.app.viewModels
import com.crypto.inzynierka.databinding.FragmentChapter13gameBinding
import kotlin.random.Random

class Chapter1_3game : Fragment() {
    private var _binding: FragmentChapter13gameBinding? = null
    private val binding get() = _binding!!
    private val Vm by viewModels<MainViewModel>()

    private var selectedButtonLeft: Button? = null
    private var correctPairs = 0
    private var isMatching = false

    // Baza danych
    private lateinit var dbHelper: DBConnection
    private var leftOptions = mutableListOf<String>()
    private var rightOptions = mutableListOf<String>()
    private val correctMatches = mutableMapOf<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = DBConnection(requireContext(), "cryptoDB",  MainViewModel.DB_VERSION)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChapter13gameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Odczyt danych z bazy
        loadFlashcardsData()

        // Tasowanie i przypisywanie przycisków
        shuffleOptions()
        setLeftButtonsClickListeners()
        setRightButtonsClickListeners()
    }

    private fun loadFlashcardsData() {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM Flashcards WHERE Chapter = 'chapter1'",
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val concept = cursor.getString(cursor.getColumnIndexOrThrow("Concept"))
                val definition = cursor.getString(cursor.getColumnIndexOrThrow("Definition"))

                // Dodanie do list i mapy poprawnych dopasowań
                leftOptions.add(concept)
                rightOptions.add(definition)
                correctMatches[concept] = definition
            } while (cursor.moveToNext())
        }
        cursor.close()
    }

    private fun shuffleOptions() {
        // Tasowanie przycisków po lewej
        val leftButtons = listOf(binding.option11, binding.option12, binding.option13, binding.option14)
        val shuffledLeftOptions = leftOptions.shuffled(Random(System.currentTimeMillis().toInt()))

        // Sprawdź, ile elementów mamy w shuffledLeftOptions i przypisz je do przycisków
        for (i in leftButtons.indices) {
            if (i < shuffledLeftOptions.size) {
                leftButtons[i].text = shuffledLeftOptions[i]
            } else {
                leftButtons[i].visibility = View.GONE
            }
        }

        // Tasowanie przycisków po prawej
        val rightButtons = listOf(binding.option21, binding.option22, binding.option23, binding.option24 )
        val shuffledRightOptions = rightOptions.shuffled(Random(System.currentTimeMillis().toInt()))

        // Sprawdź, ile elementów mamy w shuffledRightOptions i przypisz je do przycisków
        for (i in rightButtons.indices) {
            if (i < shuffledRightOptions.size) {
                rightButtons[i].text = shuffledRightOptions[i]
            } else {
                rightButtons[i].visibility = View.GONE // Ukryj przycisk, jeśli nie ma więcej opcji
            }
        }
    }


    private fun setLeftButtonsClickListeners() {
        val leftButtons = listOf(binding.option11, binding.option12, binding.option13, binding.option14)
        for (button in leftButtons) {
            button.setOnClickListener {
                if (!isMatching) {
                    selectedButtonLeft?.setBackgroundColor(Color.parseColor("#2C74B3"))
                    button.setBackgroundColor(Color.parseColor("#FFA500")) // Kolor pomarańczowy
                    selectedButtonLeft = button
                }
            }
        }
    }

    private fun setRightButtonsClickListeners() {
        val rightButtons = listOf(binding.option21, binding.option22, binding.option23, binding.option24 )
        for (button in rightButtons) {
            button.setOnClickListener {
                if (!isMatching) {
                    selectedButtonLeft?.let { leftButton ->
                        isMatching = true
                        val leftText = leftButton.text.toString()
                        val rightText = button.text.toString()
                        if (correctMatches[leftText] == rightText) {
                            highlightCorrectMatch(leftButton, button)
                        } else {
                            highlightIncorrectMatch(leftButton, button)
                        }
                    }
                }
            }
        }
    }

    private fun highlightCorrectMatch(leftButton: Button, rightButton: Button) {
        leftButton.setBackgroundColor(Color.GREEN)
        rightButton.setBackgroundColor(Color.GREEN)
        Handler(Looper.getMainLooper()).postDelayed({
            leftButton.visibility = View.GONE
            rightButton.visibility = View.GONE
            selectedButtonLeft = null
            isMatching = false
            correctPairs++
            if (correctPairs == leftOptions.size) {
                replaceFragment(Chapter1quiz(), 3)
            }
        }, 1000)
    }

    private fun highlightIncorrectMatch(leftButton: Button, rightButton: Button) {
        leftButton.setBackgroundColor(Color.RED)
        rightButton.setBackgroundColor(Color.RED)
        Handler(Looper.getMainLooper()).postDelayed({
            leftButton.setBackgroundColor(Color.parseColor("#2C74B3"))
            rightButton.setBackgroundColor(Color.parseColor("#2C74B3"))
            selectedButtonLeft?.setBackgroundColor(Color.parseColor("#2C74B3"))
            selectedButtonLeft = null
            isMatching = false
        }, 1000)
    }

    private fun replaceFragment(fragment: Fragment,chapter: Int) {
        val args = Bundle().apply {
            putInt("CurrentChapter", chapter)
        }
        fragment.arguments = args
        parentFragmentManager.beginTransaction()
            .replace(R.id.center, fragment)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
