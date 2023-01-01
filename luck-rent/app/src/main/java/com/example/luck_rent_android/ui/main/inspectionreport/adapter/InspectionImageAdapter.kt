package com.example.luck_rent_android.ui.main.inspectionreport.adapter

import android.content.Context
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
import com.example.luck_rent_android.databinding.ImageUploadInspectionBinding
import com.kodextech.project.kodexlib.utils.gone
import com.kodextech.project.kodexlib.utils.visible
import java.io.File

class InspectionImageAdapter (
    var mContext: Context,
    var mData: ArrayList<File>,
    var callBack: ((position: Int, forDelete: Boolean) -> Unit)
) : RecyclerView.Adapter<InspectionImageVH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InspectionImageVH {
        val view = LayoutInflater.from(mContext).inflate(R.layout.image_upload_inspection, parent, false)
        return InspectionImageVH(view)
    }

    override fun onBindViewHolder(holder: InspectionImageVH, position: Int) {

            val mItem = mData[position]

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


    override fun getItemCount() = mData.size
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
class InspectionImageVH(view: View) : RecyclerView.ViewHolder(view) {
    val binding: ImageUploadInspectionBinding? = DataBindingUtil.bind(view)
}
