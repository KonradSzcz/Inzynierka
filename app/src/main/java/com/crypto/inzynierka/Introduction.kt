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
import com.crypto.inzynierka.databinding.FragmentIntroductionBinding
import kotlin.math.floor

class Introduction : Fragment() {
    private var _binding: FragmentIntroductionBinding? = null
    private val binding get() = _binding!!
    private val Vm by viewModels<MainViewModel>()

    private lateinit var dbHelper: DBConnection
    private lateinit var dataArray: Array<Array<String?>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIntroductionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DBConnection(requireContext(), "cryptoDB", MainViewModel.DB_VERSION)

        loadDataIntoArray()

        updateReadIndicators()

        updateProgressBar()

        val fullText1 = Html.fromHtml(getString(R.string.chapter1_3), Html.FROM_HTML_MODE_COMPACT).toString()
        val lines1 = fullText1.lines()
        val visibleText1 = if (lines1.size > 2) {
            lines1.drop(2).joinToString("\n")
        } else {
            fullText1
        }

        val fullText2 = Html.fromHtml(getString(R.string.chapter1_5), Html.FROM_HTML_MODE_COMPACT).toString()
        val lines2 = fullText2.lines()
        val visibleText2 = if (lines2.size > 2) {
            lines2.drop(2).joinToString("\n")
        } else {
            fullText2
        }

        val fullText3 = Html.fromHtml(getString(R.string.chapter1_6), Html.FROM_HTML_MODE_COMPACT).toString()
        val lines3 = fullText3.lines()
        val visibleText3 = if (lines3.size > 1) {
            lines3.drop(2).joinToString("\n")
        } else {
            fullText3
        }


        binding.rozdzial1content.text = Html.fromHtml(getString(R.string.chapter1_1), Html.FROM_HTML_MODE_COMPACT)
        binding.rozdzial2content.text = Html.fromHtml(getString(R.string.chapter1_2), Html.FROM_HTML_MODE_COMPACT)
        binding.rozdzial3content.text = Html.fromHtml(visibleText1, Html.FROM_HTML_MODE_COMPACT)
        binding.rozdzial4content.text = Html.fromHtml(getString(R.string.chapter1_4), Html.FROM_HTML_MODE_COMPACT)
        binding.rozdzial5content.text = Html.fromHtml(visibleText2, Html.FROM_HTML_MODE_COMPACT)
        binding.rozdzial6content.text = Html.fromHtml(visibleText3, Html.FROM_HTML_MODE_COMPACT)
        binding.rozdzial7content.text = Html.fromHtml(getString(R.string.chapter1_7), Html.FROM_HTML_MODE_COMPACT)
        binding.rozdzial8content.text = Html.fromHtml(getString(R.string.chapter1_8), Html.FROM_HTML_MODE_COMPACT)



        binding.section1.setOnClickListener {
            replaceFragment(Chapter1(),1)
        }
        binding.section2.setOnClickListener {
            replaceFragment(Chapter1(),2)
        }
        binding.section3.setOnClickListener {
            replaceFragment(Chapter1(),3)
        }
        binding.section4.setOnClickListener {
            replaceFragment(Chapter1(),4)
        }
        binding.section5.setOnClickListener {
            replaceFragment(Chapter1(),5)
        }
        binding.section6.setOnClickListener {
            replaceFragment(Chapter1(),6)
        }
        binding.section7.setOnClickListener {
            replaceFragment(Chapter1(),7)
        }
        binding.section8.setOnClickListener {
            replaceFragment(Chapter1(),8)
        }
        binding.test.setOnClickListener {
            replaceFragment(Tests(),0)
        }
        binding.game.setOnClickListener {
            replaceFragment(Chapter1_3game(),1)
        }
    }

    private fun loadDataIntoArray() {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM Tests WHERE Chapter = 'chapter1' ", null)

        val rowCount = cursor.count

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
        /* for (i in dataArray.indices) {
            Log.d(
                "Introduction",
                "Row $i: Chapter=${dataArray[i][0]}, Test=${dataArray[i][1]}, Result=${dataArray[i][2]}"
            )
        }*/

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
