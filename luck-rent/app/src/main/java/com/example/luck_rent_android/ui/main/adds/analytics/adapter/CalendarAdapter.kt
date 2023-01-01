package com.example.luck_rent_android.ui.main.adds.analytics.adapter

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.CustomCalenderLayoutBinding
import com.example.luck_rent_android.ui.main.adds.analytics.AnalyticsScreen.Companion.isSelected
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.getScreenWidth
import java.util.*

class CalenderAdapter(
    val context: BaseActivity,
    val mData: ArrayList<CalenderModel>,
    val callBack: ((position: Int) -> Unit),
    val callbackItem: ((item: CalenderModel) -> Unit)
) : RecyclerView.Adapter<CalenderViewHolder>() {
    val cal: Calendar = Calendar.getInstance()
    val currentTime = cal.time

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalenderViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.custom_calender_layout,
            parent,
            false
        )
        view.layoutParams.width = (getScreenWidth() / (8.1F)).toInt()
        return CalenderViewHolder(view)
    }


    override fun onBindViewHolder(holder: CalenderViewHolder, position: Int) {
        val mItem = mData[position]
//        if (position > 4) {
//            callbackItem.invoke(mData[position - 4])
//        } else {
//            callbackItem.invoke(mData[position])
//        }

        holder.binding?.tvDayName?.setOnClickListener {
//            isSelected = position
//            if (mItem.isSelected == true) {
//                mItem.isSelected = false
//            } else {
//                mItem.isSelected = true
//            }
//            callBack(position)
//            callbackItem.invoke(mData[position])
//            notifyDataSetChanged()
        }

        if (mItem.isSelected) {
            holder.binding?.tvDayName?.setTextColor(context.getColor(R.color.darkBlue))
            holder.binding?.tvDayName?.background?.setTint(context.getColor(R.color.white))
            holder.binding?.tvDate?.setTextColor(context.getColor(R.color.white))
        } else {
            holder.binding?.tvDate?.setTextColor(context.getColor(R.color.white))
            holder.binding?.tvDayName?.setTextColor(context.getColor(R.color.white))
            holder.binding?.tvDayName?.background?.setTint(context.getColor(R.color.white_76))
        }


        val dayOfTheWeek = DateFormat.format("EEE", mItem.date) as String // Thursday
        val day = DateFormat.format("dd", mItem.date) as String // 20


//        holder.binding?.ivGreenDot?.visible()

        holder.binding?.tvDayName?.text = day
        holder.binding?.tvDate?.text = dayOfTheWeek


    }

    override fun getItemCount(): Int = mData.size

}

class CalenderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding: CustomCalenderLayoutBinding? = DataBindingUtil.bind(itemView)
}

class CalenderModel(var date: Date, var isBooked: Boolean, var isSelected: Boolean)