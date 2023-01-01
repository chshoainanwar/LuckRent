package com.example.luck_rent_android.ui.main.order.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ReportsItemBinding
import com.example.luck_rent_android.ui.main.contractormodule.ContractorReport
import com.example.luck_rent_android.ui.main.inspectionreport.InspectionReport
import com.example.luck_rent_android.ui.main.order.*
import com.example.luck_rent_android.ui.main.order.model.OrderModel
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.rentermodule.RenterReport
import com.kodextech.project.kodexlib.base.BaseActivity

class OrderAdapter(
    var context: BaseActivity,
    var list: ArrayList<OrderModel>
) : RecyclerView.Adapter<OrderAdapterVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderAdapterVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reports_item, parent, false)
        return OrderAdapterVH(view)
    }

    override fun onBindViewHolder(holder: OrderAdapterVH, position: Int) {
        var getData = list.get(position)
        holder.binding?.tvText?.text = getData.text
        getData.image?.let { holder.binding?.ivImage?.setImageResource(it) }

        if (getData.isSelected){
            holder.binding?.itemLayout?.setBackgroundColor(context.getColor(R.color.orange))
            holder.binding?.tvText?.setTextColor(context.getColor(R.color.white))
            holder.binding?.ivImage?.setColorFilter(context.getColor(R.color.white))
        }else{
            holder.binding?.itemLayout?.setBackgroundColor(context.getColor(R.color.F2White))
            holder.binding?.tvText?.setTextColor(context.getColor(R.color.bg_text))
            holder.binding?.ivImage?.setColorFilter(context.getColor(R.color.bg_text))
        }

        holder.itemView?.setOnClickListener{

            list.forEachIndexed { index, contractorInvoiceModel ->
                contractorInvoiceModel.isSelected = contractorInvoiceModel.id == getData.id
            }
            notifyDataSetChanged()
            when (position){
                0 ->{
                    var intent = Intent(context,RentEstimation::class.java )
                    context.startActivity(intent)
                }
                1 ->{
                    var intent = Intent(context,CreditCheck::class.java )
                    context.startActivity(intent)
                }
                2 ->{
                    var intent = Intent(context,Insurance::class.java )
                    context.startActivity(intent)
                }
                3 ->{
                    var intent = Intent(context,DigitalInspectioOrder::class.java )
                    context.startActivity(intent)
                }
                4 ->{
                    var intent = Intent(context,DigitalSignature::class.java )
                    context.startActivity(intent)
                }
                5 ->{
                    var intent = Intent(context,DataBackup::class.java )
                    context.startActivity(intent)
                }
                6 ->{
                    var intent = Intent(context,DataImport::class.java )
                    context.startActivity(intent)
                }
                7 ->{
                    var intent = Intent(context,RentCollection::class.java )
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

class OrderAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding: ReportsItemBinding? = DataBindingUtil.bind(itemView)
}