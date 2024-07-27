package com.crypto.inzynierka

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.crypto.inzynierka.databinding.FragmentIntroductionBinding


class Introduction : Fragment() {
    private var _binding: FragmentIntroductionBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIntroductionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rozdzial1.setOnClickListener {
            replaceFragment(Chapter1_1())
        }
        binding.rozdzial2.setOnClickListener{
            replaceFragment(Chapter1_2())
        }
        binding.rozdzial3.setOnClickListener{
            replaceFragment(Chapter1_3())
        }
        binding.rozdzial4.setOnClickListener{
            replaceFragment(Chapter1_4())
        }
        binding.rozdzial5.setOnClickListener{
            replaceFragment(Chapter1_5())
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