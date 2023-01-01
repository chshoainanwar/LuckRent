package com.example.luck_rent_android.ui.main.contractormodule

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityContractorRentPropertyBinding
import com.example.luck_rent_android.databinding.ActivityContractorViewPropertyBinding
import com.example.luck_rent_android.ui.main.chat.Chat
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.profile.Profile.Companion.roleProfile
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import java.lang.IllegalStateException
import java.util.*
import kotlin.collections.ArrayList

class ContractorViewProperty : BaseActivity(), NavigationView.OnNavigationItemSelectedListener  {
    private var binding: ActivityContractorViewPropertyBinding? = null

    private var mDataSideItemCon = ArrayList<SideItems>()
    private var mTopDataSideItemCon = ArrayList<SideItems>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_contractor_view_property)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))

        sideMenuClicks()

        binding?.topBar?.ivBack?.setOnClickListener {
            onBackPressed()
        }
        binding?.btnGoTOAllTasks?.setOnClickListener {
            val intent = Intent(this, ContractorJobComplete::class.java)
            startActivity(intent)
        }
        binding?.tvAddress?.setOnLongClickListener {
            val uri: String =
                java.lang.String.format(Locale.ENGLISH, "geo:%f,%f", 37.7749, -122.4194)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
            return@setOnLongClickListener true
        }

        binding?.tvLandLord?.setOnLongClickListener {
            val intent = Intent(this, Chat::class.java)
            startActivity(intent)
            return@setOnLongClickListener true
        }
        binding?.tvProjectManager?.setOnLongClickListener {
            val intent = Intent(this, Chat::class.java)
            startActivity(intent)
            return@setOnLongClickListener true
        }

    }

    private fun sideMenuClicks(){
        binding?.dashboardNavMenuLayoutForContractor?.ivCross?.setOnClickListener {
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

            when (roleProfile) {
                "Contractor" -> {
                    sideMenuDrawerForContractorDas(
                        binding?.dashboardDrawerLayoutContractor,
                        mDataSideItemCon,
                        binding?.dashboardNavMenuLayoutForContractor,
                        binding?.dashboardNavViewForContractor,
                        this@ContractorViewProperty,
                        mTopDataSideItemCon
                    )
                }
            }

        }

        binding?.content?.setOnClickListener {
            try {
                if (binding?.dashboardDrawerLayoutContractor?.isDrawerOpen(GravityCompat.END) == true) {
                    binding?.dashboardDrawerLayoutContractor?.closeDrawer(GravityCompat.END)
                }
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }

        }

        binding?.dashboardDrawerLayoutContractor?.addDrawerListener(object :
            DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {

                when (roleProfile) {
                    "Contractor" -> {
                        sideMenuDrawerForContractorDas(
                            binding?.dashboardDrawerLayoutContractor,
                            mDataSideItemCon,
                            binding?.dashboardNavMenuLayoutForContractor,
                            binding?.dashboardNavViewForContractor,
                            this@ContractorViewProperty,
                            mTopDataSideItemCon,
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