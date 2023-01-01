package com.example.luck_rent_android.ui.main.contractormodule.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ContractorInvoiceItemsBinding
import com.example.luck_rent_android.ui.main.contractormodule.ContractorReport
import com.example.luck_rent_android.ui.main.contractormodule.model.ContractorInvoiceModel
import com.example.luck_rent_android.ui.main.invoice.InvoicePreview
import com.example.luck_rent_android.ui.main.profile.Profile.Companion.roleProfile
import com.example.luck_rent_android.ui.main.rentermodule.RenterReport
import com.kodextech.project.kodexlib.base.BaseActivity

class ContractorInvoiceAdapter(
    var context: BaseActivity,
    var modelData: ArrayList<ContractorInvoiceModel>
) : RecyclerView.Adapter<ContractorInvoiceAdapter.ViewHolderCI>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCI {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.contractor_invoice_items, parent, false)
        return ViewHolderCI(v)
    }

    override fun onBindViewHolder(holder: ViewHolderCI, position: Int) {
        val getData = modelData[position]
        holder.binding?.tvInvoice?.text = getData.name
        holder.binding?.tvInvoiceDate?.text = getData.date
        holder.binding?.tvStat?.text = getData.status
        holder.binding?.tvStatus?.text = getData.amount

        if (getData.isSelected){
            holder.binding?.mainView?.setBackgroundColor(context.getColor(R.color.darkBlue))
            holder.binding?.tvStat?.setTextColor(context.getColor(R.color.white))
            holder.binding?.tvStatus?.setTextColor(context.getColor(R.color.white))
            holder.binding?.tvInvoice?.setTextColor(context.getColor(R.color.white))
            holder.binding?.tvInvoiceDate?.setTextColor(context.getColor(R.color.white))
            holder.binding?.ivSideArrow?.setColorFilter(context.getColor(R.color.white))
        }else{
            holder.binding?.mainView?.setBackgroundColor(context.getColor(R.color.white))
            holder.binding?.tvStat?.setTextColor(context.getColor(R.color.black))
            holder.binding?.tvStatus?.setTextColor(context.getColor(R.color.black))
            holder.binding?.tvInvoice?.setTextColor(context.getColor(R.color.black))
            holder.binding?.tvInvoiceDate?.setTextColor(context.getColor(R.color.black))
            holder.binding?.ivSideArrow?.setColorFilter(context.getColor(R.color.black))
        }
        holder.itemView?.setOnClickListener{

            modelData.forEachIndexed { index, contractorInvoiceModel ->
                contractorInvoiceModel.isSelected = contractorInvoiceModel.id == getData.id
            }
            notifyDataSetChanged()

            if (roleProfile == "Contractor"){
                val intent = Intent(context, InvoicePreview::class.java)
                context.startActivity(intent)
            }else if (roleProfile == "Renter"){
                val intent = Intent(context, InvoicePreview::class.java)
                context.startActivity(intent)
            }
        }}


    override fun getItemCount(): Int {
        return modelData.size
    }

    inner class ViewHolderCI(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ContractorInvoiceItemsBinding? = DataBindingUtil.bind(itemView)
    }
}