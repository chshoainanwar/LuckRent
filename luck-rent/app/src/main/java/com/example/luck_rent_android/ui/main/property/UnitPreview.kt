package com.example.luck_rent_android.ui.main.property

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityUnitPreviewBinding
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.property.AddProperty.Companion.mlist
import com.example.luck_rent_android.ui.main.property.adapter.AddPropertySizeAdapter
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import java.lang.IllegalStateException

class UnitPreview : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var  binding : ActivityUnitPreviewBinding? = null

    private var mDataSideItem = ArrayList<SideItems>()
    private var mTopDataSideItem = ArrayList<SideItems>()

    private var adapter: AddPropertySizeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_unit_preview)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))

        setTopBar()
        sideMenuClicks()
        setRecycler()

        binding?.rlEdit?.setOnClickListener{
            val intent = Intent(this, AddBuilding::class.java)
            startActivity(intent)
        }

        binding?.btnDone?.setOnClickListener{
            val intent = Intent(this, PropertiesActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setTopBar(){
        binding?.topBar?.rlSearch?.gone()
        binding?.topBar?.rlCounter?.gone()
        binding?.topBar?.tvText?.text = "Add Unit Preview"
        binding?.topBar?.ivAdd?.setImageResource(R.drawable.ic_back)
        binding?.topBar?.ivAdd?.setOnClickListener{
            onBackPressed()
        }
    }

    private fun setRecycler(){
        adapter = AddPropertySizeAdapter(this,mlist ){mItem , pos ->}
        binding?.recycler?.layoutManager = GridLayoutManager(applicationContext,2)
        binding?.recycler?.adapter = adapter
        adapter?.notifyDataSetChanged()
    }

    private fun sideMenuClicks(){
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
                this@UnitPreview,
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
                    this@UnitPreview,
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