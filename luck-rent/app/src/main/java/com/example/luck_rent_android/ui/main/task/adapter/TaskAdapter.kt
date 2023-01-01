package com.example.luck_rent_android.ui.main.task.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityTaskListBinding
import com.example.luck_rent_android.databinding.TaskItemBinding
import com.example.luck_rent_android.ui.main.task.model.TaskModel
import com.kodextech.project.kodexlib.base.BaseActivity

class TaskAdapter(
    var context: BaseActivity,
    var list: ArrayList<TaskModel>
) : RecyclerView.Adapter<TaskAdapter.TaskAdapterVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapterVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskAdapterVH(view)
    }

    override fun onBindViewHolder(holder: TaskAdapterVH, position: Int) {

        val getData = list[position]
        holder.binding?.tvTask?.text = getData.taskName
        holder.binding?.tvDate?.text = getData.date
        holder.binding?.tvTime?.text = getData.time

        holder.binding?.ivDelete?.setOnClickListener {
            list.removeAt(position)
            notifyDataSetChanged()
            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
        }
        holder.itemView.setOnClickListener {

            val view = View.inflate(context, R.layout.dialog_add_task, null)
            val builder = AlertDialog.Builder(context)
            builder.setView(view)
            builder.setCancelable(false)
            val dialog = builder.create()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()


            //Register Done Button & Cancel Icon from Dialog
            var btnAdd = view?.findViewById<AppCompatButton>(R.id.btnAdd)
            var etTaskName = view?.findViewById<AppCompatEditText>(R.id.etTaskName)
            var tvDueDate = view?.findViewById<AppCompatTextView>(R.id.tvDueDate)
            var tvRemindTime = view?.findViewById<AppCompatTextView>(R.id.tvRemindTime)
            var cancelIcon = view?.findViewById<AppCompatImageView>(R.id.cancelIcon)
            etTaskName?.setText(getData.taskName)
            tvDueDate?.setText(getData.date)
            tvRemindTime?.setText(getData.time)

            //Dialog Done Button
            btnAdd?.setOnClickListener {
                var getTaskName : String? = etTaskName?.text.toString()
                var getDate : String? = tvDueDate?.text.toString()
                var getTime : String? = tvRemindTime?.text.toString()
                if (getTaskName.isNullOrEmpty()) {
                    etTaskName?.error = "Required"
                }else if (getDate.isNullOrEmpty()){
                    context.showToast("Date Required")
                }else if (getTime.isNullOrEmpty()){
                    context.showToast("Time Required")
                } else {
                    list[position].taskName = getTaskName
                    list[position].date = getDate
                    list[position].time = getTime
                    notifyDataSetChanged()
                    dialog.dismiss()
                }

            }
            //Dialog Cancel Icon
            cancelIcon?.setOnClickListener {
                dialog.dismiss()
            }


        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class TaskAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: TaskItemBinding? = DataBindingUtil.bind(itemView)
    }
}