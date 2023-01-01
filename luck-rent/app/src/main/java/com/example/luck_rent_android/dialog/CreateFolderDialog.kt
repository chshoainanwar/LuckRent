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
import com.example.luck_rent_android.databinding.AddDocumentDialogBinding
import com.example.luck_rent_android.databinding.CreateFolderDialogBinding
import com.example.luck_rent_android.ui.main.expense.adapter.ImageUploadAdapter
import com.example.luck_rent_android.ui.main.property.adapter.FolderAdapter
import com.example.luck_rent_android.ui.main.property.model.DocumentModel
import com.example.luck_rent_android.ui.main.property.model.FolderModel
import com.kodextech.project.kodexlib.base.BaseDialogueFragment
import java.io.File

class CreateFolderDialog : BaseDialogueFragment() {

    private var binding: CreateFolderDialogBinding? = null
    private var mFolderAdapter: FolderAdapter? = null
    private var onClicksFolderCallBack: ((ArrayList<FolderModel>) -> Unit)? = null
    private val IMAGE_REQUEST_CODE = 2212


    override fun onSetupArguments() {

    }

    override fun onBindItemListenerOrViewVariables() {

    }

    override fun setupContentViewWithBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.create_folder_dialog, container, false)
        isCancelable = false



        binding?.btnCreateFolder?.setOnClickListener {
            val name: String = binding?.etFolderName?.text.toString()

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
            onClicksCallBack: ((label: String) -> Unit)? = null
        ): CreateFolderDialog {
            val args = Bundle()
//            args.putString("TITLE", tvLabel)
//            args.putString("isFor", isFor)
//            args.putString("WORKERUUID", WORKERUUID)
            val fragment = CreateFolderDialog()
//            fragment.onClicksCallBack = onClicksCallBack
            fragment.arguments = args
            return fragment
        }

    }

}