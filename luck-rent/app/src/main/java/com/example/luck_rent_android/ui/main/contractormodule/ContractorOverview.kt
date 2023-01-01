package com.example.luck_rent_android.ui.main.contractormodule

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityContractorOverviewBinding
import com.example.luck_rent_android.ui.main.contractormodule.adapter.ContractorOverviewAdapter
import com.example.luck_rent_android.ui.main.contractormodule.model.ContractorOverviewModel
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.profile.Profile.Companion.roleProfile
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import java.lang.IllegalStateException

class ContractorOverview : BaseActivity(), NavigationView.OnNavigationItemSelectedListener  {
    private var binding : ActivityContractorOverviewBinding? = null

    private var mDataSideItemCon = ArrayList<SideItems>()
    private var mTopDataSideItemCon = ArrayList<SideItems>()

    var adapter : ContractorOverviewAdapter? = null
    var list = ArrayList<ContractorOverviewModel>()
    var layoutManager : RecyclerView.LayoutManager? = null
    var mAdapter : RecyclerView.Adapter<ContractorOverviewAdapter.ViewHolderCO>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_contractor_overview)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))

        SetTopBar()
        SetRecyclerView()
        SetDummyData()

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
                this@ContractorOverview,
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
                    this@ContractorOverview,
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
        binding?.topBar?.tvPropertyAddress?.gone()
        binding?.topBar?.rlSearch?.gone()
        binding?.topBar?.ivAdd?.gone()
        binding?.topBar?.tvText?.text = "Overview"
    }

    private fun SetRecyclerView(){
        mAdapter = ContractorOverviewAdapter(this, list)
        binding?.recycler?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding?.recycler?.adapter = mAdapter
        mAdapter?.notifyDataSetChanged()
    }

    private fun SetDummyData(){
        list.add(
            ContractorOverviewModel(
                "John Smith",
            )
        )
        list.add(
            ContractorOverviewModel(
                "John Smith",
            )
        )
        list.add(
            ContractorOverviewModel(
                "John Smith",
            )
        )
        list.add(
            ContractorOverviewModel(
                "John Smith",
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
        binding?.dashboardDrawerLayoutContractor?.closeDrawer(GravityCompat.START)
        return true
    }
}