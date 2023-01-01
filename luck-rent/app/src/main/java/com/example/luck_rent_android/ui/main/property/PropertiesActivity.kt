package com.example.luck_rent_android.ui.main.property

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityPropertiesBinding
import com.example.luck_rent_android.model.PropertyModel
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.profile.Profile.Companion.roleProfile
import com.example.luck_rent_android.ui.main.property.adapter.PropertiesListingAdapter
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import com.kodextech.project.kodexlib.utils.visible
import kotlinx.coroutines.newSingleThreadContext
import java.lang.IllegalStateException

class PropertiesActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var binding: ActivityPropertiesBinding? = null

    private var mAdapter: PropertiesListingAdapter? = null
    private var mData = ArrayList<PropertyModel>()
    private var mDataSideItem = ArrayList<SideItems>()
    private var mTopDataSideItem = ArrayList<SideItems>()

    private var isFrom: String? = null
    private var isFor: String? = null
    private var isVacant: String? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        statusBarColor(getColor(R.color.darkBlue))
        binding = DataBindingUtil.setContentView(this, R.layout.activity_properties)
        super.onCreate(savedInstanceState)

        initTopBar()
        setDummyData()
        setRecycler()

        when(roleProfile){
            "Property Manager" ->{
                binding?.tvAddPropertyManager?.text = "Add Owner"
            }
        }

        binding?.tvAddUnit?.setOnClickListener{
            binding?.rlAdds?.visibility = View.GONE
            binding?.rlSingleAdds?.visibility = View.GONE
            binding?.btnPlus?.gone()
            val intent = Intent(this, AddProperty::class.java)
            startActivity(intent)
        }
        binding?.tvAddBuilding?.setOnClickListener{
            binding?.rlAdds?.visibility = View.GONE
            binding?.rlSingleAdds?.visibility = View.GONE
            binding?.btnPlus?.gone()
            val intent = Intent(this, AddBuilding::class.java)
            startActivity(intent)
        }
        binding?.tvAddPropertyManager?.setOnClickListener{
            binding?.rlAdds?.visibility = View.GONE
            binding?.rlSingleAdds?.visibility = View.GONE
            binding?.btnPlus?.gone()
            val intent = Intent(this, AddPM::class.java)
            startActivity(intent)
        }


        binding?.topBar?.ivAdd?.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
            finish()
        }

        isVacant = intent.getStringExtra("Vacant")
        if (isVacant == "VacantProperties") {

            binding?.topBar?.tvText?.text = "Vacant Properties"
            binding?.topBar?.etSearch?.hint = "Vacant Properties"

        }

        binding?.topBar?.ivPropertyAdd?.setOnClickListener {

            if (isFor == "1") {

                if (binding?.rlAdds?.visibility == View.VISIBLE) {
                    binding?.rlAdds?.visibility = View.GONE
                    binding?.rlSingleAdds?.visibility = View.GONE
                    binding?.btnPlus?.gone()
                } else {
                    binding?.rlAdds?.visibility = View.VISIBLE
                    binding?.rlSingleAdds?.visibility = View.GONE
                    binding?.btnPlus?.visible()
                }

            } else if (isVacant == "VacantProperties") {

                if (binding?.rlAdds?.visibility == View.VISIBLE) {
                    binding?.rlAdds?.visibility = View.GONE
                    binding?.rlSingleAdds?.visibility = View.GONE
                    binding?.btnPlus?.gone()
                } else {
                    binding?.rlAdds?.visibility = View.VISIBLE
                    binding?.rlSingleAdds?.visibility = View.GONE
                    binding?.btnPlus?.visible()
                }

            } else {

                //Change
                if (binding?.rlAdds?.visibility == View.VISIBLE) {
                    binding?.rlAdds?.visibility = View.GONE
                    binding?.rlSingleAdds?.visibility = View.GONE
                    binding?.btnPlus?.gone()
                } else {
                    binding?.rlAdds?.visibility = View.VISIBLE
                    binding?.rlSingleAdds?.visibility = View.GONE
                    binding?.btnPlus?.visible()
                }

//                if (binding?.rlAdds?.visibility == View.VISIBLE) {
//                    binding?.rlAdds?.visibility = View.GONE
//                    binding?.rlSingleAdds?.visibility = View.GONE
//                    binding?.btnPlus?.gone()
//                } else {
//                    binding?.rlSingleAdds?.visibility = View.VISIBLE
//                    binding?.rlAdds?.visibility = View.GONE
//                    binding?.btnPlus?.visible()
//                }
            }
        }

        binding?.btnPlus?.setOnClickListener {

            if (binding?.rlAdds?.visibility == View.VISIBLE) {
                binding?.rlAdds?.visibility = View.GONE
                binding?.btnPlus?.gone()
            } else if (binding?.rlSingleAdds?.visibility == View.VISIBLE) {
                binding?.rlSingleAdds?.visibility = View.GONE
                binding?.btnPlus?.gone()
            }
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
                this@PropertiesActivity,
                mTopDataSideItem
            )

        }

        binding?.contentProperties?.setOnClickListener {
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
                    this@PropertiesActivity,
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


//        binding?.tvAddBySingleUnit?.setOnClickListener {
//            val intent = Intent(this, AddProperty::class.java)
//            startActivity(intent)
//        }
//
//
//        binding?.tvAddByMultipleUnit?.setOnClickListener {
//            val intent = Intent(this, AddProperty::class.java)
//            startActivity(intent)
//        }

        binding?.tvAddMUBUnit?.setOnClickListener {
            val intent = Intent(this, AddProperty::class.java)
            startActivity(intent)
        }

    }

    private fun setRecycler() {
        mAdapter = PropertiesListingAdapter(
            this,
            mData,
            isFrom.toString(),
            isFor.toString()
        ) { position: Int ->



        }
        binding?.rvProperties?.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding?.rvProperties?.adapter = mAdapter
        mAdapter?.notifyDataSetChanged()
    }

    private fun setDummyData() {
        mData.clear()
        mData.add(PropertyModel("DHA 46-K Phase 1", "3", R.drawable.house, "1234-1241"))
        mData.add(PropertyModel("DHA 46-K Phase 1", "3", R.drawable.house, "1234-1241"))
        mData.add(PropertyModel("DHA 46-K Phase 1", "5", R.drawable.house, "1234-1241"))
        mData.add(PropertyModel("DHA 46-K Phase 1", "6", R.drawable.house, "1234-1241"))
        mData.add(PropertyModel("DHA 46-K Phase 1", "7", R.drawable.house, "1234-1241"))
    }

    private fun initTopBar() {

        isFrom = intent.getStringExtra("isFrom")
        isFor = intent.getStringExtra("isFor")

        binding?.topBar?.tvText?.gravity = Gravity.CENTER_VERTICAL
        (binding?.topBar?.tvText?.layoutParams as ConstraintLayout.LayoutParams).apply {
            marginStart = 40
            topMargin = 40
        }
//        binding?.topBar?.tvText?.text = "MUB Address"
        //Change
        binding?.topBar?.tvText?.text = "My Properties"
        binding?.topBar?.tvText?.textSize = 25F
        binding?.topBar?.ivAdd?.gone()
        binding?.topBar?.rlSearch?.visible()
        binding?.topBar?.rlCounter?.gone()
        binding?.topBar?.ivPropertyAdd?.visible()

        if (isFor == "1") {
            binding?.topBar?.tvText?.text = "My Properties"
            binding?.topBar?.ivPropertyAdd?.visible()
        }
    }

    private fun openUnitDialog(){
        val view = View.inflate(this, R.layout.dialog_mark, null)
        val builder = AlertDialog.Builder(this)
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
        var rlLateNotice =
            view?.findViewById<RelativeLayout>(R.id.rlLateNotice)?.visible()
        var view1 =
            view?.findViewById<View>(R.id.view1)?.visible()


        //Dialog Done Button
        rlMark?.setOnClickListener {

        }
        //Dialog Cancel Icon
        rlDelete?.setOnClickListener {

        }
        mAdapter?.notifyDataSetChanged()
    }

    override fun onSetupViewGroup() {
        mViewGroup = binding?.contentProperties
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