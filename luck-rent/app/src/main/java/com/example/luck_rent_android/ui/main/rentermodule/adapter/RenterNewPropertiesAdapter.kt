package com.example.luck_rent_android.ui.main.rentermodule.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.RenterPropertesItemBinding
import com.example.luck_rent_android.ui.main.adds.adapter.MyAdsAdapter
import com.example.luck_rent_android.ui.main.contractormodule.ContractorRentProperty
import com.example.luck_rent_android.ui.main.rentermodule.model.RenterNewPropertiesModel
import com.kodextech.project.kodexlib.base.BaseActivity

class RenterNewPropertiesAdapter(
    var context: BaseActivity,
    var list: ArrayList<RenterNewPropertiesModel>
) : RecyclerView.Adapter<RenterNewPropertiesAdapter.RenterNewPropertiesAdapterVH>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RenterNewPropertiesAdapterVH {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.renter_propertes_item, parent, false)
        return RenterNewPropertiesAdapterVH(view)


    }

    override fun onBindViewHolder(holder: RenterNewPropertiesAdapterVH, position: Int) {

        val getData = list.get(position)
        if (getData.isForAds.equals("true")){
            holder.binding?.recycler?.visibility = View.VISIBLE
            holder.binding?.cvFirst?.visibility = View.GONE
        }else{
            holder.binding?.recycler?.visibility = View.GONE
            holder.binding?.cvFirst?.visibility = View.VISIBLE
        }
        holder.binding?.tvLandLord?.text = getData.landlordName

        holder?.binding?.btnView?.setOnClickListener {
            val intent = Intent(context, ContractorRentProperty::class.java)
            intent.putExtra("isFrom","RentProperty")
            context.startActivity(intent)
        }

        var mAdapter: RenterAddsAdapter? = null
        var layoutManager: RecyclerView.LayoutManager? = null
        var adapter: RecyclerView.Adapter<MyAdsAdapter.ViewHolder>? = null

        mAdapter = RenterAddsAdapter(context)
        holder.binding?.recycler?.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
//        val snaper = PagerSnapHelper()
//        snaper.attachToRecyclerView(holder.binding?.recycler)
       val snap = PagerSnapHelper()
        snap.attachToRecyclerView(holder.binding?.recycler)

        holder.binding?.recycler?.adapter = mAdapter
        mAdapter?.notifyDataSetChanged()

    }

    override fun getItemCount(): Int {

        return list.size

    }

    inner class RenterNewPropertiesAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var binding: RenterPropertesItemBinding? = DataBindingUtil.bind(itemView)

    }
}