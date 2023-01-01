package com.example.luck_rent_android.ui.main.property.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ImgItemBinding
import com.example.luck_rent_android.ui.main.expense.adapter.loadImage
import com.kodextech.project.kodexlib.base.BaseActivity
import java.io.File

class PropertyImageListing(
    var mContext: BaseActivity,
    var mData: ArrayList<File>
) : RecyclerView.Adapter<PropertyImageListingVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyImageListingVH {
        val view = LayoutInflater.from(mContext).inflate(R.layout.img_item, parent, false)
        return PropertyImageListingVH(view)
    }

    override fun onBindViewHolder(holder: PropertyImageListingVH, position: Int) {
        val item = mData[position]


        holder.binding?.ivDelete?.setOnClickListener {
//            callback.invoke(position,true)
            mData.removeAt(position)
            notifyDataSetChanged()
        }

        holder.binding?.ivPhoto?.loadImage(
            item.path,
            com.example.luck_rent_android.ui.main.expense.adapter.Placeholders.DEFAULT,
            false
        )
    }

    override fun getItemCount(): Int {
        return mData.count()
    }
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

class PropertyImageListingVH(view: View) : RecyclerView.ViewHolder(view) {
    val binding: ImgItemBinding? = DataBindingUtil.bind(view)
}
