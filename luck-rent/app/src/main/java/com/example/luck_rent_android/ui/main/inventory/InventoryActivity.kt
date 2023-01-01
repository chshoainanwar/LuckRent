package com.example.luck_rent_android.ui.main.inventory

import android.app.DatePickerDialog
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
import com.example.luck_rent_android.databinding.ActivityInventoryBinding
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import java.lang.IllegalStateException
import java.util.*

class InventoryActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var binding: ActivityInventoryBinding? = null

    private var mDataSideItem = ArrayList<SideItems>()
    private var mTopDataSideItem = ArrayList<SideItems>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_inventory)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))

        setSideMenuClicks()
        setDates()

        binding?.topBar?.ivBack?.setOnClickListener{
            onBackPressed()
        }
        binding?.btnAdd?.setOnClickListener{
            val intent = Intent(this, InventoryMain::class.java)
            startActivity(intent)
        }

    }

    private fun setSideMenuClicks(){
        binding?.dashboardNavMenuLayout?.ivCross?.setOnClickListener{
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

        binding?.dashboardNavMenuLayout?.clickaBleNav?.setOnClickListener{
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
        binding?.dashboardNavMenuLayout?.rlProfilePic?.setOnClickListener{

        }
        binding?.dashboardNavMenuLayout?.tvName?.setOnClickListener{

        }

        binding?.topBar?.ivMenu?.setOnClickListener {
            sideMenuDrawer(
                binding?.dashboardDrawerLayout,
                mDataSideItem,
                binding?.dashboardNavMenuLayout,
                binding?.dashboardNavView,
                this,
                mTopDataSideItem
            )
        }

        binding?.content?.setOnClickListener {
            try {
                if (binding?.dashboardDrawerLayout?.isDrawerOpen(GravityCompat.END) == true) {
                    binding?.dashboardDrawerLayout?.closeDrawer(GravityCompat.END)
                }
            }catch (e: IllegalStateException){
                e.printStackTrace()
            }

        }

        binding?.dashboardDrawerLayout?.addDrawerListener(object : DrawerLayout.DrawerListener{
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {
                sideMenuDrawer(
                    binding?.dashboardDrawerLayout,
                    mDataSideItem,
                    binding?.dashboardNavMenuLayout,
                    binding?.dashboardNavView,
                    this@InventoryActivity,
                    mTopDataSideItem,
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

    private fun setDates(){
        binding?.tvLastMaintenancePicker?.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, day ->

                    // Display Selected date in TextView
                    val monthi = month.plus(1).toString()
                    if (monthi.toInt() > 9) {
                        binding?.tvLastMaintenancePicker?.text = "$year-$monthi-$day"
                    } else {
                        binding?.tvLastMaintenancePicker?.text = "$year-0$monthi-$day"

                    }

                }, year, month, day
            )
//            dpd.datePicker.minDate = System.currentTimeMillis()
            dpd.show()
        }

        binding?.tvNextMaintenancePicker?.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, day ->

                    // Display Selected date in TextView
                    val monthi = month.plus(1).toString()
                    if (monthi.toInt() > 9) {
                        binding?.tvNextMaintenancePicker?.text = "$year-$monthi-$day"
                    } else {
                        binding?.tvNextMaintenancePicker?.text = "$year-0$monthi-$day"

                    }

                }, year, month, day
            )
//            dpd.datePicker.minDate = System.currentTimeMillis()
            dpd.show()
        }

        binding?.tvWarrantyPicker?.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, day ->

                    // Display Selected date in TextView
                    val monthi = month.plus(1).toString()
                    if (monthi.toInt() > 9) {
                        binding?.tvWarrantyPicker?.text = "$year-$monthi-$day"
                    } else {
                        binding?.tvWarrantyPicker?.text = "$year-0$monthi-$day"

                    }

                }, year, month, day
            )
//            dpd.datePicker.minDate = System.currentTimeMillis()
            dpd.show()
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
        binding?.dashboardDrawerLayout?.closeDrawer(GravityCompat.START)
        return true
    }
}