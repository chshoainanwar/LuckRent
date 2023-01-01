package com.example.luck_rent_android.ui.main.dashboard

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityDashboardBinding
import com.example.luck_rent_android.ui.main.applicationform.ApplicationForm
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.expense.ExpenseListing
import com.example.luck_rent_android.ui.main.invoice.AddInvoice
import com.example.luck_rent_android.ui.main.notification.NotificationActivity
import com.example.luck_rent_android.ui.main.payment.AddPayment
import com.example.luck_rent_android.ui.main.payment.PaymentListing
import com.example.luck_rent_android.ui.main.profile.Profile.Companion.roleProfile
import com.example.luck_rent_android.ui.main.property.PropertiesActivity
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.example.luck_rent_android.ui.main.sideMenu.SideMenuAdapter
import com.example.luck_rent_android.ui.main.task.TaskList
import com.example.luck_rent_android.utils.getDesiredOutputDateString
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import com.kodextech.project.kodexlib.utils.visible
import java.lang.IllegalStateException
import java.util.*
import kotlin.collections.ArrayList


class Dashboard : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var binding: ActivityDashboardBinding? = null

    private var reminderState: String? = null
    private var isOutstanding: Boolean? = false
    private var isConduct: Boolean? = false
    private var isIncompleteTask: Boolean? = false
    private var isAddLayout: Boolean? = false
    private var isNotification: Boolean? = false
    private var tvAddPayment: AppCompatTextView? = null

    private var radioStatus: String? = null

    private var mDataSideItem = ArrayList<SideItems>()
    private var mTopDataSideItem = ArrayList<SideItems>()
    private var mSideAdapter: SideMenuAdapter? = null

    private var isopened: Boolean? = false
    private var mDataSideItemCon = ArrayList<SideItems>()
    private var mTopDataSideItemCon = ArrayList<SideItems>()
    private var mSideAdapterCon: SideMenuAdapter? = null

    var xAxisMonths = ArrayList<String>()
    var yGroup1 = ArrayList<BarEntry>()
    var yGroup2 = ArrayList<BarEntry>()
    var yGroup3 = ArrayList<BarEntry>()
    lateinit var barDataSet1: BarDataSet
    lateinit var barDataSet2: BarDataSet
    lateinit var barDataSet3: BarDataSet


    override fun onSetupViewGroup() {
        mViewGroup = binding?.contentDashboard

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        statusBarColor(getColor(R.color.darkBlue))
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        super.onCreate(savedInstanceState)

        setDate()
        binding?.topBar?.rlSearch?.gone()

        when(roleProfile){
            "Landlord" ->{
                binding?.rlName?.visible()
                binding?.rlPropertyName?.gone()
                binding?.rlRadio?.gone()
            }
            "Property Manager" ->{
                binding?.rlName?.gone()
                binding?.rlPropertyName?.visible()
                binding?.rlRadio?.visible()
            }
        }
        binding?.rbManagement?.setOnClickListener {
            radioStatus = "management"
            changeStatusColorState()
        }
        binding?.rbRental?.setOnClickListener {
            radioStatus = "rental"
            changeStatusColorState()
        }
        binding?.topBar?.rlCounter?.setOnClickListener {
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }
        binding?.rlTotalUnits?.setOnClickListener {
            val intent = Intent(this, PropertiesActivity::class.java)
            startActivity(intent)
        }
        binding?.rlPaid?.setOnClickListener {
            val intent = Intent(this, PaymentListing::class.java)
            startActivity(intent)
        }
        binding?.rlVacnts?.setOnClickListener {
            val intent = Intent(this, PropertiesActivity::class.java)
            intent.putExtra("Vacant","VacantProperties")
            startActivity(intent)
        }
        binding?.dashboardNavMenuLayout?.ivCross?.setOnClickListener {
            if (roleProfile == "Landlord") {
                val intent = Intent(this, Dashboard::class.java)
                startActivity(intent)
            } else if (roleProfile == "Property Manager") {
                val intent = Intent(this, Dashboard::class.java)
                startActivity(intent)
            }
        }

        binding?.topBar?.ivAdd?.setOnClickListener {
            if (isAddLayout == true) {
                isAddLayout = false
                binding?.rlAdds?.gone()
                binding?.btnPlus?.gone()

            } else {
                isAddLayout = true
                binding?.rlAdds?.visible()
                binding?.btnPlus?.visible()
            }
        }

        binding?.btnPlus?.setOnClickListener {

            if (isAddLayout == true) {
                binding?.rlAdds?.gone()
                binding?.btnPlus?.gone()
            } else {

            }
        }

        binding?.btnTurnOnNotification?.setOnClickListener {
            if (isNotification == true) {
                isNotification = false
                binding?.btnTurnOnNotification?.text = "TURN ON NOTIFICATION"
            } else {
                isNotification = true
                binding?.btnTurnOnNotification?.text = "TURN OFF NOTIFICATION"
            }
        }


        binding?.tvAddPayment?.setOnClickListener {
            binding?.rlAdds?.gone()
            binding?.btnPlus?.gone()
            if (roleProfile == "Landlord") {
                val intent = Intent(this, AddPayment::class.java)
                intent.putExtra("isFrom", "payment")

                intent.putExtra("isFor", "0")
                startActivity(intent)
            } else if (roleProfile == "Property Manager") {
                val intent = Intent(this, AddPayment::class.java)
                intent.putExtra("isFrom", "payment")

                intent.putExtra("isFor", "0")
                startActivity(intent)
            }


        }

        binding?.tvAddExpence?.setOnClickListener {
            binding?.rlAdds?.gone()
            binding?.btnPlus?.gone()
            if (roleProfile == "Landlord") {
                val intent = Intent(this, ExpenseListing::class.java)
                startActivity(intent)
            } else if (roleProfile == "Property Manager") {

                val intent = Intent(this, ExpenseListing::class.java)
                startActivity(intent)

            }


        }
        binding?.tvAddInvoice?.setOnClickListener {
            binding?.rlAdds?.gone()
            binding?.btnPlus?.gone()
            val intent = Intent(this, AddInvoice::class.java)
            intent.putExtra("isFor", "0")
            intent.putExtra("isFrom", "invoice")
            startActivity(intent)
        }

        binding?.tvAddTask?.setOnClickListener {
            binding?.rlAdds?.gone()
            binding?.btnPlus?.gone()
            val intent = Intent(this, TaskList::class.java)
            startActivity(intent)
        }

        binding?.tvByProperty?.setOnClickListener {
            reminderState = "byProperty"
            checkReminderState()

            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }
        binding?.tvByType?.setOnClickListener {
            reminderState = "byType"
            checkReminderState()

            val intent = Intent(this, NotificationActivity::class.java)
            intent.putExtra("type", "TYPE")
            startActivity(intent)
        }
        binding?.tvByStatus?.setOnClickListener {
            reminderState = "byStatus"
            checkReminderState()

            val intent = Intent(this, NotificationActivity::class.java)
            intent.putExtra("type", "STATUS")
            startActivity(intent)
        }

        binding?.rlRevenue?.setOnClickListener {
            setRevenueBarChart()
        }
        binding?.rlExpense?.setOnClickListener {
            setExpenseBarChart()
        }
        binding?.rlIncome?.setOnClickListener {
            setIncomeBarChart()
        }
        setCompleteBarChart()


        binding?.ivOutstanding?.setOnClickListener {
            if (isOutstanding == true) {
                isOutstanding = false
                binding?.ivOutstanding?.setImageResource(R.drawable.ic_new_unchecked)
            } else {
                isOutstanding = true
                binding?.ivOutstanding?.setImageResource(R.drawable.ic_new_checked)
            }
        }

        binding?.ivConduct?.setOnClickListener {
            if (isConduct == true) {
                isConduct = false
                binding?.ivConduct?.setImageResource(R.drawable.ic_new_unchecked)
            } else {
                isConduct = true
                binding?.ivConduct?.setImageResource(R.drawable.ic_new_checked)
            }
        }
        binding?.ivDishwasherRepair?.setOnClickListener {
            if (isIncompleteTask == true) {
                isIncompleteTask = false
                binding?.ivDishwasherRepair?.setImageResource(R.drawable.ic_new_unchecked)
            } else {
                isIncompleteTask = true
                binding?.ivDishwasherRepair?.setImageResource(R.drawable.ic_new_checked)
            }
        }


        binding?.topBar?.rlMenu?.setOnClickListener {
            binding?.rlAdds?.gone()
            //binding?.dashboardNavViewForContractor?.gone()
            sideMenuDrawer(
                binding?.dashboardDrawerLayout,
                mDataSideItem,
                binding?.dashboardNavMenuLayout,
                binding?.dashboardNavView,
                this@Dashboard,
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
        binding?.contentDashboard?.setOnClickListener {
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
                binding?.rlAdds?.gone()

                isopened = true
                sideMenuDrawer(
                    binding?.dashboardDrawerLayout,
                    mDataSideItem,
                    binding?.dashboardNavMenuLayout,
                    binding?.dashboardNavView,
                    this@Dashboard,
                    mTopDataSideItem,
                    true
                )

                //    binding?.dashboardNavViewForContractor?.gone()

            }
            //                    else{
//                    binding?.dashboardNavView?.gone()
//                    sideMenuDrawerForContractor(
//                        binding?.dashboardDrawerLayout,
//                        mDataSideItemCon,
//                        binding?.dashboardNav
//                        MenuLayoutForContractor,
//                        binding?.dashboardNavViewForContractor,
//                        this@Dashboard,
//                        mTopDataSideItemCon,
//                        true
//                    )
//                }


            override fun onDrawerClosed(drawerView: View) {
                isopened = false

            }

            override fun onDrawerStateChanged(newState: Int) {

                Log.d("getState", "onDrawerStateChanged: " + newState)
            }

        })

    }

    private fun changeStatusColorState() {
        when (radioStatus) {
            "management" -> {
                binding?.rbManagement?.buttonTintList = getColorStateList(R.color.darkBlue)
                binding?.rbRental?.buttonTintList = getColorStateList(R.color.sand)
            }
            "rental" -> {
                binding?.rbManagement?.buttonTintList = getColorStateList(R.color.sand)
                binding?.rbRental?.buttonTintList = getColorStateList(R.color.darkBlue)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun setDate() {
        binding?.tvFirstDate?.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, day ->

                    var monthi = month.plus(1)
                    val formatedDate = "$day-$monthi-$year"
                    binding?.tvFirstDate?.text = formatedDate.getDesiredOutputDateString(
                        "dd-MM-yyyy",
                        "dd MMM yy"
                    )


                }, year, month, day
            )
//            dpd.datePicker.minDate = System.currentTimeMillis()
            dpd.show()
        }
        binding?.tvSecondDate?.setOnClickListener {

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, day ->

                    // Display Selected date in TextView
                    var monthi = month.plus(1)
                    val formatedDate = "$day-$monthi-$year"
                    binding?.tvSecondDate?.text = formatedDate.getDesiredOutputDateString(
                        "dd-MM-yyyy",
                        "dd MMM yy"
                    )


                }, year, month, day
            )
//            dpd.datePicker.minDate = System.currentTimeMillis()
            dpd.show()

        }
    }

    private fun setCompleteBarChart() {

        val barWidth: Float
        val barSpace: Float
        val groupSpace: Float
        val groupCount = 12

        barWidth = 0.15f
        barSpace = 0.07f
        groupSpace = 0.56f

        SetXValues()
        SetYValues()

        //      Prepare Group Data

        barDataSet1 = BarDataSet(yGroup1, "Bar1")
        barDataSet2 = BarDataSet(yGroup2, "Bar2")
        barDataSet3 = BarDataSet(yGroup3, "Bar3")

        barDataSet1.setColors(this.getColor(R.color.blue_bar))
        barDataSet2.setColors(this.getColor(R.color.pink_bar))
        barDataSet3.setColors(this.getColor(R.color.yellow_bar))

        barDataSet1.setDrawValues(true)
        barDataSet2.setDrawValues(true)
        barDataSet3.setDrawValues(true)

//        barDataSet1.label = "Revenue"
//        barDataSet2.label = "Expense"
//        barDataSet3.label = "Profit"

        barDataSet1.setDrawIcons(false)
        barDataSet2.setDrawIcons(false)
        barDataSet3.setDrawIcons(false)
        barDataSet1.setDrawValues(false)
        barDataSet2.setDrawValues(false)
        barDataSet3.setDrawValues(false)

        var barData = BarData(barDataSet1, barDataSet2, barDataSet3)
        barData.setValueFormatter(LargeValueFormatter())

        binding?.barChart?.description?.isEnabled = false
        binding?.barChart?.description?.textSize = 0f

        binding?.barChart?.data = barData
        binding?.barChart?.barData?.barWidth = barWidth
        binding?.barChart?.xAxis?.axisMinimum = 0f
        binding?.barChart?.xAxis?.axisMaximum = 12f
        binding?.barChart?.groupBars(0f, groupSpace, barSpace)
        binding?.barChart?.data?.isHighlightEnabled = false
        binding?.barChart?.invalidate()

        // Customize Bar Chart Legends using Legend Entry

        binding?.barChart?.legend?.isEnabled = false

//        var legend = binding?.barChart?.legend
//        legend?.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
//        legend?.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
//        legend?.orientation = Legend.LegendOrientation.HORIZONTAL
//        legend?.setDrawInside(false)
//
//        var legenedEntries = arrayListOf<LegendEntry>()
//        legenedEntries.add(
//            LegendEntry(
//                "Revenue",
//                Legend.LegendForm.SQUARE,
//                8f,
//                8f,
//                null,
//                this.getColor(R.color.blue_bar)
//            )
//        )
//        legenedEntries.add(
//            LegendEntry(
//                "Expense",
//                Legend.LegendForm.SQUARE,
//                8f,
//                8f,
//                null,
//                this.getColor(R.color.pink_bar)
//            )
//        )
//        legenedEntries.add(
//            LegendEntry(
//                "Profit",
//                Legend.LegendForm.SQUARE,
//                8f,
//                8f,
//                null,
//                this.getColor(R.color.yellow_bar)
//            )
//        )

//        legend?.setCustom(legenedEntries)
//        legend?.yOffset = 2f
//        legend?.xOffset = 2f
//        legend?.yEntrySpace = 0f
//        legend?.textSize = 5f

        // Populate X-Axis Data

        binding?.barChart?.animateY(2000)
        binding?.barChart?.setDrawValueAboveBar(false)
        binding?.barChart?.setPinchZoom(false)
        binding?.barChart?.setTouchEnabled(false)

        val xAxis = binding?.barChart?.xAxis
        xAxis?.granularity = 1f
        xAxis?.isGranularityEnabled = true
        xAxis?.setCenterAxisLabels(true)
        xAxis?.setDrawGridLines(false)
        xAxis?.textSize = 9f

        xAxis?.position = XAxis.XAxisPosition.BOTTOM
        xAxis?.valueFormatter = IndexAxisValueFormatter(xAxisMonths)
        xAxis?.labelCount = 12
        xAxis?.mAxisMaximum = 12f
        xAxis?.setCenterAxisLabels(true)
        xAxis?.setAvoidFirstLastClipping(true)
        xAxis?.spaceMin = 4f
        xAxis?.spaceMax = 4f
        binding?.barChart?.axisRight?.isEnabled = false

        // Modify Y-Axis

        val leftAxis = binding?.barChart?.axisLeft
        leftAxis?.valueFormatter = LargeValueFormatter()
        leftAxis?.setDrawGridLines(false)
        leftAxis?.spaceTop = 1f
        leftAxis?.axisMinimum = 0f
    }

    private fun setIncomeBarChart() {

        val barWidth: Float
        val barSpace: Float
        val groupSpace: Float
        val groupCount = 12

        barWidth = 0.15f
        barSpace = 0.07f
        groupSpace = 0.56f

        SetXValues()
        SetYValues()

        //      Prepare Group Data

//        barDataSet1 = BarDataSet(yGroup1, "Bar1")
//        barDataSet2 = BarDataSet(yGroup2, "Bar2")
        barDataSet3 = BarDataSet(yGroup3, "Bar3")

//        barDataSet1.setColors(this.getColor(R.color.blue_bar))
//        barDataSet2.setColors(this.getColor(R.color.pink_bar))
        barDataSet3.setColors(this.getColor(R.color.yellow_bar))

//        barDataSet1.setDrawValues(true)
//        barDataSet2.setDrawValues(true)
        barDataSet3.setDrawValues(true)

//        barDataSet1.label = "Revenue"
//        barDataSet2.label = "Expense"
//        barDataSet3.label = "Profit"

//        barDataSet1.setDrawIcons(false)
//        barDataSet2.setDrawIcons(false)
        barDataSet3.setDrawIcons(false)
//        barDataSet1.setDrawValues(false)
//        barDataSet2.setDrawValues(false)
        barDataSet3.setDrawValues(false)

        var barData = BarData(barDataSet3)
        barData.setValueFormatter(LargeValueFormatter())

        binding?.barChart?.description?.isEnabled = false
        binding?.barChart?.description?.textSize = 0f

        binding?.barChart?.data = barData
        binding?.barChart?.barData?.barWidth = barWidth
        binding?.barChart?.xAxis?.axisMinimum = 0f
        binding?.barChart?.xAxis?.axisMaximum = 12f
//        binding?.barChart?.groupBars(0f, groupSpace, barSpace)
        binding?.barChart?.data?.isHighlightEnabled = false
        binding?.barChart?.invalidate()

        // Customize Bar Chart Legends using Legend Entry

        binding?.barChart?.legend?.isEnabled = false

//        var legend = binding?.barChart?.legend
//        legend?.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
//        legend?.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
//        legend?.orientation = Legend.LegendOrientation.HORIZONTAL
//        legend?.setDrawInside(false)
//
//        var legenedEntries = arrayListOf<LegendEntry>()
//        legenedEntries.add(
//            LegendEntry(
//                "Revenue",
//                Legend.LegendForm.SQUARE,
//                8f,
//                8f,
//                null,
//                this.getColor(R.color.blue_bar)
//            )
//        )
//        legenedEntries.add(
//            LegendEntry(
//                "Expense",
//                Legend.LegendForm.SQUARE,
//                8f,
//                8f,
//                null,
//                this.getColor(R.color.pink_bar)
//            )
//        )
//        legenedEntries.add(
//            LegendEntry(
//                "Profit",
//                Legend.LegendForm.SQUARE,
//                8f,
//                8f,
//                null,
//                this.getColor(R.color.yellow_bar)
//            )
//        )

//        legend?.setCustom(legenedEntries)
//        legend?.yOffset = 2f
//        legend?.xOffset = 2f
//        legend?.yEntrySpace = 0f
//        legend?.textSize = 5f

        // Populate X-Axis Data

        binding?.barChart?.animateY(2000)
        binding?.barChart?.setDrawValueAboveBar(false)
        binding?.barChart?.setPinchZoom(false)
        binding?.barChart?.setTouchEnabled(false)

        val xAxis = binding?.barChart?.xAxis
        xAxis?.granularity = 1f
        xAxis?.isGranularityEnabled = true
        xAxis?.setCenterAxisLabels(true)
        xAxis?.setDrawGridLines(false)
        xAxis?.textSize = 9f

        xAxis?.position = XAxis.XAxisPosition.BOTTOM
        xAxis?.valueFormatter = IndexAxisValueFormatter(xAxisMonths)
        xAxis?.labelCount = 12
        xAxis?.mAxisMaximum = 12f
        xAxis?.setCenterAxisLabels(true)
        xAxis?.setAvoidFirstLastClipping(true)
        xAxis?.spaceMin = 4f
        xAxis?.spaceMax = 4f
        binding?.barChart?.axisRight?.isEnabled = false

        // Modify Y-Axis

        val leftAxis = binding?.barChart?.axisLeft
        leftAxis?.valueFormatter = LargeValueFormatter()
        leftAxis?.setDrawGridLines(false)
        leftAxis?.spaceTop = 1f
        leftAxis?.axisMinimum = 0f
    }

    private fun setExpenseBarChart() {

        val barWidth: Float
        val barSpace: Float
        val groupSpace: Float
        val groupCount = 12

        barWidth = 0.15f
        barSpace = 0.07f
        groupSpace = 0.56f

        SetXValues()
        SetYValues()

        //      Prepare Group Data

//        barDataSet1 = BarDataSet(yGroup1, "Bar1")
        barDataSet2 = BarDataSet(yGroup2, "Bar2")
//        barDataSet3 = BarDataSet(yGroup3, "Bar3")

//        barDataSet1.setColors(this.getColor(R.color.blue_bar))
        barDataSet2.setColors(this.getColor(R.color.pink_bar))
//        barDataSet3.setColors(this.getColor(R.color.yellow_bar))

//        barDataSet1.setDrawValues(true)
        barDataSet2.setDrawValues(true)
//        barDataSet3.setDrawValues(true)

//        barDataSet1.label = "Revenue"
//        barDataSet2.label = "Expense"
//        barDataSet3.label = "Profit"

//        barDataSet1.setDrawIcons(false)
        barDataSet2.setDrawIcons(false)
//        barDataSet3.setDrawIcons(false)
//        barDataSet1.setDrawValues(false)
        barDataSet2.setDrawValues(false)
//        barDataSet3.setDrawValues(false)

        var barData = BarData(barDataSet2)
        barData.setValueFormatter(LargeValueFormatter())

        binding?.barChart?.description?.isEnabled = false
        binding?.barChart?.description?.textSize = 0f

        binding?.barChart?.data = barData
        binding?.barChart?.barData?.barWidth = barWidth
        binding?.barChart?.xAxis?.axisMinimum = 0f
        binding?.barChart?.xAxis?.axisMaximum = 12f
//        binding?.barChart?.groupBars(0f, groupSpace, barSpace)
        binding?.barChart?.data?.isHighlightEnabled = false
        binding?.barChart?.invalidate()

        // Customize Bar Chart Legends using Legend Entry

        binding?.barChart?.legend?.isEnabled = false

//        var legend = binding?.barChart?.legend
//        legend?.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
//        legend?.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
//        legend?.orientation = Legend.LegendOrientation.HORIZONTAL
//        legend?.setDrawInside(false)
//
//        var legenedEntries = arrayListOf<LegendEntry>()
//        legenedEntries.add(
//            LegendEntry(
//                "Revenue",
//                Legend.LegendForm.SQUARE,
//                8f,
//                8f,
//                null,
//                this.getColor(R.color.blue_bar)
//            )
//        )
//        legenedEntries.add(
//            LegendEntry(
//                "Expense",
//                Legend.LegendForm.SQUARE,
//                8f,
//                8f,
//                null,
//                this.getColor(R.color.pink_bar)
//            )
//        )
//        legenedEntries.add(
//            LegendEntry(
//                "Profit",
//                Legend.LegendForm.SQUARE,
//                8f,
//                8f,
//                null,
//                this.getColor(R.color.yellow_bar)
//            )
//        )

//        legend?.setCustom(legenedEntries)
//        legend?.yOffset = 2f
//        legend?.xOffset = 2f
//        legend?.yEntrySpace = 0f
//        legend?.textSize = 5f

        // Populate X-Axis Data

        binding?.barChart?.animateY(2000)
        binding?.barChart?.setDrawValueAboveBar(false)
        binding?.barChart?.setPinchZoom(false)
        binding?.barChart?.setTouchEnabled(false)

        val xAxis = binding?.barChart?.xAxis
        xAxis?.granularity = 1f
        xAxis?.isGranularityEnabled = true
        xAxis?.setCenterAxisLabels(true)
        xAxis?.setDrawGridLines(false)
        xAxis?.textSize = 9f

        xAxis?.position = XAxis.XAxisPosition.BOTTOM
        xAxis?.valueFormatter = IndexAxisValueFormatter(xAxisMonths)
        xAxis?.labelCount = 12
        xAxis?.mAxisMaximum = 12f
        xAxis?.setCenterAxisLabels(true)
        xAxis?.setAvoidFirstLastClipping(true)
        xAxis?.spaceMin = 4f
        xAxis?.spaceMax = 4f
        binding?.barChart?.axisRight?.isEnabled = false

        // Modify Y-Axis

        val leftAxis = binding?.barChart?.axisLeft
        leftAxis?.valueFormatter = LargeValueFormatter()
        leftAxis?.setDrawGridLines(false)
        leftAxis?.spaceTop = 1f
        leftAxis?.axisMinimum = 0f
    }

    private fun setRevenueBarChart() {

        val barWidth: Float
        val barSpace: Float
        val groupSpace: Float
        val groupCount = 12

        barWidth = 0.15f
        barSpace = 0.07f
        groupSpace = 0.56f

        SetXValues()
        SetYValues()

        //      Prepare Group Data

        barDataSet1 = BarDataSet(yGroup1, "Bar1")
//        barDataSet2 = BarDataSet(yGroup2, "Bar2")
//        barDataSet3 = BarDataSet(yGroup3, "Bar3")

        barDataSet1.setColors(this.getColor(R.color.blue_bar))


//        barDataSet2.setColors(this.getColor(R.color.pink_bar))
//        barDataSet3.setColors(this.getColor(R.color.yellow_bar))

        barDataSet1.setDrawValues(true)
//        barDataSet2.setDrawValues(true)
//        barDataSet3.setDrawValues(true)

//        barDataSet1.label = "Revenue"
//        barDataSet2.label = "Expense"
//        barDataSet3.label = "Profit"

        barDataSet1.setDrawIcons(false)
//        barDataSet2.setDrawIcons(false)
//        barDataSet3.setDrawIcons(false)
        barDataSet1.setDrawValues(false)
//        barDataSet2.setDrawValues(false)
//        barDataSet3.setDrawValues(false)

        var barData = BarData(barDataSet1)
        barData.setValueFormatter(LargeValueFormatter())

        binding?.barChart?.description?.isEnabled = false
        binding?.barChart?.description?.textSize = 0f

        binding?.barChart?.data = barData
        binding?.barChart?.barData?.barWidth = barWidth
        binding?.barChart?.xAxis?.axisMinimum = 0f
        binding?.barChart?.xAxis?.axisMaximum = 12f
//        binding?.barChart?.groupBars(0f, groupSpace, barSpace)
        binding?.barChart?.data?.isHighlightEnabled = false
        binding?.barChart?.invalidate()

        // Customize Bar Chart Legends using Legend Entry

        binding?.barChart?.legend?.isEnabled = false

//        var legend = binding?.barChart?.legend
//        legend?.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
//        legend?.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
//        legend?.orientation = Legend.LegendOrientation.HORIZONTAL
//        legend?.setDrawInside(false)
//
//        var legenedEntries = arrayListOf<LegendEntry>()
//        legenedEntries.add(
//            LegendEntry(
//                "Revenue",
//                Legend.LegendForm.SQUARE,
//                8f,
//                8f,
//                null,
//                this.getColor(R.color.blue_bar)
//            )
//        )
//        legenedEntries.add(
//            LegendEntry(
//                "Expense",
//                Legend.LegendForm.SQUARE,
//                8f,
//                8f,
//                null,
//                this.getColor(R.color.pink_bar)
//            )
//        )
//        legenedEntries.add(
//            LegendEntry(
//                "Profit",
//                Legend.LegendForm.SQUARE,
//                8f,
//                8f,
//                null,
//                this.getColor(R.color.yellow_bar)
//            )
//        )

//        legend?.setCustom(legenedEntries)
//        legend?.yOffset = 2f
//        legend?.xOffset = 2f
//        legend?.yEntrySpace = 0f
//        legend?.textSize = 5f

        // Populate X-Axis Data

        binding?.barChart?.animateY(2000)
        binding?.barChart?.setDrawValueAboveBar(false)
        binding?.barChart?.setPinchZoom(false)
        binding?.barChart?.setTouchEnabled(false)

        val xAxis = binding?.barChart?.xAxis
        xAxis?.granularity = 1f
        xAxis?.isGranularityEnabled = true
        xAxis?.setCenterAxisLabels(true)
        xAxis?.setDrawGridLines(false)
        xAxis?.textSize = 9f

        xAxis?.position = XAxis.XAxisPosition.BOTTOM
        xAxis?.valueFormatter = IndexAxisValueFormatter(xAxisMonths)
        xAxis?.labelCount = 12
        xAxis?.mAxisMaximum = 12f
        xAxis?.setCenterAxisLabels(true)
        xAxis?.setAvoidFirstLastClipping(true)
        xAxis?.spaceMin = 4f
        xAxis?.spaceMax = 4f
        binding?.barChart?.axisRight?.isEnabled = false

        // Modify Y-Axis

        val leftAxis = binding?.barChart?.axisLeft
        leftAxis?.valueFormatter = LargeValueFormatter()
        leftAxis?.setDrawGridLines(false)
        leftAxis?.spaceTop = 1f
        leftAxis?.axisMinimum = 0f
    }

    private fun SetXValues() {
        xAxisMonths.add("Jan")
        xAxisMonths.add("Feb")
        xAxisMonths.add("Mar")
        xAxisMonths.add("Apr")
        xAxisMonths.add("May")
        xAxisMonths.add("June")
        xAxisMonths.add("July")
        xAxisMonths.add("Aug")
        xAxisMonths.add("Sep")
        xAxisMonths.add("Oct")
        xAxisMonths.add("Nov")
        xAxisMonths.add("Dec")
    }

    private fun SetYValues() {

        yGroup1.add(BarEntry(1f, floatArrayOf(9.toFloat(), 3.toFloat())))
        yGroup2.add(BarEntry(1f, floatArrayOf(2.toFloat(), 7.toFloat())))
        yGroup3.add(BarEntry(1f, floatArrayOf(2.toFloat(), 7.toFloat())))

        yGroup1.add(BarEntry(2f, floatArrayOf(3.toFloat(), 3.toFloat())))
        yGroup2.add(BarEntry(2f, floatArrayOf(4.toFloat(), 15.toFloat())))
        yGroup3.add(BarEntry(2f, floatArrayOf(2.toFloat(), 7.toFloat())))

        yGroup1.add(BarEntry(3f, floatArrayOf(3.toFloat(), 3.toFloat())))
        yGroup2.add(BarEntry(3f, floatArrayOf(4.toFloat(), 15.toFloat())))
        yGroup3.add(BarEntry(3f, floatArrayOf(2.toFloat(), 7.toFloat())))

        yGroup1.add(BarEntry(4f, floatArrayOf(3.toFloat(), 3.toFloat())))
        yGroup2.add(BarEntry(4f, floatArrayOf(4.toFloat(), 15.toFloat())))
        yGroup3.add(BarEntry(4f, floatArrayOf(2.toFloat(), 7.toFloat())))

        yGroup1.add(BarEntry(5f, floatArrayOf(9.toFloat(), 3.toFloat())))
        yGroup2.add(BarEntry(5f, floatArrayOf(10.toFloat(), 6.toFloat())))
        yGroup3.add(BarEntry(5f, floatArrayOf(2.toFloat(), 7.toFloat())))

        yGroup1.add(BarEntry(6f, floatArrayOf(11.toFloat(), 1.toFloat())))
        yGroup2.add(BarEntry(6f, floatArrayOf(12.toFloat(), 2.toFloat())))
        yGroup3.add(BarEntry(6f, floatArrayOf(2.toFloat(), 7.toFloat())))

        yGroup1.add(BarEntry(7f, floatArrayOf(11.toFloat(), 9.toFloat())))
        yGroup2.add(BarEntry(7f, floatArrayOf(12.toFloat(), 8.toFloat())))
        yGroup3.add(BarEntry(7f, floatArrayOf(2.toFloat(), 7.toFloat())))

        yGroup1.add(BarEntry(8f, floatArrayOf(11.toFloat(), 13.toFloat())))
        yGroup2.add(BarEntry(8f, floatArrayOf(12.toFloat(), 12.toFloat())))
        yGroup3.add(BarEntry(8f, floatArrayOf(2.toFloat(), 7.toFloat())))

        yGroup1.add(BarEntry(9f, floatArrayOf(11.toFloat(), 9.toFloat())))
        yGroup2.add(BarEntry(9f, floatArrayOf(12.toFloat(), 8.toFloat())))
        yGroup3.add(BarEntry(9f, floatArrayOf(2.toFloat(), 7.toFloat())))
//
        yGroup1.add(BarEntry(10f, floatArrayOf(11.toFloat(), 2.toFloat())))
        yGroup2.add(BarEntry(10f, floatArrayOf(12.toFloat(), 7.toFloat())))
        yGroup3.add(BarEntry(10f, floatArrayOf(2.toFloat(), 7.toFloat())))
//
        yGroup1.add(BarEntry(11f, floatArrayOf(11.toFloat(), 6.toFloat())))
        yGroup2.add(BarEntry(11f, floatArrayOf(12.toFloat(), 5.toFloat())))
        yGroup3.add(BarEntry(11f, floatArrayOf(2.toFloat(), 7.toFloat())))
//
        yGroup1.add(BarEntry(12f, floatArrayOf(11.toFloat(), 2.toFloat())))
        yGroup2.add(BarEntry(12f, floatArrayOf(9.toFloat(), 3.toFloat())))
        yGroup3.add(BarEntry(12f, floatArrayOf(2.toFloat(), 7.toFloat())))

    }


    private fun checkReminderState() {
        when (reminderState) {
            "byProperty" -> {
                binding?.tvByProperty?.setTextColor(getColor(R.color.white))
                binding?.tvByType?.setTextColor(getColor(R.color.reminder))
                binding?.tvByStatus?.setTextColor(getColor(R.color.reminder))

                binding?.cvProperty?.setCardBackgroundColor(getColor(R.color.darkBlue))
                binding?.cvType?.setCardBackgroundColor(getColor(R.color.white))
                binding?.cvStatus?.setCardBackgroundColor(getColor(R.color.white))

            }
            "byType" -> {
                binding?.tvByType?.setTextColor(getColor(R.color.white))
                binding?.tvByStatus?.setTextColor(getColor(R.color.reminder))
                binding?.tvByProperty?.setTextColor(getColor(R.color.reminder))

                binding?.cvType?.setCardBackgroundColor(getColor(R.color.darkBlue))
                binding?.cvProperty?.setCardBackgroundColor(getColor(R.color.white))
                binding?.cvStatus?.setCardBackgroundColor(getColor(R.color.white))
            }
            "byStatus" -> {
                binding?.tvByStatus?.setTextColor(getColor(R.color.white))
                binding?.tvByProperty?.setTextColor(getColor(R.color.reminder))
                binding?.tvByType?.setTextColor(getColor(R.color.reminder))

                binding?.cvStatus?.setCardBackgroundColor(getColor(R.color.darkBlue))
                binding?.cvProperty?.setCardBackgroundColor(getColor(R.color.white))
                binding?.cvType?.setCardBackgroundColor(getColor(R.color.white))
            }
        }
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