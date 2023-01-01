package com.example.luck_rent_android.ui.main.renter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.luck_rent_android.R
import com.example.luck_rent_android.adapter.RenterPagerAdapter
import com.example.luck_rent_android.databinding.ActivityRenterBinding
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.profile.Profile.Companion.roleProfile
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import java.lang.IllegalStateException

class Renter : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var binding: ActivityRenterBinding? = null
    var adapter: RenterPagerAdapter? = null

    private var mDataSideItem = ArrayList<SideItems>()
    private var mTopDataSideItem = ArrayList<SideItems>()

    private var mDataSideItemCon = ArrayList<SideItems>()
    private var mTopDataSideItemCon = ArrayList<SideItems>()

    private var mDataSideItemRenter = ArrayList<SideItems>()
    private var mTopDataSideItemRenter = ArrayList<SideItems>()

    val biF = BasicInfoFragment()
    val rf = ReferencesFragment()
    val edf = EmploymentDetailFragment()
    val ecf = EmergencyContactFragment()
    val lf = LeaseFragment()
    val rpf = RentPaymentFragment()
    private val fragments: ArrayList<Fragment> = ArrayList()
    private val fragments1: ArrayList<Fragment> = ArrayList()
    private val pageCallBack = object :
        ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            Log.d("Coma BUF", "Coma BUF vp ${position}")
            if (position == 0) {
                biF.onResume()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        statusBarColor(getColor(R.color.darkBlue))
        binding = DataBindingUtil.setContentView(this, R.layout.activity_renter)
        super.onCreate(savedInstanceState)
        sideMenuClicks()

        when(roleProfile){
            "Renter" ->{
                setRenterSidePager()
            }
            "Landlord" ->{
                setLandlordSidePager()
            }
            "Property Manager" ->{
                setPropertySidePager()
            }
        }
    }

    private fun setLandlordSidePager(){
        fragments.add(biF)
        fragments.add(rf)
        fragments.add(edf)
        fragments.add(ecf)
        fragments.add(lf)
        fragments.add(rpf)

        adapter = RenterPagerAdapter(fragments, this)
        binding?.viewPager?.adapter = adapter
        binding?.indicator?.setViewPager2(binding?.viewPager!!)
        binding?.viewPager?.isSaveFromParentEnabled = false;
        binding?.viewPager?.isUserInputEnabled = false;
        binding?.viewPager?.offscreenPageLimit = 6
        binding?.viewPager?.registerOnPageChangeCallback(pageCallBack)

        if (binding?.viewPager?.currentItem == 4) {
            binding?.rlIndicator?.gravity = Gravity.END
            binding?.rlNextIcon?.gravity = Gravity.START
            binding?.nextIcon?.setImageResource(R.drawable.ic_back_new)
        } else {
            binding?.rlIndicator?.gravity = Gravity.START
            binding?.rlNextIcon?.gravity = Gravity.END
            binding?.nextIcon?.setImageResource(R.drawable.ic_nexticon)

        }

        binding?.nextIcon?.setOnClickListener {

            if (binding?.viewPager?.currentItem == 4) {
                binding?.rlIndicator?.gravity = Gravity.END
                binding?.rlNextIcon?.gravity = Gravity.START
                binding?.nextIcon?.setImageResource(R.drawable.ic_back_new)
            } else {
                binding?.rlIndicator?.gravity = Gravity.START
                binding?.rlNextIcon?.gravity = Gravity.END
                binding?.nextIcon?.setImageResource(R.drawable.ic_nexticon)

            }

            var currentPosition: Int = 0
            currentPosition = if (binding?.viewPager?.currentItem == 5) {
                binding?.viewPager?.currentItem?.minus(1) ?: 0
            } else {
                binding?.viewPager?.currentItem?.plus(1) ?: 0
            }

//            binding?.viewPager?.adapter = null
//            binding?.viewPager?.adapter = adapter
            binding?.viewPager?.currentItem = currentPosition

        }

        binding?.topBar?.backIcon?.setOnClickListener {

            if (binding?.viewPager?.currentItem == 0) {
                finish()
            } else {

                val currentPosition: Int = binding?.viewPager?.currentItem?.minus(1) ?: 0

//                binding?.viewPager?.setAdapter(null)
//                binding?.viewPager?.setAdapter(adapter)
                binding?.viewPager?.currentItem = currentPosition


            }


        }

    }

    private fun setPropertySidePager(){

        fragments.add(biF)
        fragments.add(rf)
        fragments.add(edf)
        fragments.add(ecf)
        fragments.add(lf)
        fragments.add(rpf)

        adapter = RenterPagerAdapter(fragments, this)
        binding?.viewPager?.adapter = adapter
        binding?.indicator?.setViewPager2(binding?.viewPager!!)
        binding?.viewPager?.isSaveFromParentEnabled = false;
        binding?.viewPager?.isUserInputEnabled = false;
        binding?.viewPager?.offscreenPageLimit = 6
        binding?.viewPager?.registerOnPageChangeCallback(pageCallBack)

        if (binding?.viewPager?.currentItem == 4) {
            binding?.rlIndicator?.gravity = Gravity.END
            binding?.rlNextIcon?.gravity = Gravity.START
            binding?.nextIcon?.setImageResource(R.drawable.ic_back_new)
        } else {
            binding?.rlIndicator?.gravity = Gravity.START
            binding?.rlNextIcon?.gravity = Gravity.END
            binding?.nextIcon?.setImageResource(R.drawable.ic_nexticon)

        }

        binding?.nextIcon?.setOnClickListener {

            if (binding?.viewPager?.currentItem == 4) {
                binding?.rlIndicator?.gravity = Gravity.END
                binding?.rlNextIcon?.gravity = Gravity.START
                binding?.nextIcon?.setImageResource(R.drawable.ic_back_new)
            } else {
                binding?.rlIndicator?.gravity = Gravity.START
                binding?.rlNextIcon?.gravity = Gravity.END
                binding?.nextIcon?.setImageResource(R.drawable.ic_nexticon)

            }

            var currentPosition: Int = 0
            currentPosition = if (binding?.viewPager?.currentItem == 5) {
                binding?.viewPager?.currentItem?.minus(1) ?: 0
            } else {
                binding?.viewPager?.currentItem?.plus(1) ?: 0
            }

//            binding?.viewPager?.adapter = null
//            binding?.viewPager?.adapter = adapter
            binding?.viewPager?.currentItem = currentPosition

        }

        binding?.topBar?.backIcon?.setOnClickListener {

            if (binding?.viewPager?.currentItem == 0) {
                finish()
            } else {

                val currentPosition: Int = binding?.viewPager?.currentItem?.minus(1) ?: 0

//                binding?.viewPager?.setAdapter(null)
//                binding?.viewPager?.setAdapter(adapter)
                binding?.viewPager?.currentItem = currentPosition

            }

        }
    }

    private fun setRenterSidePager(){
        fragments1.add(biF)
        fragments1.add(rf)
        fragments1.add(edf)
        fragments1.add(ecf)

        adapter = RenterPagerAdapter(fragments1, this)
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

//            binding?.viewPager?.adapter = null
//            binding?.viewPager?.adapter = adapter
            binding?.viewPager?.currentItem = currentPosition

        }

        binding?.topBar?.backIcon?.setOnClickListener {

            if (binding?.viewPager?.currentItem == 0) {
                finish()
            } else {

                val currentPosition: Int = binding?.viewPager?.currentItem?.minus(1) ?: 0

//                binding?.viewPager?.setAdapter(null)
//                binding?.viewPager?.setAdapter(adapter)
                binding?.viewPager?.currentItem = currentPosition


            }


        }

    }

    private fun sideMenuClicks(){
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
                        this@Renter,
                        mTopDataSideItem
                    )

                }
                "Property Manager" -> {

                    sideMenuDrawer(
                        binding?.dashboardDrawerLayout,
                        mDataSideItem,
                        binding?.dashboardNavMenuLayout,
                        binding?.dashboardNavView,
                        this@Renter,
                        mTopDataSideItem
                    )

                }
                "Contractor" ->{
                    sideMenuDrawerForContractorDas(
                        binding?.dashboardDrawerLayout,
                        mDataSideItemCon,
                        binding?.dashboardNavMenuLayout,
                        binding?.dashboardNavView,
                        this@Renter,
                        mTopDataSideItemCon
                    )
                }
                "Renter" -> {
                    sideMenuDrawerForRenterDas(
                        binding?.dashboardDrawerLayout,
                        mDataSideItemRenter,
                        binding?.dashboardNavMenuLayout,
                        binding?.dashboardNavView,
                        this@Renter,
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
                when (roleProfile) {
                    "Landlord" -> {

                        sideMenuDrawer(
                            binding?.dashboardDrawerLayout,
                            mDataSideItem,
                            binding?.dashboardNavMenuLayout,
                            binding?.dashboardNavView,
                            this@Renter,
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
                            this@Renter,
                            mTopDataSideItem,
                            true
                        )

                    }
                    "Contractor" ->{
                        sideMenuDrawerForContractorDas(
                            binding?.dashboardDrawerLayout,
                            mDataSideItemCon,
                            binding?.dashboardNavMenuLayout,
                            binding?.dashboardNavView,
                            this@Renter,
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
                            this@Renter,
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

    override fun onDestroy() {
        super.onDestroy()
        binding?.viewPager?.unregisterOnPageChangeCallback(pageCallBack)
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