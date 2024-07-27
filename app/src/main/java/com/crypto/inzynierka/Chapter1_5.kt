package com.crypto.inzynierka

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.crypto.inzynierka.databinding.FragmentChapter15Binding

class Chapter1_5 : Fragment() {
    private var _binding: FragmentChapter15Binding? = null
    private val binding get() = _binding!!




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChapter15Binding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.QuizRight.setOnClickListener {
            if (binding.QuizRight.text != "Dalej") {
                binding.textView1.visibility = View.INVISIBLE
                binding.Title1.visibility = View.INVISIBLE

                binding.textView2.visibility = View.VISIBLE
                binding.Title2.visibility = View.VISIBLE

                binding.QuizLeft.visibility = View.VISIBLE

                binding.QuizRight.text = "Dalej"

                binding.QuizLeft.below(binding.textView2)
                binding.QuizRight.below(binding.textView2)

                binding.scrollView.scrollY = 0

            } else {
                replaceFragment(Chapter1_1())
            }
        }

        binding.QuizLeft.setOnClickListener {

            binding.textView1.visibility = View.VISIBLE
            binding.Title1.visibility = View.VISIBLE

            binding.textView2.visibility = View.INVISIBLE
            binding.Title2.visibility = View.INVISIBLE

            binding.QuizLeft.visibility = View.GONE
            binding.QuizRight.text = "Wady"

            binding.QuizLeft.below(binding.textView1)
            binding.QuizRight.below(binding.textView1)

            binding.scrollView.scrollY = 0
        }




        binding.scrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, 	oldScrollY ->

            val length = binding.scrollView.getChildAt(0).height - binding.scrollView.height

            binding.progressbar.apply {
                max = length
                progress = scrollY
            }
        }


    }
    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.center, fragment)
            .commit()
    }

    infix fun View.below(view: View) {
        (this.layoutParams as? RelativeLayout.LayoutParams)?.addRule(RelativeLayout.BELOW, view.id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}