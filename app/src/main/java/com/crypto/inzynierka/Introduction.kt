package com.crypto.inzynierka

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.database.Cursor
import android.util.Log
import androidx.fragment.app.viewModels
import com.crypto.inzynierka.databinding.FragmentIntroductionBinding

class Introduction : Fragment() {
    private var _binding: FragmentIntroductionBinding? = null
    private val binding get() = _binding!!
    private val Vm by viewModels<MainViewModel>()

    private lateinit var dbHelper: DBConnection
    private lateinit var dataArray: Array<Array<String?>> // Dynamic 2D array to store data from the database

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIntroductionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize DBHelper
        dbHelper = DBConnection(requireContext(), "cryptoDB", MainViewModel.DB_VERSION)

        // Load data from the Tests table into the array
        loadDataIntoArray()

        // Update readIndicators based on the number of rows in dataArray
        updateReadIndicators()

        // Update ProgressBar
        updateProgressBar()

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
        // Check how many rows are in dataArray
        val numberOfRows = dataArray.size

        // Update each readIndicator based on the number of rows
        if (numberOfRows >= 1 && dataArray[0][1] == "1") {
            binding.readIndicator1.setImageResource(R.drawable.baseline_beenhere_24)
        }
        if (numberOfRows >= 2 && dataArray[1][1] == "2") {
            binding.readIndicator2.setImageResource(R.drawable.baseline_beenhere_24)
        }
        if (numberOfRows >= 3 && dataArray[2][1] == "3") {
            binding.readIndicator3.setImageResource(R.drawable.baseline_beenhere_24)
        }
        if (numberOfRows >= 4 && dataArray[3][1] == "4") {
            binding.readIndicator4.setImageResource(R.drawable.baseline_beenhere_24)
        }
        if (numberOfRows >= 5 && dataArray[4][1] == "5") {
            binding.readIndicator5.setImageResource(R.drawable.baseline_beenhere_24)
        }

        binding.endedchapters.text = "Pozostałe rozdziay: ${5 - dataArray.size}"
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
