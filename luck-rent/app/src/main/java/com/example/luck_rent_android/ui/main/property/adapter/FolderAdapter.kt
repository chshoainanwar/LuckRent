package com.example.luck_rent_android.ui.main.property.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.FolderItemBinding
import com.example.luck_rent_android.ui.main.property.model.DocumentModel
import com.example.luck_rent_android.ui.main.property.model.FolderModel
import com.kodextech.project.kodexlib.base.BaseActivity

class FolderAdapter(
    val context: BaseActivity,
    var list: ArrayList<FolderModel>
) : RecyclerView.Adapter<FolderAdapter.FolderModelVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderModelVH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.folder_item, parent, false)
        return FolderModelVH(view)
    }

    override fun onBindViewHolder(holder: FolderModelVH, position: Int) {
        val mList = list[position]
        holder.binding?.tvFolderName?.text = mList.name

        holder.itemView?.setOnLongClickListener {

            val view = View.inflate(context, R.layout.dialog_documment_main, null)
            val builder = AlertDialog.Builder(context)
            builder.setView(view)
            builder.setCancelable(false)
            val dialog = builder.create()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()


            //Register Done Button & Cancel Icon from Dialog
            var tvView = view?.findViewById<AppCompatTextView>(R.id.tvView)
            var tvEdit = view?.findViewById<AppCompatTextView>(R.id.tvEdit)
            var tvDelete = view?.findViewById<AppCompatTextView>(R.id.tvDelete)
            var tvMove = view?.findViewById<AppCompatTextView>(R.id.tvMove)
            var tvDownload = view?.findViewById<AppCompatTextView>(R.id.tvDownload)
            var tvShare = view?.findViewById<AppCompatTextView>(R.id.tvShare)
            var ivCancel = view?.findViewById<AppCompatImageView>(R.id.ivCancel)

            //Dialog Done Button
            tvView?.setOnClickListener {
                dialog.dismiss()
            }
            tvEdit?.setOnClickListener {
                dialog.dismiss()
            }
            tvDelete?.setOnClickListener {
                list.removeAt(position)
                notifyDataSetChanged()
                dialog.dismiss()
            }
            tvMove?.setOnClickListener {
                dialog.dismiss()
            }
            tvDownload?.setOnClickListener {
                dialog.dismiss()
            }
            tvShare?.setOnClickListener {
                dialog.dismiss()
            }
            ivCancel?.setOnClickListener {
                dialog.dismiss()
            }

            return@setOnLongClickListener true
        }

    }

    override fun getItemCount(): Int {
      return list.size
    }
    inner class FolderModelVH(itemView : View) : RecyclerView.ViewHolder(itemView){
        var binding : FolderItemBinding? = DataBindingUtil.bind(itemView)
    }
}