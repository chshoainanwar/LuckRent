package com.example.luck_rent_android.ui.main.rentermodule.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.AddsLayoutBinding
import com.example.luck_rent_android.ui.main.adds.Request
import com.example.luck_rent_android.ui.main.auth.Login
import com.example.luck_rent_android.ui.main.contractormodule.ContractorRentProperty
import com.example.luck_rent_android.ui.main.rentermodule.model.RenterNewPropertiesModel
import com.kodextech.project.kodexlib.base.BaseActivity

class RenterAddsAdapter(var context: BaseActivity) :
    RecyclerView.Adapter<RenterAddsAdapter.RenterAddsAdapterVH>() {

    var images = intArrayOf(
        R.drawable.add_pic, R.drawable.home2, R.drawable.home3, R.drawable.home4, R.drawable.home5
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RenterAddsAdapterVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adds_layout, parent, false)
        return RenterAddsAdapterVH(view)
    }

    override fun onBindViewHolder(holder: RenterAddsAdapterVH, position: Int) {
        holder.binding?.ivRecycle?.setImageResource(images[position])
        holder.binding?.addImg?.setOnClickListener {

            val intent = Intent(context, ContractorRentProperty::class.java)
            intent.putExtra("isFrom","RenterAds")
            context.startActivity(intent)

//            val view = View.inflate(context, R.layout.ad_dialog, null)
//            val builder = AlertDialog.Builder(context)
//            builder.setView(view)
//            builder.setCancelable(false)
//            val dialog = builder.create()
//            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
//            dialog.show()
//
//
//            //Register Done Button & Cancel Icon from Dialog
//            var btnDone = view?.findViewById<Button>(R.id.btnDone)
//            var ivCross = view?.findViewById<ImageView>(R.id.ivCross)
//
//            //Dialog Done Button
//            btnDone?.setOnClickListener {
//                dialog.dismiss()
//            }
//            //Dialog Cancel Icon
//            ivCross?.setOnClickListener {
//                dialog.dismiss()
//            }


        }
    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class RenterAddsAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var binding: AddsLayoutBinding? = DataBindingUtil.bind(itemView)

    }

}