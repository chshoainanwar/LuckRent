package com.example.luck_rent_android.ui.main.renter

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.luck_rent_android.databinding.FragmentEmergencyContactBinding
import com.example.luck_rent_android.ui.main.adds.MyAds
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.profile.Profile.Companion.roleProfile
import com.kodextech.project.kodexlib.utils.visible


class EmergencyContactFragment : Fragment() {
    private var _binding : FragmentEmergencyContactBinding? = null

    val binding get() =  _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentEmergencyContactBinding.inflate(inflater, container, false)

        when(roleProfile){
            "Renter" ->{
                binding?.layoutButton1?.visible()
                binding?.btnSubmitApplication?.setOnClickListener {
                    val intent = Intent(context, MyAds::class.java)
                    startActivity(intent)
                }
            }
        }

        return binding.root
    }


}