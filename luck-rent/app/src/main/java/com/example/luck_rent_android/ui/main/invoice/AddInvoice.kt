package com.example.luck_rent_android.ui.main.invoice

import android.app.DatePickerDialog
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
import com.example.luck_rent_android.databinding.ActivityAddInvoiceBinding
import com.example.luck_rent_android.ui.main.auth.Login
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.contractormodule.ContractorInvoice
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
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

class AddInvoice : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var binding: ActivityAddInvoiceBinding? = null
    private var mDataSideItem = ArrayList<SideItems>()
    private var mTopDataSideItem = ArrayList<SideItems>()
    override fun onCreate(savedInstanceState: Bundle?) {
        statusBarColor(getColor(R.color.darkBlue))
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_invoice)
        super.onCreate(savedInstanceState)

        initTopBar()
        binding?.topBar?.ivAdd?.setOnClickListener { onBackPressed() }

        binding?.btnPreview?.setOnClickListener {
            val intent = Intent(this, InvoicePreview::class.java)
            startActivity(intent)
        }

        when(roleProfile){
            "Landlord" ->{
//                binding?.btnSave?.text = "Save"
            }
            "Property Manager" ->{
                binding?.tvInvoiceType?.visible()
                binding?.llInvoiceType?.visible()
                binding?.rlAutoManagementInvoices?.visible()
                binding?.rlAutoManagementFees?.visible()
                binding?.rlFirst?.visible()
                binding?.tvReminder?.visible()
            }
            "Contractor" ->{
//                binding?.btnSave?.text = "Send"
//                binding?.llInvoiceType?.gone(
            //               )

                binding?.topBar?.tvText?.text = "Create Invoice"
            }
        }


//        binding?.btnSave?.setOnClickListener {
//
//            if (roleProfile == "Landlord") {
//                val intent = Intent(this, InvoiceDetail::class.java)
//                startActivity(intent)
//                finish()
//            } else if (roleProfile == "Contractor") {
//                openDialog()
//            }else if(roleProfile == "Property Manager"){
//                val intent = Intent(this, InvoiceDetail::class.java)
//                startActivity(intent)
//                finish()
//            }
//
//        }

        binding?.tvInvoiceIssues?.setOnClickListener{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, month, day ->

                    // Display Selected date in TextView
                    val monthi = month.plus(1).toString()
                    if (monthi.toInt() > 9) {
                        binding?.tvInvoiceIssues?.text = "$year-$monthi-$day"
                    }else{
                        binding?.tvInvoiceIssues?.text = "$year-0$monthi-$day"

                    }

                }, year, month, day
            )
//            dpd.datePicker.minDate = System.currentTimeMillis()
            dpd.show()
        }
        binding?.tvInvoiceDue?.setOnClickListener{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, month, day ->

                    // Display Selected date in TextView
                    val monthi = month.plus(1).toString()
                    if (monthi.toInt() > 9) {
                        binding?.tvInvoiceDue?.text = "$year-$monthi-$day"
                    }else{
                        binding?.tvInvoiceDue?.text = "$year-0$monthi-$day"

                    }

                }, year, month, day
            )
//            dpd.datePicker.minDate = System.currentTimeMillis()
            dpd.show()
        }

        binding?.tvInvoiceIssues?.setOnClickListener{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, month, day ->

                    // Display Selected date in TextView
                    val monthi = month.plus(1).toString()
                    if (monthi.toInt() > 9) {
                        binding?.tvInvoiceIssues?.text = "$year-$monthi-$day"
                    }else{
                        binding?.tvInvoiceIssues?.text = "$year-0$monthi-$day"

                    }

                }, year, month, day
            )
//            dpd.datePicker.minDate = System.currentTimeMillis()
            dpd.show()
        }

        binding?.spRecipient?.setItems(
            "Devang",
            "Tenant"
        )
        binding?.spAddress?.setItems(
            "33 street US",
            "34 street US"
        )
        binding?.spInvoiceType?.setItems(
            "Rent Invoice",
            "Management Invoice"
        )

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
                            this@AddInvoice,
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
                            this@AddInvoice,
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


    private fun initTopBar() {
        binding?.topBar?.tvText?.text = "Add Invoice"
        binding?.topBar?.ivAdd?.setImageResource(R.drawable.ic_back)
        binding?.topBar?.rlCounter?.gone()
        binding?.topBar?.rlSearch?.gone()
        binding?.topBar?.ivAdd?.setOnClickListener { onBackPressed() }

//        binding?.spInvoiceType?.setItems(
//            "Rent",
//            "Pro Rated Rent",
//            "Bad Dept",
//            "Capital Gain",
//            "Clubhouse",
//            "Damage/Deposit Security",
//            "Rent Deposit",
//            "Extra Tenant",
//            "Guest Suite",
//            "Management Fee",
//            "Move In/Move Out Fee",
//            "Damages",
//            "Deposit Not Refundable",
//            "Late Payment Fee",
//            "Laundry",
//            "Liquidated Damages",
//            "NSF Fee",
//            "Parking",
//            "Partial Rent",
//            "Pet Deposit",
//            "Meeting Room",
//            "Storage Locker",
//            "Utilities Fees",
//            "Other",
//            "Tax",
//            "Lease up Fee",
//            "Rental Management Fee",
//            "Tenant Screening Fee"
//        )
    }

    private fun openDialog() {
        val view = View.inflate(this, R.layout.invoice_dialog, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()


        //Register Done Button & Cancel Icon from Dialog
        var cancelIcon = view?.findViewById<ImageView>(R.id.cancelIcon)

        //Dialog Cancel Icon
        cancelIcon?.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(this, ContractorInvoice::class.java)
            startActivity(intent)
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