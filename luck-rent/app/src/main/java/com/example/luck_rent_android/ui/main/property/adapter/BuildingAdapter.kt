package com.example.luck_rent_android.ui.main.property.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.BuildingItemsBinding
import com.example.luck_rent_android.databinding.ReportsItemBinding
import com.example.luck_rent_android.ui.main.auth.Login
import com.example.luck_rent_android.ui.main.order.adapter.OrderAdapterVH
import com.example.luck_rent_android.ui.main.order.model.OrderModel
import com.example.luck_rent_android.ui.main.property.model.BuildingModel
import com.kodextech.project.kodexlib.base.BaseActivity
import javax.security.auth.callback.Callback

class BuildingAdapter(
    var context: BaseActivity,
    var list: ArrayList<BuildingModel>,
    var callback: ((BuildingModel,Int)->Unit)
): RecyclerView.Adapter<BuildingAdapterVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildingAdapterVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.building_items, parent, false)
        return BuildingAdapterVH(view)
    }

    override fun onBindViewHolder(holder: BuildingAdapterVH, position: Int) {
        var getData = list.get(position)
        holder.binding?.tvUnitAmenity?.text = getData.name

        holder.itemView.setOnLongClickListener {

            callback.invoke(getData,position)

        return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
       return list.size
    }
}
class BuildingAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding: BuildingItemsBinding? = DataBindingUtil.bind(itemView)
}