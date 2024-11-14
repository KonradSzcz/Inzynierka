package com.crypto.inzynierka

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.database.Cursor
import android.util.Log
import androidx.fragment.app.viewModels
import com.crypto.inzynierka.databinding.FragmentBlockchainBinding


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
        // Sprawdzanie każdego rekordu i ustawianie odpowiedniego wskaźnika.
        var completedChapters = 0

        for (i in dataArray.indices) {
            val testNumber = dataArray[i][1]?.toIntOrNull()

            when (testNumber) {
                1 -> binding.readIndicator1.setImageResource(R.drawable.baseline_beenhere_24)
                2 -> binding.readIndicator2.setImageResource(R.drawable.baseline_beenhere_24)
                3 -> binding.readIndicator3.setImageResource(R.drawable.baseline_beenhere_24)
                4 -> binding.readIndicator4.setImageResource(R.drawable.baseline_beenhere_24)
                5 -> binding.readIndicator5.setImageResource(R.drawable.baseline_beenhere_24)
                6 -> binding.readIndicator6.setImageResource(R.drawable.baseline_beenhere_24)
                7 -> binding.readIndicator7.setImageResource(R.drawable.baseline_beenhere_24)
                8 -> binding.readIndicator8.setImageResource(R.drawable.baseline_beenhere_24)
            }

            // Zliczanie zakończonych rozdziałów
            completedChapters++
        }

        // Ustawienie tekstu na podstawie liczby pozostałych rozdziałów.
        binding.endedchapters.text = "Pozostałe rozdziały: ${8 - completedChapters}"
    }

    private fun updateProgressBar() {
        binding.progressBar.apply {
            max = 10
            progress = dataArray.size
        }
    }
    private fun replaceFragment(fragment: Fragment,chapter: Int) {
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
