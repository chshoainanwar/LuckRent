package com.example.luck_rent_android.ui.main.adds.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ProfileItemBinding
import com.example.luck_rent_android.ui.main.adds.Accept
import com.example.luck_rent_android.ui.main.adds.Request
import com.example.luck_rent_android.ui.main.adds.model.RequestModel
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import com.kodextech.project.kodexlib.utils.visible

class RequestAdapter(val requestModelList : List<RequestModel>, var context: BaseActivity) : RecyclerView.Adapter<RequestAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.profile_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RequestAdapter.ViewHolder, position: Int) {

        val model : RequestModel = requestModelList[position]
        holder.binding?.ivProfile?.setImageResource(model.profileImage)
        holder.binding?.tvProfileName?.text = model.profileName
        holder.binding?.tvDescription?.text = model.profileDecription

//        val isExpandable : Boolean = requestModelList[position].expandable
//        holder.binding?.profileLayout2?.visibility = if (isExpandable)View.VISIBLE   else View.GONE
//
//        holder.binding?.profileLayout1?.setOnClickListener{
//            val profile = requestModelList[position]
//            profile.expandable = !profile.expandable
//            notifyItemChanged(position)
//        }

        holder.binding?.profileLayout1?.setOnClickListener {
            if (model.expandable  == true){
                holder.binding?.profileLayout2?.gone()
                holder.binding?.viewApplicationArrow?.setImageResource(R.drawable.ic_arrow)
                model.expandable = false
            }else if(model.expandable == false){
                holder.binding?.profileLayout2?.visible()
                holder.binding?.viewApplicationArrow?.setImageResource(R.drawable.ic_blue_arrow)
                model.expandable = true
            }
        }


        holder.binding?.btnViewApplication?.setOnClickListener{
            val intent = Intent(context, Accept::class.java)
            context?.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return requestModelList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var binding : ProfileItemBinding? = DataBindingUtil.bind(itemView)

    }
}
//var profileDescription = arrayOf(
//    "Hi there, thanks for showing us the beautiful\n" +
//            "        house you have. We are submitting our\n" +
//            "        application with the LuckRent App per your\n" +
//            "    request. Please let me know if you need\n" +
//            "    further information from me. Thank you.",
//    "Hi there, thanks for showing us the beautiful\n" +
//            "        house you have. We are submitting our\n" +
//            "        application with the LuckRent App per your\n" +
//            "    request. Please let me know if you need\n" +
//            "    further information from me. Thank you.",
//    "Hi there, thanks for showing us the beautiful\n" +
//            "        house you have. We are submitting our\n" +
//            "        application with the LuckRent App per your\n" +
//            "    request. Please let me know if you need\n" +
//            "    further information from me. Thank you."
//)
//
//var profileName = arrayOf("Thomas Baker", "Oliver Smith", "Harry Paterson")
//
//var profileImage = intArrayOf(R.drawable.profile, R.drawable.profile2, R.drawable.profile3)