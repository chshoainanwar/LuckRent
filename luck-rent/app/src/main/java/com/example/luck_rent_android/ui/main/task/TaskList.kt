package com.example.luck_rent_android.ui.main.task

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityTaskListBinding
import com.example.luck_rent_android.ui.main.auth.Login
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.contractormodule.MyButton
import com.example.luck_rent_android.ui.main.contractormodule.MyButtonClickListener
import com.example.luck_rent_android.ui.main.contractormodule.MySwipeHelper
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.order.adapter.OrderAdapter
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.example.luck_rent_android.ui.main.task.adapter.TaskAdapter
import com.example.luck_rent_android.ui.main.task.model.TaskModel
import com.example.luck_rent_android.utils.getDesiredOutputDateString
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import com.kodextech.project.kodexlib.utils.visible
import java.lang.IllegalStateException
import java.util.*
import kotlin.math.roundToInt

class TaskList : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var binding: ActivityTaskListBinding? = null

    private var mDataSideItem = ArrayList<SideItems>()
    private var mTopDataSideItem = ArrayList<SideItems>()

    val list = ArrayList<TaskModel>()
    var adapter: TaskAdapter? = null
    val layoutManager: RecyclerView.LayoutManager? = null

    var timeTonotify: String? = null
    var date: String? = null
    var time: String? = null
    var selectedDay: Int? = null
    var selectedMonth: Int? = null
    var selectedYear: Int? = null
    private val TAG = "myTAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_task_list)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))

        setTopBar()
        setSideMenuCLicks()
        setRecycler()
        setSwipe()

        if (list.isEmpty()) {
            binding?.tvAddTask?.visible()
        } else {
            binding?.tvAddTask?.gone()
        }

        binding?.rlAdd?.setOnClickListener {
            openDialog()
        }

    }

    private fun setTopBar() {
        binding?.topBar?.rlSearch?.gone()
        binding?.topBar?.rlCounter?.gone()
        binding?.topBar?.tvText?.text = "Tasks"
        binding?.topBar?.ivAdd?.setImageResource(R.drawable.ic_back)
        binding?.topBar?.ivAdd?.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setSideMenuCLicks() {
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

        binding?.dashboardNavMenuLayout?.clickaBleNav?.setOnClickListener {
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
                this@TaskList,
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
                    this@TaskList,
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

    private fun setRecycler() {
        adapter = TaskAdapter(this, list)
        binding?.recycler?.layoutManager =
            LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        binding?.recycler?.adapter = adapter
        adapter?.notifyDataSetChanged()
    }

    private fun setSwipe() {
        val swipeHelper = object : MySwipeHelper(
            this, binding?.recycler!!, TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 105f, resources.displayMetrics
            ).roundToInt()
        ) {
            override fun instantiateMyButton(
                viewHolder: RecyclerView.ViewHolder,
                buffer: MutableList<MyButton>
            ) {
                buffer.add(MyButton(this@TaskList, R.drawable.transparent_bg,
                    Color.parseColor("#0000ffff"),
                    object :
                        MyButtonClickListener {
                        override fun onClick(pos: Int) {

                            try {
                                if (list[pos].status != "Complete"){
                                    list[pos].status = "Complete"
                                    adapter?.notifyDataSetChanged()
                                    showToast("Status Updated")
                                }else{
                                    adapter?.notifyDataSetChanged()
                                }



                            } catch (e: Exception) {
                            }


                        }


                    }

                ))

            }


        }
    }


    private fun openDialog() {
        val view = View.inflate(this, R.layout.dialog_add_task, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()


        //Register Done Button & Cancel Icon from Dialog
        var btnAdd = view?.findViewById<AppCompatButton>(R.id.btnAdd)
        var etTaskName = view?.findViewById<AppCompatEditText>(R.id.etTaskName)
        var tvDueDate = view?.findViewById<AppCompatTextView>(R.id.tvDueDate)
        var tvRemindTime = view?.findViewById<AppCompatTextView>(R.id.tvRemindTime)
        var cancelIcon = view?.findViewById<AppCompatImageView>(R.id.cancelIcon)

        //Dialog Done Button
        btnAdd?.setOnClickListener {

            var getTaskName = etTaskName?.text.toString()
            var getDate : String? = tvDueDate?.text.toString()
            var getTime : String? = tvRemindTime?.text.toString()
            if (getTaskName.isEmpty()) {
                etTaskName?.error = "Required"
            }else if (getDate.isNullOrEmpty()){
                showToast("Date Required")
            } else if (getTime.isNullOrEmpty()){
                showToast("Time Required")
            }else {
                list.add(TaskModel(getTaskName,getDate,getTime))
                adapter?.notifyDataSetChanged()
                dialog.dismiss()
            }
        }

        //Dialog Pick Date
        tvDueDate?.setOnClickListener{
            showToast("Clicked")
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, day ->

                    var monthi = month.plus(1)
                    val formatedDate = "$day-$monthi-$year"
                    tvDueDate?.text = formatedDate.getDesiredOutputDateString(
                        "dd-MM-yyyy",
                        "dd MMM yy"
                    )
                    selectedDay = day
                    selectedMonth = month
                    selectedYear = year

                }, year, month, day
            )
            dpd.datePicker.minDate = System.currentTimeMillis()
            dpd.show()
        }

        //Dialog Pick Time
        tvRemindTime?.setOnClickListener{

            var calendar = Calendar.getInstance()
            val hour: Int = calendar.get(Calendar.HOUR_OF_DAY)
            val minute: Int = calendar.get(Calendar.MINUTE)
            val timePickerDialog = TimePickerDialog(this,
                { timePicker, i, i1 ->
                    Log.i(
                        TAG, "onTimeSet: " + i + " Minuts " + i1 + " Formate " + FormatTime(i, i1)
                    )
                    val datetime = Calendar.getInstance()
                    val c = Calendar.getInstance()
                    datetime[Calendar.YEAR] = selectedYear!!
                    datetime[Calendar.DAY_OF_MONTH] = selectedDay!!
                    datetime[Calendar.MONTH] = selectedMonth!!
                    datetime[Calendar.HOUR_OF_DAY] = i
                    datetime[Calendar.MINUTE] = i1
                    if (datetime.timeInMillis >= c.timeInMillis) {
                        //it's after current
                        val hour = i % 12
                        timeTonotify = "$i:$i1"
                        tvRemindTime.text = FormatTime(i, i1)
                        Log.i(
                            TAG, "onTimeSet: Valid Time"
                        )
                    } else {
                        Log.i(
                            TAG, "onTimeSet: Invalid time"
                        )
                        //it's before current'
                        Toast.makeText(applicationContext, "Invalid Time", Toast.LENGTH_LONG).show()
                    }

                    /*  timeTonotify = i + ":" + i1;
                              mTimePicker.setText(FormatTime(i, i1));*/
                }, hour, minute, false
            )
            if (tvRemindTime?.text.toString() == "Pick Date") {
                Toast.makeText(this, "Please Select Date First", Toast.LENGTH_SHORT).show()
            } else {
                timePickerDialog.show()
            }

        }

        //Dialog Cancel Icon
        cancelIcon?.setOnClickListener {
            dialog.dismiss()
        }

    }

    fun FormatTime(hour: Int, minute: Int): String? {
        var time: String
        time = ""
        val formattedMinute: String
        formattedMinute = if (minute / 10 == 0) {
            "0$minute"
        } else {
            "" + minute
        }
        time = if (hour == 0) {
            "12:$formattedMinute AM"
        } else if (hour < 12) {
            "$hour:$formattedMinute AM"
        } else if (hour == 12) {
            "12:$formattedMinute PM"
        } else {
            val temp = hour - 12
            "$temp:$formattedMinute PM"
        }
        return time
    }

    override fun onSetupViewGroup() {

    }

    override fun setupContentViewWithBinding() {
        mViewGroup = binding?.content
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