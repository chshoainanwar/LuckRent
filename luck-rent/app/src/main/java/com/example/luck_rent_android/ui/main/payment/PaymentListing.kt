package com.example.luck_rent_android.ui.main.payment

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
import com.example.luck_rent_android.databinding.ActivityPaymentListingBinding
import com.example.luck_rent_android.model.PaymentModel
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.payment.adapter.PaymentListingAdapter
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.profile.Profile.Companion.roleProfile
import com.example.luck_rent_android.ui.main.property.PropertiesActivity
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import java.lang.IllegalStateException

class PaymentListing : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var binding: ActivityPaymentListingBinding? = null

    private var mAdapter: PaymentListingAdapter? = null
    private var mData = ArrayList<PaymentModel>()
    private var mDataSideItem = ArrayList<SideItems>()
    private var mTopDataSideItem = ArrayList<SideItems>()
    override fun onCreate(savedInstanceState: Bundle?) {
        statusBarColor(getColor(R.color.darkBlue))
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_listing)
        super.onCreate(savedInstanceState)

        initTopBar()
        setDummyData()
        setAdapter()

        binding?.topBar?.ivAdd?.setOnClickListener {
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

            sideMenuDrawer(
                binding?.dashboardDrawerLayout,
                mDataSideItem,
                binding?.dashboardNavMenuLayout,
                binding?.dashboardNavView,
                this@PaymentListing,
                mTopDataSideItem
            )

        }

        binding?.contentPayment?.setOnClickListener {
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
                    this@PaymentListing,
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

    private fun setAdapter() {
        mAdapter = PaymentListingAdapter(this, mData)
        binding?.rvPaymentListing?.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding?.rvPaymentListing?.adapter = mAdapter
        mAdapter?.notifyDataSetChanged()
    }

    private fun setDummyData() {
        mData.clear()
        mData.add(
            PaymentModel(
                "Cash",
                "Ellie Goulding",
                "$ 250",
                "2021-10-09",
                "2021-10-02",
                "0",
                "Lorem ipsum is a placeholder text commonly \n" +
                        "used to demonstrate the visual form of a \n" +
                        "document or a typeface without relying on \n" +
                        "meaningful content."
            )
        )
        mData.add(
            PaymentModel(
                "Cash",
                "Ellie Goulding",
                "$ 250",
                "2021-10-09",
                "2021-10-02",
                "1",
                "Lorem ipsum is a placeholder text commonly \n" +
                        "used to demonstrate the visual form of a \n" +
                        "document or a typeface without relying on \n" +
                        "meaningful content."
            )
        )
        mData.add(
            PaymentModel(
                "Cash",
                "Ellie Goulding",
                "$ 250",
                "2021-10-09",
                "2021-10-02",
                "0",
                "Lorem ipsum is a placeholder text commonly \n" +
                        "used to demonstrate the visual form of a \n" +
                        "document or a typeface without relying on \n" +
                        "meaningful content."
            )
        )
    }

    private fun initTopBar() {
        binding?.topBar?.tvText?.text = "Payment Details"
        binding?.topBar?.rlCounter?.gone()
        binding?.topBar?.rlSearch?.gone()
        binding?.topBar?.ivAdd?.setImageResource(R.drawable.ic_back)
    }

    override fun onSetupViewGroup() {
        mViewGroup = binding?.contentPayment
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