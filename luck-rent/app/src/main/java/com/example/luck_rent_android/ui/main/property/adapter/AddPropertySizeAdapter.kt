package com.example.luck_rent_android.ui.main.property.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.BuildingItemsBinding
import com.example.luck_rent_android.databinding.PropertySizeItemBinding
import com.example.luck_rent_android.dialog.ADDOPTION
import com.example.luck_rent_android.dialog.AddSizeDialog
import com.example.luck_rent_android.ui.main.auth.Login
import com.example.luck_rent_android.ui.main.property.SizeDummyModel
import com.example.luck_rent_android.ui.main.property.model.UnitSizeModel
import com.kodextech.project.kodexlib.base.BaseActivity

class AddPropertySizeAdapter(
    var mContext: BaseActivity,
    var mData: ArrayList<UnitSizeModel>,
    var callback : ((UnitSizeModel , Int) -> Unit)
) : RecyclerView.Adapter<AddPropertySizeAdapterVH>() {
    var arraysize = ArrayList<String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddPropertySizeAdapterVH {
        val view = LayoutInflater.from(mContext).inflate(R.layout.building_items, parent, false)
        return AddPropertySizeAdapterVH(view)
    }

    override fun onBindViewHolder(holder: AddPropertySizeAdapterVH, position: Int) {


        var mItem = mData[position]
        holder.binding?.tvUnitAmenity?.text = mItem.name
        arraysize.clear()

        holder.itemView.setOnLongClickListener {


            callback.invoke(mItem , position)
            return@setOnLongClickListener true
        }

//        mItem.sizeArary?.forEachIndexed { index, s ->
//            arraysize.add(s)
//
//        }
//        holder.binding?.spPropertySize?.setItems(arraysize)
//        if (mItem.selectedIndex == -1){
//            holder.binding?.spPropertySize?.hint = "Size"
//
//        }else{
//            holder.binding?.spPropertySize?.selectedIndex = mItem.selectedIndex?:-1
//
//        }
//        holder.binding?.spPropertySize?.setOnItemSelectedListener { view, position, id, item ->
//         mItem.selectedIndex = position
//        }

    }


    override fun getItemCount(): Int {
        return mData.size
    }
}

class AddPropertySizeAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding: BuildingItemsBinding? = DataBindingUtil.bind(itemView)
}
