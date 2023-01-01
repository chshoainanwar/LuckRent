package com.example.luck_rent_android.ui.main.rentermodule

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
import com.example.luck_rent_android.databinding.ActivityRenterDashboardBinding
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.contractormodule.ContractorInvoice
import com.example.luck_rent_android.ui.main.contractormodule.ContractorRentProperty
import com.example.luck_rent_android.ui.main.contractormodule.adapter.ContractorInvoiceAdapter
import com.example.luck_rent_android.ui.main.contractormodule.model.ContractorInvoiceModel
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.notification.NotificationActivity
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import java.lang.IllegalStateException

class RenterDashboard : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var binding : ActivityRenterDashboardBinding? = null
    private var enumState : ChooseItem = ChooseItem.Paid_invoices
    var isNotification : Boolean = false

    var adapter : ContractorInvoiceAdapter? = null
    var list = ArrayList<ContractorInvoiceModel>()
    var layoutManager : RecyclerView.LayoutManager? = null
    var mAdapter : RecyclerView.Adapter<ContractorInvoiceAdapter.ViewHolderCI>? = null



    private var mDataSideItemRenter = ArrayList<SideItems>()
    private var mTopDataSideItemRenter = ArrayList<SideItems>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_renter_dashboard)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))

        setTopBar()
        setRecyclerView()
        checkEnumState()
        sideMenuClicks()
        mainCardViews()
        notification()


        binding?.tvSeeAll?.setOnClickListener {
            val intent = Intent(this,ContractorInvoice::class.java)
            startActivity(intent)
        }
        binding?.btnView?.setOnClickListener{
            val intent = Intent(this,ContractorRentProperty::class.java)
            intent.putExtra("FromDashboard","ToProperties")
            startActivity(intent)
        }

    }

    private fun setTopBar(){
        binding?.topBar?.ivAdd?.gone()
        binding?.topBar?.rlSearch?.gone()
        binding?.topBar?.rlCounter?.setOnClickListener{
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun mainCardViews(){
        binding?.cvUnpaidInvoice?.setOnClickListener{
            enumState = ChooseItem.Make_Payment
            checkEnumState()
            val intent = Intent(this,RenterUnpaidInvoice::class.java)
            startActivity(intent)
        }
        binding?.cvRequestMaintenance?.setOnClickListener{
            enumState = ChooseItem.Request_Maintenance
            checkEnumState()
            val intent = Intent(this,ContractorRentProperty::class.java)
            intent.putExtra("RenterDashboard","RequestMaintenance")
            startActivity(intent)
        }
        binding?.cvPaidInvoices?.setOnClickListener{
            enumState = ChooseItem.Paid_invoices
            checkEnumState()
            val intent = Intent(this,ContractorInvoice::class.java)
            intent.putExtra("RenterDashboardPaidInvoice","PaidInvoice")
            startActivity(intent)
        }
    }

    private fun notification(){
        binding?.btnTurnOnNotification?.setOnClickListener {
            if (isNotification == true){
                binding?.btnTurnOnNotification?.text = "TURN ON NOTIFICATION"
                isNotification = false
            }else{
                binding?.btnTurnOnNotification?.text = "TURN OFF NOTIFICATION"
                isNotification = true
            }
        }
    }

    private fun sideMenuClicks(){
        binding?.dashboardNavMenuLayoutForRenter?.ivCross?.setOnClickListener{
            if (Profile.roleProfile == "Renter") {
                val intent = Intent(this, RenterDashboard::class.java)
                startActivity(intent)
            }
        }
        binding?.dashboardNavMenuLayoutForRenter?.clickaBleNav?.setOnClickListener{
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
        binding?.dashboardNavMenuLayoutForRenter?.rlProfilePic?.setOnClickListener{

        }
        binding?.dashboardNavMenuLayoutForRenter?.tvName?.setOnClickListener{

        }
        binding?.topBar?.rlMenu?.setOnClickListener {

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
                    this@RenterDashboard,
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

    private fun setRecyclerView(){
        SetDummyData()

        var array = ArrayList<ContractorInvoiceModel>()
        array.clear()
        array.addAll(list.take(3))
//        var array = ArrayList<ContractorInvoiceModel>()
//        array.clear()
//        array.addAll(list.take(3))
        mAdapter = ContractorInvoiceAdapter(this, array)
        binding?.recycler?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding?.recycler?.adapter = mAdapter
        mAdapter?.notifyDataSetChanged()
    }

    private fun checkEnumState(){
        when(enumState){
            ChooseItem.Make_Payment ->{
                binding?.layoutMakePayment?.setBackgroundColor(getColor(R.color.orange))
                binding?.tvMakePayment?.setTextColor(getColor(R.color.white))
                binding?.ivMakePayment?.setColorFilter(getColor(R.color.white))

                binding?.layoutRequestMaintenance?.setBackgroundColor(getColor(R.color.white))
                binding?.tvRequestMaintenance?.setTextColor(getColor(R.color.bg_text))
                binding?.ivRequestMaintenance?.setColorFilter(getColor(R.color.orange))

                binding?.layoutPaidInvoices?.setBackgroundColor(getColor(R.color.white))
                binding?.tvPaidInvoice?.setTextColor(getColor(R.color.bg_text))
                binding?.ivPaidInvoices?.setColorFilter(getColor(R.color.orange))
            }
            ChooseItem.Request_Maintenance ->{
                binding?.layoutMakePayment?.setBackgroundColor(getColor(R.color.white))
                binding?.tvMakePayment?.setTextColor(getColor(R.color.bg_text))
                binding?.ivMakePayment?.setColorFilter(getColor(R.color.orange))

                binding?.layoutRequestMaintenance?.setBackgroundColor(getColor(R.color.orange))
                binding?.tvRequestMaintenance?.setTextColor(getColor(R.color.white))
                binding?.ivRequestMaintenance?.setColorFilter(getColor(R.color.white))

                binding?.layoutPaidInvoices?.setBackgroundColor(getColor(R.color.white))
                binding?.tvPaidInvoice?.setTextColor(getColor(R.color.bg_text))
                binding?.ivPaidInvoices?.setColorFilter(getColor(R.color.orange))
            }
            ChooseItem.Paid_invoices ->{
                binding?.layoutMakePayment?.setBackgroundColor(getColor(R.color.white))
                binding?.tvMakePayment?.setTextColor(getColor(R.color.bg_text))
                binding?.ivMakePayment?.setColorFilter(getColor(R.color.orange))

                binding?.layoutRequestMaintenance?.setBackgroundColor(getColor(R.color.white))
                binding?.tvRequestMaintenance?.setTextColor(getColor(R.color.bg_text))
                binding?.ivRequestMaintenance?.setColorFilter(getColor(R.color.orange))

                binding?.layoutPaidInvoices?.setBackgroundColor(getColor(R.color.orange))
                binding?.tvPaidInvoice?.setTextColor(getColor(R.color.white))
                binding?.ivPaidInvoices?.setColorFilter(getColor(R.color.white))
            }
        }
    }

    private fun SetDummyData(){
        list.add(
            ContractorInvoiceModel(
                "1",
                "Thomas Burk",
                "Jun 10, 2021",
                "Paid",
                "$325.00"
            )
        )
        list.add(
            ContractorInvoiceModel(
                "2",
                "Thomas Burk",
                "Jun 10, 2021",
                "Paid",
                "$325.00"
            )
        )
        list.add(
            ContractorInvoiceModel(
                "3",
                "Thomas Burk",
                "Jun 10, 2021",
                "Paid",
                "$325.00"
            )
        )
        list.add(
            ContractorInvoiceModel(
                "4",
                "Thomas Burk",
                "Jun 10, 2021",
                "Paid",
                "$325.00"
            )
        )
        list.add(
            ContractorInvoiceModel(
                "5",
                "Thomas Burk",
                "Jun 10, 2021",
                "Paid",
                "$325.00"
            )
        )
        list.add(
            ContractorInvoiceModel(
                "6",
                "Thomas Burk",
                "Jun 10, 2021",
                "Paid",
                "$325.00"
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
        binding?.dashboardDrawerLayoutRenter?.closeDrawer(GravityCompat.START)
        return true
    }
}

enum class ChooseItem{
    Make_Payment, Request_Maintenance, Paid_invoices
}