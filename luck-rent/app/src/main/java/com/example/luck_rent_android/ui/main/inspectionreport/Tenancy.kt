package com.example.luck_rent_android.ui.main.inspectionreport

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityTenancyBinding
import com.example.luck_rent_android.ui.main.inspectionreport.InspectionReport.Companion.roleStatus
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import okhttp3.internal.userAgent

class Tenancy : BaseActivity() {
    private var binding: ActivityTenancyBinding? = null
    var isSelected: String? = null

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tenancy)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))

        SetTopBar()

        if (roleStatus == "During-Tenancy"){
            binding?.tvEndTenancy?.text = "During Tenancy"
        }

        binding?.topBar?.ivAdd?.setOnClickListener {
            onBackPressed()
        }

        binding?.firstAgreeRB?.setOnClickListener {
            isSelected = "agree1"
            checkColorState(isSelected!!)
        }
        binding?.firstDisagreeRB?.setOnClickListener {
            isSelected = "disAgree1"
            checkColorState(isSelected!!)
        }
        binding?.secondAgreeRB?.setOnClickListener {
            isSelected = "agree2"
            checkColorState(isSelected!!)
        }
        binding?.secondDisagreeRB?.setOnClickListener {
            isSelected = "disAgree2"
            checkColorState(isSelected!!)
        }

        binding?.btnNext?.setOnClickListener{
            val intent = Intent(this,Tenant::class.java)
            startActivity(intent)
        }

    }

    private fun checkColorState(isSelected : String) {
        when (isSelected) {
            "agree1" ->{

                binding?.firstAgreeRB?.buttonTintList = getColorStateList(R.color.darkBlue)
                binding?.firstDisagreeRB?.buttonTintList = getColorStateList(R.color.hardSand)


            }
            "disAgree1" ->{
                binding?.firstDisagreeRB?.buttonTintList = getColorStateList(R.color.darkBlue)
                binding?.firstAgreeRB?.buttonTintList = getColorStateList(R.color.hardSand)
            }
            "agree2" ->{
                binding?.secondAgreeRB?.buttonTintList = getColorStateList(R.color.darkBlue)
                binding?.secondDisagreeRB?.buttonTintList = getColorStateList(R.color.hardSand)
            }
            "disAgree2" ->{
                binding?.secondDisagreeRB?.buttonTintList = getColorStateList(R.color.darkBlue)
                binding?.secondAgreeRB?.buttonTintList = getColorStateList(R.color.hardSand)
            }

        }
    }

    private fun SetTopBar() {
        binding?.topBar?.rlSearch?.gone()
        binding?.topBar?.rlCounter?.gone()
        binding?.topBar?.ivMenu?.gone()
        binding?.topBar?.ivAdd?.setImageResource(R.drawable.ic_back)
        binding?.topBar?.tvText?.text = "Inspection Report"
    }

    override fun onSetupViewGroup() {
        mViewGroup = binding?.content
    }

    override fun setupContentViewWithBinding() {

    }

    override fun onRecycleBeforeDestroy() {

    }

    override fun onBecameInvisibleToUser() {

    }

    override fun onBecameVisibleToUser() {

    }

    override fun setupLoader() {

    }
}