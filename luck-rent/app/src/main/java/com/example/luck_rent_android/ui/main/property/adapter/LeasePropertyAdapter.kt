package com.example.luck_rent_android.ui.main.property.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.LeaseItemBinding
import com.kodextech.project.kodexlib.base.BaseActivity

class LeasePropertyAdapter(
    var mContext: BaseActivity,
    var mData: ArrayList<LeasePropertyDummy>,
    var callBack: ((child: LeasePropertyDummy)->Unit)
) : RecyclerView.Adapter<LeasePropertyAdapterVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeasePropertyAdapterVH {
        val view = LayoutInflater.from(mContext).inflate(R.layout.lease_item, parent, false)
        return LeasePropertyAdapterVH(view)
    }

    override fun onBindViewHolder(holder: LeasePropertyAdapterVH, position: Int) {
        val item = mData[position]

        holder.binding?.tvPropertyId?.text = item.id
        holder.binding?.tvPropertyAddress?.text = item.address

        holder.itemView?.setOnClickListener{
            callBack(item)

        }

    }

    override fun getItemCount(): Int {
        return mData.size
    }
}

class LeasePropertyAdapterVH(view: View) : RecyclerView.ViewHolder(view) {
    val binding: LeaseItemBinding? = DataBindingUtil.bind(view)
}

data class LeasePropertyDummy(
    var id: String? = null,
    var address: String? = null,
)
