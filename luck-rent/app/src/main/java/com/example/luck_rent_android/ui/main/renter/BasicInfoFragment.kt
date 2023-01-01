package com.example.luck_rent_android.ui.main.renter

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.luck_rent_android.databinding.FragmentBasicInfoBinding
import com.example.luck_rent_android.ui.main.profile.Profile.Companion.roleProfile
import com.kodextech.project.kodexlib.utils.gone
import com.kodextech.project.kodexlib.utils.visible
import java.io.IOException
import java.util.*

class BasicInfoFragment : Fragment() {
    private var _binding: FragmentBasicInfoBinding? = null
    val binding get() = _binding!!
    private val PICK_IMAGE = 222
    var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBasicInfoBinding.inflate(inflater, container, false)

        when(roleProfile){
            "Landlord" ->{
                binding?.rlAutomatic?.visible()
            }
            "Property Manager" ->{
                binding?.rlAutomatic?.visible()
            }
            "Renter" ->{
                binding?.rlAutomatic?.gone()
            }
        }

        _binding?.pickImg?.setOnClickListener{
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), PICK_IMAGE)
        }



        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSpinnerValue()
        Log.d("Coma BUF","Coma BUF ${view.tag}")
    }

    private fun setSpinnerValue() {
        _binding?.spType?.setItems(
            "Lead",
            "Applicant",
            "Tenant"
        )
    }

    override fun onResume() {
        super.onResume()
        setSpinnerValue()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data!!.data != null) {
            imageUri = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, imageUri)
                _binding?.pickImg?.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}