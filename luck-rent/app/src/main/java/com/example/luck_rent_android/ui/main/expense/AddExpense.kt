package com.example.luck_rent_android.ui.main.expense

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.luck_rent_android.R
import com.example.luck_rent_android.adapter.RenterPagerAdapter
import com.example.luck_rent_android.databinding.ActivityAddExpenseBinding
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.expense.fragment.AddExpenseFragmentOne
import com.example.luck_rent_android.ui.main.expense.fragment.AddExpenseFragmentOne.Companion.expenseCategory
import com.example.luck_rent_android.ui.main.expense.fragment.AddExpenseFragmentTwo
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.profile.Profile.Companion.roleProfile
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import java.io.File
import java.lang.IllegalStateException
import java.util.*

class AddExpense : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var binding: ActivityAddExpenseBinding? = null
    private var transactionNature: String? = null
    var isSelected: String? = null

    private var mDataSideItem = ArrayList<SideItems>()
    private var mTopDataSideItem = ArrayList<SideItems>()

    companion object {
        val mData = ArrayList<File>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        statusBarColor(getColor(R.color.darkBlue))
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_expense)
        super.onCreate(savedInstanceState)

        initTopBar()

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
                this@AddExpense,
                mTopDataSideItem
            )

        }

        binding?.contentAddExpense?.setOnClickListener {
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
                    this@AddExpense,
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

        val fragments: ArrayList<Fragment> = arrayListOf(
            AddExpenseFragmentOne(),
            AddExpenseFragmentTwo()
        )
        var elm = AddExpenseFragmentOne()
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, elm, "AddExpenseFragmentOne").addToBackStack(null).commit();

        val adapter = RenterPagerAdapter(fragments, this)
        binding?.viewPager?.adapter = adapter
        binding?.viewPager?.isUserInputEnabled = false;
        binding?.btnAddExpense?.setOnClickListener {

            if (binding?.btnAddExpense?.text == "Add Expense") {
                if (expenseCategory?.isNullOrEmpty() == true) {
                    showToast("Please Select Expense Category")
                } else {
                    binding?.btnAddExpense?.text = "Save"
                    var elm = AddExpenseFragmentTwo()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, elm, "AddExpenseFragmentTwo")
                        .addToBackStack("SecondFragment").commit();
                }

            } else {
                val intent = Intent(this, ExpenseDetail::class.java)
                startActivity(intent)
                finish()
            }

        }

        binding?.viewPager?.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    binding?.btnAddExpense?.text = "Add Expense"
                } else {
                    binding?.btnAddExpense?.text = "Save"
                }
            }
        })

        binding?.topBar?.ivAdd?.setOnClickListener {

            if (supportFragmentManager.backStackEntryCount > 1) {
                binding?.btnAddExpense?.text = "Add Expense"
                supportFragmentManager.popBackStack()
                supportFragmentManager.beginTransaction().remove(AddExpenseFragmentTwo())
                supportFragmentManager.beginTransaction().addToBackStack(null)
                supportFragmentManager.beginTransaction().commit()

            } else {
                finish()
            }

//            if (binding?.viewPager?.currentItem == 0) {
//                expenseCategory = ""
//                finish()
//            } else {
//                binding?.viewPager?.setCurrentItem(binding?.viewPager?.currentItem?.minus(1) ?:0 , true)
//            }


        }
//        binding?.topBar?.ivAdd?.setOnClickListener { onBackPressed() }

    }


    private fun initTopBar() {
        binding?.topBar?.tvText?.text = "Add Expense"
        binding?.topBar?.rlCounter?.gone()
        binding?.topBar?.rlSearch?.gone()
        binding?.topBar?.ivAdd?.setImageResource(R.drawable.ic_back)
    }

    override fun onSetupViewGroup() {
        mViewGroup = binding?.contentAddExpense
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


    override fun onBackPressed() {

        if (supportFragmentManager.backStackEntryCount > 1) {
            binding?.btnAddExpense?.text = "Add Expense"
            supportFragmentManager.popBackStack()
            supportFragmentManager.beginTransaction().remove(AddExpenseFragmentTwo())
            supportFragmentManager.beginTransaction().addToBackStack(null)
            supportFragmentManager.beginTransaction().commit()

        } else {
            finish()
        }


    }


}