package com.example.luck_rent_android.ui.main.order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityOrderBinding
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.inspectionreport.InspectionItem
import com.example.luck_rent_android.ui.main.myreport.adapter.ReportsAdapter
import com.example.luck_rent_android.ui.main.myreport.adapter.ReportsAdapterVH
import com.example.luck_rent_android.ui.main.myreport.model.ReportsModel
import com.example.luck_rent_android.ui.main.order.adapter.OrderAdapter
import com.example.luck_rent_android.ui.main.order.adapter.OrderAdapterVH
import com.example.luck_rent_android.ui.main.order.model.OrderModel
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import com.kodextech.project.kodexlib.utils.visible
import java.lang.IllegalStateException
import java.util.ArrayList

class OrderActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var binding : ActivityOrderBinding? = null

    private var enumState: OrderItem = OrderItem.Subscription

    private var mDataSideItem = ArrayList<SideItems>()
    private var mTopDataSideItem = ArrayList<SideItems>()

    val list = ArrayList<OrderModel>()
    var adapter: OrderAdapter? = null
    var mAdapter: RecyclerView.Adapter<OrderAdapterVH>? = null
    var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_order)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))



        setTopBar()
        setSideMenuCLicks()
        SetRecycler()
        setDummyData()

        binding?.spRole?.setItems(
            "Landlord",
            "Property Manager"
        )

        binding?.buttonGroup?.setOnPositionChangedListener {
            when(it){
                0 ->{
                    binding?.rlSubscription?.visible()
                    binding?.rlPayPerUse?.gone()
                }
                1 ->{
                    binding?.rlSubscription?.gone()
                    binding?.rlPayPerUse?.visible()
                }
            }
        }

    }

    private fun SetRecycler(){
        mAdapter = OrderAdapter(this, list)
        binding?.recyclerView?.layoutManager = GridLayoutManager(this,2)
        binding?.recyclerView?.adapter = mAdapter
        mAdapter?.notifyDataSetChanged()
    }

    private fun setTopBar(){
        binding?.topBar?.rlSearch?.gone()
        binding?.topBar?.rlCounter?.gone()
        binding?.topBar?.tvText?.text = "Order"
        binding?.topBar?.ivAdd?.setImageResource(R.drawable.ic_back)
        binding?.topBar?.ivAdd?.setOnClickListener{
            onBackPressed()
        }
    }

    private fun setSideMenuCLicks(){
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
                this@OrderActivity,
                mTopDataSideItem
            ) }

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
                    this@OrderActivity,
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

    private fun setDummyData(){
        list.add(
            OrderModel(
                "1",
                "Rent\nEstimation",
                R.drawable.ic_rent_estimate
            )
        )
        list.add(
            OrderModel(
                "2",
                "Credit\nCheck",
                R.drawable.ic_credit_check
            )
        )
        list.add(
            OrderModel(
                "3",
                "Insurance",
                R.drawable.ic_insurance
            )
        )
        list.add(
            OrderModel(
                "4",
                "Digital\nInspection",
                R.drawable.ic_digital_inspection
            )
        )
        list.add(
            OrderModel(
                "5",
                "Digital\nLease & Forms",
                R.drawable.ic_digital_lease
            )
        )
        list.add(
            OrderModel(
                "6",
                "Data\nBackup",
                R.drawable.ic_data_backup
            )
        )
        list.add(
            OrderModel(
                "7",
                "Data\nImport",
                R.drawable.ic_data_import
            )
        )
        list.add(
            OrderModel(
                "8",
                "Rent\nCollection",
                R.drawable.ic_rent_collection
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
enum class OrderItem {
    Subscription, PayPerUse
}