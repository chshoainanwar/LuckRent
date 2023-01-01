package com.example.luck_rent_android.ui.main.contractormodule

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityContractorDashboardBinding
import com.example.luck_rent_android.ui.main.contractormodule.adapter.ContractorAdsAdapter
import com.example.luck_rent_android.ui.main.contractormodule.adapter.JobCompleteAdapter
import com.example.luck_rent_android.ui.main.contractormodule.model.JobCompleteModel
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.invoice.AddInvoice
import com.example.luck_rent_android.ui.main.notification.NotificationActivity
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.example.luck_rent_android.ui.main.sideMenu.SideMenuAdapter
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import java.lang.IllegalStateException

class ContractorDashboard : BaseActivity(), NavigationView.OnNavigationItemSelectedListener  {
    private var binding : ActivityContractorDashboardBinding? = null
    private var enumState : ItemChoose = ItemChoose.Mark_Invoice

    private var reminderState: String? = null
    private var mDataSideItemCon = ArrayList<SideItems>()
    private var mTopDataSideItemCon = ArrayList<SideItems>()
    private var mSideAdapterCon: SideMenuAdapter? = null
    var isNotification : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contractor_dashboard)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))

        allTask()
        setTopBar()
        sideMenuCLciks()
        checkChangeState()
        notification()

        binding?.cvCreateInvoice?.setOnClickListener {
            enumState = ItemChoose.Create_Invoice
            checkChangeState()

            val intent = Intent(this, AddInvoice::class.java)
            intent.putExtra("ContractorDashboard","Invoice")
            startActivity(intent)

        }
        binding?.cvMarkInvoice?.setOnClickListener {
            enumState = ItemChoose.Mark_Invoice
            checkChangeState()

            val intent = Intent(this, ContractorInvoice::class.java)
            startActivity(intent)
        }


    }


    private fun setTopBar(){
        binding?.topBar?.rlSearch?.gone()
        binding?.topBar?.ivAdd?.gone()
        binding?.topBar?.rlCounter?.setOnClickListener {
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun allTask(){
        binding?.tvByProperty?.setOnClickListener {
            reminderState = "byProperty"
            checkReminderState()

            val intent = Intent(this, ContractorJobComplete::class.java)
            intent.putExtra("ContractorDashboard","Property")
            startActivity(intent)
        }
        binding?.tvByType?.setOnClickListener {
            reminderState = "byType"
            checkReminderState()

            val intent = Intent(this, ContractorJobComplete::class.java)
            intent.putExtra("ContractorDashboard", "TYPE")
            startActivity(intent)
        }
        binding?.tvByStatus?.setOnClickListener {
            reminderState = "byStatus"
            checkReminderState()

            val intent = Intent(this, ContractorJobComplete::class.java)
            intent.putExtra("ContractorDashboard", "STATUS")
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

    private fun sideMenuCLciks(){
        binding?.dashboardNavMenuLayoutForContractor?.ivCross?.setOnClickListener{
            if (Profile.roleProfile == "Contractor") {
                val intent = Intent(this, ContractorDashboard::class.java)
                startActivity(intent)
            }
        }
        binding?.dashboardNavMenuLayoutForContractor?.clickaBleNav?.setOnClickListener{
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
        binding?.dashboardNavMenuLayoutForContractor?.rlProfilePic?.setOnClickListener{

        }
        binding?.dashboardNavMenuLayoutForContractor?.tvName?.setOnClickListener{

        }
        binding?.topBar?.rlMenu?.setOnClickListener {

            sideMenuDrawerForContractorDas(
                binding?.dashboardDrawerLayoutContractor,
                mDataSideItemCon,
                binding?.dashboardNavMenuLayoutForContractor,
                binding?.dashboardNavViewForContractor,
                this,
                mTopDataSideItemCon
            )

        }
        binding?.content?.setOnClickListener {
            try {
                if (binding?.dashboardDrawerLayoutContractor?.isDrawerOpen(GravityCompat.END) == true) {
                    binding?.dashboardDrawerLayoutContractor?.closeDrawer(GravityCompat.END)
                }
            }catch (e: IllegalStateException){
                e.printStackTrace()
            }

        }
        binding?.dashboardDrawerLayoutContractor?.addDrawerListener(object : DrawerLayout.DrawerListener{
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {

                sideMenuDrawerForContractorDas(
                    binding?.dashboardDrawerLayoutContractor,
                    mDataSideItemCon,
                    binding?.dashboardNavMenuLayoutForContractor,
                    binding?.dashboardNavViewForContractor,
                    this@ContractorDashboard,
                    mTopDataSideItemCon,
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

    private fun checkChangeState(){
        when(enumState){

//            ItemChoose.Job_Complete ->{
//                binding?.layoutJobComplete?.setBackgroundColor(getColor(R.color.orange))
//                binding?.tvJobComplete?.setTextColor(getColor(R.color.white))
//                binding?.ivJob?.setColorFilter(getColor(R.color.white))
//
//                binding?.layoutMarkInvoice?.setBackgroundColor(getColor(R.color.white))
//                binding?.tvMarkInvoice?.setTextColor(getColor(R.color.bg_text))
//                binding?.ivMarkInvoice?.setColorFilter(getColor(R.color.orange))
//
//                binding?.layoutCreateInvoice?.setBackgroundColor(getColor(R.color.white))
//                binding?.tvCreateInvoice?.setTextColor(getColor(R.color.bg_text))
//                binding?.ivCreateInvoice?.setColorFilter(getColor(R.color.orange))
//
//            }

            ItemChoose.Create_Invoice ->{
                binding?.layoutCreateInvoice?.setBackgroundColor(getColor(R.color.orange))
                binding?.tvCreateInvoice?.setTextColor(getColor(R.color.white))
                binding?.ivCreateInvoice?.setColorFilter(getColor(R.color.white))

//                binding?.layoutJobComplete?.setBackgroundColor(getColor(R.color.white))
//                binding?.tvJobComplete?.setTextColor(getColor(R.color.bg_text))
//                binding?.ivJob?.setColorFilter(getColor(R.color.orange))

                binding?.layoutMarkInvoice?.setBackgroundColor(getColor(R.color.white))
                binding?.tvMarkInvoice?.setTextColor(getColor(R.color.bg_text))
                binding?.ivMarkInvoice?.setColorFilter(getColor(R.color.orange))

            }
            ItemChoose.Mark_Invoice ->{
                binding?.layoutMarkInvoice?.setBackgroundColor(getColor(R.color.orange))
                binding?.tvMarkInvoice?.setTextColor(getColor(R.color.white))
                binding?.ivMarkInvoice?.setColorFilter(getColor(R.color.white))

//                binding?.layoutJobComplete?.setBackgroundColor(getColor(R.color.white))
//                binding?.tvJobComplete?.setTextColor(getColor(R.color.bg_text))
//                binding?.ivJob?.setColorFilter(getColor(R.color.orange))

                binding?.layoutCreateInvoice?.setBackgroundColor(getColor(R.color.white))
                binding?.tvCreateInvoice?.setTextColor(getColor(R.color.bg_text))
                binding?.ivCreateInvoice?.setColorFilter(getColor(R.color.orange))

            }
        }
    }

    private fun checkReminderState() {
        when (reminderState) {
            "byProperty" -> {
                binding?.tvByProperty?.setTextColor(getColor(R.color.white))
                binding?.tvByType?.setTextColor(getColor(R.color.reminder))
                binding?.tvByStatus?.setTextColor(getColor(R.color.reminder))

                binding?.cvProperty?.setCardBackgroundColor(getColor(R.color.darkBlue))
                binding?.cvType?.setCardBackgroundColor(getColor(R.color.white))
                binding?.cvStatus?.setCardBackgroundColor(getColor(R.color.white))

            }
            "byType" -> {
                binding?.tvByType?.setTextColor(getColor(R.color.white))
                binding?.tvByStatus?.setTextColor(getColor(R.color.reminder))
                binding?.tvByProperty?.setTextColor(getColor(R.color.reminder))

                binding?.cvType?.setCardBackgroundColor(getColor(R.color.darkBlue))
                binding?.cvProperty?.setCardBackgroundColor(getColor(R.color.white))
                binding?.cvStatus?.setCardBackgroundColor(getColor(R.color.white))
            }
            "byStatus" -> {
                binding?.tvByStatus?.setTextColor(getColor(R.color.white))
                binding?.tvByProperty?.setTextColor(getColor(R.color.reminder))
                binding?.tvByType?.setTextColor(getColor(R.color.reminder))

                binding?.cvStatus?.setCardBackgroundColor(getColor(R.color.darkBlue))
                binding?.cvProperty?.setCardBackgroundColor(getColor(R.color.white))
                binding?.cvType?.setCardBackgroundColor(getColor(R.color.white))
            }
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
enum class ItemChoose{
    Create_Invoice, Mark_Invoice
}