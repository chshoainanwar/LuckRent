package com.example.luck_rent_android.ui.main.chat

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.core.view.marginEnd
import androidx.core.view.marginTop
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityMessagesBinding
import com.example.luck_rent_android.ui.main.chat.adapter.MessageAdapter
import com.example.luck_rent_android.ui.main.chat.adapter.MessageAdapterVH
import com.example.luck_rent_android.ui.main.chat.model.MessagesModel
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.profile.Profile.Companion.roleProfile
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import com.kodextech.project.kodexlib.utils.visible
import java.lang.IllegalStateException

class Messages : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var binding: ActivityMessagesBinding? = null
    val list = ArrayList<MessagesModel>()
    var adapter: MessageAdapter? = null
    var mAdapter: RecyclerView.Adapter<MessageAdapterVH>? = null
    var layoutManager: RecyclerView.LayoutManager? = null

    private var mDataSideItem = java.util.ArrayList<SideItems>()
    private var mTopDataSideItem = java.util.ArrayList<SideItems>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_messages)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))

        setTopBar()
        sideMenuClicks()
        setDummyData()
        setRecycler()
        openDialog()

    }

    private fun setTopBar() {
        binding?.topBar?.rlSearch?.visible()
        binding?.topBar?.rlCounter?.gone()
        binding?.topBar?.ivAdd?.setImageResource(R.drawable.ic_back)
        binding?.topBar?.tvText?.text = "Contacts & Chat"
        binding?.topBar?.etSearch?.hint = "Search"
        val locationIcon = resources.getDrawable(R.drawable.ic_search)
        binding?.topBar?.etSearch?.setCompoundDrawablesWithIntrinsicBounds(null,null, locationIcon, null);
        binding?.topBar?.ivAdd?.setOnClickListener {
            onBackPressed()
        }
//        binding?.topBar?.tvText?.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
//
//        (binding?.topBar?.tvText?.layoutParams as ConstraintLayout.LayoutParams).apply {
//            marginStart = 40
//            topMargin = 10
//        }
    }

    private fun sideMenuClicks(){
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
                        this@Messages,
                        mTopDataSideItem
                    )

                }

                "Property Manager" -> {

                    sideMenuDrawer(
                        binding?.dashboardDrawerLayout,
                        mDataSideItem,
                        binding?.dashboardNavMenuLayout,
                        binding?.dashboardNavView,
                        this@Messages,
                        mTopDataSideItem
                    )

                }
                "Renter" -> {

                    sideMenuDrawerForRenterDas(
                        binding?.dashboardDrawerLayout,
                        mDataSideItem,
                        binding?.dashboardNavMenuLayout,
                        binding?.dashboardNavView,
                        this@Messages,
                        mTopDataSideItem
                    )

                }
                "Contractor" -> {

                    sideMenuDrawerForContractorDas(
                        binding?.dashboardDrawerLayout,
                        mDataSideItem,
                        binding?.dashboardNavMenuLayout,
                        binding?.dashboardNavView,
                        this@Messages,
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
                            this@Messages,
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
                            this@Messages,
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
                            this@Messages,
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
                            this@Messages,
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

    private fun setRecycler() {
        mAdapter = MessageAdapter(this, list)
        binding?.recycler?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding?.recycler?.adapter = mAdapter
        mAdapter?.notifyDataSetChanged()
    }

    private fun setDummyData() {
        list.add(
            MessagesModel(
                "James",
                "In Publishing and graphic design , Lorem ispum ia a placeholder text commonly",
                "8:15 pm",
                "2",
                R.drawable.msg1
            )
        )
        list.add(
            MessagesModel(
                "Jesus",
                "In Publishing and graphic design , Lorem ispum ia a placeholder text commonly",
                "8:15 pm",
                "4",
                R.drawable.msg2
            )
        )
        list.add(
            MessagesModel(
                "Finch",
                "In Publishing and graphic design , Lorem ispum ia a placeholder text commonly",
                "8:15 pm",
                "4",
                R.drawable.msg3
            )
        )
        list.add(
            MessagesModel(
                "Smith",
                "In Publishing and graphic design , Lorem ispum ia a placeholder text commonly",
                "8:15 pm",
                "4",
                R.drawable.msg4
            )
        )
        list.add(
            MessagesModel(
                "John",
                "In Publishing and graphic design , Lorem ispum ia a placeholder text commonly",
                "8:15 pm",
                "4",
                R.drawable.msg5
            )
        )
        list.add(
            MessagesModel(
                "James",
                "In Publishing and graphic design , Lorem ispum ia a placeholder text commonly",
                "8:15 pm",
                "2",
                R.drawable.msg1
            )
        )
        list.add(
            MessagesModel(
                "Jesus",
                "In Publishing and graphic design , Lorem ispum ia a placeholder text commonly",
                "8:15 pm",
                "4",
                R.drawable.msg2
            )
        )
        list.add(
            MessagesModel(
                "Finch",
                "In Publishing and graphic design , Lorem ispum ia a placeholder text commonly",
                "8:15 pm",
                "4",
                R.drawable.msg3
            )
        )
        list.add(
            MessagesModel(
                "Smith",
                "In Publishing and graphic design , Lorem ispum ia a placeholder text commonly",
                "8:15 pm",
                "4",
                R.drawable.msg4
            )
        )
        list.add(
            MessagesModel(
                "John",
                "In Publishing and graphic design , Lorem ispum ia a placeholder text commonly",
                "8:15 pm",
                "4",
                R.drawable.msg5
            )
        )

    }

    private fun openDialog() {
        val view = View.inflate(this, R.layout.dialog_chat, null)
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