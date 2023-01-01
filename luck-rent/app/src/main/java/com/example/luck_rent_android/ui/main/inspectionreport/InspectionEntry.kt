package com.example.luck_rent_android.ui.main.inspectionreport

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityInspectionEntryBinding
import com.example.luck_rent_android.ui.main.adds.adapter.RequestAdapter
import com.example.luck_rent_android.ui.main.adds.model.RequestModel
import com.example.luck_rent_android.ui.main.auth.Login
import com.example.luck_rent_android.ui.main.expense.adapter.ImageUploadAdapter
import com.example.luck_rent_android.ui.main.inspectionreport.adapter.EntryAdapter
import com.example.luck_rent_android.ui.main.inspectionreport.adapter.InspectionImageAdapter
import com.example.luck_rent_android.ui.main.inspectionreport.model.EntryModel
import com.example.luck_rent_android.ui.main.property.FileUtilityClass
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.FilePickerConst
import java.io.File

class InspectionEntry : BaseActivity() {
    private var binding: ActivityInspectionEntryBinding? = null
    val list = ArrayList<EntryModel>()
    private var mAdapter: EntryAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var adapter: RecyclerView.Adapter<EntryAdapter.EntryAdapterVH>? = null

    var mImageAdapter: InspectionImageAdapter? = null
    val IMAGE_REQUEST_CODE = 2212
    val IMAGE_REQUEST_CODE1 = 2213
    val mImageData = java.util.ArrayList<File>()
    var position: Int? = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inspection_entry)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))

        SetTopBar()
        SetRecycler()
        SetDummyData()
//        setImageAdapter()

        binding?.topBar?.ivAdd?.setOnClickListener {
            onBackPressed()
        }
        binding?.btnNext?.setOnClickListener {
            val intent = Intent(this, Tenancy::class.java)
            startActivity(intent)
        }
        binding?.btnAdditionalArea?.setOnClickListener {
            openDialog()
        }


    }

    private fun SetTopBar() {
        binding?.topBar?.rlSearch?.gone()
        binding?.topBar?.rlCounter?.gone()
        binding?.topBar?.ivAdd?.setImageResource(R.drawable.ic_back)
        binding?.topBar?.ivMenu?.setImageResource(R.drawable.ic_resource_true)
        binding?.topBar?.tvText?.text = "Inspection Report"
    }


    private fun SetRecycler() {
        mAdapter = EntryAdapter(this, list, false,{position ->
            FilePickerBuilder.instance
                .setMaxCount(5)
                .setActivityTheme(R.style.LibAppTheme) //optional
                .pickPhoto(this, IMAGE_REQUEST_CODE)
        }) {position,second ->
            FilePickerBuilder.instance
                .setMaxCount(5)
                .setActivityTheme(R.style.LibAppTheme) //optional
                .pickPhoto(this, IMAGE_REQUEST_CODE1)
        }
        binding?.recycler?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding?.recycler?.adapter = mAdapter
        mAdapter?.notifyDataSetChanged()
    }

    private fun SetDummyData() {
        list.add(
            EntryModel("Exterior")
        )
        list.add(
            EntryModel("Entrance")
        )
        list.add(
            EntryModel("Living Room")
        )
        list.add(
            EntryModel("Kitchen")
        )
        list.add(
            EntryModel("Dining Room")
        )
        list.add(
            EntryModel("Bathroom")
        )
        list.add(
            EntryModel("Bedroom")
        )
        list.add(
            EntryModel("Stairs")
        )
        list.add(
            EntryModel("Hallway")
        )
    }

    override fun onSetupViewGroup() {
        mViewGroup = binding?.content
    }

    override fun setupContentViewWithBinding() {

    }

    override fun onRecycleBeforeDestroy() {

    }

    override fun onBecameInvisibleToUser() {

    }

    override fun onBecameVisibleToUser() {

    }

    override fun setupLoader() {

    }

    private fun openDialog() {
        val view = View.inflate(this, R.layout.dialog_additional_area, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()


        //Register Done Button & Cancel Icon from Dialog
        var btnAdd = view?.findViewById<Button>(R.id.btnAdd)
        var cancelIcon = view?.findViewById<ImageView>(R.id.cancelIcon)
        var etAreaName = view?.findViewById<AppCompatEditText>(R.id.etAreaName)

        //Dialog Done Button
        btnAdd?.setOnClickListener {

            var areaName = etAreaName?.text.toString()
            if (areaName?.isEmpty()) {
                etAreaName?.error = "Required"
            } else {
                list.add(EntryModel(areaName))
                binding?.recycler?.adapter = mAdapter
                dialog.dismiss()
                Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
                mAdapter?.notifyDataSetChanged()
            }

        }
        //Dialog Cancel Icon
        cancelIcon?.setOnClickListener {
            dialog.dismiss()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && data != null) {
            val array = java.util.ArrayList<Uri>()
            val finalArray = ArrayList<File>()
            data.getParcelableArrayListExtra<Uri>(FilePickerConst.KEY_SELECTED_MEDIA)?.let {
                array.addAll(
                    it
                )
            }
            AsyncTask.THREAD_POOL_EXECUTOR.execute {
                array.forEachIndexed { index, uri ->
                    val file =
                        FileUtilityClass.getFileFromUri(
                            this,
                            uri
                        )
                    finalArray.add(file!!)
//                    list[position ?:0].documentModel?.add(file)
                    list[position!!].documentModel?.add(file)
                }
                this.runOnUiThread {
                    mAdapter?.notifyDataSetChanged()
                }
            }
            Log.d("ProfileTAG", array.toString())
        }

        if (requestCode == IMAGE_REQUEST_CODE1 && data != null){
            val array1 = java.util.ArrayList<Uri>()
            val finalArray1 = ArrayList<File>()
            data.getParcelableArrayListExtra<Uri>(FilePickerConst.KEY_SELECTED_MEDIA)?.let {
                array1.addAll(
                    it
                )
            }
            AsyncTask.THREAD_POOL_EXECUTOR.execute {
                array1.forEachIndexed { index, uri ->
                    val file1 =
                        FileUtilityClass.getFileFromUri(
                            this,
                            uri
                        )
                    finalArray1.add(file1!!)
//                    list[position ?:0].documentModel?.add(file)
                    list[position!!].endPhotoslist?.add(file1)
                }
                this.runOnUiThread {
//                    Log.d("getSize", "onActivityResult: " + list[position ?:0].documentModel?.size)
                    mAdapter?.notifyDataSetChanged()
                }

            }
        }
    }
}