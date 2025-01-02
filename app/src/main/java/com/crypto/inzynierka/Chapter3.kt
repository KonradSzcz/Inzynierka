package com.crypto.inzynierka

import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.crypto.inzynierka.databinding.FragmentChapter3Binding

class Chapter3 : Fragment() {
    private var _binding: FragmentChapter3Binding? = null
    private val binding get() = _binding!!

    private var CurrentChapter: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChapter3Binding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            CurrentChapter = it.getInt("CurrentChapter", 1)
        }

        if (CurrentChapter == 1) {
            binding.Title.text = Html.fromHtml(getString(R.string.chapter3_1_title), Html.FROM_HTML_MODE_COMPACT)
            binding.string.text = Html.fromHtml(getString(R.string.chapter3_1), Html.FROM_HTML_MODE_COMPACT)

            binding.Quiz.setOnClickListener {
                replaceFragment(Chapter3quiz(),1)
            }
        } else if (CurrentChapter == 2) {
            binding.Title.text = Html.fromHtml(getString(R.string.chapter3_2_title), Html.FROM_HTML_MODE_COMPACT)
            binding.string.text = Html.fromHtml(getString(R.string.chapter3_2), Html.FROM_HTML_MODE_COMPACT)

            binding.Quiz.setOnClickListener {
                replaceFragment(Chapter3quiz(),2)
            }
        } else if (CurrentChapter == 3) {
            binding.Title.text = Html.fromHtml(getString(R.string.chapter3_3_title), Html.FROM_HTML_MODE_COMPACT)
            binding.string.text = Html.fromHtml(getString(R.string.chapter3_3), Html.FROM_HTML_MODE_COMPACT)

            binding.Quiz.setOnClickListener {
                replaceFragment(Chapter3quiz(),3)
            }
        } else if (CurrentChapter == 4) {
            binding.Title.text = Html.fromHtml(getString(R.string.chapter3_4_title), Html.FROM_HTML_MODE_COMPACT)
            binding.string.text = Html.fromHtml(getString(R.string.chapter3_4), Html.FROM_HTML_MODE_COMPACT)

            binding.Quiz.setOnClickListener {
                replaceFragment(Chapter3quiz(),4)
            }
        } else if (CurrentChapter == 5) {
            binding.Title.text = Html.fromHtml(getString(R.string.chapter3_5_title), Html.FROM_HTML_MODE_COMPACT)
            binding.string.text = Html.fromHtml(getString(R.string.chapter3_5), Html.FROM_HTML_MODE_COMPACT)

            binding.Quiz.setOnClickListener {
                replaceFragment(Chapter3quiz(),5)
            }
        } else if (CurrentChapter == 6) {
            binding.Title.text = Html.fromHtml(getString(R.string.chapter3_6_title), Html.FROM_HTML_MODE_COMPACT)
            binding.string.text = Html.fromHtml(getString(R.string.chapter3_6), Html.FROM_HTML_MODE_COMPACT)

            binding.Quiz.setOnClickListener {
                replaceFragment(Chapter3quiz(),6)
            }
        } else if (CurrentChapter == 7) {
            binding.Title.text = Html.fromHtml(getString(R.string.chapter3_7_title), Html.FROM_HTML_MODE_COMPACT)
            binding.string.text = Html.fromHtml(getString(R.string.chapter3_7), Html.FROM_HTML_MODE_COMPACT)

            binding.Quiz.setOnClickListener {
                replaceFragment(Chapter3quiz(),7)
            }
        } else if (CurrentChapter == 8) {
            binding.Title.text = Html.fromHtml(getString(R.string.chapter3_8_title), Html.FROM_HTML_MODE_COMPACT)
            binding.string.text = Html.fromHtml(getString(R.string.chapter3_8), Html.FROM_HTML_MODE_COMPACT)

            binding.Quiz.setOnClickListener {
                replaceFragment(Chapter3quiz(),8)
            }
        }else if (CurrentChapter == 9) {
            binding.Title.text = Html.fromHtml(getString(R.string.chapter3_9_title), Html.FROM_HTML_MODE_COMPACT)
            binding.string.text = Html.fromHtml(getString(R.string.chapter3_9), Html.FROM_HTML_MODE_COMPACT)

            binding.Quiz.setOnClickListener {
                replaceFragment(Chapter3quiz(),9)
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