package com.example.luck_rent_android.ui.main.applicationform

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.FragmentFourthBinding
import com.example.luck_rent_android.databinding.FragmentThirdBinding
import com.example.luck_rent_android.ui.main.adds.MyAds
import com.example.luck_rent_android.ui.main.property.PropertiesActivity

class FourthFragment : Fragment() {
    private var _binding: FragmentFourthBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentFourthBinding.inflate(inflater, container, false)

        binding?.btnSubmit?.setOnClickListener {
            val intent = Intent(context, MyAds::class.java)
            intent.putExtra("isFor","1")
            startActivity(intent)
        }

        return binding.root
    }

}