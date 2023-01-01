package com.example.luck_rent_android.ui.main.adds

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
import com.example.luck_rent_android.databinding.ActivityRequestBinding
import com.example.luck_rent_android.ui.main.adds.adapter.RequestAdapter
import com.example.luck_rent_android.ui.main.adds.model.RequestModel
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.profile.Profile.Companion.roleProfile
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import java.lang.IllegalStateException

class Request : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    val profileList = ArrayList<RequestModel>()

    private var binding: ActivityRequestBinding? = null
    private var mAdapter: RequestAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var adapter: RecyclerView.Adapter<RequestAdapter.ViewHolder>? = null
    private var mDataSideItem = ArrayList<SideItems>()
    private var mTopDataSideItem = ArrayList<SideItems>()

    private var mDataSideItemCon = ArrayList<SideItems>()
    private var mTopDataSideItemCon = java.util.ArrayList<SideItems>()

    private var mDataSideItemRenter = ArrayList<SideItems>()
    private var mTopDataSideItemRenter = ArrayList<SideItems>()

    override fun onCreate(savedInstanceState: Bundle?) {

        statusBarColor(getColor(R.color.darkBlue))
        binding = DataBindingUtil.setContentView(this, R.layout.activity_request)
        super.onCreate(savedInstanceState)

        initData()
        setRecycler()

        binding?.topBar?.backIcon?.setOnClickListener {
            onBackPressed()
        }

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

            when (roleProfile) {
                "Landlord" -> {

                    sideMenuDrawer(
                        binding?.dashboardDrawerLayout,
                        mDataSideItem,
                        binding?.dashboardNavMenuLayout,
                        binding?.dashboardNavView,
                        this@Request,
                        mTopDataSideItem
                    )

                }
                "Contractor" ->{
                    sideMenuDrawerForContractorDas(
                        binding?.dashboardDrawerLayout,
                        mDataSideItemCon,
                        binding?.dashboardNavMenuLayout,
                        binding?.dashboardNavView,
                        this@Request,
                        mTopDataSideItemCon
                    )
                }
                "Renter" -> {
                    sideMenuDrawerForRenterDas(
                        binding?.dashboardDrawerLayout,
                        mDataSideItemRenter,
                        binding?.dashboardNavMenuLayout,
                        binding?.dashboardNavView,
                        this@Request,
                        mTopDataSideItemRenter
                    )
                }

            }
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

                when (roleProfile) {
                    "Landlord" -> {

                        sideMenuDrawer(
                            binding?.dashboardDrawerLayout,
                            mDataSideItem,
                            binding?.dashboardNavMenuLayout,
                            binding?.dashboardNavView,
                            this@Request,
                            mTopDataSideItem,
                            true
                        )

                    }
                    "Contractor" ->{
                        sideMenuDrawerForContractorDas(
                            binding?.dashboardDrawerLayout,
                            mDataSideItemCon,
                            binding?.dashboardNavMenuLayout,
                            binding?.dashboardNavView,
                            this@Request,
                            mTopDataSideItemCon,
                            true
                        )
                    }
                    "Renter" -> {
                        sideMenuDrawerForRenterDas(
                            binding?.dashboardDrawerLayout,
                            mDataSideItemRenter,
                            binding?.dashboardNavMenuLayout,
                            binding?.dashboardNavView,
                            this@Request,
                            mTopDataSideItemRenter,
                            true
                        )
                    }

                }

            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerStateChanged(newState: Int) {

                Log.d("getState", "onDrawerStateChanged: " + newState)
            }

        })

    }

    private fun setRecycler() {
        mAdapter = RequestAdapter(profileList, this)
        binding?.recycler?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding?.recycler?.adapter = mAdapter
        mAdapter?.notifyDataSetChanged()
    }


    private fun initData() {
        profileList.add(
            RequestModel(
                "Thomas Baker",
                "Hi there, thanks for showing us the beautiful \n" +
                        "house you have. We are submitting our \n" +
                        "application with the LuckRent App per your \n" +
                        "request. Please let me know if you need \n" +
                        "further information from me. Thank you.",
                R.drawable.profile1
            )
        )
        profileList.add(
            RequestModel(
                "Oliver Smith",
                "Hi there, thanks for showing us the beautiful +\n" +
                        "house you have. We are submitting our \n" +
                        "application with the LuckRent App per your \n" +
                        "request. Please let me know if you need \n" +
                        "further information from me. Thank you.",
                R.drawable.profile2
            )
        )
        profileList.add(
            RequestModel(
                "Harry Paterson",
                "Hi there, thanks for showing us the beautiful \n" +
                        "house you have. We are submitting our \n" +
                        "application with the LuckRent App per your \n" +
                        "request. Please let me know if you need \n" +
                        "further information from me. Thank you.",
                R.drawable.profile3
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