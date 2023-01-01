package com.example.luck_rent_android.ui.main.renter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.luck_rent_android.databinding.FragmentReferencesBinding


class ReferencesFragment : Fragment() {
   private var _binding : FragmentReferencesBinding? = null

    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentReferencesBinding.inflate(inflater, container, false)

        setSpinnerValue()

        return binding.root
    }

   private fun setSpinnerValue() {

//        _binding?.spType?.setItems(
//            "Load",
//            "Application",
//            "Tenant"
//        )
    }

}