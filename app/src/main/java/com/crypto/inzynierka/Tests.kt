package com.crypto.inzynierka

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.database.Cursor
import android.opengl.Visibility
import android.text.Html
import android.util.Log
import androidx.fragment.app.viewModels
import com.crypto.inzynierka.databinding.FragmentTestsBinding
import kotlin.math.floor

class Tests : Fragment() {
    private var _binding: FragmentTestsBinding? = null
    private val binding get() = _binding!!
    private val Vm by viewModels<MainViewModel>()

    private lateinit var dbHelper: DBConnection
    private lateinit var dataArray: Array<Array<String?>>
    private var testNumber: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTestsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DBConnection(requireContext(), "cryptoDB", MainViewModel.DB_VERSION)

        loadDataIntoArray()
        countTestsAboveThreshold()
        updateChapterPercentages()
        updateProgressBar()

        binding.introduction.setOnClickListener {
            replaceFragment(Chapter1test(), 1)
        }
        binding.blockchain.setOnClickListener {
            replaceFragment(Chapter1test(), 2)
        }
        binding.bitcoin.setOnClickListener {
            replaceFragment(Chapter1test(), 3)
        }
        binding.ethereum.setOnClickListener {
            replaceFragment(Chapter1test(), 4)
        }
    }

    private fun loadDataIntoArray() {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM Results", null)

        val rowCount = cursor.count

        dataArray = Array(rowCount) { arrayOfNulls<String>(2) }

        if (cursor.moveToFirst()) {
            var rowIndex = 0
            do {
                val chapter = cursor.getString(cursor.getColumnIndexOrThrow("Chapter"))
                val result = cursor.getString(cursor.getColumnIndexOrThrow("Result"))
                dataArray[rowIndex][0] = chapter
                dataArray[rowIndex][1] = result
                rowIndex++
            } while (cursor.moveToNext())
        } else {
            Log.d("Results", "No data found in the Tests table.")
        }

        cursor.close()
        db.close()
    }

    private fun countTestsAboveThreshold() {
        testNumber = dataArray.count {
            it[1]?.toIntOrNull() ?: 0 >= 70
        }
    }

    private fun updateChapterPercentages() {
        for (i in dataArray.indices) {
            val chapter = dataArray[i][0]
            val result = dataArray[i][1]?.toIntOrNull()
            val percentage = result ?: 0

            when (chapter) {
                "chapter1" -> {
                    binding.intropercent.text = "$percentage%"
                    if (percentage >= 70) {
                        binding.introicon.setImageResource(R.drawable.testicon_done)
                        binding.blockchainlock.visibility = View.GONE
                        binding.blockchainpercent.visibility = View.VISIBLE
                    }
                }
                "chapter2" -> {
                    binding.blockchainpercent.text = "$percentage%"
                    if (percentage >= 70) {
                        binding.blockchainicon.setImageResource(R.drawable.testicon_done)
                        binding.bitcoinlock.visibility = View.GONE
                        binding.etherumlock.visibility = View.GONE

                        binding.bitcoinpercent.visibility = View.VISIBLE
                        binding.ethereumpercent.visibility = View.VISIBLE
                    }
                }
                "chapter3" -> {
                    binding.bitcoinpercent.text = "$percentage%"
                    if (percentage >= 70) {
                        binding.bitcoinicon.setImageResource(R.drawable.testicon_done)
                    }
                }
                "chapter4" -> {
                    binding.ethereumpercent.text = "$percentage%"
                    if (percentage >= 70) {
                        binding.ethereumicon.setImageResource(R.drawable.testicon_done)
                    }
                }
            }
        }
    }

    private fun updateProgressBar() {
        binding.progressBar.apply {
            max = 4
            progress = testNumber

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
