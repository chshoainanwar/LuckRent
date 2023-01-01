package com.example.luck_rent_android.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.AdDialogBinding
import com.example.luck_rent_android.databinding.AdDialogForLinkBinding
import com.example.luck_rent_android.ui.main.adds.MyAds
import com.kodextech.project.kodexlib.base.BaseDialogueFragment

class AddDialog : BaseDialogueFragment() {

    private var binding: AdDialogBinding? = null
    override fun onSetupArguments() {
//        arguments?.let {
//            this.tvLabel = it.getString("TITLE", "") ?: ""
//            this.isFor = it.getString("ISFOR", "") ?: ""
//            this.workerUUID = it.getString("WORKERUUID", "") ?: ""
//        }
    }

    override fun onBindItemListenerOrViewVariables() {

    }

    override fun setupContentViewWithBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.ad_dialog, container, false)
        isCancelable = false

        binding?.btnDone?.setOnClickListener {
            val intent = Intent(context,MyAds::class.java)
            startActivity(intent) }

        binding?.ivCross?.setOnClickListener { dismiss() }

        return binding?.root!!
    }

    override fun onRecycleBeforeDestroy() {

    }

    override fun onBecameInvisibleToUser() {

    }

    override fun onBecameVisibleToUser() {

    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        dialog?.window?.setLayout(
            (getScreenWidth(mActivity) * .9).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

    }

    private fun getScreenWidth(activity: Activity): Int {
        val size = Point()
        activity.windowManager.defaultDisplay.getSize(size)
        return size.x
    }

    companion object {
        fun newInstance(
            tvLabel: String? = "",
            isFor: String? = "",
            WORKERUUID: String? = "",
            onClicksCallBack: ((label: String) -> Unit)? = null
        ): AddDialog {
            val args = Bundle()
//            args.putString("TITLE", tvLabel)
//            args.putString("isFor", isFor)
//            args.putString("WORKERUUID", WORKERUUID)
            val fragment = AddDialog()
//            fragment.onClicksCallBack = onClicksCallBack
            fragment.arguments = args
            return fragment
        }

    }

}