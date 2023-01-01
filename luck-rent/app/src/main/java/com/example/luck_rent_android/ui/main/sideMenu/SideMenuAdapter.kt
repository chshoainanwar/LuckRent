package com.example.luck_rent_android.ui.main.sideMenu

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.SideItemBinding
import com.example.luck_rent_android.ui.main.expense.ExpenseListing
import com.example.luck_rent_android.ui.main.invoice.AddInvoice
import com.example.luck_rent_android.ui.main.payment.AddPayment
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.profile.Profile.Companion.roleProfile
import com.example.luck_rent_android.ui.main.property.LeaseActivity
import com.example.luck_rent_android.ui.main.property.PropertiesActivity
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import com.kodextech.project.kodexlib.utils.visible

class SideMenuAdapter(
    private var mContext: BaseActivity,
    private var mData: ArrayList<SideItems>,
    private var isFor: String,
    private var callBack: ((position: Int) -> Unit)
) :
    RecyclerView.Adapter<SideVH>() {

    var animState: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SideVH {
        return SideVH(LayoutInflater.from(mContext).inflate(R.layout.side_item, parent, false))
    }

    override fun onBindViewHolder(holder: SideVH, position: Int) {
        val item = mData[position]

//        if (isFor == "top") {
//            if (position == 2) {
//                holder.binding?.ivArrow?.visible()
//            } else {
//                holder.binding?.ivArrow?.gone()
//            }
//        }

//        holder.itemView?.setOnClickListener {
//            if (holder.binding?.rlAdds?.visibility == View.VISIBLE) {
//                animState = false
//                holder.binding?.rlAdds?.gone()
//            } else {
//                animState = true
//                holder.binding?.rlAdds?.visible()
//            }
//            mContext.checkAnimState(holder.binding?.ivArrow!!, animState)
//        }

        holder.itemView.setOnClickListener {
            callBack(position)
//            if (isFor == "top") {
//                if (position == 2) {
//                    if (holder.binding?.rlAdds?.visibility == View.VISIBLE) {
//                        animState = false
//                        holder.binding?.rlAdds?.gone()
//                    } else {
//                        animState = true
//                        holder.binding?.rlAdds?.visible()
//                    }
//                    mContext.checkAnimState(holder.binding?.ivArrow!!, animState)
//                } else {
//                    callBack(position)
//                }
//            } else {
//                callBack(position)
//            }
        }
        holder.binding?.ivSideItem?.setImageResource(item.image)
        holder.binding?.tvTitleSideItem?.text = item.title


//        holder.binding?.tvExpenses?.setOnClickListener {
//
//            if (roleProfile == "Landlord") {
////                val intent = Intent(mContext, ExpenseListing::class.java)
////                mContext.startActivity(intent)
//            }else if (roleProfile == "Property Manager"){
////                val intent = Intent(mContext, ExpenseListing::class.java)
////                mContext.startActivity(intent)
//            }
//
//        }
//        holder.binding?.tvInvoices?.setOnClickListener {
//
//            if (roleProfile == "Landlord") {
////                val intent = Intent(mContext, AddInvoice::class.java)
////                intent.putExtra("isFrom", "invoice")
////                mContext.startActivity(intent)
//            }else if (roleProfile == "Property Manager"){
////                val intent = Intent(mContext, AddInvoice::class.java)
////                intent.putExtra("isFrom", "invoice")
////                mContext.startActivity(intent)
//            }
//        }
//        holder.binding?.tvPayments?.setOnClickListener {
//
//            if (roleProfile == "Landlord") {
////                val intent = Intent(mContext, AddPayment::class.java)
////                intent.putExtra("isFrom", "payment")
////                mContext.startActivity(intent)
//            }else if (roleProfile == "Property Manager"){
////                val intent = Intent(mContext, AddPayment::class.java)
////                intent.putExtra("isFrom", "payment")
////                mContext.startActivity(intent)
//            }
//
//
//        }

    }

    override fun getItemCount(): Int = mData.size
}

class SideVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var binding: SideItemBinding? = DataBindingUtil.bind(itemView)
}

class SideItems(var title: String, var image: Int)