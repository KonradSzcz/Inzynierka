package com.crypto.inzynierka

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.crypto.inzynierka.databinding.FragmentChapter11testBinding
import android.database.Cursor
import kotlin.random.Random

class Chapter1_1test : Fragment() {
    private var _binding: FragmentChapter11testBinding? = null
    private val binding get() = _binding!!

    private var questions = mutableListOf<String>()
    private var options = mutableListOf<Array<String>>()
    private var correctAnswers = mutableListOf<Int>()

    private var currentQuestionIndex = 0
    private var score = 0

    private lateinit var dbHelper: DBConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = DBConnection(requireContext(), "cryptoDB", 6)
        loadQuestionsFromDB()
    }

    private fun loadQuestionsFromDB() {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM ${DBConnection.TABLE_NAME_QUESTIONS} where Chapter= 'chapter11'", null)
        if (cursor.moveToFirst()) {
            do {
                val question = cursor.getString(cursor.getColumnIndexOrThrow(DBConnection.COL2_QUESTIONS))
                questions.add(question)
                val questionID = cursor.getInt(cursor.getColumnIndexOrThrow(DBConnection.COL1_QUESTIONS))
                loadAnswersFromDB(questionID)
            } while (cursor.moveToNext())
        }
        cursor.close()

        // Shuffle the questions and answers to randomize the order
        val indices = questions.indices.toList().shuffled()
        questions = indices.map { questions[it] }.toMutableList()
        options = indices.map { options[it] }.toMutableList()
        correctAnswers = indices.map { correctAnswers[it] }.toMutableList()
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
        _binding = FragmentChapter11testBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayQuestion()

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
        binding.pytanie.text = "Twój wynik"
        binding.score.text = "$score / ${questions.size}"
    }

    private fun displayQuestion() {
        resetButtonColors()
        enableButtons(true)
        binding.pytanie.text = questions[currentQuestionIndex]
        binding.option1.text = options[currentQuestionIndex][0]
        binding.option2.text = options[currentQuestionIndex][1]
        binding.option3.text = options[currentQuestionIndex][2]
        binding.option4.text = options[currentQuestionIndex][3]
    }

    private fun checkAnswer(selectedAnswerIndex: Int) {
        enableButtons(false)
        val correctAnswerIndex = correctAnswers[currentQuestionIndex]

        if (selectedAnswerIndex == correctAnswerIndex) {
            score++
            correctButtonColors(selectedAnswerIndex)
        } else {
            wrongButtonColors(selectedAnswerIndex)
            correctButtonColors(correctAnswerIndex)
        }

        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
            binding.pytanie.postDelayed({ displayQuestion() }, 1000)
        } else {
            binding.pytanie.postDelayed({ showResults() }, 1000)
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
    }
}
