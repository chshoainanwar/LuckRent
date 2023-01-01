package com.example.luck_rent_android.ui.main.contractormodule

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityContractorReportBinding
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.profile.Profile.Companion.roleProfile
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import java.lang.IllegalStateException

class ContractorReport : BaseActivity(), NavigationView.OnNavigationItemSelectedListener  {
    private var binding : ActivityContractorReportBinding? = null

    private var mDataSideItemCon = ArrayList<SideItems>()
    private var mTopDataSideItemCon = ArrayList<SideItems>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_contractor_report)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))

        SetTopBar()

        binding?.topBar?.ivAdd?.setOnClickListener{
            onBackPressed()
        }

        binding?.dashboardNavMenuLayoutForContractor?.ivCross?.setOnClickListener{
            when (roleProfile) {
                "Landlord" -> {
                    val intent = Intent(this, Dashboard::class.java)
                    startActivity(intent)
                }
                "Property Manager" -> {
                    val intent = Intent(this, Dashboard::class.java)
                    startActivity(intent)
                }
                "Contractor" -> {
                    val intent = Intent(this, ContractorDashboard::class.java)
                    startActivity(intent)
                }
                "Renter" -> {
                    val intent = Intent(this, RenterDashboard::class.java)
                    startActivity(intent)
                }
            }
        }

        binding?.dashboardNavMenuLayoutForContractor?.clickaBleNav?.setOnClickListener{
            when (roleProfile) {
                "Landlord" -> {
                    val intent = Intent(this, Dashboard::class.java)
                    startActivity(intent)
                }
                "Property Manager" -> {
                    val intent = Intent(this, Dashboard::class.java)
                    startActivity(intent)
                }
                "Contractor" -> {
                    val intent = Intent(this, ContractorDashboard::class.java)
                    startActivity(intent)
                }
                "Renter" -> {
                    val intent = Intent(this, RenterDashboard::class.java)
                    startActivity(intent)
                }
            }

        }
        binding?.dashboardNavMenuLayoutForContractor?.rlProfilePic?.setOnClickListener{

        }
        binding?.dashboardNavMenuLayoutForContractor?.tvName?.setOnClickListener{

        }

        binding?.topBar?.ivMenu?.setOnClickListener {
            sideMenuDrawerForContractorDas(
                binding?.dashboardDrawerLayoutContractor,
                mDataSideItemCon,
                binding?.dashboardNavMenuLayoutForContractor,
                binding?.dashboardNavViewForContractor,
                this@ContractorReport,
                mTopDataSideItemCon
            )
        }

        binding?.content?.setOnClickListener {
            try {
                if (binding?.dashboardDrawerLayoutContractor?.isDrawerOpen(GravityCompat.END) == true) {
                    binding?.dashboardDrawerLayoutContractor?.closeDrawer(GravityCompat.END)
                }
            }catch (e: IllegalStateException){
                e.printStackTrace()
            }

        }

        binding?.dashboardDrawerLayoutContractor?.addDrawerListener(object : DrawerLayout.DrawerListener{
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {
                sideMenuDrawerForContractorDas(
                    binding?.dashboardDrawerLayoutContractor,
                    mDataSideItemCon,
                    binding?.dashboardNavMenuLayoutForContractor,
                    binding?.dashboardNavViewForContractor,
                    this@ContractorReport,
                    mTopDataSideItemCon,
                    true
                )
            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerStateChanged(newState: Int) {

                Log.d("getState", "onDrawerStateChanged: " + newState)
            }

        })


    }

    private fun SetTopBar(){
        binding?.topBar?.rlSearch?.gone()
        binding?.topBar?.rlCounter?.gone()
        binding?.topBar?.tvText?.text = "Reports"
        binding?.topBar?.ivAdd?.setImageResource(R.drawable.ic_back)
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding?.dashboardDrawerLayoutContractor?.closeDrawer(GravityCompat.START)
        return true
    }
}