package com.example.luck_rent_android.ui.main.inspectionreport

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityTenantBinding
import com.example.luck_rent_android.ui.main.inspectionreport.InspectionReport.Companion.roleStatus
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import java.util.*

class Tenant : BaseActivity() {
    private var binding : ActivityTenantBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_tenant)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))

        SetTopBar()

        if (roleStatus == "During-Tenancy"){
            binding?.layout2?.gone()
            binding?.layout3?.gone()
        }

        binding?.topBar?.ivAdd?.setOnClickListener{
            onBackPressed()
        }

        binding?.tvDateLandlord?.setOnClickListener {

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, month, day ->

                    // Display Selected date in TextView
                    val monthi = month.plus(1).toString()
                    if (monthi.toInt() > 9) {
                        binding?.tvDateLandlord?.text = "$year-$monthi-$day"
                    }else{
                        binding?.tvDateLandlord ?.text = "$year-0$monthi-$day"

                    }

                }, year, month, day
            )
//            dpd.datePicker.minDate = System.currentTimeMillis()
            dpd.show()
        }
        binding?.tvDateTenant?.setOnClickListener {

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, month, day ->

                    // Display Selected date in TextView
                    val monthi = month.plus(1).toString()
                    if (monthi.toInt() > 9) {
                        binding?.tvDateTenant?.text = "$year-$monthi-$day"
                    }else{
                        binding?.tvDateTenant ?.text = "$year-0$monthi-$day"

                    }

                }, year, month, day
            )
//            dpd.datePicker.minDate = System.currentTimeMillis()
            dpd.show()
        }
    }

    private fun SetTopBar(){
        binding?.topBar?.rlSearch?.gone()
        binding?.topBar?.ivMenu?.gone()
        binding?.topBar?.rlCounter?.gone()
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