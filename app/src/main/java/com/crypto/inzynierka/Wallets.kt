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
import com.crypto.inzynierka.databinding.FragmentWalletsBinding
import kotlin.math.floor

class Wallets : Fragment() {
    private var _binding: FragmentWalletsBinding? = null
    private val binding get() = _binding!!
    private val vm by viewModels<MainViewModel>()

    private lateinit var dbHelper: DBConnection
    private lateinit var chapterArray: List<Pair<String, Boolean>> // List to store chapter and isChecked values

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWalletsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DBConnection(requireContext(), "cryptoDB", MainViewModel.DB_VERSION)

        loadDataFromDatabase()

        updateReadIndicators()

        updateProgressBar()

        binding.rozdzial1content.text = Html.fromHtml(getString(R.string.chapter5_1), Html.FROM_HTML_MODE_COMPACT)
        binding.rozdzial2content.text = Html.fromHtml(getString(R.string.chapter5_2), Html.FROM_HTML_MODE_COMPACT)
        binding.rozdzial3content.text = Html.fromHtml(getString(R.string.chapter5_3), Html.FROM_HTML_MODE_COMPACT)
        binding.rozdzial4content.text = Html.fromHtml(getString(R.string.chapter5_4), Html.FROM_HTML_MODE_COMPACT)
        binding.rozdzial5content.text = Html.fromHtml(getString(R.string.chapter5_5), Html.FROM_HTML_MODE_COMPACT)

        binding.section1.setOnClickListener {
            replaceFragment(Chapter5(),1)
        }
        binding.section2.setOnClickListener {
            replaceFragment(Chapter5(),2)
        }
        binding.section3.setOnClickListener {
            replaceFragment(Chapter5(),3)
        }
        binding.section4.setOnClickListener {
            replaceFragment(Chapter5(),4)
        }
        binding.section5.setOnClickListener {
            replaceFragment(Chapter5(),5)
        }

    }

    private fun loadDataFromDatabase() {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT Chapter, isChecked FROM Wallets", null)

        chapterArray = mutableListOf<Pair<String, Boolean>>()

        if (cursor.moveToFirst()) {
            do {
                val chapter = cursor.getString(cursor.getColumnIndexOrThrow("Chapter"))
                val isChecked = cursor.getInt(cursor.getColumnIndexOrThrow("isChecked")) == 1
                (chapterArray as MutableList).add(Pair(chapter, isChecked))
            } while (cursor.moveToNext())
        } else {
            Log.d("Wallets", "No data found in the Wallets table.")
        }

        cursor.close()
        db.close()
    }

    private fun updateReadIndicators() {
        var completedChapters = 0

        for ((index, pair) in chapterArray.withIndex()) {
            val isChecked = pair.second

            if (isChecked) {
                when (index + 1) {
                    1 -> binding.readIndicator1.setImageResource(R.drawable.baseline_check_circle_24)
                    2 -> binding.readIndicator2.setImageResource(R.drawable.baseline_check_circle_24)
                    3 -> binding.readIndicator3.setImageResource(R.drawable.baseline_check_circle_24)
                    4 -> binding.readIndicator4.setImageResource(R.drawable.baseline_check_circle_24)
                    5 -> binding.readIndicator5.setImageResource(R.drawable.baseline_check_circle_24)
                }
                completedChapters++
            }
        }

        binding.endedchapters.text = "Pozostałe rozdziały: ${5 - completedChapters}"
    }

    private fun updateProgressBar() {
        val completedChapters = chapterArray.count { it.second }
        binding.progressBar.apply {
            max = chapterArray.size
            progress = completedChapters

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