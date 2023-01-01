package com.example.luck_rent_android.ui.main.adds

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityMyAdsBinding
import com.example.luck_rent_android.ui.main.adds.adapter.MyAdsAdapter
import com.example.luck_rent_android.ui.main.adds.model.MyAdsModel
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.contractormodule.MyButton
import com.example.luck_rent_android.ui.main.contractormodule.MyButtonClickListener
import com.example.luck_rent_android.ui.main.contractormodule.MySwipeHelper
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.profile.Profile.Companion.roleProfile
import com.example.luck_rent_android.ui.main.property.AddBuilding.Companion.list
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import kotlin.math.roundToInt

class MyAds : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var list = ArrayList<MyAdsModel>()
    private var binding: ActivityMyAdsBinding? = null
    private var mAdapter: MyAdsAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var adapter: RecyclerView.Adapter<MyAdsAdapter.ViewHolder>? = null

    private var mDataSideItem = ArrayList<SideItems>()
    private var mTopDataSideItem = ArrayList<SideItems>()

    private var mDataSideItemCon = ArrayList<SideItems>()
    private var mTopDataSideItemCon = ArrayList<SideItems>()

    private var mDataSideItemRenter = ArrayList<SideItems>()
    private var mTopDataSideItemRenter = ArrayList<SideItems>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_ads)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))

        setDummyData()
        setRecycler()
        setSwiper()
        sideMenuClicks()

        when (roleProfile) {
            "Landlord" -> {
                binding?.topBar?.tvText?.text = "My Ads"
            }
            "Property Manager" -> {
                binding?.topBar?.tvText?.text = "My Ads"
            }
            "Contractor" -> {
                binding?.rlMakeAdd?.gone()
            }
            "Renter" -> {
                binding?.rlMakeAdd?.gone()
                binding?.topBar?.tvText?.text = "Rental Ads"
            }
        }

    }

    private fun setRecycler() {
        mAdapter = MyAdsAdapter(this, list)
        binding?.recycler?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding?.recycler?.adapter = mAdapter
        mAdapter?.notifyDataSetChanged()
    }

    private fun sideMenuClicks(){
        binding?.topBar?.backIcon?.setOnClickListener {
            onBackPressed()
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
        binding?.rlMakeAdd?.setOnClickListener {
            val intent = Intent(this, MakeAdd::class.java)
            startActivity(intent)
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
                "Landlord" -> {

                    sideMenuDrawer(
                        binding?.dashboardDrawerLayout,
                        mDataSideItem,
                        binding?.dashboardNavMenuLayout,
                        binding?.dashboardNavView,
                        this@MyAds,
                        mTopDataSideItem
                    )

                }
                "Property Manager" -> {

                    sideMenuDrawer(
                        binding?.dashboardDrawerLayout,
                        mDataSideItem,
                        binding?.dashboardNavMenuLayout,
                        binding?.dashboardNavView,
                        this@MyAds,
                        mTopDataSideItem
                    )

                }
                "Contractor" -> {
                    sideMenuDrawerForContractorDas(
                        binding?.dashboardDrawerLayout,
                        mDataSideItemCon,
                        binding?.dashboardNavMenuLayout,
                        binding?.dashboardNavView,
                        this@MyAds,
                        mTopDataSideItemCon
                    )
                }
                "Renter" -> {
                    sideMenuDrawerForRenterDas(
                        binding?.dashboardDrawerLayout,
                        mDataSideItemRenter,
                        binding?.dashboardNavMenuLayout,
                        binding?.dashboardNavView,
                        this,
                        mTopDataSideItemRenter
                    )
                }
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
                            this@MyAds,
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
                            this@MyAds,
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
                            this@MyAds,
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
                            this@MyAds,
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

    private fun setDummyData() {
        list.add(
            MyAdsModel(
                R.drawable.add_pic
            )
        )
        list.add(
            MyAdsModel(
                R.drawable.home2
            )
        )
        list.add(
            MyAdsModel(
                R.drawable.home3
            )
        )
        list.add(
            MyAdsModel(
                R.drawable.home4
            )
        )
        list.add(
            MyAdsModel(
                R.drawable.home5
            )
        )
    }

    private fun setSwiper() {
        val swipeHelper = object : MySwipeHelper(
            this, binding?.recycler!!, TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 105f, resources.displayMetrics
            ).roundToInt()
        ) {
            override fun instantiateMyButton(
                viewHolder: RecyclerView.ViewHolder,
                buffer: MutableList<MyButton>
            ) {
                buffer.add(MyButton(this@MyAds, R.drawable.transparent_bg,
                    Color.parseColor("#0000ffff"),
                    object :
                        MyButtonClickListener {
                        override fun onClick(pos: Int) {

                            try {

                                if (list[pos].boolean == false){
                                    val view = View.inflate(context, R.layout.dialog_adds, null)
                                    val builder = AlertDialog.Builder(context)
                                    builder.setView(view)
                                    builder.setCancelable(true)
                                    val dialog = builder.create()
                                    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                                    dialog.show()


                                    //Register Done Button & Cancel Icon from Dialog
                                    var tvMark =
                                        view?.findViewById<AppCompatTextView>(R.id.tvMark)
                                    var tvDelete =
                                        view?.findViewById<AppCompatTextView>(R.id.tvDelete)
                                    var ivMark =
                                        view?.findViewById<AppCompatImageView>(R.id.ivMark)
                                    var ivDelete =
                                        view?.findViewById<AppCompatImageView>(R.id.ivDelete)
                                    var rlMark = view?.findViewById<RelativeLayout>(R.id.rlMark)
                                    var rlDelete =
                                        view?.findViewById<RelativeLayout>(R.id.rlDelete)

                                    tvMark?.text = "Mark Archive"
                                    tvDelete?.text = "Copy Content"
                                    ivMark?.setImageResource(R.drawable.ic_archive)
                                    ivDelete?.setImageResource(R.drawable.ic_copy1)

                                    //Dialog Done Button
                                    rlMark?.setOnClickListener {
                                        list[pos].boolean = true
                                        showToast("Mark Archive")
                                        mAdapter?.notifyDataSetChanged()
                                        dialog.dismiss()
                                    }
                                    //Dialog Cancel Icon
                                    rlDelete?.setOnClickListener {
                                        showToast("Content Copied")
                                        mAdapter?.notifyDataSetChanged()
                                        dialog.dismiss()
                                    }
                                    mAdapter?.notifyDataSetChanged()
                                }else if (list[pos].boolean == true){
                                    val view = View.inflate(context, R.layout.dialog_adds, null)
                                    val builder = AlertDialog.Builder(context)
                                    builder.setView(view)
                                    builder.setCancelable(true)
                                    val dialog = builder.create()
                                    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                                    dialog.show()


                                    //Register Done Button & Cancel Icon from Dialog
                                    var tvMark =
                                        view?.findViewById<AppCompatTextView>(R.id.tvMark)
                                    var tvDelete =
                                        view?.findViewById<AppCompatTextView>(R.id.tvDelete)
                                    var ivMark =
                                        view?.findViewById<AppCompatImageView>(R.id.ivMark)
                                    var ivDelete =
                                        view?.findViewById<AppCompatImageView>(R.id.ivDelete)
                                    var rlMark = view?.findViewById<RelativeLayout>(R.id.rlMark)
                                    var rlDelete =
                                        view?.findViewById<RelativeLayout>(R.id.rlDelete)

                                    tvMark?.text = "Mark Activate"
                                    tvDelete?.text = "Copy Content"
                                    ivMark?.setImageResource(R.drawable.ic_activate)
                                    ivDelete?.setImageResource(R.drawable.ic_copy1)

                                    //Dialog Done Button
                                    rlMark?.setOnClickListener {
                                        list[pos].boolean = false
                                        showToast("Mark Activate")
                                        mAdapter?.notifyDataSetChanged()
                                        dialog.dismiss()
                                    }
                                    //Dialog Cancel Icon
                                    rlDelete?.setOnClickListener {
                                        showToast("Content Copied")
                                        mAdapter?.notifyDataSetChanged()
                                        dialog.dismiss()
                                    }
                                    mAdapter?.notifyDataSetChanged()
                                }

                            } catch (e: Exception) {
                            }


                        }


                    }

                ))

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