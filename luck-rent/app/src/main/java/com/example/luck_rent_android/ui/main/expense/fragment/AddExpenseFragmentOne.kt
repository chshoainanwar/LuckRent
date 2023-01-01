package com.example.luck_rent_android.ui.main.expense.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.recyclerview.widget.GridLayoutManager
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.AcitivitySignupBinding.inflate
import com.example.luck_rent_android.databinding.AddExpenseLayoutOneBinding
import com.example.luck_rent_android.databinding.FragmentBasicInfoBinding
import com.example.luck_rent_android.ui.main.expense.AddExpense
import com.example.luck_rent_android.ui.main.expense.adapter.ImageUploadAdapter
import com.kodextech.project.kodexlib.base.BaseActivity
import droidninja.filepicker.FilePickerBuilder
import java.util.*

class AddExpenseFragmentOne : Fragment() {

    private var binding: AddExpenseLayoutOneBinding? = null
    val _binding get() = binding!!
    private var taxNature: String? = null

    private var transactionNature: String? = null
    private var mImageAdapter: ImageUploadAdapter? = null
    private val IMAGE_REQUEST_CODE = 2212
    var context: BaseActivity? = null


    companion object {
        var expenseCategory: String? = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = AddExpenseLayoutOneBinding.inflate(inflater, container, false)


        setValues()
        setImageAdapter()
        changeTransactionColorState()
        binding?.tvTransactionDate?.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(requireContext(),
                DatePickerDialog.OnDateSetListener { view, year, month, day ->

                    // Display Selected date in TextView
                    val monthi = month.plus(1).toString()
                    if (monthi.toInt() > 9) {
                        binding?.tvTransactionDate?.text = "$year-$monthi-$day"
                    }else{
                        binding?.tvTransactionDate?.text = "$year-0$monthi-$day"

                    }

                }, year, month, day
            )
//            dpd.datePicker.minDate = System.currentTimeMillis()
            dpd.show()
        }

        binding?.btnYes?.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                transactionNature = "yes"
                changeTransactionColorState()
            }
        }

        binding?.btnNo?.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                transactionNature = "no"
                changeTransactionColorState()
            }
        }
        binding?.btnNonTax?.setOnClickListener {
            taxNature = "non_payable"
            changeTaxeColorState()
        }

        binding?.btnTaxable?.setOnClickListener {
            taxNature = "payable"
            changeTaxeColorState()
        }


        return _binding?.root
    }

    private fun setImageAdapter() {
        mImageAdapter =
            ImageUploadAdapter(requireContext(), AddExpense.mData) { position, forDelete ->
                if (forDelete) {
                    if (position == null) {
                        Toast.makeText(
                            requireContext(),
                            "System HAving Some Issue",
                            Toast.LENGTH_LONG
                        ).show()

                    } else {
//                        deleteImage(position)
                    }
                } else {
                    FilePickerBuilder.instance
                        .setMaxCount(5)
                        .setActivityTheme(R.style.LibAppTheme) //optional
                        .pickPhoto(this, IMAGE_REQUEST_CODE)


                }
                mImageAdapter?.notifyDataSetChanged()
            }

        binding?.rvImage?.layoutManager =
            GridLayoutManager(requireContext(), 3)
        binding?.rvImage?.adapter = mImageAdapter
        mImageAdapter?.notifyDataSetChanged()

    }

    override fun onResume() {
        super.onResume()
    }

    private fun setValues() {
        binding?.spProperty?.setItems("Property A", "Property B", "Property C")
        binding?.spExpenseCategory?.setItems(
            "Admin & Other",
            "Capital Expense",
            "Insurance",
            "Legal & Professional",
            "Management Fess",
            "Mortgages& Loans",
            "Others",
            "Repairs & Maintenance",
            "Security Deposits",
            "Taxes",
            "Utilities"
        )
        binding?.spRepeat?.setItems(
            "Never",
            "Every Day",
            "Every Week",
            "Every Month",
            "Every Year"
        )
        binding?.spExpenseCategory?.setOnItemSelectedListener { view, position, id, item ->
            expenseCategory = position.toString()
        }
    }

    private fun changeTransactionColorState() {
        when (transactionNature) {
            "yes" -> {
                binding?.btnYes?.buttonTintList = requireActivity()?.getColorStateList(R.color.darkBlue)
                binding?.btnNo?.buttonTintList = requireActivity()?.getColorStateList(R.color.sand)
            }
            "no" -> {
                binding?.btnYes?.buttonTintList = requireActivity()?.getColorStateList(R.color.sand)
                binding?.btnNo?.buttonTintList = requireActivity()?.getColorStateList(R.color.darkBlue)
            }
        }
    }
    private fun changeTaxeColorState() {
        when (taxNature) {
            "payable" -> {
                binding?.btnTaxable?.buttonTintList = requireActivity().getColorStateList(R.color.darkBlue)
                binding?.btnNonTax?.buttonTintList = requireActivity().getColorStateList(R.color.sand)
            }
            "non_payable" -> {
                binding?.btnTaxable?.buttonTintList = requireActivity().getColorStateList(R.color.sand)
                binding?.btnNonTax?.buttonTintList = requireActivity().getColorStateList(R.color.darkBlue)
            }
        }
    }

}