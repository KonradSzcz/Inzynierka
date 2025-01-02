package com.crypto.inzynierka

import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.crypto.inzynierka.databinding.FragmentChapter6Binding


class Chapter6 : Fragment() {
    private var _binding: FragmentChapter6Binding? = null
    private val binding get() = _binding!!
    private lateinit var dbHelper: DBConnection

    private var CurrentChapter: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChapter6Binding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper = DBConnection(requireContext(), "cryptoDB", MainViewModel.DB_VERSION)

        arguments?.let {
            CurrentChapter = it.getInt("CurrentChapter", 1)
        }

        updateChapterStatus(CurrentChapter)

        if (CurrentChapter == 1) {
            binding.Title.text = Html.fromHtml(getString(R.string.chapter6_1_title), Html.FROM_HTML_MODE_COMPACT)
            binding.string.text = Html.fromHtml(getString(R.string.chapter6_1), Html.FROM_HTML_MODE_COMPACT)

            binding.Quiz.setOnClickListener {
                replaceFragment(Chapter6(),2)
            }
        } else if (CurrentChapter == 2) {
            binding.Title.text = Html.fromHtml(getString(R.string.chapter6_2_title), Html.FROM_HTML_MODE_COMPACT)
            binding.string.text = Html.fromHtml(getString(R.string.chapter6_2), Html.FROM_HTML_MODE_COMPACT)

            binding.Quiz.setOnClickListener {
                replaceFragment(Chapter6(),3)
            }
        } else if (CurrentChapter == 3) {
            binding.Title.text = Html.fromHtml(getString(R.string.chapter6_3_title), Html.FROM_HTML_MODE_COMPACT)
            binding.string.text = Html.fromHtml(getString(R.string.chapter6_3), Html.FROM_HTML_MODE_COMPACT)

            binding.Quiz.setOnClickListener {
                replaceFragment(Chapter6(),4)
            }
        } else if (CurrentChapter == 4) {
            binding.Title.text = Html.fromHtml(getString(R.string.chapter6_4_title), Html.FROM_HTML_MODE_COMPACT)
            binding.string.text = Html.fromHtml(getString(R.string.chapter6_4), Html.FROM_HTML_MODE_COMPACT)

            binding.Quiz.setOnClickListener {
                replaceFragment(Chapter6(),5)
            }
        } else if (CurrentChapter == 5) {
            binding.Title.text = Html.fromHtml(getString(R.string.chapter6_5_title), Html.FROM_HTML_MODE_COMPACT)
            binding.string.text = Html.fromHtml(getString(R.string.chapter6_5), Html.FROM_HTML_MODE_COMPACT)

            binding.Quiz.setOnClickListener {
                replaceFragment(Chapter6(),6)
            }
        } else if (CurrentChapter == 6) {
            binding.Title.text = Html.fromHtml(getString(R.string.chapter6_6_title), Html.FROM_HTML_MODE_COMPACT)
            binding.string.text = Html.fromHtml(getString(R.string.chapter6_6), Html.FROM_HTML_MODE_COMPACT)

            binding.Quiz.setOnClickListener {
                replaceFragment(Dangers(),0)
            }
        }

        binding.scrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, 	oldScrollY ->
            val length = binding.scrollView.getChildAt(0).height - binding.scrollView.height
            binding.progressbar.apply {
                max = length
                progress = scrollY
            }
        }
    }

    private fun updateChapterStatus(chapter: Int) {
        val db = DBConnection(requireContext(), "cryptoDB", MainViewModel.DB_VERSION).writableDatabase
        db.execSQL("UPDATE Dangers SET isChecked = 1 WHERE Chapter = 'chapter$chapter'")
        db.close()
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