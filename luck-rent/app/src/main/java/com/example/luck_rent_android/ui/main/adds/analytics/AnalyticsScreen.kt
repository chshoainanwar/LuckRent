package com.example.luck_rent_android.ui.main.adds.analytics

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityAnalyticsScreenBinding
import com.example.luck_rent_android.ui.main.adds.analytics.adapter.CalenderAdapter
import com.example.luck_rent_android.ui.main.adds.analytics.adapter.CalenderModel
import com.example.luck_rent_android.utils.stringToDate
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import com.kodextech.project.kodexlib.base.BaseActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AnalyticsScreen : BaseActivity() {

    private var binding: ActivityAnalyticsScreenBinding? = null
    private var mAdapter: CalenderAdapter? = null
    private var mCalenderDates = ArrayList<CalenderModel>()
    private var monthArray: ArrayList<String> = ArrayList()
    var values = ArrayList<Entry>()


    companion object {
        var isSelected = -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        statusBarColor(getColor(R.color.darkBlue))
        binding = DataBindingUtil.setContentView(this, R.layout.activity_analytics_screen)
        super.onCreate(savedInstanceState)

        initTopBar()
        getDatesArray()
        setupCalenderRecycler()
        setRevenueBarChart()
        setProfitLineChart()
        setTotalLineChart()

        binding?.topBar?.ivBack?.setOnClickListener { onBackPressed() }

    }

    private fun setTotalLineChart() {
//        val values = java.util.ArrayList<Entry>()
        var values = ArrayList<Entry>()

        values.add(Entry(10f, 100f))
        values.add(Entry(20f, 300f))
        values.add(Entry(30f, 200f))
        values.add(Entry(40f, 600f))
        values.add(Entry(50f, 500f))


        val lineDataSet = LineDataSet(values as List<Entry>?, "Cells")
        lineDataSet.color = this.getColor(R.color.white)
        lineDataSet.setDrawValues(true)
        lineDataSet.calcMinMax()
        lineDataSet.highLightColor = this.getColor(R.color.white)
        lineDataSet.setDrawCircles(true)
        lineDataSet.setDrawCircles(true)
        lineDataSet.setCircleColorHole(this.getColor(R.color.red))
        lineDataSet.setCircleColor(this.getColor(R.color.white))


        lineDataSet.cubicIntensity = 0.2f
        lineDataSet.setDrawFilled(true)

        lineDataSet.lineWidth = 1.8f
        lineDataSet.circleRadius = 4f
        lineDataSet.highLightColor = getColor(R.color.white)
        lineDataSet.color = getColor(R.color.white)
        lineDataSet.fillColor = getColor(R.color.darkBlue)
        lineDataSet.fillAlpha = 100

        val barData = LineData(lineDataSet)
        barData.setValueTextColor(R.color.lightPurple)
        binding?.totalActivity?.data = barData


        binding?.totalActivity?.animateY(2000)
        binding?.totalActivity?.setTouchEnabled(false)
//        binding?.totalActivity?.markerView?.setPadding(20, 0, 0, 0)
        binding?.totalActivity?.setPinchZoom(false)
        val xAxis = binding?.totalActivity?.xAxis
        xAxis?.position = XAxis.XAxisPosition.BOTTOM
        xAxis?.textColor = getColor(R.color.white)
        xAxis?.setDrawGridLines(true)



        binding?.totalActivity?.legend?.isEnabled = false
        binding?.totalActivity?.axisLeft?.setDrawGridLines(true)
        binding?.totalActivity?.xAxis?.setDrawGridLines(true)
        binding?.totalActivity?.xAxis?.setDrawAxisLine(true)



        binding?.totalActivity?.axisRight?.isEnabled = true
        binding?.totalActivity?.legend?.isEnabled = false
        binding?.totalActivity?.description?.isEnabled = false
        binding?.totalActivity?.invalidate()

        binding?.totalActivity?.axisLeft?.isEnabled = true
        binding?.totalActivity?.axisRight?.isEnabled = false

        binding?.totalActivity?.axisLeft?.textColor = getColor(R.color.white);
        binding?.totalActivity?.axisLeft?.axisLineColor = getColor(R.color.white);


//        binding?.totalActivity?.setViewPortOffsets(0f, 0f, 0f, 0f)
//        binding?.totalActivity?.animateXY(2000, 2000)
//
//
//        binding?.totalActivity?.data = barData
//        binding?.totalActivity?.labelFor = 0
//        binding?.totalActivity?.setDescription("")
//        binding?.totalActivity?.setTouchEnabled(true)
//        binding?.totalActivity?.isDragEnabled = true
//        binding?.totalActivity?.axisLeft?.setDrawGridLines(true)
//        binding?.totalActivity?.xAxis?.setDrawGridLines(true)
//        binding?.totalActivity?.setScaleEnabled(true)
//        binding?.totalActivity?.setPinchZoom(true)
//
//        val xAxis = binding?.revenueBarChart?.xAxis
//        xAxis?.position = XAxis.XAxisPosition.BOTTOM
//        xAxis?.setDrawGridLines(true)
//
//        binding?.totalActivity?.legend?.isEnabled = true
//        binding?.totalActivity?.xAxis?.setDrawAxisLine(true)
//
//        binding?.totalActivity?.setDescription("")
//        binding?.totalActivity?.invalidate()
//
//        binding?.totalActivity?.axisLeft?.isEnabled = true
//        binding?.totalActivity?.axisRight?.isEnabled = true
//
//
//

//
//        binding?.totalActivity?.animateXY(2000, 2000)
//        binding?.totalActivity?.invalidate()


    }

    private fun setProfitLineChart() {

        values.add(Entry(10f, 100f))
        values.add(Entry(20f, 300f))
        values.add(Entry(30f, 200f))
        values.add(Entry(40f, 600f))
        values.add(Entry(50f, 500f))


        var lineDataSet = LineDataSet(values as List<Entry>?, "Cells")
        lineDataSet.setColors(this.getColor(R.color.orange))
        lineDataSet.setDrawValues(true)
//        lineDataSet.calcMinMax(1, 1000)
        lineDataSet.calcMinMax()
        lineDataSet.highLightColor = this.getColor(R.color.orange)
        lineDataSet.setDrawCircles(true)
        lineDataSet.setDrawFilled(true)
        lineDataSet.setCircleColorHole(this.getColor(R.color.orange))
        lineDataSet.setCircleColor(this.getColor(R.color.orange))


        lineDataSet.cubicIntensity = 0f

        lineDataSet.lineWidth = 1.5f
        lineDataSet.circleRadius = 4f
        lineDataSet.setCircleColor(getColor(R.color.light_orange1))
        lineDataSet.highLightColor = getColor(R.color.light_orange1)
        lineDataSet.color = getColor(R.color.light_orange)
        lineDataSet.fillColor = getColor(R.color.light_orange)
        lineDataSet.fillAlpha = 100

        val barData = LineData(lineDataSet)
        barData.setValueTextColor(android.R.color.transparent)
        binding?.profitChart?.data = barData
        binding?.profitChart?.setViewPortOffsets(0f, 0f, 0f, 0f)
//        binding?.profitChart?.setBackgroundColor(Color.rgb(104, 241, 175))
//        binding?.profitChart?.setTouchEnabled(true)
//        binding?.profitChart?.isDragEnabled = true
//        binding?.profitChart?.setScaleEnabled(true)
//        binding?.profitChart?.setPinchZoom(false)
        binding?.profitChart?.animateXY(2000, 2000)


        binding?.profitChart?.labelFor = 0
        binding?.profitChart?.description?.isEnabled = false
        binding?.profitChart?.setTouchEnabled(true)
        binding?.profitChart?.isDragEnabled = true
        binding?.profitChart?.axisLeft?.setDrawGridLines(false)
        binding?.profitChart?.xAxis?.setDrawGridLines(false)
        binding?.profitChart?.setScaleEnabled(true)
        binding?.profitChart?.setPinchZoom(false)
        binding?.profitChart?.setTouchEnabled(false)

        binding?.profitChart?.setDrawGridBackground(false)
        val x: XAxis? = binding?.profitChart?.xAxis
        x?.isEnabled = false
        x?.textSize = 0f

        val y: YAxis? = binding?.profitChart?.axisLeft
        y?.isEnabled = false
        y?.textSize = 0f


        binding?.profitChart?.axisRight?.isEnabled = false
        binding?.profitChart?.axisLeft?.isEnabled = false
        binding?.profitChart?.legend?.isEnabled = false

        binding?.profitChart?.animateXY(2000, 2000)
        binding?.profitChart?.invalidate()

    }


    private fun setRevenueBarChart() {

        val barWidth: Float
        barWidth = 0.35f

        monthArray.add("JAN")
        monthArray.add("FEB")
        monthArray.add("MAR")
        monthArray.add("APR")
        monthArray.add("MAY")
        monthArray.add("JUN")
        monthArray.add("JUL")
        monthArray.add("AUG")
        monthArray.add("SEP")
        monthArray.add("OCT")
        monthArray.add("NOV")
        monthArray.add("DEC")


        val entries: ArrayList<BarEntry> = ArrayList()
        entries.add(BarEntry(1f, floatArrayOf(9.toFloat(), 3.toFloat())))
        entries.add(BarEntry(2f, floatArrayOf(3.toFloat(), 3.toFloat())))
        entries.add(BarEntry(3f, floatArrayOf(3.toFloat(), 3.toFloat())))
        entries.add(BarEntry(4f, floatArrayOf(9.toFloat(), 3.toFloat())))


        val barDataSet = BarDataSet(entries, "Cell")
//        val mutableListA = mutableListOf<Int>(Color.RED ,Color.GRAY);
//
//        val colors =
//            mutableListOf(Color.GREEN, Color.YELLOW)
//        barDataSet.setColors( ColorTemplate.COLORFUL_COLORS)

        barDataSet.color = getColor(R.color.darkBlue)

        val data = BarData(barDataSet)
        binding?.revenueBarChart?.data = data
        binding?.revenueBarChart?.data?.barWidth = barWidth

        binding?.revenueBarChart?.animateY(2000)
        binding?.revenueBarChart?.setDrawValueAboveBar(false)
        binding?.revenueBarChart?.setPinchZoom(false)
        binding?.revenueBarChart?.setTouchEnabled(false)
        val xAxis = binding?.revenueBarChart?.xAxis
        xAxis?.position = XAxis.XAxisPosition.BOTTOM
        xAxis?.setDrawGridLines(false)

        binding?.revenueBarChart?.legend?.isEnabled = false
        binding?.revenueBarChart?.axisLeft?.setDrawGridLines(false)
        binding?.revenueBarChart?.xAxis?.setDrawGridLines(false)
        binding?.revenueBarChart?.xAxis?.setDrawAxisLine(false)

        binding?.revenueBarChart?.axisRight?.isEnabled = false
        binding?.revenueBarChart?.legend?.isEnabled = false
        binding?.revenueBarChart?.description?.isEnabled = false
        binding?.revenueBarChart?.invalidate()

        binding?.revenueBarChart?.axisLeft?.isEnabled = false
        binding?.revenueBarChart?.axisRight?.isEnabled = false


    }


    private fun initTopBar() {

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


    private fun setupCalenderRecycler() {
        mAdapter = CalenderAdapter(this, mCalenderDates, { position ->
//            dateAppoint = SimpleDateFormat("yyyy-MM-dd").format(mCalenderDates[position].date)
//            getDateAppointment(DataManager.sharedInstance.user?.uuid.toString(), dateAppoint)
        }) { item ->
            binding?.topBar?.tvMonth?.text =
                item.date.toString().stringToDate("EEEE dd, MMM", "EEE MMM dd HH:mm:ss zzz yyyy")
        }

        binding?.topBar?.rvCalendar?.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.HORIZONTAL,
            false
        )

        binding?.topBar?.rvCalendar?.adapter = mAdapter
    }

    private fun getDatesArray() {

        val cal: Calendar = Calendar.getInstance()
        val currentTime = cal.time

        for (i in 0..500) {
            cal.time = currentTime
            cal.add(Calendar.DAY_OF_MONTH, i)
            val objDate: Date = cal.time

            mCalenderDates.add(CalenderModel(objDate, true, isSelected = false))

        }
        mAdapter?.notifyDataSetChanged()

        cal.add(Calendar.DAY_OF_MONTH, 1) //Adds a day

        cal.add(Calendar.DAY_OF_MONTH, -1) //Goes to previous day

        cal.time


    }
}