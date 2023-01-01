package com.example.luck_rent_android.ui.main.profile

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityProfileBinding
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.visible

class Profile : BaseActivity() {
    private var binding: ActivityProfileBinding? = null
    private var enumState: ItemSelection = ItemSelection.RENTER

    var landlord: String? = null
    var propertyManager: String? = null
    var conractor: String? = null
    var renter: String? = null

    companion object {
        var roleProfile: String? = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        statusBarColor(getColor(R.color.darkBlue))
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        super.onCreate(savedInstanceState)

        checkProfileState()
        binding?.easyToggle?.toggleImmediately(binding?.easyToggle?.STATE_ON ?: 1)

        when (roleProfile) {
            "Property Manager" -> {
                binding?.rlCoManage?.visible()
            }
        }

        binding?.ivEditProfile?.setOnClickListener {
            val intent = Intent(this, EditProfile::class.java)
            startActivity(intent)
        }
        binding?.ivBack?.setOnClickListener {
            onBackPressed()
        }

        binding?.landlordLayout?.setOnClickListener {
            enumState = ItemSelection.LANDLORD
            checkProfileState()
            landlord = "Landlord"
            roleProfile = "Landlord"
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
            finish()
        }
        binding?.propertyLayout?.setOnClickListener {
            enumState = ItemSelection.PROPERTY
            checkProfileState()

            roleProfile = "Property Manager"
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
            finish()
        }
        binding?.renterLayout?.setOnClickListener {
            enumState = ItemSelection.RENTER
            checkProfileState()

            roleProfile = "Renter"
            val intent = Intent(this, RenterDashboard::class.java)
            startActivity(intent)
            finish()
        }
        binding?.contractorLayout?.setOnClickListener {
            enumState = ItemSelection.CONTRACTOR
            checkProfileState()

            roleProfile = "Contractor"
            val intent = Intent(this, ContractorDashboard::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun checkProfileState() {
        when (enumState) {
            ItemSelection.LANDLORD -> {
                binding?.landlordLayout?.setBackgroundColor(getColor(R.color.orange))
                binding?.propertyLayout?.setBackgroundColor(getColor(R.color.white))
                binding?.renterLayout?.setBackgroundColor(getColor(R.color.white))
                binding?.contractorLayout?.setBackgroundColor(getColor(R.color.white))

                binding?.tvLandLord?.setTextColor(getColor(R.color.white))
                binding?.tvPropertyManager?.setTextColor(getColor(R.color.bg_text))
                binding?.tvRenter?.setTextColor(getColor(R.color.bg_text))
                binding?.tvContractor?.setTextColor(getColor(R.color.bg_text))

                binding?.ivLandLord?.setColorFilter(getColor(R.color.white))
                binding?.ivPropertyManager?.setColorFilter(getColor(R.color.orange))
                binding?.ivRenter?.setColorFilter(getColor(R.color.orange))
                binding?.ivContractor?.setColorFilter(getColor(R.color.orange))


            }
            ItemSelection.PROPERTY -> {
                binding?.landlordLayout?.setBackgroundColor(getColor(R.color.white))
                binding?.propertyLayout?.setBackgroundColor(getColor(R.color.orange))
                binding?.renterLayout?.setBackgroundColor(getColor(R.color.white))
                binding?.contractorLayout?.setBackgroundColor(getColor(R.color.white))

                binding?.tvLandLord?.setTextColor(getColor(R.color.bg_text))
                binding?.tvPropertyManager?.setTextColor(getColor(R.color.white))
                binding?.tvRenter?.setTextColor(getColor(R.color.bg_text))
                binding?.tvContractor?.setTextColor(getColor(R.color.bg_text))

                binding?.ivLandLord?.setColorFilter(getColor(R.color.orange))
                binding?.ivPropertyManager?.setColorFilter(getColor(R.color.white))
                binding?.ivRenter?.setColorFilter(getColor(R.color.orange))
                binding?.ivContractor?.setColorFilter(getColor(R.color.orange))

            }
            ItemSelection.RENTER -> {
                binding?.landlordLayout?.setBackgroundColor(getColor(R.color.white))
                binding?.propertyLayout?.setBackgroundColor(getColor(R.color.white))
                binding?.renterLayout?.setBackgroundColor(getColor(R.color.orange))
                binding?.contractorLayout?.setBackgroundColor(getColor(R.color.white))

                binding?.tvLandLord?.setTextColor(getColor(R.color.bg_text))
                binding?.tvPropertyManager?.setTextColor(getColor(R.color.bg_text))
                binding?.tvRenter?.setTextColor(getColor(R.color.white))
                binding?.tvContractor?.setTextColor(getColor(R.color.bg_text))

                binding?.ivLandLord?.setColorFilter(getColor(R.color.orange))
                binding?.ivPropertyManager?.setColorFilter(getColor(R.color.orange))
                binding?.ivRenter?.setColorFilter(getColor(R.color.white))
                binding?.ivContractor?.setColorFilter(getColor(R.color.orange))
            }
            ItemSelection.CONTRACTOR -> {
                binding?.landlordLayout?.setBackgroundColor(getColor(R.color.white))
                binding?.propertyLayout?.setBackgroundColor(getColor(R.color.white))
                binding?.renterLayout?.setBackgroundColor(getColor(R.color.white))
                binding?.contractorLayout?.setBackgroundColor(getColor(R.color.orange))

                binding?.tvLandLord?.setTextColor(getColor(R.color.bg_text))
                binding?.tvPropertyManager?.setTextColor(getColor(R.color.bg_text))
                binding?.tvRenter?.setTextColor(getColor(R.color.bg_text))
                binding?.tvContractor?.setTextColor(getColor(R.color.white))

                binding?.ivLandLord?.setColorFilter(getColor(R.color.orange))
                binding?.ivPropertyManager?.setColorFilter(getColor(R.color.orange))
                binding?.ivRenter?.setColorFilter(getColor(R.color.orange))
                binding?.ivContractor?.setColorFilter(getColor(R.color.white))
            }
        }
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


private enum class ItemSelection {
    LANDLORD, PROPERTY, RENTER, CONTRACTOR
}