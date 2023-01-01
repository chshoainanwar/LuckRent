package com.example.luck_rent_android.ui.main.contractormodule

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityContractorJobCompleteBinding
import com.example.luck_rent_android.ui.main.contractormodule.adapter.JobCompleteAdapter

import com.example.luck_rent_android.ui.main.contractormodule.model.JobCompleteModel
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
import kotlin.math.roundToInt

class ContractorJobComplete : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var binding : ActivityContractorJobCompleteBinding? = null

    private var state: String? = "property"
    private var getValue: String? = null

    private var mDataSideItemCon = ArrayList<SideItems>()
    private var mTopDataSideItemCon = ArrayList<SideItems>()

    var adapter : JobCompleteAdapter? = null
    var list = ArrayList<JobCompleteModel>()
    var layoutManager : RecyclerView.LayoutManager? = null
    var mAdapter : RecyclerView.Adapter<JobCompleteAdapter.JobCompleteAdapterVH>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_contractor_job_complete)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))

        binding?.topBar?.ivAdd?.setOnClickListener{
            onBackPressed()
        }

        setTopBar()
        setRecycler()
        setDummyData()
        setSwipeOnItems()
        sideMenuClicks()

        //From Dashboard Check State
        getValue = intent.getStringExtra("ContractorDashboard")
        if (getValue == "Property"){
            state = "property"
            checkColorState(state ?: "")
        }else if (getValue == "TYPE"){
            state = "type"
            checkColorState(state ?: "")
        }
        else if (getValue == "STATUS") {
            state = "status"
            checkColorState(state ?: "")
        }
//Within Activity
            binding?.cvByProperty?.setOnClickListener {
            state = "property"
            checkColorState(state ?: "")
        }

        binding?.cvByStatus?.setOnClickListener {
            state = "status"
            checkColorState(state ?: "")
        }
        binding?.cvByType?.setOnClickListener {
            state = "type"
            checkColorState(state ?: "")
        }

    }

    private fun setRecycler() {
        mAdapter = JobCompleteAdapter(this, list)
        binding?.recycler?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding?.recycler?.adapter = mAdapter
        mAdapter?.notifyDataSetChanged()
    }

    private fun setDummyData(){
        list.add(
            JobCompleteModel(
                "1",
                title = "Job assigned date",
                "Complete",
                isFor = "job"
            )
        )
        list.add(
            JobCompleteModel(
                "2",
                title = "Job assigned date",
                "Complete",
                isFor = "job"
            )
        )
        list.add(
            JobCompleteModel(
                "3",
                title = "Maintainence Request",
                "Request",
                isFor = "maintainence"
            )
        )
        list.add(
            JobCompleteModel(
                "4",
                title = "Job assigned date",
                "Complete",
                isFor = "job"
            )
        )

    }



//    if (list[pos].status != "Complete"){
//        list[pos].status = "Complete"
//
//        mAdapter?.notifyDataSetChanged()
//        showToast("Status Updated")
//    }else{
//        mAdapter?.notifyDataSetChanged()
//    }


    private fun sideMenuClicks(){
        binding?.dashboardNavMenuLayoutForContractor?.ivCross?.setOnClickListener{
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

        binding?.dashboardNavMenuLayoutForContractor?.clickaBleNav?.setOnClickListener{
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
        binding?.dashboardNavMenuLayoutForContractor?.rlProfilePic?.setOnClickListener{

        }
        binding?.dashboardNavMenuLayoutForContractor?.tvName?.setOnClickListener{

        }

        binding?.topBar?.ivMenu?.setOnClickListener {

            sideMenuDrawerForContractorDas(
                binding?.dashboardDrawerLayoutContractor,
                mDataSideItemCon,
                binding?.dashboardNavMenuLayoutForContractor,
                binding?.dashboardNavViewForContractor,
                this,
                mTopDataSideItemCon
            )


        }

        binding?.content?.setOnClickListener {
            try {
                if (binding?.dashboardDrawerLayoutContractor?.isDrawerOpen(GravityCompat.END) == true) {
                    binding?.dashboardDrawerLayoutContractor?.closeDrawer(GravityCompat.END)
                }
            }catch (e: IllegalStateException){
                e.printStackTrace()
            }

        }

        binding?.dashboardDrawerLayoutContractor?.addDrawerListener(object : DrawerLayout.DrawerListener{
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {

                sideMenuDrawerForContractorDas(
                    binding?.dashboardDrawerLayoutContractor,
                    mDataSideItemCon,
                    binding?.dashboardNavMenuLayoutForContractor,
                    binding?.dashboardNavViewForContractor,
                    this@ContractorJobComplete,
                    mTopDataSideItemCon,
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

    private fun setTopBar() {
        binding?.topBar?.rlSearch?.gone()
        binding?.topBar?.rlCounter?.gone()
        binding?.topBar?.ivAdd?.setImageResource(R.drawable.ic_back)
        binding?.topBar?.tvText?.text = "Tasks & Notifications"
    }

    private fun checkColorState(state: String) {
        when (state) {
            "property" -> {
                binding?.cvByProperty?.setCardBackgroundColor(getColor(R.color.orange))
                binding?.cvByType?.setCardBackgroundColor(getColor(R.color.f3White))
                binding?.cvByStatus?.setCardBackgroundColor(getColor(R.color.f3White))

                binding?.tvByProperty?.setTextColor(getColor(R.color.white))
                binding?.tvByStatus?.setTextColor(getColor(R.color.sand))
                binding?.tvByType?.setTextColor(getColor(R.color.sand))
            }

            "status" -> {
                binding?.cvByProperty?.setCardBackgroundColor(getColor(R.color.f3White))
                binding?.cvByType?.setCardBackgroundColor(getColor(R.color.f3White))
                binding?.cvByStatus?.setCardBackgroundColor(getColor(R.color.orange))

                binding?.tvByProperty?.setTextColor(getColor(R.color.sand))
                binding?.tvByStatus?.setTextColor(getColor(R.color.white))
                binding?.tvByType?.setTextColor(getColor(R.color.sand))
            }
            "type" -> {
                binding?.cvByProperty?.setCardBackgroundColor(getColor(R.color.f3White))
                binding?.cvByType?.setCardBackgroundColor(getColor(R.color.orange))
                binding?.cvByStatus?.setCardBackgroundColor(getColor(R.color.f3White))

                binding?.tvByProperty?.setTextColor(getColor(R.color.sand))
                binding?.tvByStatus?.setTextColor(getColor(R.color.sand))
                binding?.tvByType?.setTextColor(getColor(R.color.white))
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding?.dashboardDrawerLayoutContractor?.closeDrawer(GravityCompat.START)
        return true
    }
    private fun setSwipeOnItems(){

        val swipeHelper = object : MySwipeHelper(
            this, binding?.recycler!!, TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 105f, resources.displayMetrics
            ).roundToInt()
        ) {
            override fun instantiateMyButton(
                viewHolder: RecyclerView.ViewHolder,
                buffer: MutableList<MyButton>
            ) {
                buffer.add(MyButton(this@ContractorJobComplete, R.drawable.transparent_bg,
                    Color.parseColor("#0000ffff"),
                    object :
                        MyButtonClickListener {
                        override fun onClick(pos: Int) {

                            try {

                                if (list[pos].isFor.equals("job")) {
                                    if (list[pos].status != "Complete") {
                                        list[pos].status = "Complete"
//                                        adapter?.notifyDataSetChanged()
                                        mAdapter?.notifyDataSetChanged()
                                        showToast("Status Updated")
                                    } else {
                                        showToast("Already Completed")
                                        mAdapter?.notifyDataSetChanged()
                                    }
                                }else{

                                    val view = View.inflate(context, R.layout.dialog_mark, null)
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

                                    tvMark?.text = "Accept"
                                    tvDelete?.text = "Decline"
                                    ivMark?.setImageResource(R.drawable.ic_accept)
                                    ivDelete?.setImageResource(R.drawable.ic_decline)

                                    //Dialog Done Button
                                    rlMark?.setOnClickListener {
                                        if (list[pos].status == "Request") {
                                            list[pos].status = "Accept"
//                                        adapter?.notifyDataSetChanged()
                                            mAdapter?.notifyDataSetChanged()
                                            showToast("Status Updated")
                                            dialog.dismiss()
                                        } else if(list[pos].status == "Decline"){
                                            list[pos].status = "Accept"
//                                        adapter?.notifyDataSetChanged()
                                            mAdapter?.notifyDataSetChanged()
                                            showToast("Status Updated")
                                            dialog.dismiss()
                                        }else if(list[pos].status == "Accept"){
                                            mAdapter?.notifyDataSetChanged()
                                            showToast("Already Accepted")
                                            dialog.dismiss()
                                        }else  {
                                            mAdapter?.notifyDataSetChanged()
                                        dialog.dismiss()
                                    }}
                                    //Dialog Cancel Icon
                                    rlDelete?.setOnClickListener {
                                        if (list[pos].status == "Request") {
                                            list[pos].status = "Decline"
//                                        adapter?.notifyDataSetChanged()
                                            mAdapter?.notifyDataSetChanged()
                                            showToast("Status Updated")
                                            dialog.dismiss()
                                        } else if (list[pos].status == "Accept"){
                                            list[pos].status = "Decline"
//                                        adapter?.notifyDataSetChanged()
                                            mAdapter?.notifyDataSetChanged()
                                            showToast("Status Updated")
                                            dialog.dismiss()
                                        }else if (list[pos].status == "Decline"){
                                            showToast("Already Declined")
                                            mAdapter?.notifyDataSetChanged()
                                            dialog.dismiss()
                                        } else {
                                            mAdapter?.notifyDataSetChanged()
                                        }
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
}



