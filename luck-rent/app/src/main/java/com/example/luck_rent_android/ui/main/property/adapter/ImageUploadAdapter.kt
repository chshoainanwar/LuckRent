package com.example.luck_rent_android.ui.main.property.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ImageNewUploadBinding
import com.example.luck_rent_android.ui.main.expense.adapter.Placeholders
import com.example.luck_rent_android.ui.main.expense.adapter.loadImage
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import com.kodextech.project.kodexlib.utils.visible
import java.io.File

class ImageUploadAdapter(
    var mContext: BaseActivity,
    var mData: ArrayList<File>,
    var callBack: ((position: Int, forDelete: Boolean) -> Unit)
) : RecyclerView.Adapter<ImageVH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageVH {
        val view = LayoutInflater.from(mContext).inflate(R.layout.image_new_upload, parent, false)
        return ImageVH(view)
    }

    override fun onBindViewHolder(holder: ImageVH, position: Int) {

        if (position == 0) {
            holder.binding?.rluploadFile?.visible()
            holder.binding?.llItem?.gone()
            holder.itemView.setOnClickListener {
                callBack(position, false)
            }
        } else {
            holder.binding?.llItem?.visible()
            holder.binding?.rluploadFile?.gone()
            val mItem = mData[position - 1]


            holder.binding?.ivUploadedMedia?.loadImage(
                mItem.path,
                Placeholders.USER,
                false
            )

            holder.binding?.ivDeleteImageMediaUpload?.setOnClickListener {
                callBack(position, true)
            }

            holder.itemView.setOnClickListener {
            }

        }


    }


    override fun getItemCount() = mData.size + 1
}

class ImageVH(view: View) : RecyclerView.ViewHolder(view) {
    val binding: ImageNewUploadBinding? = DataBindingUtil.bind(view)
}
