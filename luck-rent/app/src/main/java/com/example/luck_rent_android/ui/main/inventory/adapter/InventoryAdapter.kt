package com.example.luck_rent_android.ui.main.inventory.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.InventoryMainItemsBinding
import com.example.luck_rent_android.ui.main.inventory.model.InventoryModel
import com.example.luck_rent_android.ui.main.order.adapter.OrderAdapterVH
import com.example.luck_rent_android.ui.main.order.model.OrderModel
import com.kodextech.project.kodexlib.base.BaseActivity

class InventoryAdapter(
    var context: BaseActivity,
    var list: ArrayList<InventoryModel>
) : RecyclerView.Adapter<InventoryAdapter.InventoryAdapterVH>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryAdapterVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.inventory_main_items,parent,false)
        return InventoryAdapterVH(view)
    }

    override fun onBindViewHolder(holder: InventoryAdapterVH, position: Int) {
        val getData = list[position]
        holder.binding?.tvCategory?.text = getData.category
        holder.binding?.tvBrand?.text = getData.brand
        holder.binding?.tvModel?.text = getData.model
        holder.binding?.tvLastMaintenance?.text = getData.lastMaintenance
        holder.binding?.tvWarrantyUntil?.text = getData.warrantyUntil
        holder.binding?.tvNextMaintenance?.text = getData.nextMaintenance
        holder.binding?.tvNotes?.text = getData.notes
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class InventoryAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView){
        val  binding : InventoryMainItemsBinding? = DataBindingUtil.bind(itemView)
    }
}