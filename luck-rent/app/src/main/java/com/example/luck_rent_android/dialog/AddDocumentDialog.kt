package com.example.luck_rent_android.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Point
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.AddDocumentDialogBinding
import com.example.luck_rent_android.ui.main.expense.adapter.ImageUploadAdapter
import com.example.luck_rent_android.ui.main.property.FileUtilityClass
import com.example.luck_rent_android.ui.main.property.model.DocumentModel
import com.kodextech.project.kodexlib.base.BaseDialogueFragment
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.FilePickerConst
import java.io.File

class AddDocumentDialog : BaseDialogueFragment() {

    private var binding: AddDocumentDialogBinding? = null
    private var onClicksCallBack: ((ArrayList<DocumentModel>) -> Unit)? = null

    private var mImageAdapter: ImageUploadAdapter? = null
    private val IMAGE_REQUEST_CODE = 2212
    val mData = ArrayList<File>()
    val list = ArrayList<DocumentModel>()

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

        binding = DataBindingUtil.inflate(inflater, R.layout.add_document_dialog, container, false)
        isCancelable = false

        setImageAdapter()

        binding?.btnAddNow?.setOnClickListener {
            val name: String = binding?.etName?.text.toString()
            val category: String = binding?.spCategory?.text.toString()
            if (name?.isNullOrEmpty()) {
                binding?.etName?.error = "Required"
            } else if (category?.isNullOrEmpty()) {
                binding?.spCategory?.error = "Required"
            } else if (mData.isNullOrEmpty()) {
                Toast.makeText(context, "Kindly Choose File", Toast.LENGTH_SHORT).show()
            }else{
                list.add(DocumentModel(name, category, mData))
                onClicksCallBack?.invoke(list)
                dialog?.dismiss()
            }



        }
        binding?.ivCross?.setOnClickListener { dismiss() }
        binding?.spCategory?.setItems(
            "Application",
            "Credit Check",
            "Notices",
            "Inspection Report",
            "Legal",
            "Receipt",
            "Other"
        )

        return binding?.root!!

    }

    private fun setImageAdapter() {
        mImageAdapter =
            ImageUploadAdapter(mActivity, mData) { position, forDelete ->
                if (forDelete) {
                    if (position == null) {
                        mActivity.showToast("System Having Some Issue")
                    } else {
//                        deleteImage(position)
                    }
                } else {
                    FilePickerBuilder.instance
                        .setMaxCount(5)
                        .setActivityTheme(R.style.LibAppTheme) //optional
                        .pickFile(this, IMAGE_REQUEST_CODE)


                }
                mImageAdapter?.notifyDataSetChanged()
            }

        binding?.rvPhotos?.layoutManager =
            GridLayoutManager(mActivity, 3)
        binding?.rvPhotos?.adapter = mImageAdapter
        mImageAdapter?.notifyDataSetChanged()

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

            onClicksCallBack: ((ArrayList<DocumentModel>) -> Unit)? = null
        ): AddDocumentDialog {
            val args = Bundle()
//
            val fragment = AddDocumentDialog()
            fragment.onClicksCallBack = onClicksCallBack
            fragment.arguments = args
            return fragment
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && data != null) {
            val array = ArrayList<Uri>()
            data.getParcelableArrayListExtra<Uri>(FilePickerConst.KEY_SELECTED_DOCS)?.let {
                array.addAll(
                    it
                )
            }
            AsyncTask.THREAD_POOL_EXECUTOR.execute {
                array.forEachIndexed { index, uri ->
                    val file =
                        FileUtilityClass.getFileFromUri(
                            mActivity,
                            uri
                        )
                    mData.add(file!!)
                }

                mActivity.runOnUiThread {
                    mImageAdapter?.notifyDataSetChanged()

                }

            }
//            profile_image_selected?.addAll(array)
            Log.d("ProfileTAG", array.toString())
        }
    }
}