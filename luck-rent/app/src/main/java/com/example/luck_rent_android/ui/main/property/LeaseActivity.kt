package com.example.luck_rent_android.ui.main.property

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityLeaseBinding
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.contractormodule.adapter.JobCompleteAdapter
import com.example.luck_rent_android.ui.main.contractormodule.model.JobCompleteModel
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.expense.adapter.ImageUploadAdapter
import com.example.luck_rent_android.ui.main.expense.adapter.Placeholders
import com.example.luck_rent_android.ui.main.profile.Profile.Companion.roleProfile
import com.example.luck_rent_android.ui.main.property.adapter.LeaseFileAdapter
import com.example.luck_rent_android.ui.main.property.adapter.LeasePropertyAdapter
import com.example.luck_rent_android.ui.main.property.adapter.LeasePropertyDummy
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import com.kodextech.project.kodexlib.utils.visible
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.FilePickerConst
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class LeaseActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var binding: ActivityLeaseBinding? = null
    var isSelected: String? = null
    var isRecycler: Boolean? = false
    var isLease: Boolean? = false
    var isApp: Boolean? = true
    var isNotice: Boolean? = true
    var isMove: Boolean? = true
    var isEvic: Boolean? = true
    var PICKFILE_RESULT_CODE = 111

    private var mDataSideItem = ArrayList<SideItems>()
    private var mTopDataSideItem = ArrayList<SideItems>()
    private var mData: ArrayList<LeasePropertyDummy> = ArrayList()

    val mFile: ArrayList<File> = ArrayList()
    private var mFileAdapter: LeaseFileAdapter? = null

    var list = ArrayList<JobCompleteModel>()
    var layoutManager: RecyclerView.LayoutManager? = null
    var mAdapter: RecyclerView.Adapter<JobCompleteAdapter.JobCompleteAdapterVH>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        statusBarColor(getColor(R.color.darkBlue))
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lease)
        super.onCreate(savedInstanceState)
        initTopBar()
        setSpinnerValues()
        sideMenuClicks()
        setCalenders()

        binding?.btnEndLease?.setOnClickListener {
            onBackPressed()
        }

        binding?.btnRevenue?.setOnClickListener {
            isSelected = "revenue"
            checkColorState(isSelected!!)
        }
        binding?.btnNew?.setOnClickListener {
            isSelected = "new"
            checkColorState(isSelected!!)
        }
        binding?.btnYes?.setOnClickListener {
            isSelected = "yes"
            checkColorState(isSelected!!)
            binding?.tvAutoAmount?.visible()
        }
        binding?.btnNo?.setOnClickListener {
            isSelected = "no"
            checkColorState(isSelected!!)
            binding?.tvAutoAmount?.gone()
        }

        binding?.topBar?.ivAdd?.setOnClickListener { onBackPressed() }


        fun ImageView.loadImage(
            link: Any?,
            drawable: Placeholders = Placeholders.USER,
            isForCircle: Boolean = false,
        ) {
            try {
                if (isForCircle) {
                    Glide.with(this)
                        .asBitmap()
                        .load(link)
                        .circleCrop()
                        .placeholder(drawable.intValue)//circularProgressDrawable
                        .error(drawable.intValue)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .into(this)
                } else {
                    Glide.with(this)
                        .load(link)
                        .centerCrop()
                        .placeholder(drawable.intValue)//circularProgressDrawable
                        .error(drawable.intValue)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .into(this)
                }
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }


        }

    }

    private fun sideMenuClicks() {
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
            //binding?.dashboardNavViewForContractor?.gone()
            sideMenuDrawer(
                binding?.dashboardDrawerLayout,
                mDataSideItem,
                binding?.dashboardNavMenuLayout,
                binding?.dashboardNavView,
                this@LeaseActivity,
                mTopDataSideItem
            )
//            else{
//                binding?.dashboardNavView?.gone()
//                sideMenuDrawerForContractor(
//                    binding?.dashboardDrawerLayout,
//                    mDataSideItemCon,
//                    binding?.dashboardNavMenuLayoutForContractor,
//                    binding?.dashboardNavViewForContractor,
//                    this@Dashboard,
//                    mTopDataSideItemCon
//                )
//            }

        }
        binding?.contentLease?.setOnClickListener {
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
                Log.d("getState", "onDrawerStateChanged: " + slideOffset)

            }

            override fun onDrawerOpened(drawerView: View) {

                sideMenuDrawer(
                    binding?.dashboardDrawerLayout,
                    mDataSideItem,
                    binding?.dashboardNavMenuLayout,
                    binding?.dashboardNavView,
                    this@LeaseActivity,
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

    //        binding?.btnUploadFiles?.setOnClickListener{
//            FilePickerBuilder.instance
//                .setMaxCount(5)
//                .setActivityTheme(R.style.LibAppTheme) //optional
//                .pickFile(this, PICKFILE_RESULT_CODE)
//        }

    //    private fun setFileAdapter() {
//        binding?.rvPhotos?.layoutManager =
//            GridLayoutManager(this, 3)
//        mFileAdapter =
//            LeaseFileAdapter(this, mFile) { position, forDelete ->
//                FilePickerBuilder.instance
//                    .setMaxCount(5)
//                    .setActivityTheme(R.style.LibAppTheme) //optional
//                    .pickFile(this, PICKFILE_RESULT_CODE)
//            }
//
//
//        binding?.rvPhotos?.adapter = mFileAdapter
//        mFileAdapter?.notifyDataSetChanged()
//
//    }
    private fun setCalenders() {
        binding?.rlStart?.setOnClickListener {
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
                        binding?.tvStartDate?.text = "$year-$monthi-$day"
                    } else {
                        binding?.tvStartDate?.text = "$year-0$monthi-$day"

                    }

                }, year, month, day
            )
//            dpd.datePicker.minDate = System.currentTimeMillis()
            dpd.show()
        }
        binding?.rlEnd?.setOnClickListener {
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
                        binding?.tvEndDate?.text = "$year-$monthi-$day"
                    } else {
                        binding?.tvEndDate?.text = "$year-0$monthi-$day"

                    }

                }, year, month, day
            )
//            dpd.datePicker.minDate = System.currentTimeMillis()
            dpd.show()
        }
        binding?.rlRentDueDate?.setOnClickListener {
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
                        binding?.tvRentDueDate?.text = "$year-$monthi-$day"
                    } else {
                        binding?.tvRentDueDate?.text = "$year-0$monthi-$day"

                    }

                }, year, month, day
            )
//            dpd.datePicker.minDate = System.currentTimeMillis()
            dpd.show()
        }

    }

    private fun setSpinnerValues() {
        binding?.spRenewal?.setItems(
            "30 days",
            "60 days",
            "90 days",
            "120 days"
        )
        binding?.spRentFrequency?.setItems(
            "One-Time",
            "Weekly",
            "Monthly"
        )
        binding?.spRentIncrease?.setItems(
            "60 days",
            "90 days",
            "120 days",
            "150 days"
        )
        binding?.spRentOverdue?.setItems(
            "1 day",
            "2 days",
            "3 days",
            "4 days",
            "5 days"
        )

    }

    private fun checkColorState(isSelected: String) {
        when (isSelected) {
            "revenue" -> {

                binding?.btnRevenue?.buttonTintList = getColorStateList(R.color.darkBlue)
                binding?.btnNew?.buttonTintList = getColorStateList(R.color.hardSand)


            }
            "new" -> {
                binding?.btnNew?.buttonTintList = getColorStateList(R.color.darkBlue)
                binding?.btnRevenue?.buttonTintList = getColorStateList(R.color.hardSand)
            }
            "yes" -> {

                binding?.btnYes?.buttonTintList = getColorStateList(R.color.darkBlue)
                binding?.btnNo?.buttonTintList = getColorStateList(R.color.hardSand)


            }
            "no" -> {
                binding?.btnNo?.buttonTintList = getColorStateList(R.color.darkBlue)
                binding?.btnYes?.buttonTintList = getColorStateList(R.color.hardSand)
            }
        }
    }

//    private fun setRecycler() {
//        mData.clear()
//        mData.add(LeasePropertyDummy("Code :  123-4343", "New Colony 33 Streerny"))
//        mData.add(LeasePropertyDummy("Code :  123-4344", "New Colony 33 Streerny"))
//        mData.add(LeasePropertyDummy("Code :  123-4345", "New Colony 33 Streerny"))
//        mData.add(LeasePropertyDummy("Code :  123-4346", "New Colony 33 Streerny"))
//
//
//        binding?.rvProperty?.adapter = LeasePropertyAdapter(this, mData){child: LeasePropertyDummy ->
//            binding?.tvProperty?.text = child.id + "\n" +child.address
//            binding?.rlRecycler?.gone()
//            isRecycler = false
//
//        }
//        binding?.rvProperty?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
//        binding?.rvProperty?.adapter?.notifyDataSetChanged()
//
//
//    }

    private fun initTopBar() {
        binding?.topBar?.tvText?.text = "Add Lease"
        binding?.topBar?.ivAdd?.setImageResource(R.drawable.ic_back)
        binding?.topBar?.rlSearch?.gone()
        binding?.topBar?.rlCounter?.gone()

    }

//   private fun radioButtons(){
//       binding?.btnLeaseExhibits?.setOnClickListener{
//           if (isLease == false){
//               binding?.btnLeaseExhibits?.isChecked = false
//               isLease = true
//           }else{
//               binding?.btnLeaseExhibits?.isChecked = true
//               isLease = false
//           }
//       }
//       binding?.btnAppsCredit?.setOnClickListener{
//           if (isApp == false){
//               binding?.btnAppsCredit?.isChecked = false
//               isApp = true
//           }else{
//               binding?.btnAppsCredit?.isChecked = true
//               isApp = false
//           }
//       }
//       binding?.btnNotice?.setOnClickListener{
//           if (isNotice == false){
//               binding?.btnNotice?.isChecked = false
//               isNotice = true
//           }else{
//               binding?.btnNotice?.isChecked = true
//               isNotice = false
//           }
//       }
//       binding?.btnMovieIn?.setOnClickListener{
//           if (isMove == false){
//               binding?.btnMovieIn?.isChecked = false
//               isMove = true
//           }else{
//               binding?.btnMovieIn?.isChecked = true
//               isMove = false
//           }
//       }
//       binding?.btnEviction?.setOnClickListener{
//           if (isEvic == false){
//               binding?.btnEviction?.isChecked = false
//               isEvic = true
//           }else{
//               binding?.btnEviction?.isChecked = true
//               isEvic = false
//           }
//       }
//   }


    override fun onSetupViewGroup() {
        mViewGroup = binding?.contentLease
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
        if (requestCode == PICKFILE_RESULT_CODE && data != null) {
            val array = ArrayList<Uri>()
            data.getParcelableArrayListExtra<Uri>(FilePickerConst.KEY_SELECTED_DOCS)?.let {
                array.addAll(
                    it
                )
            }
            AsyncTask.THREAD_POOL_EXECUTOR.execute {
                array.forEachIndexed { index, uri ->
                    val file =
                        FileUtilityClass.getFileFromUri(
                            this@LeaseActivity,
                            uri
                        )
                    mFile.add(file!!)
                }



                runOnUiThread {
                    mFileAdapter?.notifyDataSetChanged()

                }

            }

//            profile_image_selected?.addAll(array)
            Log.d("ProfileTAG", array.toString())
        }
    }
}

