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
import com.crypto.inzynierka.databinding.FragmentChapter13gameBinding
import kotlin.random.Random

class Chapter1_3game : Fragment() {
    private var _binding: FragmentChapter13gameBinding? = null
    private val binding get() = _binding!!

    private var selectedButtonLeft: Button? = null
    private var correctPairs = 0
    private var isMatching = false

    private val leftOptions = listOf(
        "Zdecentralizowanie",
        "Anonimowość",
        "Bezpośrednie transakcje",
        "Ograniczona podaż"
    )

    private val rightOptions = listOf(
        "Tożsamość uczestników transakcji może pozostać nieujawniona",
        "Istnieje ściśle określona\nilość jednostek waluty,\nktóra może zostać\nwytworzona",
        "Brak kontroli przez jedną instytucję czy osobę",
        "Kontrola sprawowana\nprzez rząd lub banki",
        "Transakcje bez\npośrednictwa instytucji finansowych"
    )

    private val correctMatches = mapOf(
        "Zdecentralizowanie" to "Brak kontroli przez jedną instytucję czy osobę",
        "Anonimowość" to "Tożsamość uczestników transakcji może pozostać nieujawniona",
        "Bezpośrednie transakcje" to "Transakcje bez\n" +
                                    "pośrednictwa instytucji finansowych",
        "Ograniczona podaż" to "Istnieje ściśle określona\n" +
                                "ilość jednostek waluty,\n" +
                                "która może zostać\n" +
                                "wytworzona"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChapter13gameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shuffleOptions()
        setLeftButtonsClickListeners()
        setRightButtonsClickListeners()
    }

    private fun shuffleOptions() {
        // Shuffle left options
        val leftButtons = listOf(binding.option11, binding.option12, binding.option13, binding.option14)
        val shuffledLeftOptions = leftOptions.shuffled(Random(System.currentTimeMillis().toInt()))
        for (i in leftButtons.indices) {
            leftButtons[i].text = shuffledLeftOptions[i]
        }

        // Shuffle right options
        val rightButtons = listOf(binding.option21, binding.option22, binding.option23, binding.option24, binding.option25)
        val shuffledRightOptions = rightOptions.shuffled(Random(System.currentTimeMillis().toInt()))
        for (i in rightButtons.indices) {
            rightButtons[i].text = shuffledRightOptions[i]
        }
    }

    private fun setLeftButtonsClickListeners() {
        val leftButtons = listOf(binding.option11, binding.option12, binding.option13, binding.option14)
        for (button in leftButtons) {
            button.setOnClickListener {
                if (!isMatching) {
                    selectedButtonLeft?.setBackgroundColor(Color.parseColor("#2C74B3"))
                    button.setBackgroundColor(Color.parseColor("#FFA500")) // Orange color
                    selectedButtonLeft = button
                }
            }
        }
    }

    private fun setRightButtonsClickListeners() {
        val rightButtons = listOf(binding.option21, binding.option22, binding.option23, binding.option24, binding.option25)
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
            if (correctPairs == 4) {
                replaceFragment(Chapter1_3test())
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

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.center, fragment)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
