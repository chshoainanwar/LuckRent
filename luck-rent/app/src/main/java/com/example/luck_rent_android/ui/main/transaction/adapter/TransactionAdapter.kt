package com.example.luck_rent_android.ui.main.transaction.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ContractorInvoiceItemsBinding
import com.example.luck_rent_android.ui.main.adds.analytics.AnalyticsScreen.Companion.isSelected
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.invoice.InvoicePreview
import com.example.luck_rent_android.ui.main.transaction.Transaction.Companion.enumState
import com.example.luck_rent_android.ui.main.transaction.TransactionItem
import com.example.luck_rent_android.ui.main.transaction.model.TransactionModel
import com.kodextech.project.kodexlib.base.BaseActivity

class TransactionAdapter(
    var context : BaseActivity,
    var list : ArrayList<TransactionModel>
) : RecyclerView.Adapter<TransactionAdapter.TransactionAdapterVH>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionAdapterVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contractor_invoice_items, parent, false)
        return TransactionAdapterVH(view)
    }

    override fun onBindViewHolder(holder: TransactionAdapterVH, position: Int) {
        var mList = list[position]
        holder.binding?.tvInvoice?.text = mList.name
        holder.binding?.tvInvoiceDate?.text = mList.date
        holder.binding?.tvStat?.text = mList.status
        holder.binding?.tvStatus?.text = mList.amount

        if (mList.isSelected){
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
            list.forEachIndexed{index, transactionModel ->
                transactionModel.isSelected = transactionModel.id == mList.id
            }
            notifyDataSetChanged()

            when(enumState){
                TransactionItem.Invoices ->{
                    val intent = Intent(context, InvoicePreview::class.java)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class TransactionAdapterVH(itemView : View) : RecyclerView.ViewHolder(itemView){
        var binding : ContractorInvoiceItemsBinding? = DataBindingUtil.bind(itemView)
    }
}