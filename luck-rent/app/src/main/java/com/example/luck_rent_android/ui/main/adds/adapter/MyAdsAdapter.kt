package com.example.luck_rent_android.ui.main.adds.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.AddLayoutBinding
import com.example.luck_rent_android.databinding.AddsLayoutBinding
import com.example.luck_rent_android.ui.main.adds.Details
import com.example.luck_rent_android.ui.main.adds.Request
import com.example.luck_rent_android.ui.main.adds.model.MyAdsModel
import com.example.luck_rent_android.ui.main.contractormodule.ContractorRentProperty
import com.example.luck_rent_android.ui.main.profile.Profile.Companion.roleProfile
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import com.kodextech.project.kodexlib.utils.visible

class MyAdsAdapter(
    var context: BaseActivity,
    var list: ArrayList<MyAdsModel>
) : RecyclerView.Adapter<MyAdsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdsAdapter.ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.adds_layout, parent, false)
            return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyAdsAdapter.ViewHolder, position: Int) {
        var mList = list[position]
        mList.images?.let { holder.binding?.ivRecycle?.setImageResource(it) }

        if (mList.boolean == true){
            holder.binding?.tvHide?.visible()
        }else if (mList.boolean == false){
            holder.binding?.tvHide?.gone()
        }

        holder.binding?.addImg?.setOnClickListener {
            if (roleProfile == "Renter") {
                val intent = Intent(context, Details::class.java)
                intent.putExtra("isFrom", "RenterAds")
                context.startActivity(intent)
            } else {
                val intent = Intent(context, Request::class.java)
                context?.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: AddsLayoutBinding? = DataBindingUtil.bind(itemView)
    }
}