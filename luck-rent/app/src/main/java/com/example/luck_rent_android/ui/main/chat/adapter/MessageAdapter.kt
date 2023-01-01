package com.example.luck_rent_android.ui.main.chat.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.MessagesItemsBinding
import com.example.luck_rent_android.ui.main.chat.Chat
import com.example.luck_rent_android.ui.main.chat.model.MessagesModel
import com.kodextech.project.kodexlib.base.BaseActivity

class MessageAdapter(
    var context: BaseActivity,
    var list: ArrayList<MessagesModel>
) : RecyclerView.Adapter<MessageAdapterVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdapterVH {
        val v = LayoutInflater.from(context).inflate(R.layout.messages_items, parent, false)
        return MessageAdapterVH(v)
    }

    override fun onBindViewHolder(holder: MessageAdapterVH, position: Int) {
        val getData = list[position]
        holder.binding?.tvName?.text = getData.name
        holder.binding?.tvDescription?.text = getData.description
        holder.binding?.tvTime?.text = getData.time
        holder.binding?.tvNumber?.text = getData.number
        getData.image?.let { holder.binding?.ivImage?.setImageResource(it) }
        holder.binding?.content?.setOnClickListener{
            val intent = Intent(context, Chat::class.java)
            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

class MessageAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var binding: MessagesItemsBinding? = DataBindingUtil.bind(itemView)
}

