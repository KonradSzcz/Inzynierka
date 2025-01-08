package com.crypto.inzynierka

import android.content.ContentValues
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.database.Cursor
import android.widget.ImageView
import androidx.fragment.app.viewModels
import com.crypto.inzynierka.databinding.FragmentChapter1testBinding
import kotlin.random.Random

class Chapter1test : Fragment() {
    private var _binding: FragmentChapter1testBinding? = null
    private val binding get() = _binding!!
    private val Vm by viewModels<MainViewModel>()

    private var questions = mutableListOf<String>()
    private var options = mutableListOf<Array<String>>()
    private var correctAnswers = mutableListOf<Int>()

    private var currentQuestionIndex = 0
    private var score = 0

    private lateinit var dbHelper: DBConnection
    private lateinit var countDownTimer: CountDownTimer

    private var CurrentChapter: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = DBConnection(requireContext(), "cryptoDB", MainViewModel.DB_VERSION)

        // Pobranie rozdziału przekazanego z argumentów
        arguments?.let {
            CurrentChapter = it.getInt("CurrentChapter", -1)
            Log.d("Chapter1test", "Received chapter: $CurrentChapter")
        }

        loadQuestionsFromDB(CurrentChapter)
    }

    private fun loadQuestionsFromDB(chapter: Int) {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM ${DBConnection.TABLE_NAME_QUESTIONS} WHERE Chapter LIKE 'chapter$chapter%'", null)
        if (cursor.moveToFirst()) {
            do {
                val question = cursor.getString(cursor.getColumnIndexOrThrow(DBConnection.COL2_QUESTIONS))
                questions.add(question)
                val questionID = cursor.getInt(cursor.getColumnIndexOrThrow(DBConnection.COL1_QUESTIONS))
                loadAnswersFromDB(questionID)
            } while (cursor.moveToNext())
        }
        cursor.close()

        Log.d("Chapter1test", "Loaded ${questions.size} questions")

        val indices = questions.indices.toList().shuffled()
        questions = indices.map { questions[it] }.toMutableList()
        options = indices.map { options[it] }.toMutableList()
        correctAnswers = indices.map { correctAnswers[it] }.toMutableList()

        val selectedIndices = questions.indices.shuffled().take(10)
        questions = selectedIndices.map { questions[it] }.toMutableList()
        options = selectedIndices.map { options[it] }.toMutableList()
        correctAnswers = selectedIndices.map { correctAnswers[it] }.toMutableList()

        Log.d("Chapter1test", "Selected ${questions.size} questions for the test")
    }

    private fun loadAnswersFromDB(questionID: Int) {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM ${DBConnection.TABLE_NAME_ANSWERS} WHERE ${DBConnection.COL3_ANSWERS} = $questionID", null)
        val answers = mutableListOf<String>()
        var correctAnswerIndex = -1
        if (cursor.moveToFirst()) {
            do {
                val answer = cursor.getString(cursor.getColumnIndexOrThrow(DBConnection.COL2_ANSWERS))
                val isCorrect = cursor.getInt(cursor.getColumnIndexOrThrow(DBConnection.COL4_ANSWERS)) == 1
                answers.add(answer)
                if (isCorrect) {
                    correctAnswerIndex = answers.size - 1
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        options.add(answers.toTypedArray())
        correctAnswers.add(correctAnswerIndex)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChapter1testBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val imageResId = when (CurrentChapter) {
            1 -> R.drawable.test1
            2 -> R.drawable.test2
            3 -> R.drawable.test3
            4 -> R.drawable.test4
            else -> R.drawable.test2
        }
        binding.imageView.setImageResource(imageResId)


        if (questions.isNotEmpty()) {
            displayQuestion()
        } else {
            Log.e("Chapter1test", "No questions available to display")
        }

        binding.option1.setOnClickListener {
            checkAnswer(0)
        }
        binding.option2.setOnClickListener {
            checkAnswer(1)
        }
        binding.option3.setOnClickListener {
            checkAnswer(2)
        }
        binding.option4.setOnClickListener {
            checkAnswer(3)
        }
        binding.nextChapter.setOnClickListener {
            replaceFragment(Tests())
        }
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.timer.text = "${millisUntilFinished / 1000}s"
            }

            override fun onFinish() {
                nextQuestion()
            }
        }
        countDownTimer.start()
    }

    private fun correctButtonColors(buttonIndex: Int) {
        when (buttonIndex) {
            0 -> binding.option1.setBackgroundColor(Color.GREEN)
            1 -> binding.option2.setBackgroundColor(Color.GREEN)
            2 -> binding.option3.setBackgroundColor(Color.GREEN)
            3 -> binding.option4.setBackgroundColor(Color.GREEN)
        }
    }

    private fun wrongButtonColors(buttonIndex: Int) {
        when (buttonIndex) {
            0 -> binding.option1.setBackgroundColor(Color.RED)
            1 -> binding.option2.setBackgroundColor(Color.RED)
            2 -> binding.option3.setBackgroundColor(Color.RED)
            3 -> binding.option4.setBackgroundColor(Color.RED)
        }
    }

    private fun resetButtonColors() {
        binding.option1.setBackgroundColor(Color.parseColor("#2C74B3"))
        binding.option2.setBackgroundColor(Color.parseColor("#2C74B3"))
        binding.option3.setBackgroundColor(Color.parseColor("#2C74B3"))
        binding.option4.setBackgroundColor(Color.parseColor("#2C74B3"))
    }

    private fun enableButtons(enable: Boolean) {
        binding.option1.isEnabled = enable
        binding.option2.isEnabled = enable
        binding.option3.isEnabled = enable
        binding.option4.isEnabled = enable

        // Dodaj obsługę przycisków w MainActivity
        val mainActivity = activity as? MainActivity
        mainActivity?.let {
            it.findViewById<ImageView>(R.id.profile)?.isEnabled = enable
            it.findViewById<ImageView>(R.id.home)?.isEnabled = enable
            it.findViewById<ImageView>(R.id.notifications)?.isEnabled = enable
        }
    }


    private fun showResults() {
        enableButtons(true)
        binding.linearLayout.visibility = View.INVISIBLE
        binding.nextChapter.visibility = View.VISIBLE
        binding.score.visibility = View.VISIBLE
        binding.timer.visibility = View.INVISIBLE
        binding.pytanie.text = "Twój wynik"
        val resultPercentage = (score.toFloat() / questions.size * 100).toInt()
        binding.score.text = "$resultPercentage%"

        saveTestResult(CurrentChapter, resultPercentage)
    }



    private fun saveTestResult(chapter: Int, result: Int) {
        val db = dbHelper.writableDatabase

        // Sprawdzenie, czy istnieje wynik dla danego rozdziału
        val cursor = db.rawQuery(
            "SELECT Result FROM Results WHERE Chapter = ?",
            arrayOf("chapter$chapter")
        )

        if (cursor.moveToFirst()) {
            val existingResult = cursor.getInt(cursor.getColumnIndexOrThrow("Result"))
            cursor.close()

            // Sprawdzenie, czy nowy wynik jest wyższy
            if (result > existingResult) {
                val values = ContentValues().apply {
                    put("Result", result)
                }

                val rowsUpdated = db.update(
                    "Results",
                    values,
                    "Chapter = ?",
                    arrayOf("chapter$chapter")
                )

                if (rowsUpdated > 0) {
                    Log.d("Chapter1test", "Result updated for chapter$chapter: $result")
                } else {
                    Log.e("Chapter1test", "Error updating result for chapter$chapter")
                }
            } else {
                Log.d("Chapter1test", "Existing result for chapter$chapter ($existingResult) is higher or equal. Not updating.")
            }
        } else {
            cursor.close()

            // Jeśli wynik nie istnieje, wstawiamy nowy
            val values = ContentValues().apply {
                put("Chapter", "chapter$chapter")
                put("Result", result)
            }

            val newRowId = db.insert("Results", null, values)

            if (newRowId == -1L) {
                Log.e("Chapter1test", "Error inserting new result for chapter$chapter")
            } else {
                Log.d("Chapter1test", "New result saved for chapter$chapter with row id: $newRowId")
            }
        }

        db.close()
    }

    private fun displayQuestion() {
        if (questions.isEmpty() || options.isEmpty() || correctAnswers.isEmpty()) {
            Log.e("Chapter1test", "Cannot display question, lists are empty")
            return
        }

        shuffleAnswersForCurrentQuestion()

        resetButtonColors()
        enableButtons(true)
        binding.questionNumber.text = "Pytanie ${currentQuestionIndex + 1}"
        binding.pytanie.text = questions[currentQuestionIndex]
        binding.option1.text = options[currentQuestionIndex][0]
        binding.option2.text = options[currentQuestionIndex][1]
        binding.option3.text = options[currentQuestionIndex][2]
        binding.option4.text = options[currentQuestionIndex][3]
        startTimer()
    }

    private fun shuffleAnswersForCurrentQuestion() {
        val currentOptions = options[currentQuestionIndex]
        val currentCorrectAnswerIndex = correctAnswers[currentQuestionIndex]

        val shuffledIndices = currentOptions.indices.shuffled()
        val shuffledOptions = shuffledIndices.map { currentOptions[it] }.toTypedArray()
        val newCorrectAnswerIndex = shuffledIndices.indexOf(currentCorrectAnswerIndex)

        options[currentQuestionIndex] = shuffledOptions
        correctAnswers[currentQuestionIndex] = newCorrectAnswerIndex
    }

    private fun checkAnswer(selectedAnswerIndex: Int) {
        countDownTimer.cancel()
        enableButtons(false)
        val correctAnswerIndex = correctAnswers[currentQuestionIndex]

        if (selectedAnswerIndex == correctAnswerIndex) {
            score++
            correctButtonColors(selectedAnswerIndex)
        } else {
            wrongButtonColors(selectedAnswerIndex)
            correctButtonColors(correctAnswerIndex)
        }

        binding.pytanie.postDelayed({ nextQuestion() }, 1000)
    }

    private fun nextQuestion() {
        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
            displayQuestion()
        } else {
            showResults()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.center, fragment)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        if (::countDownTimer.isInitialized) {
            countDownTimer.cancel()
        }
    }
}
