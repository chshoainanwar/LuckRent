package com.example.luck_rent_android.ui.main.notification.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.NotificationItemBinding
import com.example.luck_rent_android.ui.main.notification.model.NotificationModel
import com.kodextech.project.kodexlib.base.BaseActivity
import java.io.Serializable

class NotificationAdapter
    (
    var context: BaseActivity,
    var list: ArrayList<NotificationModel>
) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val getData = list[position]
        holder.binding?.tvNotificationTitle?.text = getData.title
        holder.binding?.tvNotificationDetail?.text = getData.detail
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var binding: NotificationItemBinding? = DataBindingUtil.bind(itemView)
}
