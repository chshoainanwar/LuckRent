package com.example.luck_rent_android.ui.main.renter

import android.app.Application
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.luck_rent_android.databinding.FragmentRentPaymentBinding
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.property.PropertiesActivity
import java.util.*


class RentPaymentFragment : Fragment() {

    private var _binding: FragmentRentPaymentBinding? = null

    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRentPaymentBinding.inflate(inflater, container, false)

        setSpinnerValue()

        binding?.saveBtn?.setOnClickListener {
            val intent = Intent(context, PropertiesActivity::class.java)
            intent.putExtra("isFor","1")
            startActivity(intent)
        }

        binding?.rlReminder?.setOnClickListener {
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
                        binding?.tvDatePicker?.text = "$year-$monthi-$day"
                    } else {
                        binding?.tvDatePicker?.text = "$year-0$monthi-$day"

                    }

                }, year, month, day
            )
//            dpd.datePicker.minDate = System.currentTimeMillis()
            dpd.show()
        }

        return binding.root
    }

    private fun setSpinnerValue() {
        _binding?.spPaymentMethod?.setItems(
            "Cheque",
            "Cash",
            "E-Transfer",
            "PAD",
            "Wire",
            "Others"
        )
        _binding?.spRemindCheque?.setItems(
            "15 days earlier",
            "30 days earlier",
            "45 days earlier"
        )

    }

}