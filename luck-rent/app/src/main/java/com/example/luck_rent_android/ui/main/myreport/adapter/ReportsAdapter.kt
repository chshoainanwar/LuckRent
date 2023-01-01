package com.example.luck_rent_android.ui.main.myreport.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ReportsItemBinding
import com.example.luck_rent_android.ui.main.myreport.ReportsProfit
import com.example.luck_rent_android.ui.main.myreport.model.ReportsModel
import com.kodextech.project.kodexlib.base.BaseActivity

class ReportsAdapter(
    var context: BaseActivity,
    var list: ArrayList<ReportsModel>
) : RecyclerView.Adapter<ReportsAdapterVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportsAdapterVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reports_item, parent, false)
        return ReportsAdapterVH(view)
    }

    override fun onBindViewHolder(holder: ReportsAdapterVH, position: Int) {

        var getData = list.get(position)
        holder.binding?.tvText?.text = getData.text
        getData.image?.let { holder.binding?.ivImage?.setImageResource(it) }

        holder.binding?.itemLayout?.setOnClickListener{
            val intent = Intent(context,ReportsProfit::class.java)
            intent.putExtra("pos" , position)
            context.startActivity(intent)
        }

        if (position == 0){
            holder.binding?.itemLayout?.setBackgroundColor(context.getColor(R.color.rent_balance))

        }else if (position == 1){
            holder.binding?.itemLayout?.setBackgroundColor(context.getColor(R.color.invoice))
        }else if (position == 2){
            holder.binding?.itemLayout?.setBackgroundColor(context.getColor(R.color.tax_preparation))
        }
        else if (position == 3){
            holder.binding?.itemLayout?.setBackgroundColor(context.getColor(R.color.orange))
        }else if (position == 4){
            holder.binding?.itemLayout?.setBackgroundColor(context.getColor(R.color.rent_rolls))
        }else if (position == 5){
            holder.binding?.itemLayout?.setBackgroundColor(context.getColor(R.color.cashflow_reports))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

class ReportsAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding: ReportsItemBinding? = DataBindingUtil.bind(itemView)
}