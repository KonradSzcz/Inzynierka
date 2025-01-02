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
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit

class Chapter1_3game : Fragment() {
    private var _binding: FragmentChapter13gameBinding? = null
    private val binding get() = _binding!!
    private val Vm by viewModels<MainViewModel>()

    private var selectedButtonLeft: Button? = null
    private var correctPairs = 0
    private var isMatching = false
    private var CurrentChapter: Int = 1

    // Baza danych
    private lateinit var dbHelper: DBConnection
    private var leftOptions = mutableListOf<String>()
    private var rightOptions = mutableListOf<String>()
    private val correctMatches = mutableMapOf<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = DBConnection(requireContext(), "cryptoDB", MainViewModel.DB_VERSION)
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

        arguments?.let {
            CurrentChapter = it.getInt("CurrentChapter", 1)
        }

        resetButtonColors()

        val imageResId = when (CurrentChapter) {
            1 -> R.drawable.test1
            2 -> R.drawable.test2
            3 -> R.drawable.test3
            4 -> R.drawable.test4
            else -> R.drawable.test2
        }
        binding.imageView.setImageResource(imageResId)

        loadFlashcardsData()
        shuffleOptions()
        setLeftButtonsClickListeners()
        setRightButtonsClickListeners()
    }

    private fun loadFlashcardsData() {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM Flashcards WHERE Chapter = ?",
            arrayOf("chapter$CurrentChapter")
        )


        if (cursor.moveToFirst()) {
            do {
                val concept = cursor.getString(cursor.getColumnIndexOrThrow("Concept"))
                val definition = cursor.getString(cursor.getColumnIndexOrThrow("Definition"))

                leftOptions.add(concept)
                rightOptions.add(definition)
                correctMatches[concept] = definition
            } while (cursor.moveToNext())
        }
        cursor.close()
    }

    private fun shuffleOptions() {
        val leftButtons = listOf(binding.option11, binding.option12, binding.option13, binding.option14)
        val rightButtons = listOf(binding.option21, binding.option22, binding.option23, binding.option24)

        val allPairs = leftOptions.zip(rightOptions)

        val selectedPairs = allPairs.shuffled(Random(System.currentTimeMillis().toInt())).take(4)

        val selectedLeftOptions = selectedPairs.map { it.first }
        val selectedRightOptions = selectedPairs.map { it.second }.shuffled(Random(System.currentTimeMillis().toInt()))

        correctMatches.clear()
        for ((concept, definition) in selectedPairs) {
            correctMatches[concept] = definition
        }

        for (i in leftButtons.indices) {
            if (i < selectedLeftOptions.size) {
                leftButtons[i].text = selectedLeftOptions[i]
                leftButtons[i].visibility = View.VISIBLE
            } else {
                leftButtons[i].visibility = View.GONE
            }
        }

        for (i in rightButtons.indices) {
            if (i < selectedRightOptions.size) {
                rightButtons[i].text = selectedRightOptions[i]
                rightButtons[i].visibility = View.VISIBLE
            } else {
                rightButtons[i].visibility = View.GONE
            }
        }
    }

    private fun setLeftButtonsClickListeners() {
        val leftButtons = listOf(binding.option11, binding.option12, binding.option13, binding.option14)
        for (button in leftButtons) {
            button.setOnClickListener {
                if (!isMatching) {
                    selectedButtonLeft?.setBackgroundColor(Color.parseColor("#2C74B3"))
                    button.setBackgroundColor(Color.parseColor("#FFA500"))
                    selectedButtonLeft = button
                }
            }
        }
    }

    private fun setRightButtonsClickListeners() {
        val rightButtons = listOf(binding.option21, binding.option22, binding.option23, binding.option24)
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
                // Pierwsze konfetti
                val konfettiParty1 = Party(
                    speed = 0f,
                    maxSpeed = 30f,
                    damping = 0.9f,
                    spread = 360,
                    colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
                    emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100),
                    position = Position.Relative(0.5, 0.3)
                )
                binding.konfettiView.start(konfettiParty1)

                // Drugie konfetti po krótkim opóźnieniu
                Handler(Looper.getMainLooper()).postDelayed({
                    if (isAdded && !isDetached) {
                        val konfettiParty2 = Party(
                            speed = 0f,
                            maxSpeed = 30f,
                            damping = 0.9f,
                            spread = 360,
                            colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
                            emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100),
                            position = Position.Relative(0.5, 0.3)
                        )
                        binding.konfettiView.start(konfettiParty2)
                        // Opóźnienie przed zmianą fragmentu
                        Handler(Looper.getMainLooper()).postDelayed({
                            if (isAdded && !isDetached) {
                                when (CurrentChapter) {
                                    1 -> replaceFragment(Introduction(), 0)
                                    2 -> replaceFragment(Blockchain(), 0)
                                    3 -> replaceFragment(Bitcoin(), 0)
                                    4 -> replaceFragment(Ethereum(), 0)
                                }
                            }

                        }, 1500)
                    }
                }, 700)
            }
        }, 1000)
    }

    private fun resetButtonColors() {
        binding.option11.setBackgroundColor(Color.parseColor("#2C74B3"))
        binding.option12.setBackgroundColor(Color.parseColor("#2C74B3"))
        binding.option13.setBackgroundColor(Color.parseColor("#2C74B3"))
        binding.option14.setBackgroundColor(Color.parseColor("#2C74B3"))

        binding.option21.setBackgroundColor(Color.parseColor("#2C74B3"))
        binding.option22.setBackgroundColor(Color.parseColor("#2C74B3"))
        binding.option23.setBackgroundColor(Color.parseColor("#2C74B3"))
        binding.option24.setBackgroundColor(Color.parseColor("#2C74B3"))

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

    private fun replaceFragment(fragment: Fragment, chapter: Int) {
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
