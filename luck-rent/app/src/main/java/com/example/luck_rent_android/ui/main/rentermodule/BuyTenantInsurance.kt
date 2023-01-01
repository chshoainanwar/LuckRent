package com.example.luck_rent_android.ui.main.rentermodule

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.webkit.WebViewClient
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityBuyTenantInsuranceBinding
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import java.lang.IllegalStateException

class BuyTenantInsurance : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var binding: ActivityBuyTenantInsuranceBinding? = null

    private var mDataSideItemRenter = ArrayList<SideItems>()
    private var mTopDataSideItemRenter = ArrayList<SideItems>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_buy_tenant_insurance)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))
        setTopBar()
        sideMenuCLicks()

        binding?.tvLink?.setOnClickListener {
            val openURL = Intent(android.content.Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.kodextech.com/")
            startActivity(openURL)
        }
    }

    private fun setTopBar() {
        binding?.topBar?.rlSearch?.gone()
        binding?.topBar?.rlCounter?.gone()
        binding?.topBar?.ivAdd?.setImageResource(R.drawable.ic_back)
        binding?.topBar?.tvText?.text = "Buy Tenant Insurance"

        binding?.topBar?.ivAdd?.setOnClickListener {
            onBackPressed()
        }
    }

    private fun sideMenuCLicks() {
        binding?.dashboardNavMenuLayoutForRenter?.ivCross?.setOnClickListener {
            if (Profile.roleProfile == "Renter") {
                val intent = Intent(this, RenterDashboard::class.java)
                startActivity(intent)
            }
        }

        binding?.dashboardNavMenuLayoutForRenter?.clickaBleNav?.setOnClickListener {
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
        binding?.dashboardNavMenuLayoutForRenter?.rlProfilePic?.setOnClickListener {

        }
        binding?.dashboardNavMenuLayoutForRenter?.tvName?.setOnClickListener {

        }

        binding?.topBar?.ivMenu?.setOnClickListener {

            sideMenuDrawerForRenterDas(
                binding?.dashboardDrawerLayoutRenter,
                mDataSideItemRenter,
                binding?.dashboardNavMenuLayoutForRenter,
                binding?.dashboardNavViewForRenter,
                this,
                mTopDataSideItemRenter
            )

        }

        binding?.content?.setOnClickListener {
            try {
                if (binding?.dashboardDrawerLayoutRenter?.isDrawerOpen(GravityCompat.END) == true) {
                    binding?.dashboardDrawerLayoutRenter?.closeDrawer(GravityCompat.END)
                }
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }

        }

        binding?.dashboardDrawerLayoutRenter?.addDrawerListener(object :
            DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {
                sideMenuDrawerForRenterDas(
                    binding?.dashboardDrawerLayoutRenter,
                    mDataSideItemRenter,
                    binding?.dashboardNavMenuLayoutForRenter,
                    binding?.dashboardNavViewForRenter,
                    this@BuyTenantInsurance,
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