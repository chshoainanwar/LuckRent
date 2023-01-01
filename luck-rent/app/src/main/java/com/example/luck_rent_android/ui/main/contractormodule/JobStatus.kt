package com.example.luck_rent_android.ui.main.contractormodule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityAddBuildingBinding
import com.example.luck_rent_android.databinding.ActivityJobStatusBinding
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import java.lang.IllegalStateException

class JobStatus : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var  binding : ActivityJobStatusBinding? = null

    private var status: String? = null

    private var mDataSideItemCon = ArrayList<SideItems>()
    private var mTopDataSideItemCon = ArrayList<SideItems>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_job_status)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))

        setTopBar()
        sideMenuClicks()

        binding?.btnRequest?.setOnClickListener {
            val intent = Intent(this, ContractorRentProperty::class.java)
            startActivity(intent)
        }

        binding?.btnInProgress?.setOnClickListener {
            status = "in_progress"
            changeStatusColorState()
        }
        binding?.btnCompleted?.setOnClickListener {
            status = "completed"
            changeStatusColorState()
        }

    }

    private fun setTopBar(){
        binding?.topBar?.rlSearch?.gone()
        binding?.topBar?.rlCounter?.gone()
        binding?.topBar?.tvText?.text = "Job Status"
        binding?.topBar?.ivAdd?.setImageResource(R.drawable.ic_back)
        binding?.topBar?.ivAdd?.setOnClickListener{
            onBackPressed()
        }
    }

    private fun sideMenuClicks(){
        binding?.dashboardNavMenuLayoutForContractor?.ivCross?.setOnClickListener{
            if (Profile.roleProfile == "Contractor") {
                val intent = Intent(this, ContractorDashboard::class.java)
                startActivity(intent)
            }
        }

        binding?.dashboardNavMenuLayoutForContractor?.clickaBleNav?.setOnClickListener{
            when (Profile.roleProfile) {
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

        binding?.topBar?.rlMenu?.setOnClickListener {

            sideMenuDrawerForContractorDas(
                binding?.dashboardDrawerLayoutContractor,
                mDataSideItemCon,
                binding?.dashboardNavMenuLayoutForContractor,
                binding?.dashboardNavViewForContractor,
                this,
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
                    this@JobStatus,
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

    private fun changeStatusColorState() {
        when (status) {
            "in_progress" -> {
                binding?.btnInProgress?.buttonTintList = getColorStateList(R.color.darkBlue)
                binding?.btnCompleted?.buttonTintList = getColorStateList(R.color.sand)
            }
            "completed" -> {
                binding?.btnInProgress?.buttonTintList = getColorStateList(R.color.sand)
                binding?.btnCompleted?.buttonTintList = getColorStateList(R.color.darkBlue)
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding?.dashboardDrawerLayoutContractor?.closeDrawer(GravityCompat.START)
        return true
    }
}