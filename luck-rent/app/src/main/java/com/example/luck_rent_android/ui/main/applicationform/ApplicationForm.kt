package com.example.luck_rent_android.ui.main.applicationform

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.luck_rent_android.R
import com.example.luck_rent_android.adapter.RenterPagerAdapter
import com.example.luck_rent_android.databinding.ActivityApplicationFormBinding
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import java.lang.IllegalStateException

class ApplicationForm : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var binding: ActivityApplicationFormBinding? = null

    private var adapter: RenterPagerAdapter? = null

    private var mDataSideItem = ArrayList<SideItems>()
    private var mTopDataSideItem = ArrayList<SideItems>()

    private var mDataSideItemCon = ArrayList<SideItems>()
    private var mTopDataSideItemCon = ArrayList<SideItems>()

    private var mDataSideItemRenter = ArrayList<SideItems>()
    private var mTopDataSideItemRenter = ArrayList<SideItems>()

    val fragment1 = FirstFragment()
    val fragment2 = SecondFragment()
    val fragment3 = ThirdFragment()
    val fragment4 = FourthFragment()

    val fragments: ArrayList<Fragment> = ArrayList()
    val fragment0: ArrayList<Fragment> = ArrayList()

    private val pageCallBack = object :
        ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            Log.d("Coma BUF", "Coma BUF vp ${position}")
            if (position == 0) {
                fragment1.onResume()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_application_form)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))
        setTopBar()
        sideMenuClicks()
        setPagerAdapter()


    }

    private fun setTopBar() {
        binding?.topBar?.tvText?.text = "Application"
    }

    private fun setPagerAdapter() {
        fragments.add(fragment1)
        fragments.add(fragment2)
        fragments.add(fragment3)
        fragments.add(fragment4)
        adapter = RenterPagerAdapter(fragments, this)
        binding?.viewPager?.adapter = adapter
        binding?.indicator?.setViewPager2(binding?.viewPager!!)
        binding?.viewPager?.isSaveFromParentEnabled = false;
        binding?.viewPager?.isUserInputEnabled = false;
        binding?.viewPager?.offscreenPageLimit = 4
        binding?.viewPager?.registerOnPageChangeCallback(pageCallBack)

        if (binding?.viewPager?.currentItem == 2) {
            binding?.rlIndicator?.gravity = Gravity.END
            binding?.rlNextIcon?.gravity = Gravity.START
            binding?.nextIcon?.setImageResource(R.drawable.ic_back_new)
        } else {
            binding?.rlIndicator?.gravity = Gravity.START
            binding?.rlNextIcon?.gravity = Gravity.END
            binding?.nextIcon?.setImageResource(R.drawable.ic_nexticon)

        }

        binding?.nextIcon?.setOnClickListener {

            if (binding?.viewPager?.currentItem == 2) {
                binding?.rlIndicator?.gravity = Gravity.END
                binding?.rlNextIcon?.gravity = Gravity.START
                binding?.nextIcon?.setImageResource(R.drawable.ic_back_new)
            } else {
                binding?.rlIndicator?.gravity = Gravity.START
                binding?.rlNextIcon?.gravity = Gravity.END
                binding?.nextIcon?.setImageResource(R.drawable.ic_nexticon)

            }

            var currentPosition: Int = 0
            currentPosition = if (binding?.viewPager?.currentItem == 3) {
                binding?.viewPager?.currentItem?.minus(1) ?: 0
            } else {
                binding?.viewPager?.currentItem?.plus(1) ?: 0
            }

            binding?.viewPager?.currentItem = currentPosition

        }

        binding?.topBar?.backIcon?.setOnClickListener {

            if (binding?.viewPager?.currentItem == 0) {
                finish()
            } else {
                val currentPosition: Int = binding?.viewPager?.currentItem?.minus(1) ?: 0
                binding?.viewPager?.currentItem = currentPosition
            }


        }
    }

    private fun sideMenuClicks() {
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

            when (Profile.roleProfile) {
                "Landlord" -> {

                    sideMenuDrawer(
                        binding?.dashboardDrawerLayout,
                        mDataSideItem,
                        binding?.dashboardNavMenuLayout,
                        binding?.dashboardNavView,
                        this@ApplicationForm,
                        mTopDataSideItem
                    )

                }
                "Property Manager" -> {

                    sideMenuDrawer(
                        binding?.dashboardDrawerLayout,
                        mDataSideItem,
                        binding?.dashboardNavMenuLayout,
                        binding?.dashboardNavView,
                        this@ApplicationForm,
                        mTopDataSideItem
                    )

                }
                "Contractor" -> {
                    sideMenuDrawerForContractorDas(
                        binding?.dashboardDrawerLayout,
                        mDataSideItemCon,
                        binding?.dashboardNavMenuLayout,
                        binding?.dashboardNavView,
                        this@ApplicationForm,
                        mTopDataSideItemCon
                    )
                }
                "Renter" -> {
                    sideMenuDrawerForRenterDas(
                        binding?.dashboardDrawerLayout,
                        mDataSideItemRenter,
                        binding?.dashboardNavMenuLayout,
                        binding?.dashboardNavView,
                        this@ApplicationForm,
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
                when (Profile.roleProfile) {
                    "Landlord" -> {

                        sideMenuDrawer(
                            binding?.dashboardDrawerLayout,
                            mDataSideItem,
                            binding?.dashboardNavMenuLayout,
                            binding?.dashboardNavView,
                            this@ApplicationForm,
                            mTopDataSideItem,
                            true
                        )

                    }
                    "Property Manager" -> {

                        sideMenuDrawer(
                            binding?.dashboardDrawerLayout,
                            mDataSideItem,
                            binding?.dashboardNavMenuLayout,
                            binding?.dashboardNavView,
                            this@ApplicationForm,
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
                            this@ApplicationForm,
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
                            this@ApplicationForm,
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