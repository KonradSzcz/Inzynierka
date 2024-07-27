package com.crypto.inzynierka

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.crypto.inzynierka.databinding.FragmentChapter13Binding

class Chapter1_3 : Fragment() {
    private var _binding: FragmentChapter13Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChapter13Binding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.Quiz.setOnClickListener {
            replaceFragment(Chapter1_3game())
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}