package com.example.luck_rent_android.ui.main.expense.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ImageUploadBinding
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import com.kodextech.project.kodexlib.utils.visible
import java.io.File

class ImageUploadAdapter(
    var mContext: Context,
    var mData: ArrayList<File>,
    var callBack: ((position: Int, forDelete: Boolean) -> Unit)
) : RecyclerView.Adapter<ImageVH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageVH {
        val view = LayoutInflater.from(mContext).inflate(R.layout.image_upload, parent, false)
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
                Placeholders.DEFAULT,
                false
            )

            holder.binding?.ivDeleteImageMediaUpload?.setOnClickListener {
                callBack(position, true)
            }


            holder.itemView.setOnClickListener {
//                val intent = Intent(mContext, FullImageView::class.java)
//                intent.putExtra("from", "addBooking")
//                mContext.startActivity(intent)
            }

        }

    }


    override fun getItemCount() = mData.size + 1
}

enum class Placeholders(var intValue: Int) {
    USER(R.drawable.ic_no_pic),
    DEFAULT(R.drawable.pdf),
    ROOM_DEFAULT(R.drawable.ic_no_pic),
    PROFILE_PLACE_HOLDER(R.drawable.ic_no_pic)
}


fun ImageView.loadImage(
    link: Any?,
    drawable: Placeholders = Placeholders.USER,
    isForCircle: Boolean = false,
) {
    try {
        if (isForCircle) {
            Glide.with(this)
                .asBitmap()
                .load(link)
                .circleCrop()
                .placeholder(drawable.intValue)//circularProgressDrawable
                .error(drawable.intValue)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(this)
        } else {
            Glide.with(this)
                .load(link)
                .centerCrop()
                .placeholder(drawable.intValue)//circularProgressDrawable
                .error(drawable.intValue)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(this)
        }
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    }
}
class ImageVH(view: View) : RecyclerView.ViewHolder(view) {
    val binding: ImageUploadBinding? = DataBindingUtil.bind(view)
}
