package com.crypto.inzynierka

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.database.Cursor
import android.text.Html
import android.util.Log
import androidx.fragment.app.viewModels
import com.crypto.inzynierka.databinding.FragmentBlockchainBinding
import kotlin.math.floor


class Blockchain : Fragment() {
    private var _binding: FragmentBlockchainBinding? = null
    private val binding get() = _binding!!
    private val Vm by viewModels<MainViewModel>()

    private lateinit var dbHelper: DBConnection
    private lateinit var dataArray: Array<Array<String?>> // Dynamic 2D array to store data from the database

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBlockchainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DBConnection(requireContext(), "cryptoDB", MainViewModel.DB_VERSION)

        loadDataIntoArray()

        updateReadIndicators()

        updateProgressBar()


        val fullText1 = Html.fromHtml(getString(R.string.chapter2_3), Html.FROM_HTML_MODE_COMPACT).toString()
        val lines1 = fullText1.lines()
        val visibleText1 = if (lines1.size > 2) {
            lines1.drop(2).joinToString("\n")
        } else {
            fullText1
        }

        val fullText2 = Html.fromHtml(getString(R.string.chapter2_6), Html.FROM_HTML_MODE_COMPACT).toString()
        val lines2 = fullText2.lines()
        val visibleText2 = if (lines2.size > 2) {
            lines2.drop(2).joinToString("\n")
        } else {
            fullText2
        }

        val fullText3 = Html.fromHtml(getString(R.string.chapter2_7), Html.FROM_HTML_MODE_COMPACT).toString()
        val lines3 = fullText3.lines()
        val visibleText3 = if (lines3.size > 2) {
            lines3.drop(2).joinToString("\n")
        } else {
            fullText3
        }

        val fullText4 = Html.fromHtml(getString(R.string.chapter2_8), Html.FROM_HTML_MODE_COMPACT).toString()
        val lines4 = fullText4.lines()
        val visibleText4 = if (lines4.size > 2) {
            lines4.drop(2).joinToString("\n")
        } else {
            fullText4
        }


        binding.rozdzial1content.text = Html.fromHtml(getString(R.string.chapter2_1), Html.FROM_HTML_MODE_COMPACT)
        binding.rozdzial2content.text = Html.fromHtml(getString(R.string.chapter2_2), Html.FROM_HTML_MODE_COMPACT)
        binding.rozdzial3content.text = Html.fromHtml(visibleText1, Html.FROM_HTML_MODE_COMPACT)
        binding.rozdzial4content.text = Html.fromHtml(getString(R.string.chapter2_4), Html.FROM_HTML_MODE_COMPACT)
        binding.rozdzial5content.text = Html.fromHtml(getString(R.string.chapter2_5), Html.FROM_HTML_MODE_COMPACT)
        binding.rozdzial6content.text = Html.fromHtml(visibleText2, Html.FROM_HTML_MODE_COMPACT)
        binding.rozdzial7content.text = Html.fromHtml(visibleText3, Html.FROM_HTML_MODE_COMPACT)
        binding.rozdzial8content.text = Html.fromHtml(visibleText4, Html.FROM_HTML_MODE_COMPACT)

        binding.section1.setOnClickListener {
            replaceFragment(Chapter2(),1)
        }
        binding.section2.setOnClickListener {
            replaceFragment(Chapter2(),2)
        }
        binding.section3.setOnClickListener {
            replaceFragment(Chapter2(),3)
        }
        binding.section4.setOnClickListener {
            replaceFragment(Chapter2(),4)
        }
        binding.section5.setOnClickListener {
            replaceFragment(Chapter2(),5)
        }
        binding.section6.setOnClickListener {
            replaceFragment(Chapter2(),6)
        }
        binding.section7.setOnClickListener {
            replaceFragment(Chapter2(),7)
        }
        binding.section8.setOnClickListener {
            replaceFragment(Chapter2(),8)
        }
        binding.test.setOnClickListener {
            replaceFragment(Tests(),0)
        }
        binding.game.setOnClickListener {
            replaceFragment(Chapter1_3game(),2)
        }
    }

    private fun loadDataIntoArray() {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM Tests WHERE Chapter = 'chapter2'", null)

        // Get the number of rows in the cursor
        val rowCount = cursor.count

        // Initialize the dynamic 2D array with the number of rows from the cursor and 3 columns
        dataArray = Array(rowCount) { arrayOfNulls<String>(3) }

        if (cursor.moveToFirst()) {
            var rowIndex = 0

            do {
                // Get column values
                val chapter = cursor.getString(cursor.getColumnIndexOrThrow("Chapter"))
                val testNumber = cursor.getString(cursor.getColumnIndexOrThrow("Test"))
                val result = cursor.getString(cursor.getColumnIndexOrThrow("Result"))

                // Store the data in the array
                dataArray[rowIndex][0] = chapter
                dataArray[rowIndex][1] = testNumber
                dataArray[rowIndex][2] = result

                rowIndex++

            } while (cursor.moveToNext())
        } else {
            Log.d("Introduction", "No data found in the Tests table.")
        }

        // Debug: Log the array contents
        for (i in dataArray.indices) {
            Log.d(
                "Introduction",
                "Row $i: Chapter=${dataArray[i][0]}, Test=${dataArray[i][1]}, Result=${dataArray[i][2]}"
            )
        }

        cursor.close()
        db.close()

    }

    private fun updateReadIndicators() {
        var completedChapters = 0

        for (i in dataArray.indices) {
            val testNumber = dataArray[i][1]?.toIntOrNull()

            when (testNumber) {
                1 -> binding.readIndicator1.setImageResource(R.drawable.baseline_check_circle_24)
                2 -> binding.readIndicator2.setImageResource(R.drawable.baseline_check_circle_24)
                3 -> binding.readIndicator3.setImageResource(R.drawable.baseline_check_circle_24)
                4 -> binding.readIndicator4.setImageResource(R.drawable.baseline_check_circle_24)
                5 -> binding.readIndicator5.setImageResource(R.drawable.baseline_check_circle_24)
                6 -> binding.readIndicator6.setImageResource(R.drawable.baseline_check_circle_24)
                7 -> binding.readIndicator7.setImageResource(R.drawable.baseline_check_circle_24)
                8 -> binding.readIndicator8.setImageResource(R.drawable.baseline_check_circle_24)
            }

            completedChapters++
        }

        binding.endedchapters.text = "Pozostałe rozdziały: ${8 - completedChapters}"
    }

    private fun updateProgressBar() {
        binding.progressBar.apply {
            max = 8
            progress = dataArray.size

            val progressPercentage = (progress.toFloat() / max) * 100
            val roundedPercentage = floor(progressPercentage).toInt()
            binding.progressTextView.text = "$roundedPercentage%"
        }
    }
    private fun replaceFragment(fragment: Fragment, chapter: Int) {
        val args = Bundle().apply {
            putInt("CurrentChapter", chapter)
        }
        fragment.arguments = args
        parentFragmentManager.beginTransaction()
            .replace(R.id.center, fragment)
            .addToBackStack(null) // Dodaje operację do stosu powrotu
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
