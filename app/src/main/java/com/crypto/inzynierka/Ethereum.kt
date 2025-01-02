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
import com.crypto.inzynierka.databinding.FragmentEthereumBinding
import kotlin.math.floor


class Ethereum : Fragment() {
    private var _binding: FragmentEthereumBinding? = null
    private val binding get() = _binding!!
    private val Vm by viewModels<MainViewModel>()

    private lateinit var dbHelper: DBConnection
    private lateinit var dataArray: Array<Array<String?>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEthereumBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DBConnection(requireContext(), "cryptoDB", MainViewModel.DB_VERSION)

        loadDataIntoArray()

        updateReadIndicators()

        updateProgressBar()



        binding.rozdzial1content.text = Html.fromHtml(getString(R.string.chapter4_1), Html.FROM_HTML_MODE_COMPACT)
        binding.rozdzial2content.text = Html.fromHtml(getString(R.string.chapter4_2), Html.FROM_HTML_MODE_COMPACT)
        binding.rozdzial3content.text = Html.fromHtml(getString(R.string.chapter4_3), Html.FROM_HTML_MODE_COMPACT)
        binding.rozdzial4content.text = Html.fromHtml(getString(R.string.chapter4_4), Html.FROM_HTML_MODE_COMPACT)

        binding.section1.setOnClickListener {
            replaceFragment(Chapter4(),1)
        }
        binding.section2.setOnClickListener {
            replaceFragment(Chapter4(),2)
        }
        binding.section3.setOnClickListener {
            replaceFragment(Chapter4(),3)
        }
        binding.section4.setOnClickListener {
            replaceFragment(Chapter4(), 4)
        }
        binding.test.setOnClickListener {
            replaceFragment(Tests(),0)
        }
        binding.game.setOnClickListener {
            replaceFragment(Chapter1_3game(),4)
        }
    }

    private fun loadDataIntoArray() {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM Tests WHERE Chapter = 'chapter4'", null)

        val rowCount = cursor.count

        dataArray = Array(rowCount) { arrayOfNulls<String>(3) }

        if (cursor.moveToFirst()) {
            var rowIndex = 0

            do {
                val chapter = cursor.getString(cursor.getColumnIndexOrThrow("Chapter"))
                val testNumber = cursor.getString(cursor.getColumnIndexOrThrow("Test"))
                val result = cursor.getString(cursor.getColumnIndexOrThrow("Result"))

                dataArray[rowIndex][0] = chapter
                dataArray[rowIndex][1] = testNumber
                dataArray[rowIndex][2] = result

                rowIndex++

            } while (cursor.moveToNext())
        } else {
            Log.d("Introduction", "No data found in the Tests table.")
        }

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
            }

            completedChapters++
        }

        binding.endedchapters.text = "Pozostałe rozdziały: ${4 - completedChapters}"
    }

    private fun updateProgressBar() {
        binding.progressBar.apply {
            max = 4
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
