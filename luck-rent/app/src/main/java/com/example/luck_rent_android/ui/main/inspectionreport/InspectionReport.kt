package com.example.luck_rent_android.ui.main.inspectionreport

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityInspectionReportBinding
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.expense.adapter.ImageUploadAdapter
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.property.FileUtilityClass
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import com.kodextech.project.kodexlib.utils.visible
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.FilePickerConst
import java.io.File
import java.lang.IllegalStateException
import java.util.*

class InspectionReport : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var binding: ActivityInspectionReportBinding? = null

    private var enumState: InspectionItem = InspectionItem.CheckIn


    private var mImageAdapter: ImageUploadAdapter? = null
    private val IMAGE_REQUEST_CODE = 2212
    val mFileData = ArrayList<File>()

    private var mDataSideItem = ArrayList<SideItems>()
    private var mTopDataSideItem = ArrayList<SideItems>()

    companion object {
        var roleStatus: String? = "Move-In"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inspection_report)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))

        SetTopBar()
        SetSpinnerValue()
        setImageAdapter()
        sideMenuClicks()



        binding?.btnNext?.setOnClickListener {
            if (enumState == InspectionItem.CheckIn){
                val intent = Intent(this, InspectionEntry::class.java)
                intent.putExtra("Report","CheckIn")
                startActivity(intent)
            }else if (enumState == InspectionItem.CheckOut){
                val intent = Intent(this, InspectionEntry::class.java)
                startActivity(intent)
            }else if (enumState == InspectionItem.DuringTenancy){
                val intent = Intent(this, InspectionEntry::class.java)
                intent.putExtra("Report","DuringTenancy")
                startActivity(intent)
            }


        }
        binding?.cvMoveIn?.setOnClickListener {
            roleStatus = "Move-In"
            enumState = InspectionItem.CheckIn
            checkProfileState()


        }
        binding?.cvMoveOut?.setOnClickListener {
            roleStatus = "Move-Out"
            enumState = InspectionItem.CheckOut
            checkProfileState()
        }
        binding?.cvDuringTenancy?.setOnClickListener {
            roleStatus = "During-Tenancy"
            enumState = InspectionItem.DuringTenancy
            checkProfileState()
        }

        binding?.tvMoveInDateTen?.setOnClickListener {

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
                        binding?.tvMoveInDateTen?.text = "$year-$monthi-$day"
                    } else {
                        binding?.tvMoveInDateTen?.text = "$year-0$monthi-$day"

                    }

                }, year, month, day
            )
//            dpd.datePicker.minDate = System.currentTimeMillis()
            dpd.show()
        }

        binding?.tvMoveOutDateTen?.setOnClickListener {

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
                        binding?.tvMoveOutDateTen?.text = "$year-$monthi-$day"
                    } else {
                        binding?.tvMoveOutDateTen?.text = "$year-0$monthi-$day"

                    }

                }, year, month, day
            )
//            dpd.datePicker.minDate = System.currentTimeMillis()
            dpd.show()
        }

    }

    private fun setImageAdapter() {
        mImageAdapter =
            ImageUploadAdapter(this@InspectionReport, mFileData) { position, forDelete ->
                if (forDelete) {
                    if (position == null) {
                        showToast("System Having Some Issue")
                    } else {
                        deleteImage(position)
                    }
                } else {
                    FilePickerBuilder.instance
                        .setMaxCount(5)
                        .setActivityTheme(R.style.LibAppTheme) //optional
                        .pickPhoto(this, IMAGE_REQUEST_CODE)

                }
                mImageAdapter?.notifyDataSetChanged()
            }

//        binding?.rvPhotos?.layoutManager =
//            GridLayoutManager(this@InspectionReport, 3)
//        binding?.rvPhotos?.adapter = mImageAdapter
//        mImageAdapter?.notifyDataSetChanged()
    }

    private fun deleteImage(position: Int) {
        mFileData.removeAt(position - 1)
        mImageAdapter?.notifyDataSetChanged()

    }

    fun SetSpinnerValue() {
//        binding?.spInspection?.setItems(
//            "Move In",
//            "During Tenancy",
//            "Move Out"
//        )
    }

    fun checkProfileState() {
        when (enumState) {
            InspectionItem.CheckIn -> {
                binding?.tvMoveIn?.setBackgroundColor(getColor(R.color.orange))
                binding?.tvMoveOut?.setBackgroundColor(getColor(R.color.f3White))
                binding?.tvDuringTenancy?.setBackgroundColor(getColor(R.color.f3White))

                binding?.tvMoveIn?.setTextColor(getColor(R.color.white))
                binding?.tvMoveOut?.setTextColor(getColor(R.color.bg_text))
                binding?.tvDuringTenancy?.setTextColor(getColor(R.color.bg_text))


            }
            InspectionItem.CheckOut -> {
                binding?.tvMoveOut?.setBackgroundColor(getColor(R.color.orange))
                binding?.tvMoveIn?.setBackgroundColor(getColor(R.color.f3White))
                binding?.tvDuringTenancy?.setBackgroundColor(getColor(R.color.f3White))

                binding?.tvMoveOut?.setTextColor(getColor(R.color.white))
                binding?.tvMoveIn?.setTextColor(getColor(R.color.bg_text))
                binding?.tvDuringTenancy?.setTextColor(getColor(R.color.bg_text))

            }

            InspectionItem.DuringTenancy -> {
                binding?.tvMoveOut?.setBackgroundColor(getColor(R.color.f3White))
                binding?.tvMoveIn?.setBackgroundColor(getColor(R.color.f3White))
                binding?.tvDuringTenancy?.setBackgroundColor(getColor(R.color.orange))

                binding?.tvMoveOut?.setTextColor(getColor(R.color.bg_text))
                binding?.tvMoveIn?.setTextColor(getColor(R.color.bg_text))
                binding?.tvDuringTenancy?.setTextColor(getColor(R.color.white))

            }

        }
    }

    private fun SetTopBar() {
        binding?.topBar?.rlSearch?.gone()
        binding?.topBar?.rlCounter?.gone()
        binding?.topBar?.tvText?.text = "Digital Inspection"
        binding?.topBar?.ivAdd?.setImageResource(R.drawable.ic_back)
    }

    private fun sideMenuClicks(){
        binding?.topBar?.ivAdd?.setOnClickListener {
            onBackPressed()
        }
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
                this@InspectionReport,
                mTopDataSideItem
            )
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

                sideMenuDrawer(
                    binding?.dashboardDrawerLayout,
                    mDataSideItem,
                    binding?.dashboardNavMenuLayout,
                    binding?.dashboardNavView,
                    this@InspectionReport,
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding?.dashboardDrawerLayout?.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && data != null) {
            val array = ArrayList<Uri>()
            data.getParcelableArrayListExtra<Uri>(FilePickerConst.KEY_SELECTED_MEDIA)?.let {
                array.addAll(
                    it
                )
            }
            AsyncTask.THREAD_POOL_EXECUTOR.execute {
                array.forEachIndexed { index, uri ->
                    val file =
                        FileUtilityClass.getFileFromUri(
                            this,
                            uri
                        )
                    mFileData.add(file!!)
                }
                this.runOnUiThread {
                    mImageAdapter?.notifyDataSetChanged()
                }
            }
            Log.d("ProfileTAG", array.toString())
        }
    }
}

enum class InspectionItem {
    CheckIn, CheckOut, DuringTenancy
}