package com.example.luck_rent_android.ui.main.contractormodule.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.JobCompleteItemBinding
import com.example.luck_rent_android.ui.main.contractormodule.JobStatus
import com.example.luck_rent_android.ui.main.contractormodule.model.ContractorInvoiceModel
import com.example.luck_rent_android.ui.main.contractormodule.model.JobCompleteModel
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.kodextech.project.kodexlib.base.BaseActivity

class JobCompleteAdapter(
    var context: BaseActivity,
    var modelData: ArrayList<JobCompleteModel>
) : RecyclerView.Adapter<JobCompleteAdapter.JobCompleteAdapterVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobCompleteAdapterVH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.job_complete_item, parent, false)
        return JobCompleteAdapterVH(v)
    }

    override fun onBindViewHolder(holder: JobCompleteAdapterVH, position: Int) {
        val getData = modelData[position]
        holder.binding?.tvStatus?.text = getData.status
        holder.binding?.tvInvoice?.text = getData.title

        holder.itemView?.setOnClickListener{

            val intent = Intent(context, JobStatus::class.java)
            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return modelData.size
    }

    inner class JobCompleteAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: JobCompleteItemBinding? = DataBindingUtil.bind(itemView)
    }
}