package com.example.luck_rent_android.ui.main.renter

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.luck_rent_android.databinding.FragmentLeaseBinding
import java.text.SimpleDateFormat
import java.util.*


class LeaseFragment : Fragment() {

 private var _binding : FragmentLeaseBinding? = null

 val  binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLeaseBinding.inflate(inflater, container, false)

        setSpinnerValue()

        _binding?.tvStartDate?.setOnClickListener{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(requireContext(),
                DatePickerDialog.OnDateSetListener { view, year, month, day ->

                    // Display Selected date in TextView
                    val monthi = month.plus(1).toString()
                    if (monthi.toInt() > 9) {
                        binding?.tvStartDate?.text = "$year-$monthi-$day"
                    }else{
                        binding?.tvStartDate?.text = "$year-0$monthi-$day"

                    }

                }, year, month, day
            )
//            dpd.datePicker.minDate = System.currentTimeMillis()
            dpd.show()
        }

        _binding?.tvEndDate?.setOnClickListener{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(requireContext(),
                DatePickerDialog.OnDateSetListener { view, year, month, day ->

                    // Display Selected date in TextView
                    val monthi = month.plus(1).toString()
                    if (monthi.toInt() > 9) {
                        binding?.tvEndDate?.text = "$year-$monthi-$day"
                    }else{
                        binding?.tvEndDate?.text = "$year-0$monthi-$day"

                    }

                }, year, month, day
            )
//            dpd.datePicker.minDate = System.currentTimeMillis()
            dpd.show()


        }

        _binding?.spRentDue?.setItems(
            "1st",
            "2nd",
            "3rd",
            "4th",
            "5th",
            "6th",
            "7th",
            "8th",
            "9th",
            "10th",
            "11th",
            "12th",
            "13th",
            "14th",
            "15th",
            "16th",
            "17th",
            "18th",
            "19th",
            "20th",
            "21st",
            "22nd",
            "23rd",
            "24th",
            "25th",
            "26th",
            "27th",
            "28th",
            "29th",
            "30th",
            "31st"
        )

        return binding.root
    }

    private fun setSpinnerValue() {

//        _binding?.spRentDue?.setItems(
//            "1st",
//            "2nd",
//            "3rd",
//            "4th",
//            "5th"
//        )
        _binding?.spRenewal?.setItems(
            "30 days",
            "60 days",
            "90 days",
            "120 days"
        )
        _binding?.spRentIncrease?.setItems(
            "60 days",
            "90 days",
            "120 days",
            "150 days"
        )
        _binding?.spRentOverdue?.setItems(
            "1 day",
            "2 days",
            "3 days",
            "4 days",
            "5 days"
        )
    }

}