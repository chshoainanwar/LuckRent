package com.example.luck_rent_android.ui.main.adds

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityMakeAddBinding
import com.example.luck_rent_android.databinding.AdDialogBinding
import com.example.luck_rent_android.dialog.AddDialog
import com.example.luck_rent_android.dialog.AddDialog.Companion.newInstance
import com.example.luck_rent_android.ui.main.adds.adapter.MyAdsAdapter
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.profile.Profile.Companion.roleProfile
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import java.lang.IllegalStateException
import java.util.*

class MakeAdd : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var binding: ActivityMakeAddBinding? = null

    private var mDataSideItem = ArrayList<SideItems>()
    private var mTopDataSideItem = ArrayList<SideItems>()

    private var mDataSideItemCon = ArrayList<SideItems>()
    private var mTopDataSideItemCon = ArrayList<SideItems>()

    private var mDataSideItemRenter = ArrayList<SideItems>()
    private var mTopDataSideItemRenter = ArrayList<SideItems>()

    private var isopened: Boolean? = false
    private var isUnit: Boolean? = false
    private var isKitchen: Boolean? = false
    private var isGarage: Boolean? = false
    private var isParking: Boolean? = false
    private var isWasher: Boolean? = false
    private var isAir: Boolean? = false
    private var isFurniture: Boolean? = false
    private var isPatio: Boolean? = false
    private var isHardwood: Boolean? = false
    private var isDish: Boolean? = false
    private var isFireplace: Boolean? = false
    private var isWalk: Boolean? = false
    private var isWifi: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {


        statusBarColor(getColor(R.color.darkBlue))
        binding = DataBindingUtil.setContentView(this, R.layout.activity_make_add)
        super.onCreate(savedInstanceState)
        setColorState()

        binding?.spLease?.setItems(
            "1 Year",
            "2 Years",
            "3 Years",
            "4 Years",
            "5 Years"
        )

        binding?.btnNext?.setOnClickListener {

//            val dialog = AddDialog.newInstance { }
//            dialog.show(supportFragmentManager, "")

            val intent = Intent(this, Details::class.java)
            intent.putExtra("MakeAdd", "Details")
            startActivity(intent)

        }

        binding?.topBar?.backIcon?.setOnClickListener {
          onBackPressed()
        }

        binding?.topBar?.tvText?.text = "Write An Ad"

        binding?.ivStartDate?.setOnClickListener {
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
                        binding?.tvStartDatePicker?.text = "$year-$monthi-$day"
                    } else {
                        binding?.tvStartDatePicker?.text = "$year-0$monthi-$day"

                    }

                }, year, month, day
            )
//            dpd.datePicker.minDate = System.currentTimeMillis()
            dpd.show()
        }

        binding?.ivEndDate?.setOnClickListener {
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
                        binding?.tvStartDatePicker?.text = "$year-$monthi-$day"
                    } else {
                        binding?.tvStartDatePicker?.text = "$year-0$monthi-$day"

                    }

                }, year, month, day
            )
//            dpd.datePicker.minDate = System.currentTimeMillis()
            dpd.show()
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

            when (roleProfile) {
                "Landlord" -> sideMenuDrawer(
                    binding?.dashboardDrawerLayout,
                    mDataSideItem,
                    binding?.dashboardNavMenuLayout,
                    binding?.dashboardNavView,
                    this@MakeAdd,
                    mTopDataSideItem
                )
                "Property Manager" -> sideMenuDrawer(
                    binding?.dashboardDrawerLayout,
                    mDataSideItem,
                    binding?.dashboardNavMenuLayout,
                    binding?.dashboardNavView,
                    this@MakeAdd,
                    mTopDataSideItem
                )
                "Contractor" -> sideMenuDrawerForContractorDas(
                    binding?.dashboardDrawerLayout,
                    mDataSideItemCon,
                    binding?.dashboardNavMenuLayout,
                    binding?.dashboardNavView,
                    this,
                    mTopDataSideItemCon
                )
                "Renter" -> sideMenuDrawerForRenterDas(
                    binding?.dashboardDrawerLayout,
                    mDataSideItemRenter,
                    binding?.dashboardNavMenuLayout,
                    binding?.dashboardNavView,
                    this,
                    mTopDataSideItemRenter
                )


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
                    "Landlord" -> sideMenuDrawer(
                        binding?.dashboardDrawerLayout,
                        mDataSideItem,
                        binding?.dashboardNavMenuLayout,
                        binding?.dashboardNavView,
                        this@MakeAdd,
                        mTopDataSideItem,
                        true
                    )
                    "Property Manager" -> sideMenuDrawer(
                        binding?.dashboardDrawerLayout,
                        mDataSideItem,
                        binding?.dashboardNavMenuLayout,
                        binding?.dashboardNavView,
                        this@MakeAdd,
                        mTopDataSideItem,
                        true
                    )
                    "Contractor" -> sideMenuDrawerForContractorDas(
                        binding?.dashboardDrawerLayout,
                        mDataSideItemCon,
                        binding?.dashboardNavMenuLayout,
                        binding?.dashboardNavView,
                        this@MakeAdd,
                        mTopDataSideItemCon,
                        true
                    )
                    "Renter" -> sideMenuDrawerForRenterDas(
                        binding?.dashboardDrawerLayout,
                        mDataSideItemRenter,
                        binding?.dashboardNavMenuLayout,
                        binding?.dashboardNavView,
                        this@MakeAdd,
                        mTopDataSideItemRenter,
                        true
                    )


                }


            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerStateChanged(newState: Int) {

                Log.d("getState", "onDrawerStateChanged: " + newState)
            }

        })
    }

    private fun setColorState() {
        binding?.tvUnitAmenity?.setOnClickListener {
            if (isUnit == true) {
                binding?.tvUnitAmenity?.setTextColor(getColor(R.color.grey))
                isUnit = false
            } else {
                binding?.tvUnitAmenity?.setTextColor(getColor(R.color.orange))
                isUnit = true
            }
        }

        binding?.tvKitchenAmenity?.setOnClickListener {
            if (isKitchen == true) {
                binding?.tvKitchenAmenity?.setTextColor(getColor(R.color.grey))
                isKitchen = false
            } else {
                binding?.tvKitchenAmenity?.setTextColor(getColor(R.color.orange))
                isKitchen = true
            }
        }

        binding?.tvGarageAmenity?.setOnClickListener {
            if (isGarage == true) {
                binding?.tvGarageAmenity?.setTextColor(getColor(R.color.grey))
                isGarage = false
            } else {
                binding?.tvGarageAmenity?.setTextColor(getColor(R.color.orange))
                isGarage = true
            }
        }

        binding?.tvParkingAmenity?.setOnClickListener {
            if (isParking == true) {
                binding?.tvParkingAmenity?.setTextColor(getColor(R.color.grey))
                isParking = false
            } else {
                binding?.tvParkingAmenity?.setTextColor(getColor(R.color.orange))
                isParking = true
            }
        }

        binding?.tvWasherAmenity?.setOnClickListener {
            if (isWasher == true) {
                binding?.tvWasherAmenity?.setTextColor(getColor(R.color.grey))
                isUnit = false
            } else {
                binding?.tvWasherAmenity?.setTextColor(getColor(R.color.orange))
                isWasher = true
            }
        }

        binding?.tvAirAmenity?.setOnClickListener {
            if (isAir == true) {
                binding?.tvAirAmenity?.setTextColor(getColor(R.color.grey))
                isAir = false
            } else {
                binding?.tvAirAmenity?.setTextColor(getColor(R.color.orange))
                isAir = true
            }
        }

        binding?.tvFurnitureAmenity?.setOnClickListener {
            if (isFurniture == true) {
                binding?.tvFurnitureAmenity?.setTextColor(getColor(R.color.grey))
                isFurniture = false
            } else {
                binding?.tvFurnitureAmenity?.setTextColor(getColor(R.color.orange))
                isFurniture = true
            }
        }

        binding?.tvPatioAmenity?.setOnClickListener {
            if (isPatio == true) {
                binding?.tvPatioAmenity?.setTextColor(getColor(R.color.grey))
                isPatio = false
            } else {
                binding?.tvPatioAmenity?.setTextColor(getColor(R.color.orange))
                isPatio = true
            }
        }

        binding?.tvHardwoodAmenity?.setOnClickListener {
            if (isHardwood == true) {
                binding?.tvHardwoodAmenity?.setTextColor(getColor(R.color.grey))
                isHardwood = false
            } else {
                binding?.tvHardwoodAmenity?.setTextColor(getColor(R.color.orange))
                isHardwood = true
            }
        }

        binding?.tvDishWasherAmenity?.setOnClickListener {
            if (isDish == true) {
                binding?.tvDishWasherAmenity?.setTextColor(getColor(R.color.grey))
                isDish = false
            } else {
                binding?.tvDishWasherAmenity?.setTextColor(getColor(R.color.orange))
                isDish = true
            }
        }

        binding?.tvFirePlaceAmenity?.setOnClickListener {
            if (isFurniture == true) {
                binding?.tvFirePlaceAmenity?.setTextColor(getColor(R.color.grey))
                isFurniture = false
            } else {
                binding?.tvFirePlaceAmenity?.setTextColor(getColor(R.color.orange))
                isFurniture = true
            }
        }

        binding?.tvWalkAmenity?.setOnClickListener {
            if (isWalk == true) {
                binding?.tvWalkAmenity?.setTextColor(getColor(R.color.grey))
                isWalk = false
            } else {
                binding?.tvWalkAmenity?.setTextColor(getColor(R.color.orange))
                isWalk = true
            }
        }
        binding?.tvWifiAmenity?.setOnClickListener {
            if (isWifi == true) {
                binding?.tvWifiAmenity?.setTextColor(getColor(R.color.grey))
                isWifi = false
            } else {
                binding?.tvWifiAmenity?.setTextColor(getColor(R.color.orange))
                isWifi = true
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