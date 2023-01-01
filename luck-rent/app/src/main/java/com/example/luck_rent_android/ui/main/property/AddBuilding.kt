package com.example.luck_rent_android.ui.main.property

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityAddBuildingBinding
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.property.adapter.BuildingAdapter
import com.example.luck_rent_android.ui.main.property.adapter.BuildingAdapterVH
import com.example.luck_rent_android.ui.main.property.model.BuildingModel
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import java.util.*
import kotlin.collections.ArrayList

class AddBuilding : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var  binding : ActivityAddBuildingBinding? = null

    private var mDataSideItem = ArrayList<SideItems>()
    private var mTopDataSideItem = ArrayList<SideItems>()


    var adapter: BuildingAdapter? = null
    var mAdapter: RecyclerView.Adapter<BuildingAdapterVH>? = null
    var layoutManager: RecyclerView.LayoutManager? = null

    companion object{
        val list = ArrayList<BuildingModel>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_building)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))

        setTopBar()
        sideMenuClicks()
        setRecycler()
        setDummyData()


        binding?.btSave?.setOnClickListener{
            val intent = Intent(this, BuildingPreview::class.java)
            startActivity(intent)
        }

        binding?.btnAddOther?.setOnClickListener{
            openDialog()
        }
        binding?.etAddress?.setOnLongClickListener {

            val uri: String =
                java.lang.String.format(Locale.ENGLISH, "geo:%f,%f", 37.7749, -122.4194)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)

            return@setOnLongClickListener true
        }

    }

    private fun setRecycler(){
        mAdapter = BuildingAdapter(this, list){getData,position ->


            val builder = AlertDialog.Builder(this)
            //set title for alert dialog
            builder.setTitle("Edit or Delete")
            //set message for alert dialog
            builder.setMessage("Update or Delete Item")
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            //performing positive action
            builder.setPositiveButton("Edit"){ _, which ->

                val view = View.inflate(this, R.layout.dialog_building, null)
                val builder = AlertDialog.Builder(this)
                builder.setView(view)
                builder.setCancelable(false)
                val dialog = builder.create()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.show()

                //Register Done Button & Cancel Icon from Dialog
                var doneBtn = view?.findViewById<Button>(R.id.doneBtn)
                var cancelIcon = view?.findViewById<ImageView>(R.id.cancelIcon)
                var etAmenity = view?.findViewById<AppCompatEditText>(R.id.etAmenity)

                etAmenity?.setText(getData.name)

                //Dialog Done Button
                doneBtn?.setOnClickListener {
                    val getAmenity : String? = etAmenity?.text.toString()
                    if (getAmenity.isNullOrEmpty()){
                        etAmenity?.error = "Required"
                    }else{
                        getData.name = getAmenity
                        mAdapter?.notifyDataSetChanged()
                        dialog.dismiss()
                    }
                }
                //Dialog Cancel Icon
                cancelIcon?.setOnClickListener {
                    dialog.dismiss()
                }

            }
            //performing cancel action
            builder.setNeutralButton("Cancel"){ _, which ->
                Toast.makeText(this,"clicked cancel\n operation cancel", Toast.LENGTH_LONG).show()
            }
            //performing negative action
            builder.setNegativeButton("Delete"){ _, which ->
                list.removeAt(position)
                mAdapter?.notifyDataSetChanged()
                Toast.makeText(this,"Deleted", Toast.LENGTH_LONG).show()
            }
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()

        }
        binding?.recycler?.layoutManager = GridLayoutManager(this,2)
        binding?.recycler?.adapter = mAdapter
        mAdapter?.notifyDataSetChanged()


    }

    private fun setDummyData(){
        list.add(
            BuildingModel(
                "Rooftop Garden"
            )
        )
        list.add(
            BuildingModel(
                "Swimming Pool"
            )
        )
        list.add(
            BuildingModel(
                "Bike Storage"
            )
        )
        list.add(
            BuildingModel(
                "Parkade"
            )
        )
        list.add(
            BuildingModel(
                "On-Site Laundry"
            )
        )
        list.add(
            BuildingModel(
                "Elevator"
            )
        )
        list.add(
            BuildingModel(
                "Concierge"
            )
        )
        list.add(
            BuildingModel(
                "Gym"
            )
        )
        list.add(
            BuildingModel(
                "Playground"
            )
        )
    }

    private fun setTopBar(){
        binding?.topBar?.rlSearch?.gone()
        binding?.topBar?.rlCounter?.gone()
        binding?.topBar?.tvText?.text = "Add Building"
        binding?.topBar?.ivAdd?.setImageResource(R.drawable.ic_back)
        binding?.topBar?.ivAdd?.setOnClickListener{
            onBackPressed()
        }
    }

    private fun sideMenuClicks(){
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
                this@AddBuilding,
                mTopDataSideItem
            )

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

                sideMenuDrawer(
                    binding?.dashboardDrawerLayout,
                    mDataSideItem,
                    binding?.dashboardNavMenuLayout,
                    binding?.dashboardNavView,
                    this@AddBuilding,
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

    private fun openDialog() {
        val view = View.inflate(this, R.layout.dialog_building, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()


        //Register Done Button & Cancel Icon from Dialog
        var doneBtn = view?.findViewById<AppCompatButton>(R.id.doneBtn)
        var etAmenity = view?.findViewById<AppCompatEditText>(R.id.etAmenity)
        var cancelIcon = view?.findViewById<ImageView>(R.id.cancelIcon)

        //Dialog Done Button
        doneBtn?.setOnClickListener {

            val getAmenity : String? = etAmenity?.text.toString()
            if (getAmenity.isNullOrEmpty()){
                etAmenity?.setError("Required")
            }else{
                list.add(BuildingModel(getAmenity))
                mAdapter?.notifyDataSetChanged()
                dialog.dismiss()
            }

        }
        //Dialog Cancel Icon
        cancelIcon?.setOnClickListener {
            dialog.dismiss()
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding?.dashboardDrawerLayout?.closeDrawer(GravityCompat.START)
        return true
    }
}