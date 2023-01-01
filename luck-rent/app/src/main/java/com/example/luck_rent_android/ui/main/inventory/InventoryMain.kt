package com.example.luck_rent_android.ui.main.inventory

import android.app.Activity
import android.content.Intent
import android.icu.lang.UCharacter
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityInventoryBinding
import com.example.luck_rent_android.databinding.ActivityInventoryMainBinding
import com.example.luck_rent_android.databinding.ActivityOrderBinding
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.inventory.adapter.InventoryAdapter
import com.example.luck_rent_android.ui.main.inventory.model.InventoryModel
import com.example.luck_rent_android.ui.main.order.adapter.OrderAdapter
import com.example.luck_rent_android.ui.main.order.adapter.OrderAdapterVH
import com.example.luck_rent_android.ui.main.order.model.OrderModel
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import com.kodextech.project.kodexlib.utils.visible
import java.io.IOException
import java.lang.IllegalStateException
import java.util.ArrayList

class InventoryMain : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var binding : ActivityInventoryMainBinding? = null
    val PICK_IMAGE = 123
    var imageUri: Uri? = null
    private var mDataSideItem = ArrayList<SideItems>()
    private var mTopDataSideItem = ArrayList<SideItems>()

    val list = ArrayList<InventoryModel>()
    var adapter: InventoryAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_inventory_main)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))

        setTopBar()
        setSideMenuCLicks()
        setRecycler()
        setDummyData()

        binding?.btnAppliance?.setOnClickListener {
            val intent = Intent(this, InventoryActivity::class.java)
            startActivity(intent)
        }

        binding?.btnMaintenance?.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT

            startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), PICK_IMAGE)
        }

    }

    private fun setTopBar(){
        binding?.topBar?.rlSearch?.visible()
        binding?.topBar?.etSearch?.hint = "Try Dishwasher"
        binding?.topBar?.rlCounter?.gone()
        binding?.topBar?.tvText?.text = "APPLIANCES"
        binding?.topBar?.ivAdd?.setImageResource(R.drawable.ic_back)
        binding?.topBar?.ivAdd?.setOnClickListener{
            onBackPressed()
        }
        val locationIcon = resources.getDrawable(R.drawable.ic_search)
        binding?.topBar?.etSearch?.setCompoundDrawablesWithIntrinsicBounds(null,null, locationIcon, null);
    }

    private fun setRecycler(){
        adapter = InventoryAdapter(this, list)
        binding?.recycler?.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true)
        binding?.recycler?.adapter = adapter
        adapter?.notifyDataSetChanged()
    }

    private fun setDummyData(){
        list.add(
            InventoryModel(
               "Dishwasher",
                "LG",
                "239G9",
                "2021-09-29",
                "23 Aug 2020",
                "2022-09-29",
                "Door not close"
        ))
        list.add(
            InventoryModel(
"Dryer",
                "Blomberg",
                "x9d8",
                "2022-09-29",
                "21 Jul 2020",
                "2022-09-06",
                "n/a"
            )
        )

    }

    private fun setSideMenuCLicks(){
        binding?.dashboardNavMenuLayout?.ivCross?.setOnClickListener{
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

        binding?.dashboardNavMenuLayout?.clickaBleNav?.setOnClickListener{
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
                this@InventoryMain,
                mTopDataSideItem
            ) }

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
                sideMenuDrawer(
                    binding?.dashboardDrawerLayout,
                    mDataSideItem,
                    binding?.dashboardNavMenuLayout,
                    binding?.dashboardNavView,
                    this@InventoryMain,
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data!!.data != null) {
            imageUri = data.data
            try {

                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                binding?.ivWarranty?.visible()
                binding?.ivWarranty?.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onSetupViewGroup() {

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