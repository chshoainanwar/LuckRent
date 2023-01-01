package com.example.luck_rent_android.ui.main.contractormodule.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.AddsLayoutContractorBinding
import com.kodextech.project.kodexlib.base.BaseActivity

class ContractorAdsAdapter (var context: BaseActivity): RecyclerView.Adapter<ContractorAdsAdapter.ViewHolderCA>() {

    private var binding: AddsLayoutContractorBinding?=null

    var images = intArrayOf(
        R.drawable.add_pic, R.drawable.home2, R.drawable.home3, R.drawable.home4
    )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCA {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adds_layout_contractor, parent, false)
        return ViewHolderCA(v)
    }

    override fun onBindViewHolder(holder: ViewHolderCA, position: Int) {
        holder.binding?.ivRecycle?.setImageResource(images[position])
    }

    override fun getItemCount(): Int {
      return images.size
    }

    inner class ViewHolderCA(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var binding: AddsLayoutContractorBinding? = DataBindingUtil.bind(itemView)
    }
}