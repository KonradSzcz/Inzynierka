package com.crypto.inzynierka

import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.crypto.inzynierka.databinding.FragmentChapter4Binding


class Chapter4 : Fragment() {
    private var _binding: FragmentChapter4Binding? = null
    private val binding get() = _binding!!

    private var CurrentChapter: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChapter4Binding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            CurrentChapter = it.getInt("CurrentChapter", 1)
        }

        if (CurrentChapter == 1) {
            binding.Title.text = Html.fromHtml(getString(R.string.chapter4_1_title), Html.FROM_HTML_MODE_COMPACT)
            binding.string.text = Html.fromHtml(getString(R.string.chapter4_1), Html.FROM_HTML_MODE_COMPACT)

            binding.Quiz.setOnClickListener {
                replaceFragment(Chapter4quiz(),1)
            }
        } else if (CurrentChapter == 2) {
            binding.Title.text = Html.fromHtml(getString(R.string.chapter4_2_title), Html.FROM_HTML_MODE_COMPACT)
            binding.string.text = Html.fromHtml(getString(R.string.chapter4_2), Html.FROM_HTML_MODE_COMPACT)

            binding.Quiz.setOnClickListener {
                replaceFragment(Chapter4quiz(),2)
            }
        } else if (CurrentChapter == 3) {
            binding.Title.text = Html.fromHtml(getString(R.string.chapter4_3_title), Html.FROM_HTML_MODE_COMPACT)
            binding.string.text = Html.fromHtml(getString(R.string.chapter4_3), Html.FROM_HTML_MODE_COMPACT)

            binding.Quiz.setOnClickListener {
                replaceFragment(Chapter4quiz(),3)
            }
        } else if (CurrentChapter == 4) {
            binding.Title.text = Html.fromHtml(getString(R.string.chapter4_4_title), Html.FROM_HTML_MODE_COMPACT)
            binding.string.text = Html.fromHtml(getString(R.string.chapter4_4), Html.FROM_HTML_MODE_COMPACT)

            binding.Quiz.setOnClickListener {
                replaceFragment(Chapter4quiz(),4)
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