package com.example.luck_rent_android.ui.main.contractormodule

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityContractorRentPropertyBinding
import com.example.luck_rent_android.ui.main.chat.Chat
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.profile.Profile.Companion.roleProfile
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import com.kodextech.project.kodexlib.utils.visible
import java.lang.IllegalStateException
import java.util.*
import kotlin.collections.ArrayList

class ContractorRentProperty : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var binding: ActivityContractorRentPropertyBinding? = null
    var renter: String? = null
    var renterDashboard: String? = null
    var renterDashboardRequest: String? = null
    var baseNavMenu: String? = null

    private var mDataSideItemCon = ArrayList<SideItems>()
    private var mTopDataSideItemCon = ArrayList<SideItems>()

    private var mDataSideItemRenter = ArrayList<SideItems>()
    private var mTopDataSideItemRenter = ArrayList<SideItems>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contractor_rent_property)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))


        binding?.topBar?.ivBack?.setOnClickListener {
            onBackPressed()
        }

        binding?.btnRequest?.setOnClickListener {
            openDialog()
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

//        baseNavMenu = intent.getStringExtra("FromBaseActivity")
//        if (baseNavMenu == "ToContractorProperty"){
//            binding?.topBar?.tvRent?.text = "My Rented Place"
//        }

        renterDashboard = intent.getStringExtra("FromDashboard")
        if (renterDashboard == "ToProperties"){
            binding?.topBar?.tvRent?.text = "My Rental Home"
        }

        renterDashboardRequest = intent.getStringExtra("RenterDashboard")
        if (renterDashboardRequest == "RequestMaintenance"){
            binding?.topBar?.tvRent?.text = "Request Maintenance"
        }

        renter = intent.getStringExtra("isFrom")
        if (renter == "RentProperty") {
            binding?.btnRequest?.text = "Send Request"
            binding?.topBar?.tvRent?.text = "03257 - 21251"
            binding?.topBar?.tvAddress?.visible()
        }

        if (renter == "RenterAds") {
            binding?.btnRequest?.gone()
            binding?.topBar?.tvRent?.text = "03257 - 21251"
            binding?.topBar?.tvAddress?.visible()
            binding?.layoutButton?.visible()
        }

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
                "Renter" -> {
                    sideMenuDrawerForRenterDas(
                        binding?.dashboardDrawerLayoutContractor,
                        mDataSideItemRenter,
                        binding?.dashboardNavMenuLayoutForContractor,
                        binding?.dashboardNavViewForContractor,
                        this@ContractorRentProperty,
                        mTopDataSideItemRenter
                    )
                }
                "Contractor" -> {
                    sideMenuDrawerForContractorDas(
                        binding?.dashboardDrawerLayoutContractor,
                        mDataSideItemCon,
                        binding?.dashboardNavMenuLayoutForContractor,
                        binding?.dashboardNavViewForContractor,
                        this@ContractorRentProperty,
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
                    "Renter" -> {
                        sideMenuDrawerForRenterDas(
                            binding?.dashboardDrawerLayoutContractor,
                            mDataSideItemRenter,
                            binding?.dashboardNavMenuLayoutForContractor,
                            binding?.dashboardNavViewForContractor,
                            this@ContractorRentProperty,
                            mTopDataSideItemRenter,
                            true
                        )
                    }
                    "Contractor" -> {
                        sideMenuDrawerForContractorDas(
                            binding?.dashboardDrawerLayoutContractor,
                            mDataSideItemCon,
                            binding?.dashboardNavMenuLayoutForContractor,
                            binding?.dashboardNavViewForContractor,
                            this@ContractorRentProperty,
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

    private fun openDialog() {
        val view = View.inflate(this, R.layout.view_property_dialog, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()


        //Register Done Button & Cancel Icon from Dialog
        var cancelIcon = view?.findViewById<ImageView>(R.id.cancelIcon)
        var text1 = view?.findViewById<TextView>(R.id.text1)
        var text2 = view?.findViewById<TextView>(R.id.text2)

        if (renter == "RentProperty") {
            text1?.text = "Your Request"
            text2?.text = "Has Been Sent"
        }else if (renterDashboard == "ToProperties"){
            text1?.text = "Your Request For Maintenance"
            text2?.text = "Has Been Sent"
        }

        //Dialog Cancel Icon
        cancelIcon?.setOnClickListener {
            dialog.dismiss()
//            val intent = Intent(this, ContractorOverview::class.java)
//            startActivity(intent)
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