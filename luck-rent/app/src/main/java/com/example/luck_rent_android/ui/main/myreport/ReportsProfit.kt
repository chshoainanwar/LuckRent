package com.example.luck_rent_android.ui.main.myreport

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityReportsProfitBinding
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.profile.Profile.Companion.roleProfile
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.example.luck_rent_android.ui.main.spinn.MaterialSpinner
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import com.kodextech.project.kodexlib.utils.visible
import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ReportsProfit : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var binding: ActivityReportsProfitBinding? = null

    private var mDataSideItem = ArrayList<SideItems>()
    private var mTopDataSideItem = ArrayList<SideItems>()
    var position: Int? = 0
    private var status: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reports_profit)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))

        SetTopBar()

        binding?.spProperty?.setItems(
            "02351 - 21251",
            "02351 - 21252",
            "02351 - 21253",
            "02351 - 21254"
        )
        binding?.spOwner?.setItems(
            "James",
            "John",
            "Robert"
        )

        when (roleProfile) {
            "Landlord" -> {
                binding?.tvOwner?.gone()
                binding?.spLayoutOwner?.gone()
            }
            "Property Manager" -> {
                binding?.tvOwner?.visible()
                binding?.spLayoutOwner?.visible()
            }
        }

        position = intent.getIntExtra("pos", 0)
        if (position == 0) {
            binding?.layoutProfitLose?.gone()
            binding?.layoutReportInvoice?.gone()
            binding?.layoutReportsBalance?.visible()
            binding?.layoutReportsRolls?.gone()
            binding?.layoutReportsIncome?.gone()
            binding?.layoutCashFlow?.gone()
            binding?.tvProfit?.text = "Rent Balance"
            binding?.tvProfitLoss?.text = "1 Sep 2021        Balance - $1600.00"
            binding?.topBar?.tvText?.text = "Rent Balance"
        } else if (position == 1) {
            binding?.layoutProfitLose?.gone()
            binding?.layoutReportInvoice?.visible()
            binding?.layoutReportsBalance?.gone()
            binding?.layoutReportsRolls?.gone()
            binding?.layoutReportsIncome?.gone()
            binding?.layoutCashFlow?.gone()
            binding?.tvProfit?.text = "Rent Invoice"
            binding?.tvProfitLoss?.text = "2021-08-28 - 1112-908 Quayside"
            when (roleProfile) {
                "Landlord" -> {
                    binding?.topBar?.tvText?.text = "Rent Invoice"

                }
                "Property Manager" -> {
                    binding?.topBar?.tvText?.text = "Invoice"
                    binding?.tvInvoiceType?.visible()
                    binding?.spLayoutInvoice?.visible()
                    binding?.tvStatus?.visible()
                    binding?.llStatus ?.visible()

                    binding?.spInvoiceType?.setItems("Rent Invoice","Management Fee")
                    binding?.spInvoiceType?.setOnItemSelectedListener { view, position1, id, item ->
                        if (position1 == 0) {
                            binding?.tvProfit?.text = "Rent Invoice"
                            binding?.tvProfitLoss?.text = "1 Jan 2020     To     31 Dec 2020"
                        } else if (position1 == 1) {
                            binding?.tvProfit?.text = "Management Fee"
                            binding?.tvProfitLoss?.text = "1 Jan 2020     To     31 Dec 2020"

                        }
                    }
                    binding?.spStatus?.setItems("Paid","Unpaid")
                    binding?.spStatus?.setOnItemSelectedListener { view, position2, id, item ->
                        if (position2 == 0) {
                            binding?.btnMark?.gone()
                        } else if (position2 == 1) {
                          binding?.btnMark?.visible()

                        }
                    }
                }
            }

        } else if (position == 2) {
            binding?.layoutProfitLose?.visible()
            binding?.layoutReportInvoice?.gone()
            binding?.layoutReportsBalance?.gone()
            binding?.layoutReportsRolls?.visible()
            binding?.layoutReportsIncome?.visible()
            binding?.incomeTop?.visible()
            binding?.rollsTop?.visible()
            binding?.cashFlowTop?.visible()
            binding?.layoutCashFlow?.visible()
            binding?.topBar?.tvText?.text = "Tax Preparation"

        } else if (position == 3) {
            binding?.layoutProfitLose?.gone()
            binding?.layoutReportInvoice?.gone()
            binding?.layoutReportsBalance?.gone()
            binding?.layoutReportsRolls?.gone()
            binding?.layoutReportsIncome?.visible()
            binding?.layoutCashFlow?.gone()
            binding?.tvProfit?.text = "Income Statement"
            binding?.tvProfitLoss?.text = "1 Jan 2020     To     31 Dec 2020"
            binding?.topBar?.tvText?.text = "Income Statement"
        } else if (position == 4) {
            binding?.layoutProfitLose?.gone()
            binding?.layoutReportInvoice?.gone()
            binding?.layoutReportsBalance?.gone()
            binding?.layoutReportsRolls?.visible()
            binding?.layoutReportsIncome?.gone()
            binding?.layoutCashFlow?.gone()
            binding?.tvProfit?.text = "Rent Roll"
            binding?.tvProfitLoss?.text = "September 1, 2021"
            binding?.topBar?.tvText?.text = "Rent Rolls"
        } else if (position == 5) {
            binding?.layoutProfitLose?.gone()
            binding?.layoutReportInvoice?.gone()
            binding?.layoutReportsBalance?.gone()
            binding?.layoutReportsRolls?.gone()
            binding?.layoutReportsIncome?.gone()
            binding?.layoutCashFlow?.visible()
            binding?.tvProfit?.text = "Net Cash Flow Report"
            binding?.tvProfitLoss?.text = "1 Jan 2020     To     31 Dec 2020"
            binding?.topBar?.tvText?.text = "Net CashFlow Report"
        }

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

        binding?.dashboardNavMenuLayout?.clickaBleNav?.setOnClickListener {
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
        binding?.dashboardNavMenuLayout?.rlProfilePic?.setOnClickListener {

        }
        binding?.dashboardNavMenuLayout?.tvName?.setOnClickListener {

        }

        binding?.topBar?.ivMenu?.setOnClickListener {
            sideMenuDrawer(
                binding?.dashboardDrawerLayout,
                mDataSideItem,
                binding?.dashboardNavMenuLayout,
                binding?.dashboardNavView,
                this@ReportsProfit,
                mTopDataSideItem
            )
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
                sideMenuDrawer(
                    binding?.dashboardDrawerLayout,
                    mDataSideItem,
                    binding?.dashboardNavMenuLayout,
                    binding?.dashboardNavView,
                    this@ReportsProfit,
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
        binding?.topBar?.ivAdd?.setOnClickListener {
            onBackPressed()
        }

        binding?.btnShare?.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "\n" +
                        "LuckRent"
            )
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }

        binding?.tvFirstDate?.setOnClickListener {

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, day ->

                    // Display Selected date in TextView
                    val monthi = month.plus(1).toString()
                    if (monthi.toInt() > 9) {
                        binding?.tvFirstDate?.text = "$year-$monthi-$day"
                    } else {
                        binding?.tvFirstDate?.text = "$year-0$monthi-$day"

                    }

                }, year, month, day
            )
//            dpd.datePicker.minDate = System.currentTimeMillis()
            dpd.show()
        }

        binding?.tvSecondDate?.setOnClickListener {

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, day ->

                    // Display Selected date in TextView
                    val monthi = month.plus(1).toString()
                    if (monthi.toInt() > 9) {
                        binding?.tvSecondDate?.text = "$year-$monthi-$day"
                    } else {
                        binding?.tvSecondDate?.text = "$year-0$monthi-$day"

                    }

                }, year, month, day
            )
//            dpd.datePicker.minDate = System.currentTimeMillis()
            dpd.show()
        }

    }

    private fun SetTopBar() {
        binding?.topBar?.rlSearch?.gone()
        binding?.topBar?.rlCounter?.gone()
        binding?.topBar?.tvText?.text = "Reports Profit"
        binding?.topBar?.ivAdd?.setImageResource(R.drawable.ic_back)
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