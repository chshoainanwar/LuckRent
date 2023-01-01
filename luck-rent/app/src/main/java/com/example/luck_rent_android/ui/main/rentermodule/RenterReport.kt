package com.example.luck_rent_android.ui.main.rentermodule

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityRenterReportBinding
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.profile.Profile.Companion.roleProfile
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RenterReport : BaseActivity(), NavigationView.OnNavigationItemSelectedListener  {
    private var binding : ActivityRenterReportBinding? = null

    private var mDataSideItemRenter = ArrayList<SideItems>()
    private var mTopDataSideItemRenter = ArrayList<SideItems>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_renter_report)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))

        setTopBar()

        binding?.topBar?.ivAdd?.setOnClickListener{
            onBackPressed()
        }

        binding?.spProperty?.setItems(
            "03257 - 21251",
            "03257 - 21252",
            "03257 - 21253",
            "03257 - 21254"
        )

        binding?.tvFirstDate?.setOnClickListener {

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, month, day ->

                    // Display Selected date in TextView
                    val month = month.plus(1)
                    val monthString = SimpleDateFormat("MM")
                    val monthFinal = monthString.format(month)
                    binding?.tvFirstDate?.text = "$year-$monthFinal-$day"

                }, year, month, day
            )
            dpd.show()
        }

        binding?.tvSecondDate?.setOnClickListener {

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, month, day ->

                    // Display Selected date in TextView
                    val month = month + 1
                    val monthString = SimpleDateFormat("MM")
                    val monthFinal = monthString.format(month)
                    binding?.tvSecondDate?.text = "$year-$monthFinal-$day"

                }, year, month, day
            )
            dpd.show()
        }

        binding?.dashboardNavMenuLayoutForRenter?.ivCross?.setOnClickListener{
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

        binding?.dashboardNavMenuLayoutForRenter?.clickaBleNav?.setOnClickListener{
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
        binding?.dashboardNavMenuLayoutForRenter?.rlProfilePic?.setOnClickListener{

        }
        binding?.dashboardNavMenuLayoutForRenter?.tvName?.setOnClickListener{

        }

        binding?.topBar?.ivMenu?.setOnClickListener {

            sideMenuDrawerForRenterDas(
                binding?.dashboardDrawerLayoutRenter,
                mDataSideItemRenter,
                binding?.dashboardNavMenuLayoutForRenter,
                binding?.dashboardNavViewForRenter,
                this@RenterReport,
                mTopDataSideItemRenter
            )

        }

        binding?.content?.setOnClickListener {
            try {
                if (binding?.dashboardDrawerLayoutRenter?.isDrawerOpen(GravityCompat.END) == true) {
                    binding?.dashboardDrawerLayoutRenter?.closeDrawer(GravityCompat.END)
                }
            }catch (e: IllegalStateException){
                e.printStackTrace()
            }

        }

        binding?.dashboardDrawerLayoutRenter?.addDrawerListener(object : DrawerLayout.DrawerListener{
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {

                sideMenuDrawerForRenterDas(
                    binding?.dashboardDrawerLayoutRenter,
                    mDataSideItemRenter,
                    binding?.dashboardNavMenuLayoutForRenter,
                    binding?.dashboardNavViewForRenter,
                    this@RenterReport,
                    mTopDataSideItemRenter,
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

    private fun setTopBar(){
        binding?.topBar?.rlSearch?.gone()
        binding?.topBar?.rlCounter?.gone()
        binding?.topBar?.tvText?.text = "Rent Invoice"
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
        binding?.dashboardDrawerLayoutRenter?.closeDrawer(GravityCompat.START)
        return true
    }
}