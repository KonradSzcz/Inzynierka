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
import com.crypto.inzynierka.databinding.FragmentChapter1testBinding
import kotlin.random.Random

class Chapter1test : Fragment() {
    private var _binding: FragmentChapter1testBinding? = null
    private val binding get() = _binding!!

    private var questions = mutableListOf<String>()
    private var options = mutableListOf<Array<String>>()
    private var correctAnswers = mutableListOf<Int>()

    private var currentQuestionIndex = 0
    private var score = 0

    private lateinit var dbHelper: DBConnection
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = DBConnection(requireContext(), "cryptoDB", 6)
        loadQuestionsFromDB()
    }

    private fun loadQuestionsFromDB() {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM ${DBConnection.TABLE_NAME_QUESTIONS} WHERE Chapter LIKE 'chapter1%'", null)
        if (cursor.moveToFirst()) {
            do {
                val question = cursor.getString(cursor.getColumnIndexOrThrow(DBConnection.COL2_QUESTIONS))
                questions.add(question)
                val questionID = cursor.getInt(cursor.getColumnIndexOrThrow(DBConnection.COL1_QUESTIONS))
                loadAnswersFromDB(questionID)
            } while (cursor.moveToNext())
        }
        cursor.close()

        // Debugging logs
        Log.d("Chapter1test", "Loaded ${questions.size} questions")

        // Shuffle the questions and answers to randomize the order
        val indices = questions.indices.toList().shuffled()
        questions = indices.map { questions[it] }.toMutableList()
        options = indices.map { options[it] }.toMutableList()
        correctAnswers = indices.map { correctAnswers[it] }.toMutableList()

        // Choose only 10 random questions
        val selectedIndices = questions.indices.shuffled().take(10)
        questions = selectedIndices.map { questions[it] }.toMutableList()
        options = selectedIndices.map { options[it] }.toMutableList()
        correctAnswers = selectedIndices.map { correctAnswers[it] }.toMutableList()

        // Debugging logs
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

        // Check if questions are loaded before attempting to display
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
            replaceFragment(Chapter1_2())
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
    }

    private fun showResults() {
        binding.linearLayout.visibility = View.INVISIBLE
        binding.nextChapter.visibility = View.VISIBLE
        binding.score.visibility = View.VISIBLE
        binding.timer.visibility = View.INVISIBLE
        binding.pytanie.text = "Twój wynik"
        val resultPercentage = (score.toFloat() / questions.size * 100).toInt()
        binding.score.text = "$resultPercentage%"

        saveResult(resultPercentage)
    }

    private fun saveResult(resultPercentage: Int) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DBConnection.COL2_RESULTS, "chapter1")
            put(DBConnection.COL3_RESULTS, resultPercentage)
        }
        db.insert(DBConnection.TABLE_NAME_RESULTS, null, values)
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
