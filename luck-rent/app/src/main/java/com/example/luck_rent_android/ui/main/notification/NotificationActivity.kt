package com.example.luck_rent_android.ui.main.notification

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.BaseBundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.core.view.marginStart
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityNotificationBinding
import com.example.luck_rent_android.model.PaymentModel
import com.example.luck_rent_android.swiper.MyButton
import com.example.luck_rent_android.swiper.MySwipeHelper
import com.example.luck_rent_android.ui.main.auth.Login
import com.example.luck_rent_android.ui.main.contractormodule.MyButtonClickListener
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.notification.adapter.NotificationAdapter
import com.example.luck_rent_android.ui.main.notification.model.NotificationModel
import com.example.luck_rent_android.utils.getDesiredOutputDateString
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class NotificationActivity : BaseActivity() {

    private var binding: ActivityNotificationBinding? = null
    private var isFor: String? = null
    var timeTonotify: String? = null
    var date: String? = null
    var time: String? = null
    var selectedDay: Int? = null
    var selectedMonth: Int? = null
    var selectedYear: Int? = null
    private val TAG = "myTAG"

    private var mAdapter: NotificationAdapter? = null
    private var list = ArrayList<NotificationModel>()

    private var state: String? = "property"

    override fun onCreate(savedInstanceState: Bundle?) {
        statusBarColor(getColor(R.color.darkBlue))
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification)
        super.onCreate(savedInstanceState)

        initTopBar()
        setAdapter()
        setDummyData()
        swipeHelper()


        isFor = intent.getStringExtra("type")
        if (isFor == "TYPE"){
            state = "type"
            checkColorState(state ?: "")
        }else if (isFor == "STATUS"){
            state = "status"
            checkColorState(state ?: "")

        }

        binding?.cvByProperty?.setOnClickListener {
            state = "priority"
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
        binding?.topBar?.ivAdd?.setOnClickListener{
            onBackPressed()
        }



    }

    private fun setAdapter(){
        mAdapter = NotificationAdapter(this,list)
        binding?.recycler?.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        binding?.recycler?.adapter = mAdapter
        mAdapter?.notifyDataSetChanged()
    }

    private fun swipeHelper(){
        val swipeHelper = object : MySwipeHelper(
            this, binding?.recycler!!, TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 95f, resources.displayMetrics
            ).roundToInt()
        ) {
            override fun instantiateMyButton(
                viewHolder: RecyclerView.ViewHolder,
                buffer: MutableList<MyButton>
            ) {
                buffer.add(MyButton(this@NotificationActivity, R.drawable.setrimner,
                    Color.parseColor("#0000ffff"),
                    object :
                        MyButtonClickListener {
                        override fun onClick(pos: Int) {

                            try {
                                openDialog()
                                mAdapter?.notifyDataSetChanged()


                            } catch (e: Exception) {
                            }


                        }



                    }

                ))

            }


        }
    }

    private fun setDummyData(){
        list.add(
            NotificationModel(
            "Late Rent",
            "$2000 I 3 Days Late I Mike Wells"
        )
        )
        list.add(
            NotificationModel(
            "Next Rent Notice",
                "$1600 I 5 Days Late I James Bullock"
        ))
        list.add(
            NotificationModel(
            "Message from Ken Moore",
            "Hi Landlord, I will be on vacation"
        ))
        list.add(
            NotificationModel(
            "Late Maintenance",
            "LG Washer I 123 Rock Road I 2 Days Late"
        )
        )
        list.add(
            NotificationModel(
            "Upcoming Maintenance",
            "Air Conditioner I 123 Rock Road"
        )
        )
        list.add(
            NotificationModel(
            "Credit Report Ready",
            "123 Rock Road I Simon Hall"
        )
        )
        list.add(
            NotificationModel(
            "Rent Payment Received",
            "$1800 I 987 River Street I Alex Wong"
        )
        )
    }

    private fun checkColorState(state: String) {
        when (state) {
            "priority" -> {
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

    private fun initTopBar() {
        binding?.topBar?.llLogout?.gone()
        binding?.topBar?.rlSearch?.gone()
        binding?.topBar?.rlCounter?.gone()
        binding?.topBar?.ivAdd?.setImageResource(R.drawable.ic_back)

//        binding?.topBar?.tvText?.gravity = Gravity.CENTER_VERTICAL

        binding?.topBar?.tvText?.text = "Tasks & Notifications"
//        binding?.topBar?.tvText?.textAlignment = View.TEXT_ALIGNMENT_TEXT_START


//        binding?.topBar?.tvText?.gravity = Gravity.CENTER_VERTICAL
        (binding?.topBar?.tvText?.layoutParams as ConstraintLayout.LayoutParams).apply {
            marginEnd = 100
            topMargin = 10
        }
//        binding?.topBar?.tvText?.textSize = 16F
    }

    private fun openDialog() {

        val view = View.inflate(this, R.layout.dialog_set_reminder, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()


        //Register Done Button & Cancel Icon from Dialog
        var doneBtn = view?.findViewById<AppCompatButton>(R.id.doneBtn)
        var tvPickDate = view?.findViewById<AppCompatTextView>(R.id.tvPickDate)
        var tvPickTime = view?.findViewById<AppCompatTextView>(R.id.tvPickTime)
        var cancelIcon = view?.findViewById<AppCompatImageView>(R.id.cancelIcon)

        //Dialog Cancel Icon
        cancelIcon?.setOnClickListener {
            dialog.dismiss()
        }

        //Dialog Done Button
        doneBtn?.setOnClickListener {
//            val intent = Intent(this, Login::class.java)
//            startActivity(intent)
//            finish()

        }

        //Dialog Pick Date
        tvPickDate?.setOnClickListener{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, day ->

                    var monthi = month.plus(1)
                    val formatedDate = "$day-$monthi-$year"
                    tvPickDate?.text = formatedDate.getDesiredOutputDateString(
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
        tvPickTime?.setOnClickListener{

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
                        tvPickTime.text = FormatTime(i, i1)
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
            if (tvPickDate?.text.toString() == "Pick Date") {
                Toast.makeText(this, "Please Select Date First", Toast.LENGTH_SHORT).show()
            } else {
                timePickerDialog.show()
            }

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
        mViewGroup = binding?.contentNotification
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
}