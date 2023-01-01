package com.example.luck_rent_android.ui.main.chat

import android.content.Intent
import android.os.BaseBundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityChatBinding
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.profile.Profile.Companion.roleProfile
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.visible
import java.lang.IllegalStateException
import java.util.ArrayList

class Chat : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var binding: ActivityChatBinding? = null

    private var mDataSideItem = ArrayList<SideItems>()
    private var mTopDataSideItem = ArrayList<SideItems>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))

        binding?.ivCamera?.setOnClickListener {
            val intent = Intent(this, OpenCamera::class.java)
            startActivity(intent)
        }
        binding?.topBar?.ivCall?.setOnClickListener {
            val intent = Intent(this, AudioCall::class.java)
            startActivity(intent)
        }
        binding?.topBar?.ivBack?.setOnClickListener {
            onBackPressed()
        }
        binding?.topBar?.ivProfileImage?.setOnLongClickListener {

            return@setOnLongClickListener true
        }

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
                        this@Chat,
                        mTopDataSideItem
                    )

                }

                "Property Manager" -> {

                    sideMenuDrawer(
                        binding?.dashboardDrawerLayout,
                        mDataSideItem,
                        binding?.dashboardNavMenuLayout,
                        binding?.dashboardNavView,
                        this@Chat,
                        mTopDataSideItem
                    )

                }
                "Renter" -> {

                    sideMenuDrawerForRenterDas(
                        binding?.dashboardDrawerLayout,
                        mDataSideItem,
                        binding?.dashboardNavMenuLayout,
                        binding?.dashboardNavView,
                        this@Chat,
                        mTopDataSideItem
                    )

                }
                "Contractor" -> {

                    sideMenuDrawerForContractorDas(
                        binding?.dashboardDrawerLayout,
                        mDataSideItem,
                        binding?.dashboardNavMenuLayout,
                        binding?.dashboardNavView,
                        this@Chat,
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

        binding?.dashboardDrawerLayout?.addDrawerListener(object : DrawerLayout.DrawerListener{
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
                            this@Chat,
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
                            this@Chat,
                            mTopDataSideItem,
                            true
                        )

                    }
                    "Renter" -> {

                        sideMenuDrawerForRenterDas(
                            binding?.dashboardDrawerLayout,
                            mDataSideItem,
                            binding?.dashboardNavMenuLayout,
                            binding?.dashboardNavView,
                            this@Chat,
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
                            this@Chat,
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

    private fun openDialog(){
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
        var tvLateNotice =
            view?.findViewById<AppCompatTextView>(R.id.tvLateNotice)
        var ivMark =
            view?.findViewById<AppCompatImageView>(R.id.ivMark)
        var ivDelete =
            view?.findViewById<AppCompatImageView>(R.id.ivDelete)
        var ivLateNotice =
            view?.findViewById<AppCompatImageView>(R.id.ivLateNotice)
        var rlMark = view?.findViewById<RelativeLayout>(R.id.rlMark)
        var rlDelete =
            view?.findViewById<RelativeLayout>(R.id.rlDelete)
        var rlLateNotice =
            view?.findViewById<RelativeLayout>(R.id.rlLateNotice)?.visible()
        var view1 =
            view?.findViewById<View>(R.id.view1)?.visible()

        tvMark?.text = "Edit"
        tvDelete?.text = "Call"
        tvLateNotice?.text = "Send an Email"
        ivMark?.setImageResource(R.drawable.ic_unpaid2)
        ivDelete?.setImageResource(R.drawable.ic_unpaid2)
        ivLateNotice?.setImageResource(R.drawable.ic_unpaid2)

        //Dialog Done Button
        rlMark?.setOnClickListener {

        }
        //Dialog Cancel Icon
        rlDelete?.setOnClickListener {

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