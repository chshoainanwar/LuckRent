package com.example.luck_rent_android.ui.main.expense.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ExpenseItemBinding
import com.example.luck_rent_android.model.ExpenseModel
import com.example.luck_rent_android.ui.main.expense.ExpenseDetail

class ExpenseListingAdapter(
    var mContext: Context,
    var mData: ArrayList<ExpenseModel>
) : RecyclerView.Adapter<ExpenseListingAdapterVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseListingAdapterVH {
        val view = LayoutInflater.from(mContext).inflate(R.layout.expense_item, parent, false)
        return ExpenseListingAdapterVH(view)
    }

    override fun onBindViewHolder(holder: ExpenseListingAdapterVH, position: Int) {
        val mItem = mData[position]

        holder.binding?.tvBuilding?.text = mItem.building
        holder.binding?.tvCategory?.text = mItem.category
        holder.binding?.tvUnit?.text = mItem.unit
        holder.binding?.tvAmount?.text = mItem.amount

        holder.binding?.btnView?.setOnClickListener {
            val intent = Intent(mContext, ExpenseDetail::class.java)
            mContext.startActivity(intent)
        }



    }

    override fun getItemCount(): Int {
        return mData.size
    }
}

class ExpenseListingAdapterVH(view: View) : RecyclerView.ViewHolder(view) {
    var binding: ExpenseItemBinding? = DataBindingUtil.bind(view)
}
