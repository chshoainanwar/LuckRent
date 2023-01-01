package com.example.luck_rent_android.ui.main.contractormodule.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ContractorOverviewItemsBinding
import com.example.luck_rent_android.ui.main.contractormodule.ContractorRentProperty
import com.example.luck_rent_android.ui.main.contractormodule.ContractorViewProperty
import com.example.luck_rent_android.ui.main.contractormodule.model.ContractorOverviewModel
import com.kodextech.project.kodexlib.base.BaseActivity

class ContractorOverviewAdapter(
    var context: BaseActivity,
    var list: ArrayList<ContractorOverviewModel>
) : RecyclerView.Adapter<ContractorOverviewAdapter.ViewHolderCO>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCO {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contractor_overview_items, parent, false)
        return ViewHolderCO(view)
    }

    override fun onBindViewHolder(holder: ViewHolderCO, position: Int) {

        val getData = list[position]
        holder.binding?.tvLandLord?.text = getData.landlordName

        holder.binding?.btnView?.setOnClickListener {
            val intent = Intent(context, ContractorViewProperty::class.java)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolderCO(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var binding: ContractorOverviewItemsBinding? = DataBindingUtil.bind(itemView)
    }
}