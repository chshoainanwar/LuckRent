package com.example.luck_rent_android.ui.main.property.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.PropertyItemBinding
import com.example.luck_rent_android.model.PropertyModel
import com.example.luck_rent_android.ui.main.invoice.AddInvoice
import com.example.luck_rent_android.ui.main.payment.AddPayment
import com.example.luck_rent_android.ui.main.property.AddProperty
import com.example.luck_rent_android.ui.main.property.OverViewProperty

class PropertiesListingAdapter(
    var mContext: Context,
    var mData: ArrayList<PropertyModel>,
    var isFrom: String,
    var isFor: String,
    var callBack: ((position: Int) -> Unit)
) : RecyclerView.Adapter<PropertiesListingAdapterVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertiesListingAdapterVH {
        val view = LayoutInflater.from(mContext).inflate(R.layout.property_item, parent, false)
        return PropertiesListingAdapterVH(view)
    }

    override fun onBindViewHolder(holder: PropertiesListingAdapterVH, position: Int) {
        val mItem = mData[position]
        holder.binding?.cvMain?.setOnLongClickListener {
            callBack(position)

            val view = View.inflate(mContext, R.layout.dialog_mark, null)
            val builder = AlertDialog.Builder(mContext)
            builder.setView(view)
            builder.setCancelable(true)
            val dialog = builder.create()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()


            //Register Done Button & Cancel Icon from Dialog
            var tvMark =
                view?.findViewById<AppCompatTextView>(R.id.tvMark)
            var tvDelete =
                view?.findViewById<AppCompatTextView>(R.id.tvDelete)
            var ivMark =
                view?.findViewById<AppCompatImageView>(R.id.ivMark)
            var ivDelete =
                view?.findViewById<AppCompatImageView>(R.id.ivDelete)
            var rlMark = view?.findViewById<RelativeLayout>(R.id.rlMark)
            var rlDelete =
                view?.findViewById<RelativeLayout>(R.id.rlDelete)

            tvMark?.text = "Edit"
            ivMark?.setImageResource(R.drawable.ic_edit)


            //Dialog Done Button
            rlMark?.setOnClickListener {
                dialog.dismiss()
                val intent = Intent(mContext, AddProperty::class.java)
                mContext.startActivity(intent)
            }
            //Dialog Cancel Icon
            rlDelete?.setOnClickListener {
                Toast.makeText(mContext, "Delete", Toast.LENGTH_SHORT).show()
                mData.removeAt(position)
                notifyDataSetChanged()
                dialog.dismiss()
            }
            notifyDataSetChanged()

            return@setOnLongClickListener true
        }

        if (isFor == "1") {
            holder.binding?.btnSelect?.text = "View"
        } else {
            holder.binding?.btnSelect?.text = "View"
        }

        holder.binding?.propertyNo?.text = mItem.propertyNumber
        holder.binding?.propertyUnits?.text = mItem.propertyUnit + " Units"
        holder.binding?.tvPropertyAddress?.text = mItem.propertyAddress
        Glide
            .with(mContext)
            .load(mItem.propertyImage)
            .centerCrop()
            .placeholder(R.drawable.ic_no_pic)
            .into(holder.binding?.ivProperty!!);

        holder.binding?.btnSelect?.setOnClickListener {
            if (isFrom == "payment") {
                val intent = Intent(mContext, AddPayment::class.java)
                mContext.startActivity(intent)
            } else if (isFrom == "invoice") {
                val intent = Intent(mContext, AddInvoice::class.java)
                mContext.startActivity(intent)
            } else {
                val view = View.inflate(mContext, R.layout.dialog_unit, null)
                val builder = AlertDialog.Builder(mContext)
                builder.setView(view)
                builder.setCancelable(true)
                val dialog = builder.create()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.show()


                //Register Done Button & Cancel Icon from Dialog
                var tvUnitNumber =
                    view?.findViewById<AppCompatTextView>(R.id.tvUnitNumber)
                var btnNext =
                    view?.findViewById<AppCompatButton>(R.id.btnNext)



                //Dialog Done Button
                btnNext?.setOnClickListener {
                    dialog.dismiss()
                    val intent = Intent(mContext, OverViewProperty::class.java)
                    mContext.startActivity(intent)
                }
                notifyDataSetChanged()
            }
        }

    }

    override fun getItemCount(): Int {
        return mData.size
    }
}

class PropertiesListingAdapterVH(view: View) : RecyclerView.ViewHolder(view) {
    var binding: PropertyItemBinding? = DataBindingUtil.bind(view)
}
