package com.crypto.inzynierka

import android.content.ContentValues
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.database.Cursor
import androidx.fragment.app.viewModels
import com.crypto.inzynierka.databinding.FragmentChapter1quizBinding

class Chapter1quiz : Fragment() {
    private var _binding: FragmentChapter1quizBinding? = null
    private val binding get() = _binding!!
    private val Vm by viewModels<MainViewModel>()

    private var questions = mutableListOf<String>()
    private var options = mutableListOf<Array<String>>()
    private var correctAnswers = mutableListOf<Int>()

    private var currentQuestionIndex = 0
    private var score = 0

    private var CurrentChapter: Int = 1

    private lateinit var dbHelper: DBConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = DBConnection(requireContext(), "cryptoDB", MainViewModel.DB_VERSION)

        // Przekazana wartośćł rozdziału
        arguments?.let {
            CurrentChapter = it.getInt("CurrentChapter", 1)
        }

        loadQuestionsFromDB(CurrentChapter)
    }

    private fun loadQuestionsFromDB(chapter: Int) {
        val db = dbHelper.readableDatabase
        val chapterColumn = "chapter1$chapter"
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM ${DBConnection.TABLE_NAME_QUESTIONS} WHERE ${DBConnection.COL3_QUESTIONS} = '$chapterColumn'",
            null
        )
        if (cursor.moveToFirst()) {
            do {
                val question =
                    cursor.getString(cursor.getColumnIndexOrThrow(DBConnection.COL2_QUESTIONS))
                questions.add(question)
                val questionID =
                    cursor.getInt(cursor.getColumnIndexOrThrow(DBConnection.COL1_QUESTIONS))
                loadAnswersFromDB(questionID)
            } while (cursor.moveToNext())
        }
        cursor.close()

        // Mieszanie pytań i odpowiedzi
        val indices = questions.indices.toList().shuffled()
        questions = indices.map { questions[it] }.toMutableList()
        options = indices.map { options[it] }.toMutableList()
        correctAnswers = indices.map { correctAnswers[it] }.toMutableList()
    }

    private fun loadAnswersFromDB(questionID: Int) {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM ${DBConnection.TABLE_NAME_ANSWERS} WHERE ${DBConnection.COL3_ANSWERS} = $questionID",
            null
        )
        val answers = mutableListOf<String>()
        var correctAnswerIndex = -1
        if (cursor.moveToFirst()) {
            do {
                val answer =
                    cursor.getString(cursor.getColumnIndexOrThrow(DBConnection.COL2_ANSWERS))
                val isCorrect =
                    cursor.getInt(cursor.getColumnIndexOrThrow(DBConnection.COL4_ANSWERS)) == 1
                answers.add(answer)
                if (isCorrect) {
                    correctAnswerIndex = answers.size - 1
                }
            } while (cursor.moveToNext())
        }
        cursor.close()

        // Mieszanie odpowiedzi
        val shuffledAnswers = answers.indices.shuffled()
        val shuffledAnswersList = shuffledAnswers.map { answers[it] }
        val shuffledCorrectAnswerIndex = shuffledAnswers.indexOf(correctAnswerIndex)

        options.add(shuffledAnswersList.toTypedArray())
        correctAnswers.add(shuffledCorrectAnswerIndex)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChapter1quizBinding.inflate(inflater, container, false)
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

        if (CurrentChapter < 6) {
            binding.nextChapter.setOnClickListener {
                replaceFragment(Chapter1(), CurrentChapter + 1)
            }
        } else {
            binding.nextChapter.setOnClickListener {
                replaceFragment(Chapter1test(), 0)
            }
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
        binding.option1.setBackgroundColor(Color.parseColor("#0D2F53"))
        binding.option2.setBackgroundColor(Color.parseColor("#0D2F53"))
        binding.option3.setBackgroundColor(Color.parseColor("#0D2F53"))
        binding.option4.setBackgroundColor(Color.parseColor("#0D2F53"))
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

        if (score >= 0.75 * questions.size) {
            saveResult()
        }
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

    private fun saveResult() {
        val db = dbHelper.writableDatabase

        // Sprawdź, czy rekord z danym rozdziałem już istnieje
        val cursor = db.rawQuery(
            "SELECT * FROM ${DBConnection.TABLE_NAME_TESTS} WHERE ${DBConnection.COL2_TESTS} = ? AND ${DBConnection.COL3_TESTS} = ?",
            arrayOf("1", CurrentChapter.toString())
        )

        if (!cursor.moveToFirst()) {
            // Jeśli nie ma rekordu, wstaw nowy
            val values = ContentValues().apply {
                put(DBConnection.COL2_TESTS, "chapter1")
                put(DBConnection.COL3_TESTS, CurrentChapter)
                put(DBConnection.COL4_TESTS, 1)
            }
            db.insert(DBConnection.TABLE_NAME_TESTS, null, values)
        }

        cursor.close()
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
