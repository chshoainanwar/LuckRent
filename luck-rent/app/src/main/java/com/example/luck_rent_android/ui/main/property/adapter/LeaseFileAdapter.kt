package com.example.luck_rent_android.ui.main.property.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ImageUploadBinding
import com.example.luck_rent_android.databinding.LeaseFileItemBinding
import com.example.luck_rent_android.ui.main.expense.adapter.ImageVH
import com.example.luck_rent_android.ui.main.expense.adapter.Placeholders
import com.example.luck_rent_android.ui.main.expense.adapter.loadImage
import java.io.File

class LeaseFileAdapter(
    var mContext: Context,
    var mData: ArrayList<File>,
    var callBack: ((position: Int, forDelete: Boolean) -> Unit)
) : RecyclerView.Adapter<FileVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileVH {
        val view = LayoutInflater.from(mContext).inflate(R.layout.lease_file_item, parent, false)
        return FileVH(view)
    }

    override fun onBindViewHolder(holder: FileVH, position: Int) {
        val mFile = mData[position]

        holder.binding?.ivUploadedMedia?.loadImage(
            mFile.path,
            Placeholders.DEFAULT,
            false
        )
        holder.binding?.ivDeleteImageMediaUpload?.setOnClickListener {
            callBack(position, true)
        }
    }

    override fun getItemCount(): Int {
     return mData.size
    }
}

class FileVH(view: View) : RecyclerView.ViewHolder(view) {
    val binding: LeaseFileItemBinding? = DataBindingUtil.bind(view)
}