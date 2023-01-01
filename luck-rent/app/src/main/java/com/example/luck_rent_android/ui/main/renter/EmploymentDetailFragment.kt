package com.example.luck_rent_android.ui.main.renter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.luck_rent_android.databinding.FragmentEmploymentDetailBinding


class EmploymentDetailFragment : Fragment() {
    private var _binding : FragmentEmploymentDetailBinding? = null

    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEmploymentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }


}