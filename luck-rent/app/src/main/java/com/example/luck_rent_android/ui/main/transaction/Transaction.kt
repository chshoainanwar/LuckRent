package com.example.luck_rent_android.ui.main.transaction

import android.content.Intent
import android.icu.lang.UCharacter
import androidx.appcompat.app.AppCompatActivity
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
import com.example.luck_rent_android.databinding.ActivityTransactionBinding
import com.example.luck_rent_android.model.ExpenseModel
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.expense.adapter.ExpenseListingAdapter
import com.example.luck_rent_android.ui.main.inspectionreport.InspectionItem
import com.example.luck_rent_android.ui.main.inspectionreport.InspectionReport
import com.example.luck_rent_android.ui.main.order.adapter.OrderAdapter
import com.example.luck_rent_android.ui.main.order.adapter.OrderAdapterVH
import com.example.luck_rent_android.ui.main.order.model.OrderModel
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.example.luck_rent_android.ui.main.transaction.adapter.TransactionAdapter
import com.example.luck_rent_android.ui.main.transaction.model.TransactionModel
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import com.kodextech.project.kodexlib.utils.visible
import java.lang.IllegalStateException
import java.util.ArrayList

class Transaction : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var binding: ActivityTransactionBinding? = null

    companion object{
           var enumState: TransactionItem = TransactionItem.Invoices
    }

    private var mDataSideItem = ArrayList<SideItems>()
    private var mTopDataSideItem = ArrayList<SideItems>()

    val list = ArrayList<TransactionModel>()
    var adapter: TransactionAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null

    // Expense Adapter
    private var eAdapter: ExpenseListingAdapter? = null
    private var eList = ArrayList<ExpenseModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_transaction)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))

        topButtons()
        setTopBar()
        sideMenuClicks()
        checkProfileState()
        setRecycler()
        setExpenseRecycler()
        setDummyData()
        setExpenseDummyData()

    }

    private fun topButtons() {
        binding?.cvInvoices?.setOnClickListener {
            enumState = TransactionItem.Invoices
            checkProfileState()
//            roleTransaction = "Invoices"
            binding?.tvHeading?.text = "Invoices"
            binding?.rvExpense?.gone()
            binding?.recycler?.visible()
        }
        binding?.cvPayments?.setOnClickListener {
            enumState = TransactionItem.Payments
            checkProfileState()
//            roleTransaction = "Payments"
            binding?.tvHeading?.text = "Payments"
            binding?.rvExpense?.gone()
            binding?.recycler?.visible()
        }
        binding?.cvExpenses?.setOnClickListener {
            enumState = TransactionItem.Expenses
            checkProfileState()
//            roleTransaction = "Expenses"
            binding?.tvHeading?.text = "Expenses"
            binding?.rvExpense?.visible()
            binding?.recycler?.gone()
        }
    }

    private fun setRecycler() {
        //Invoice & Payment
        adapter = TransactionAdapter(this, list)
        binding?.recycler?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding?.recycler?.adapter = adapter
        adapter?.notifyDataSetChanged()
    }

    private fun setExpenseRecycler() {
        eAdapter = ExpenseListingAdapter(this, eList)
        binding?.rvExpense?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding?.rvExpense?.adapter = eAdapter
        eAdapter?.notifyDataSetChanged()
    }

    private fun setTopBar() {
        binding?.topBar?.rlSearch?.gone()
        binding?.topBar?.rlCounter?.gone()
        binding?.topBar?.tvText?.text = "Transactions"
        binding?.topBar?.ivAdd?.setImageResource(R.drawable.ic_back)
    }

    private fun sideMenuClicks() {
        binding?.topBar?.ivAdd?.setOnClickListener {
            onBackPressed()
        }
        binding?.dashboardNavMenuLayout?.ivCross?.setOnClickListener {
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
        binding?.dashboardNavMenuLayout?.clickaBleNav?.setOnClickListener {
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
                this@Transaction,
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
                    this@Transaction,
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

    private fun setDummyData() {
        when (enumState) {
            TransactionItem.Invoices -> {
                list.add(
                    TransactionModel(
                        "1",
                        "Thomas Burk",
                        "2021/11/23",
                        "Unpaid",
                        "$1600.00"
                    )
                )
                list.add(
                    TransactionModel(
                        "2",
                        "Susan Miles",
                        "2021/11/23",
                        "Paid",
                        "$1600.00"
                    )
                )
                list.add(
                    TransactionModel(
                        "3",
                        "Thomas Burk",
                        "2021/11/23",
                        "Unpaid",
                        "$1600.00"
                    )
                )
            }
            TransactionItem.Payments -> {
                list.add(
                    TransactionModel(
                        "1",
                        "George Ariles",
                        "Paid 2021/11/23",
                        "PAD",
                        "$1500.00"
                    )
                )
                list.add(
                    TransactionModel(
                        "2",
                        "Susan Miles",
                        "Paid 2021/11/23",
                        "Cash",
                        "$1600.00"
                    )
                )
                list.add(
                    TransactionModel(
                        "3",
                        "Thomas Burk",
                        "2021/11/23",
                        "Unpaid",
                        "$1600.00"
                    )
                )
            }
        }
    }

    private fun setExpenseDummyData(){
        eList.add(ExpenseModel("132 Road","Repairs", "206","$788.00"))
        eList.add(ExpenseModel("1529 Mermaid St","Insurance", "n/a","$1128.00"))
    }

    fun checkProfileState() {
        when (enumState) {
            TransactionItem.Invoices -> {
                binding?.tvInvoices?.setBackgroundColor(getColor(R.color.orange))
                binding?.tvPayments?.setBackgroundColor(getColor(R.color.f3White))
                binding?.tvExpenses?.setBackgroundColor(getColor(R.color.f3White))

                binding?.tvInvoices?.setTextColor(getColor(R.color.white))
                binding?.tvPayments?.setTextColor(getColor(R.color.bg_text))
                binding?.tvExpenses?.setTextColor(getColor(R.color.bg_text))


            }
            TransactionItem.Payments -> {
                binding?.tvPayments?.setBackgroundColor(getColor(R.color.orange))
                binding?.tvInvoices?.setBackgroundColor(getColor(R.color.f3White))
                binding?.tvExpenses?.setBackgroundColor(getColor(R.color.f3White))

                binding?.tvPayments?.setTextColor(getColor(R.color.white))
                binding?.tvInvoices?.setTextColor(getColor(R.color.bg_text))
                binding?.tvExpenses?.setTextColor(getColor(R.color.bg_text))

            }

            TransactionItem.Expenses -> {
                binding?.tvPayments?.setBackgroundColor(getColor(R.color.f3White))
                binding?.tvInvoices?.setBackgroundColor(getColor(R.color.f3White))
                binding?.tvExpenses?.setBackgroundColor(getColor(R.color.orange))

                binding?.tvPayments?.setTextColor(getColor(R.color.bg_text))
                binding?.tvInvoices?.setTextColor(getColor(R.color.bg_text))
                binding?.tvExpenses?.setTextColor(getColor(R.color.white))

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
        binding?.dashboardDrawerLayout?.closeDrawer(GravityCompat.START)
        return true
    }
}

enum class TransactionItem {
    Invoices, Payments, Expenses
}