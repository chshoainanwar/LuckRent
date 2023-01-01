package com.example.luck_rent_android.ui.main.rentermodule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityRenterNewPropertiesBinding
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.profile.Profile.Companion.roleProfile
import com.example.luck_rent_android.ui.main.rentermodule.adapter.RenterNewPropertiesAdapter
import com.example.luck_rent_android.ui.main.rentermodule.model.RenterNewPropertiesModel
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import java.lang.IllegalStateException

class RenterNewProperties : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var binding: ActivityRenterNewPropertiesBinding? = null

    private var mDataSideItemRenter = ArrayList<SideItems>()
    private var mTopDataSideItemRenter = ArrayList<SideItems>()

    private var adapter : RenterNewPropertiesAdapter? = null
    private var mAdapter : RecyclerView.Adapter<RenterNewPropertiesAdapter.RenterNewPropertiesAdapterVH>? = null
    private var list = ArrayList<RenterNewPropertiesModel>()
    private var finalArray = ArrayList<RenterNewPropertiesModel>()
    private var layoutManager : RecyclerView.LayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_renter_new_properties)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))

        setTopBar()
        setRecyclerView()
        setDummyData()



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
                this@RenterNewProperties,
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
                    this@RenterNewProperties,
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
        binding?.topBar?.rlCounter?.gone()
        binding?.topBar?.ivAdd?.gone()
        binding?.topBar?.tvText?.text = "My Rented Place"
        binding?.topBar?.tvText?.gravity = Gravity.START
        (binding?.topBar?.tvText?.layoutParams as ConstraintLayout.LayoutParams).apply {
            marginStart = 40
            topMargin = 40
        }
        binding?.topBar?.tvText?.textSize = 25F

    }

    private fun setRecyclerView(){
        mAdapter = RenterNewPropertiesAdapter(this,list)
        binding?.recycler?.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding?.recycler?.adapter = mAdapter
        mAdapter?.notifyDataSetChanged()
    }

    private fun setDummyData(){

        finalArray.clear()
       for (item in 1..5){
           list.add(RenterNewPropertiesModel( "John Smith"))
       }

        if (list.count() > 0){
            list.add(1,RenterNewPropertiesModel(isForAds = "true"))
        }
        mAdapter?.notifyDataSetChanged()
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