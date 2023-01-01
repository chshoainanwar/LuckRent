package com.example.luck_rent_android.ui.main.expense.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.AddExpenseLayoutOneBinding
import com.example.luck_rent_android.databinding.FragmentAddExpenseTwoBinding

class AddExpenseFragmentTwo : Fragment() {

    private var binding: FragmentAddExpenseTwoBinding? = null
    val _binding get() = binding!!
    private var transactionNature: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddExpenseTwoBinding.inflate(inflater, container, false)


        binding?.btnStatusYes?.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                transactionNature = "yes"
                changeTransactionColorState()
            }
        }

        binding?.btnStatusNo?.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                transactionNature = "no"
                changeTransactionColorState()
            }
        }
        binding?.btnTaxStatusYes?.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                transactionNature = "nonDeduct"
                changeTransactionColorState()
            }
        }
        binding?.btnTaxStatusNo?.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                transactionNature = "deduct"
                changeTransactionColorState()
            }
        }
        binding?.btnRentInvoiceYes?.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                transactionNature = "yes1"
                changeTransactionColorState()
            }
        }
        binding?.btnRentInvoiceNo?.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                transactionNature = "no1"
                changeTransactionColorState()
            }
        }


        return _binding?.root
    }


    override fun onResume() {
        super.onResume()
        binding?.spPaid?.setItems("Yes", "No")
        setExpenseSubCategory()
    }

    private fun setExpenseSubCategory() {
        when (AddExpenseFragmentOne.expenseCategory) {
            "0" -> {
                binding?.spExpenseCategory?.setItems(
                    "Advertising",
                    "Background & Credit Checks",
                    "Travel",
                    "Mileage",
                    "Meals",
                    "Office Supplies & Postage",
                    "Software Subscriptions",
                    "HOA Dues",
                    "Bank Fees",
                    "Education",
                    "Gifts",
                    "Licenses",
                    "Rent Concessions"
                )
            }
            "1" -> {
                binding?.spExpenseCategory?.setItems(
                    "New Roof",
                    "New Acquisitions",
                    "New Appliances",
                    "New HVAC",
                    "New Flooring & Carpet",
                    "New Doors & Windows",
                    "New Landscaping",
                    "New Plumbing & Electrical",
                    "New Furniture & Equipment",
                    "Remodeling",
                    "Closing Costs",
                    "Loan Costs"
                )
            }
            "2" -> {
                binding?.spExpenseCategory?.setItems(
                    "Rental Dwelling", "Rental Condo",
                    "Flood", "Hurricane",
                    "Earthquake",
                    "Liability",
                    "Umbrella"
                )
            }
            "3" -> {

            }
            "4" -> {
                binding?.spExpenseCategory?.setItems(
                    "Legal",
                    "Accounting",
                    "Court Fees",
                    "Eviction Fees",
                    "Inspections",
                    "Surveys",
                    "Appraisals"
                )
            }
            "5" -> {
                binding?.spExpenseCategory?.setItems(
                    "Property Management",
                    "Service Calls",
                    "Leasing Commissions",
                    "Booking & Platform Fees",
                    "Fund Management"
                )
            }
            "6" -> {
                binding?.spExpenseCategory?.setItems(
                    "Mortgage Payment",
                    "Mortgage Interest",
                    "Mortgage Principal",
                    "Proceeds & Payoffs",
                    "Other Loan Payment",
                    "Other Interest",
                    "Other Principal",
                    "PMI"
                )
            }
            "7" -> {
                binding?.spExpenseCategory?.setItems("Others")
            }
            "8" -> {
                binding?.spExpenseCategory?.setItems(
                    "Cleaning & Janitorial",
                    "Painting",
                    "Electrical Repairs",
                    "Plumbing Repairs",
                    "HVAC Repairs",
                    "Appliance Repairs",
                    "Roof Repairs",
                    "Doors & Windows Repairs",
                    "Other Repairs",
                    "Security, Locks & Keys",
                    "Pest",
                    "Gardening & Landscaping",
                    "Pool & Spa",
                    "Snow Removal",
                    "R&M Supplies",
                    "R&M Permits & Inspections",
                    "Labor",
                    "Linens, Soaps, Other Consum.. "
                )
            }
            "9" -> {
                binding?.spExpenseCategory?.setItems("Security Deposit Interest")
            }
            "10" -> {
                binding?.spExpenseCategory?.setItems(
                    "Property Taxes",
                    "Special Assessments",
                    "City, State, & Local Taxes",
                    "Short Term Occupancy Taxes",
                    "Federal Taxes",
                    "Tax Licenses & Registrations"
                )
            }
            "11" -> {
                binding?.spExpenseCategory?.setItems(
                    "Gas",
                    "Electric",
                    "Gas & Electric",
                    "Garbage & Recycling",
                    "Telephone, Cable & Internet",
                    "Water & Sewer",
                    "Heating Oil"
                )
            }
        }
    }



    private fun changeTransactionColorState() {
        when (transactionNature) {
            "yes" -> {
                binding?.btnRentInvoiceYes?.buttonTintList = requireActivity()?.getColorStateList(R.color.darkBlue)
                binding?.btnRentInvoiceNo?.buttonTintList = requireActivity()?.getColorStateList(R.color.sand)
            }
            "no" -> {
                binding?.btnRentInvoiceYes?.buttonTintList = requireActivity()?.getColorStateList(R.color.sand)
                binding?.btnRentInvoiceNo?.buttonTintList = requireActivity()?.getColorStateList(R.color.darkBlue)
            } "nonDeduct" -> {
                binding?.btnTaxStatusYes?.buttonTintList = requireActivity()?.getColorStateList(R.color.sand)
                binding?.btnTaxStatusNo?.buttonTintList = requireActivity()?.getColorStateList(R.color.darkBlue)
            }"deduct" -> {
                binding?.btnTaxStatusYes?.buttonTintList = requireActivity()?.getColorStateList(R.color.sand)
                binding?.btnTaxStatusNo?.buttonTintList = requireActivity()?.getColorStateList(R.color.darkBlue)
            }"yes1" -> {
                binding?.btnStatusYes?.buttonTintList = requireActivity()?.getColorStateList(R.color.sand)
                binding?.btnStatusNo?.buttonTintList = requireActivity()?.getColorStateList(R.color.darkBlue)
            }"no1" -> {
                binding?.btnStatusYes?.buttonTintList = requireActivity()?.getColorStateList(R.color.sand)
                binding?.btnStatusNo?.buttonTintList = requireActivity()?.getColorStateList(R.color.darkBlue)
            }
        }
    }
}