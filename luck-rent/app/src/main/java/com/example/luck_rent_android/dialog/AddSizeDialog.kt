package com.example.luck_rent_android.dialog

import android.app.Activity
import android.app.Dialog
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.AddSizeDialogBinding
import com.kodextech.project.kodexlib.base.BaseDialogueFragment

class AddSizeDialog : BaseDialogueFragment() {

    private var binding: AddSizeDialogBinding? = null

    private var onClicksCallBack: ((ADDOPTION, sizeName: String) -> Unit)? = null
    private var etSizeName: String? = null

    override fun onSetupArguments() {

    }

    override fun onBindItemListenerOrViewVariables() {

    }

    override fun setupContentViewWithBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_size_dialog, container, false)
        isCancelable = false




        binding?.btnSave?.setOnClickListener {
            etSizeName = binding?.etSize?.text?.toString()

            if (etSizeName.isNullOrEmpty()) {
                binding?.etSize?.error = "Required"
            } else {
                onClicksCallBack?.invoke(ADDOPTION.SAVE, etSizeName ?: "")
                dismiss()
            }
        }
        binding?.ivCross?.setOnClickListener { dismiss() }

        return binding?.root!!
    }

    override fun onRecycleBeforeDestroy() {

    }

    override fun onBecameInvisibleToUser() {

    }

    override fun onBecameVisibleToUser() {

    }

    private fun getScreenWidth(activity: Activity): Int {
        val size = Point()
        activity.windowManager.defaultDisplay.getSize(size)
        return size.x
    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        dialog?.window?.setLayout(
            (getScreenWidth(mActivity) * .9).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

    }

    companion object {
        fun newInstance(
            tvLabel: String? = "",
            isFor: String? = "",
            WORKERUUID: String? = "",
            onClicksCallBack: ((ADDOPTION, sizeName: String) -> Unit)? = null
        ): AddSizeDialog {
            val args = Bundle()
//            args.putString("TITLE", tvLabel)
//            args.putString("isFor", isFor)
//            args.putString("WORKERUUID", WORKERUUID)
            val fragment = AddSizeDialog()
            fragment.onClicksCallBack = onClicksCallBack
            fragment.arguments = args
            return fragment
        }

    }


}

enum class ADDOPTION {
    SAVE
}