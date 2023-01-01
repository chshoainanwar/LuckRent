package com.example.luck_rent_android.ui.main.adds

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityDetailsBinding
import com.example.luck_rent_android.ui.main.applicationform.ApplicationForm
import com.example.luck_rent_android.ui.main.auth.Login
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.profile.Profile.Companion.roleProfile
import com.example.luck_rent_android.ui.main.renter.Renter
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import com.kodextech.project.kodexlib.utils.visible
import java.lang.IllegalStateException

class Details : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var binding: ActivityDetailsBinding? = null

    private var makeAdd : String? = null
    private var renterAdd : String? = null

    private var mDataSideItem = ArrayList<SideItems>()
    private var mTopDataSideItem = ArrayList<SideItems>()

    private var mDataSideItemCon = ArrayList<SideItems>()
    private var mTopDataSideItemCon = ArrayList<SideItems>()

    private var mDataSideItemRenter = ArrayList<SideItems>()
    private var mTopDataSideItemRenter = ArrayList<SideItems>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))
        sideMenuClicks()

        binding?.topBar?.backIcon?.setOnClickListener {
            onBackPressed()
        }

        makeAdd = intent.getStringExtra("MakeAdd")
        renterAdd = intent.getStringExtra("isFrom")
        if (makeAdd == "Details"){
            binding?.layoutButton?.visible()
            binding?.btnArchiveAd?.gone()
            binding?.rlEdit?.visible()
            binding?.tvTextAdd?.visible()
            binding?.storeLayout?.visible()
            binding?.layoutButton1?.gone()
        }else if(renterAdd == "RenterAds"){
            binding?.layoutButton?.gone()
            binding?.btnArchiveAd?.gone()
            binding?.rlEdit?.gone()
            binding?.tvTextAdd?.gone()
            binding?.storeLayout?.gone()
            binding?.layoutButton1?.visible()
            binding?.btnSendApplication?.setOnClickListener {
                val intent = Intent(this, ApplicationForm::class.java)
                startActivity(intent)
            }
        }else{
            binding?.layoutButton?.gone()
            binding?.btnArchiveAd?.visible()
            binding?.rlEdit?.gone()
            binding?.tvTextAdd?.gone()
            binding?.storeLayout?.gone()
            binding?.layoutButton1?.gone()
        }
        binding?.btnPost?.setOnClickListener {
            postDialog()
        }
        binding?.rlEdit?.setOnClickListener {
            val intent = Intent(this, MakeAdd::class.java)
            startActivity(intent)
            finish()
        }
        binding?.btnArchiveAd?.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
            finish()
        }

        }

    private fun sideMenuClicks(){
        binding?.dashboardNavMenuLayout?.ivCross?.setOnClickListener {
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
                        this@Details,
                        mTopDataSideItem
                    )

                }
                "Contractor" -> {
                    sideMenuDrawerForContractorDas(
                        binding?.dashboardDrawerLayout,
                        mDataSideItemCon,
                        binding?.dashboardNavMenuLayout,
                        binding?.dashboardNavView,
                        this@Details,
                        mTopDataSideItemCon
                    )
                }
                "Renter" -> {
                    sideMenuDrawerForRenterDas(
                        binding?.dashboardDrawerLayout,
                        mDataSideItemRenter,
                        binding?.dashboardNavMenuLayout,
                        binding?.dashboardNavView,
                        this@Details,
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
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }

        }
        binding?.dashboardDrawerLayout?.addDrawerListener(object : DrawerLayout.DrawerListener {
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
                            this@Details,
                            mTopDataSideItem,
                            true
                        )

                    }
                    "Contractor" -> {
                        sideMenuDrawerForContractorDas(
                            binding?.dashboardDrawerLayout,
                            mDataSideItemCon,
                            binding?.dashboardNavMenuLayout,
                            binding?.dashboardNavView,
                            this@Details,
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
                            this@Details,
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

    private fun postDialog(){
        val view = View.inflate(this, R.layout.dialog_post, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()


        //Register Done Button & Cancel Icon from Dialog
        var ivCancel = view?.findViewById<ImageView>(R.id.ivCancel)

        //Dialog Cancel Icon
        ivCancel?.setOnClickListener {
            dialog.dismiss()
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