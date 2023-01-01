package com.example.luck_rent_android.ui.main.inspectionreport.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.InspectionItemsBinding
import com.example.luck_rent_android.databinding.ProfileItemBinding
import com.example.luck_rent_android.interfaces.OnIntentRecieved
import com.example.luck_rent_android.ui.main.expense.adapter.ImageUploadAdapter
import com.example.luck_rent_android.ui.main.inspectionreport.InspectionItem
import com.example.luck_rent_android.ui.main.inspectionreport.InspectionReport.Companion.roleStatus
import com.example.luck_rent_android.ui.main.inspectionreport.model.EntryModel
import com.example.luck_rent_android.ui.main.property.FileUtilityClass
import com.example.luck_rent_android.ui.main.property.model.DocumentModel
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import com.kodextech.project.kodexlib.utils.visible
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.FilePickerConst
import java.io.File
import java.util.ArrayList

class EntryAdapter(
    var context: BaseActivity,
    val mlist: List<EntryModel>,
    var booleanExpanded: Boolean,
    var callback: ((Int) -> Unit),
    var callback1: ((Int, second: Boolean) -> Unit)
) :
    RecyclerView.Adapter<EntryAdapter.EntryAdapterVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryAdapterVH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.inspection_items, parent, false)
        return EntryAdapterVH(view)
    }

    override fun onBindViewHolder(holder: EntryAdapterVH, position: Int) {
        val getData = mlist[position]
        holder.binding?.tvEntry?.text = getData.title

        val isExpandable: Boolean = mlist[position].expandable
        holder.binding?.hideLayout?.visibility =
            if (isExpandable) View.VISIBLE else View.GONE

        //Start Photos Recycler

        if (getData.documentModel?.size ?: 0 > 0) {
            holder.binding?.layoutImagesStart?.visible()

            var mImageAdapter: InspectionImageAdapter? = null

            mImageAdapter =
                InspectionImageAdapter(context, getData.documentModel!!) { position, forDelete ->
                    if (forDelete) {
                        if (position == null) {
                            context.showToast("System Having Some Issue")
                        } else {
//                       context.deleteImage(position)
                            getData.documentModel!!.removeAt(position)
                            mImageAdapter?.notifyDataSetChanged()
                        }
                    } else {
                        callback.invoke(position)

                    }
                    mImageAdapter?.notifyDataSetChanged()
                }

            holder.binding?.rvPhotosStart?.layoutManager =
                GridLayoutManager(context, 3)
            holder.binding?.rvPhotosStart?.adapter = mImageAdapter
            mImageAdapter?.notifyDataSetChanged()
        }

        //End Photos Recycler

        if (getData.endPhotoslist?.size ?: 0 > 0) {

            var mImageAdapter: InspectionImageAdapter? = null

            mImageAdapter =
                InspectionImageAdapter(
                    context,
                    getData.endPhotoslist!!
                ) { position, forDelete ->
                    if (forDelete) {
                        if (position == null) {
                            context.showToast("System Having Some Issue")
                        } else {
//                       context.deleteImage(position)
                            getData.endPhotoslist!!.removeAt(position)
                            mImageAdapter?.notifyDataSetChanged()
                        }
                    } else {
                        callback.invoke(position)

                    }
                    mImageAdapter?.notifyDataSetChanged()
                }

            holder.binding?.rvPhotosEnd?.layoutManager =
                GridLayoutManager(context, 3)
            holder.binding?.rvPhotosEnd?.adapter = mImageAdapter
            mImageAdapter?.notifyDataSetChanged()

        }


        if (getData.isSelected) {
            holder.binding?.entryFirstLayout?.setBackgroundColor(context.getColor(R.color.green))
            holder.binding?.layoutEntryFirstDesc?.setBackgroundColor(context.getColor(R.color.green))
            holder.binding?.ivBg?.setColorFilter(context.getColor(R.color.dark_green))
            holder.binding?.ivArrow?.setColorFilter(context.getColor(R.color.white))
            holder.binding?.tvEntry?.setTextColor(context.getColor(R.color.white))
            holder.binding?.tvEntryFirstDesc?.setTextColor(context.getColor(R.color.white))

        }
        else {
            holder.binding?.entryFirstLayout?.setBackgroundColor(context.getColor(R.color.faWhite))
            holder.binding?.layoutEntryFirstDesc?.setBackgroundColor(context.getColor(R.color.faWhite))
            holder.binding?.ivBg?.setColorFilter(context.getColor(R.color.white))
            holder.binding?.ivArrow?.setColorFilter(context.getColor(R.color.black))
            holder.binding?.tvEntry?.setTextColor(context.getColor(R.color.darkBlue))
            holder.binding?.tvEntryFirstDesc?.setTextColor(context.getColor(R.color.darkBlue))
        }

        when (roleStatus) {
            "Move-In" -> {
                holder.binding?.rlEndTenanacy?.gone()
                holder.binding?.boxMainLayout2?.gone()
            }
            "During-Tenancy" -> {
                holder.binding?.etFirstComments?.gone()
                holder.binding?.rlQuickWordPicks?.gone()
                holder.binding?.tvEndTenancy?.text = "Condition at During Tenancy"
            }
            "Move-Out" -> {
                holder.binding?.etFirstComments?.gone()
                holder.binding?.rlQuickWordPicks?.gone()
            }
        }

        when (position) {
            3 -> {
                holder.binding?.tabLayout1?.gone()
                holder.binding?.tabLayout2?.gone()
                holder.binding?.tabLayout3?.gone()
                holder.binding?.tabLayout4?.gone()
                holder.binding?.tabLayout5?.visible()
                holder.binding?.tabLayout6?.visible()
                holder.binding?.tabLayout7?.visible()

                holder.binding?.tabLayout1Start?.gone()
                holder.binding?.tabLayout2Start?.gone()
                holder.binding?.tabLayout3Start?.gone()
                holder.binding?.tabLayout4Start?.gone()
                holder.binding?.tabLayout5Start?.visible()
                holder.binding?.tabLayout6Start?.visible()
                holder.binding?.tabLayout7Start?.visible()
            }
        }

        holder.binding?.rluploadFile?.setOnClickListener {
            callback.invoke(position)
        }

        holder.binding?.rluploadFileEnd?.setOnClickListener {
            callback1.invoke(position, true)
        }



        holder.binding?.entryFirstLayout?.setOnClickListener {

            if (getData.isSelected) {
                getData.isSelected = false
            } else {
                getData.isSelected = true
            }
            notifyDataSetChanged()

            val profile = mlist[position]
            profile.expandable = !profile.expandable
            notifyItemChanged(position)

        }


    }

    override fun getItemCount(): Int {
        return mlist.size
    }


    inner class EntryAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var binding: InspectionItemsBinding? = DataBindingUtil.bind(itemView)

    }


}


