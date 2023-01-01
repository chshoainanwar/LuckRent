package com.example.luck_rent_android.ui.main.payment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.RotateAnimation
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.PaymentDetailItemBinding
import com.example.luck_rent_android.model.PaymentModel
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import com.kodextech.project.kodexlib.utils.visible
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PaymentListingAdapter(
    var mContext: BaseActivity,
    var mData: ArrayList<PaymentModel>
) : RecyclerView.Adapter<PaymentListingAdapterVH>() {

    var animState: Boolean = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentListingAdapterVH {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.payment_detail_item, parent, false)
        return PaymentListingAdapterVH(view)
    }


    override fun onBindViewHolder(holder: PaymentListingAdapterVH, position: Int) {
        val mItem = mData[position]

        holder.binding?.tvIncomeType?.text = mItem.incomeMethod
        holder.binding?.tvPayor?.text = mItem.payor
        holder.binding?.tvAmount?.text = mItem.amount
        holder.binding?.tvPaidWith?.text = mItem.incomeMethod
        holder.binding?.tvDateReceived?.text = mItem.dateReceived
        holder.binding?.tvDateFrom?.text = mItem.dateFrom
        holder.binding?.tvNotes?.text = mItem.notes

        if (mItem.taxStatus == "0") {
            holder.binding?.tvTaxStatus?.text = "Non Taxable"
        } else {
            holder.binding?.tvTaxStatus?.text = "Taxable"
        }

        val sdf = SimpleDateFormat("dd MMM yyyy")
        val currentDate = sdf.format(Date())
        holder.binding?.tvCurrentDate?.text = currentDate

        holder.binding?.ivOpen?.setOnClickListener {
            if(animState){
                animState = false
                holder.binding?.rlCollapsedView?.gone()
            }else{
                animState = true
                holder.binding?.rlCollapsedView?.visible()
            }
            checkAnimState(holder.binding?.ivOpen!!, animState)
        }


    }

    override fun getItemCount(): Int {
        return mData.size
    }
}

class PaymentListingAdapterVH(view: View) : RecyclerView.ViewHolder(view) {
    var binding: PaymentDetailItemBinding? = DataBindingUtil.bind(view)
}

fun checkAnimState(image: ImageView, animState: Boolean) {
    var animation: RotateAnimation? = null
    if (animState) {
        animation = RotateAnimation(

            0f,
            180f,
            RotateAnimation.RELATIVE_TO_SELF,
            0.5f,
            RotateAnimation.RELATIVE_TO_SELF,
            0.5f
        )
    } else {
        animation = RotateAnimation(

            180f,
            0f,
            RotateAnimation.RELATIVE_TO_SELF,
            0.5f,
            RotateAnimation.RELATIVE_TO_SELF,
            0.5f
        )
    }
    animation.duration = 200
    animation.fillAfter = true
    image.startAnimation(animation)
}