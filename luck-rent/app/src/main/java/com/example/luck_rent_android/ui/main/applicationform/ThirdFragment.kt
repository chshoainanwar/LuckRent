package com.example.luck_rent_android.ui.main.applicationform

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.FragmentSecondBinding
import com.example.luck_rent_android.databinding.FragmentThirdBinding

class ThirdFragment : Fragment() {
    private var _binding: FragmentThirdBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentThirdBinding.inflate(inflater, container, false)

        return binding.root
    }
}