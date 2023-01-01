package com.example.luck_rent_android.ui.main.invoice

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
import com.example.luck_rent_android.databinding.ActivityAddInvoiceBinding
import com.example.luck_rent_android.databinding.ActivityInvoicePreviewBinding
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
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

class InvoicePreview : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var binding: ActivityInvoicePreviewBinding? = null

    private var mDataSideItem = ArrayList<SideItems>()
    private var mTopDataSideItem = ArrayList<SideItems>()

    private var status: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_invoice_preview)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))

        setTopBar()
        sideMenuClicks()

        binding?.btnPaid?.setOnClickListener {
            status = "paid"
            changeStatusColorState()
        }
        binding?.btnUnpaid?.setOnClickListener {
            status = "unpaid"
            changeStatusColorState()
        }

        binding?.btnDone?.setOnClickListener {
            when(roleProfile){
                "Landlord" ->{
                    val intent = Intent(this, Dashboard::class.java)
                    startActivity(intent)
                }
                "Property Manager" ->{
                    val intent = Intent(this, Dashboard::class.java)
                    startActivity(intent)
                }
                "Contractor" ->{
                    val intent = Intent(this, ContractorDashboard::class.java)
                    startActivity(intent)
                }
                "Renter" ->{
                    val intent = Intent(this, RenterDashboard::class.java)
                    startActivity(intent)
                }
            }

        }

        when(roleProfile){
            "Contractor" ->{
                binding?.topBar?.tvText?.text = "Contractor Invoice"
                binding?.firstRg?.visible()
                binding?.llBtn?.visible()
                binding?.btnDone?.gone()
                binding?.rbPid?.gone()
            }
        }

    }
    private fun setTopBar() {
        binding?.topBar?.tvText?.text = "Invoice Preview"
        binding?.topBar?.ivAdd?.setImageResource(R.drawable.ic_back)
        binding?.topBar?.rlCounter?.gone()
        binding?.topBar?.rlSearch?.gone()
        binding?.topBar?.ivAdd?.setOnClickListener { onBackPressed() }}

    private fun changeStatusColorState() {
        when (status) {
            "paid" -> {
                binding?.btnPaid?.buttonTintList = getColorStateList(R.color.darkBlue)
                binding?.btnUnpaid?.buttonTintList = getColorStateList(R.color.sand)
            }
            "unpaid" -> {
                binding?.btnPaid?.buttonTintList = getColorStateList(R.color.sand)
                binding?.btnUnpaid?.buttonTintList = getColorStateList(R.color.darkBlue)
            }
        }
    }

    private fun sideMenuClicks(){
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
                        this,
                        mTopDataSideItem
                    )
                }
                "Contractor" -> {
                    sideMenuDrawerForContractorDas(
                        binding?.dashboardDrawerLayout,
                        mDataSideItem,
                        binding?.dashboardNavMenuLayout,
                        binding?.dashboardNavView,
                        this,
                        mTopDataSideItem
                    )

                }
                "Renter" -> {
                    sideMenuDrawerForRenterDas(
                        binding?.dashboardDrawerLayout,
                        mDataSideItem,
                        binding?.dashboardNavMenuLayout,
                        binding?.dashboardNavView,
                        this@InvoicePreview,
                        mTopDataSideItem,
                        true
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
                            this@InvoicePreview,
                            mTopDataSideItem,
                            true
                        )
                    }
                    "Contractor" -> {
                        sideMenuDrawerForContractorDas(
                            binding?.dashboardDrawerLayout,
                            mDataSideItem,
                            binding?.dashboardNavMenuLayout,
                            binding?.dashboardNavView,
                            this@InvoicePreview,
                            mTopDataSideItem,
                            true
                        )

                    }
                    "Renter" -> {
                        sideMenuDrawerForRenterDas(
                            binding?.dashboardDrawerLayout,
                            mDataSideItem,
                            binding?.dashboardNavMenuLayout,
                            binding?.dashboardNavView,
                            this@InvoicePreview,
                            mTopDataSideItem,
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
        binding?.dashboardDrawerLayout?.closeDrawer(GravityCompat.START)
        return true
    }
}