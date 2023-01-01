package com.example.luck_rent_android.ui.main.myreport

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityReportsBinding
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.myreport.adapter.ReportsAdapter
import com.example.luck_rent_android.ui.main.myreport.adapter.ReportsAdapterVH
import com.example.luck_rent_android.ui.main.myreport.model.ReportsModel
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.profile.Profile.Companion.roleProfile
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import java.lang.IllegalStateException
import java.util.ArrayList

class Reports : BaseActivity() , NavigationView.OnNavigationItemSelectedListener{
    private var binding : ActivityReportsBinding? = null

    private var mDataSideItem = ArrayList<SideItems>()
    private var mTopDataSideItem = ArrayList<SideItems>()

    val list = ArrayList<ReportsModel>()
    var adapter: ReportsAdapter? = null
    var mAdapter: RecyclerView.Adapter<ReportsAdapterVH>? = null
    var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding= DataBindingUtil.setContentView(this,R.layout.activity_reports)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))

        SetTopBar()
        SetDummyData()
        SetRecycler()

        binding?.dashboardNavMenuLayout?.ivCross?.setOnClickListener{
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

        binding?.dashboardNavMenuLayout?.clickaBleNav?.setOnClickListener{
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
                this@Reports,
                mTopDataSideItem
            ) }

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
                    this@Reports,
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
        binding?.topBar?.ivAdd?.setOnClickListener{
            onBackPressed()
        }

    }

    private fun SetRecycler(){
        mAdapter = ReportsAdapter(this, list)
        binding?.recyclerView?.layoutManager = GridLayoutManager(this,2)
        binding?.recyclerView?.adapter = mAdapter
        mAdapter?.notifyDataSetChanged()
    }

    private fun SetTopBar(){
        binding?.topBar?.rlSearch?.gone()
        binding?.topBar?.rlCounter?.gone()
        binding?.topBar?.tvText?.text = "Reports"
        binding?.topBar?.ivAdd?.setImageResource(R.drawable.ic_back)
    }

    private fun SetDummyData(){
        list.add(
            ReportsModel(
                "Rent \nBalance",
                R.drawable.ic_rent
            )
        )
        list.add(
            ReportsModel(
                "Invoice",
                R.drawable.ic_invoice
            )
        )
        list.add(
            ReportsModel(
                "Tax \nPreparation",
                R.drawable.ic_tax
            )
        )
        list.add(
            ReportsModel(
                "Income \nStatement",
                R.drawable.ic_income
            )
        )
        list.add(
            ReportsModel(
                "Rent \nRolls",
                R.drawable.ic_rolls
            )
        )
        list.add(
            ReportsModel(
                "Cashflow \nReports",
                R.drawable.ic_cashflow
            )
        )
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