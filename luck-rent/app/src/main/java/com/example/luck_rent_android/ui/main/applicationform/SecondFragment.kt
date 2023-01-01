package com.example.luck_rent_android.ui.main.applicationform

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.FragmentFirstBinding
import com.example.luck_rent_android.databinding.FragmentSecondBinding
import java.io.IOException
import java.util.*

class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    val binding get() = _binding!!
    private val PICK_IMAGE = 222
    var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentSecondBinding.inflate(inflater, container, false)

        binding?.rlBirthLayout?.setOnClickListener {
            setDate()
        }
        binding?.pickImg?.setOnClickListener{
            pickIdCardImage()
        }


        return binding.root
    }

    private fun setDate(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { view, year, month, day ->

                // Display Selected date in TextView
                val monthi = month.plus(1).toString()
                if (monthi.toInt() > 9) {
                    binding?.tvBirthDatePicker?.text = "$year-$monthi-$day"
                } else {
                    binding?.tvBirthDatePicker?.text = "$year-0$monthi-$day"

                }

            }, year, month, day
        )
//            dpd.datePicker.minDate = System.currentTimeMillis()
        dpd.show()
    }
    private fun pickIdCardImage(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), PICK_IMAGE)
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