package com.crypto.inzynierka

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.crypto.inzynierka.databinding.FragmentProfileBinding

class Profile : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val Vm by viewModels<MainViewModel>()

    private lateinit var dbHelper: DBConnection
    private var testNumber: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DBConnection(requireContext(), "cryptoDB", MainViewModel.DB_VERSION)

        loadUsername()
        loadResultFromDatabase()
        countTests()
        updateProgressBar()
    }

    private fun loadUsername() {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT Username FROM Username WHERE flag = 1",
            null
        )

        if (cursor.moveToFirst()) {
            val username = cursor.getString(0)
            binding.nick.text = username
        }
        cursor.close()
        db.close()
    }

    private fun loadResultFromDatabase() {
        val db = dbHelper.readableDatabase
        val resultIds = arrayOf("1", "2", "3", "4") // ID sprawdzianów w bazie
        val resultViews = arrayOf(binding.percent1, binding.percent2, binding.percent3, binding.percent4)
        val iconViews = arrayOf(binding.introicon, binding.blockchainicon, binding.bitcoinicon, binding.ethereumicon)

        for ((index, id) in resultIds.withIndex()) {
            val cursor: Cursor = db.rawQuery("SELECT Result FROM Results WHERE ID = ?", arrayOf(id))
            if (cursor.moveToFirst()) {
                val result = cursor.getString(0)
                resultViews[index].text = "$result%"

                if (result.toInt() >= 70) {
                    resultViews[index].setTextColor(resources.getColor(R.color.green, null))
                    iconViews[index].setImageResource(R.drawable.testicon_done)
                }
            } else {
                resultViews[index].text = "Brak"
            }
            cursor.close()
        }
        db.close()
    }


    private fun countTests() {
        val db = dbHelper.readableDatabase

        val cursor: Cursor = db.rawQuery("SELECT COUNT(DISTINCT Chapter) FROM Results WHERE Result >= 70", null)

        if (cursor.moveToFirst()) {
            testNumber = cursor.getInt(0)

            cursor.close()
            db.close()
        }
    }

    private fun updateProgressBar() {
        binding.progressBar.apply {
            max = 4
            progress = testNumber
            scaleX = -1f
            scaleY = -1f
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
