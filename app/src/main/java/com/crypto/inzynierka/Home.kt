package com.crypto.inzynierka

import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.crypto.inzynierka.databinding.FragmentHomeBinding

class Home : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val Vm by viewModels<MainViewModel>()

    private lateinit var dbHelper: DBConnection
    private lateinit var resultsArray: Array<Array<String?>> // Tablica na dane z tabeli Results
    private lateinit var testsArray: Array<Array<String?>> // Tablica na dane z tabeli Tests


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DBConnection(requireContext(), "cryptoDB", MainViewModel.DB_VERSION)

        loadResults()
        loadTests()

        unlockChapters()


        binding.introductionbutton.setOnClickListener {
            replaceFragment(Introduction())
        }
        binding.blockchainbutton.setOnClickListener {
            replaceFragment(Blockchain())
        }
        binding.bitcoinnbutton.setOnClickListener {
            replaceFragment(Bitcoin())
        }
        binding.ethereumbutton.setOnClickListener {
            replaceFragment(Ethereum())
        }
        binding.walletsbutton.setOnClickListener {
            replaceFragment(Wallets())
        }
        binding.dangerbutton.setOnClickListener {
            replaceFragment(Dangers())
        }
        binding.flashcardsbutton.setOnClickListener {
            replaceFragment(Flashcards())
        }
        binding.testsbutton.setOnClickListener {
            replaceFragment(Tests())
        }
    }

    private fun loadResults() {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM Results", null)

        val rowCount = cursor.count
        resultsArray = Array(rowCount) { arrayOfNulls<String>(2) }

        if (cursor.moveToFirst()) {
            var rowIndex = 0
            do {
                val chapter = cursor.getString(cursor.getColumnIndexOrThrow("Chapter"))
                val result = cursor.getString(cursor.getColumnIndexOrThrow("Result"))

                resultsArray[rowIndex][0] = chapter
                resultsArray[rowIndex][1] = result
                rowIndex++
                Log.d("Results TABLE", "Chapter $chapter, Result $result.")
            } while (cursor.moveToNext())
        } else {
            Log.d("Results - HOME", "No data found in the Results table.")
        }

        cursor.close()
        db.close()
    }

    private fun loadTests() {
        val db = dbHelper.readableDatabase

        // Tworzymy mapę do przechowywania liczby rekordów dla każdego chaptera
        val chapterCounts = mutableMapOf<String, Int>()

        val query = "SELECT Chapter, COUNT(*) as Count FROM Tests GROUP BY Chapter"
        val cursor: Cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val chapter = cursor.getString(cursor.getColumnIndexOrThrow("Chapter"))
                val count = cursor.getInt(cursor.getColumnIndexOrThrow("Count"))

                chapterCounts[chapter] = count
                Log.d("TestsTable", "Chapter=$chapter, Count=$count")
            } while (cursor.moveToNext())
        } else {
            Log.d("TestsTable", "No data found in the Tests table.")
        }

        cursor.close()
        db.close()

        // Przekazanie danych do paska postępu
        updateChapterProgressBars(chapterCounts)
    }





    private fun unlockChapters() {
        for (i in resultsArray.indices) {
            val chapter = resultsArray[i][0]
            val result = resultsArray[i][1]?.toIntOrNull() ?: 0

            when (chapter) {
                "chapter1" -> {
                    if (result >= 70) {
                        binding.blockchainlock.visibility = View.GONE
                        binding.blockchainpercent.visibility = View.VISIBLE
                    }
                }
                "chapter2" -> {
                    if (result >= 70) {
                        binding.bitcoinlock.visibility = View.GONE
                        binding.ethereumlock.visibility = View.GONE
                        binding.bitcoinpercent.visibility = View.VISIBLE
                        binding.ethereumpercent.visibility = View.VISIBLE
                    }
                }
                "chapter3" -> {
                    if (result >= 70) {
                        binding.walletslock.visibility = View.GONE
                        binding.dangerslock.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun updateChapterProgressBars(chapterCounts: Map<String, Int>) {
        chapterCounts.forEach { (chapter, count) ->
            val totalTests = when (chapter) {
                "chapter1" -> 8
                "chapter2" -> 8
                "chapter3" -> 9
                "chapter4" -> 4
                else -> 0
            }

            val progress = if (totalTests > 0) (count * 100) / totalTests else 0

            when (chapter) {
                "chapter1" -> {
                    binding.progressbar.progress = count
                    binding.intropercent.text = "$progress%"
                }
                "chapter2" -> {
                    binding.progressbar2.progress = count
                    binding.blockchainpercent.text = "$progress%"
                }
                "chapter3" -> {
                    binding.progressbar3.progress = count
                    binding.bitcoinpercent.text = "$progress%"
                }
                "chapter4" -> {
                    binding.progressbar4.progress = count
                    binding.ethereumpercent.text = "$progress%"
                }
            }
        }
    }




    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.center, fragment)
            .addToBackStack(null)
            .commit()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
