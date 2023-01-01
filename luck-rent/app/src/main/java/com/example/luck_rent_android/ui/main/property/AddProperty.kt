package com.example.luck_rent_android.ui.main.property

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.icu.lang.UCharacter
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityAddPropertyBinding
import com.example.luck_rent_android.dialog.ADDOPTION
import com.example.luck_rent_android.dialog.AddSizeDialog
import com.example.luck_rent_android.model.MediaModel
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.expense.adapter.ImageUploadAdapter
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.profile.Profile.Companion.roleProfile
import com.example.luck_rent_android.ui.main.property.adapter.AddPropertySizeAdapter
import com.example.luck_rent_android.ui.main.property.model.BuildingModel
import com.example.luck_rent_android.ui.main.property.model.UnitSizeModel
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.example.luck_rent_android.ui.main.sideMenu.SideMenuAdapter
import com.google.android.material.navigation.NavigationView
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.FilePickerConst
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.Serializable
import java.lang.IllegalStateException

class AddProperty : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var binding: ActivityAddPropertyBinding? = null

    private var mImageAdapter: ImageUploadAdapter? = null
    private val IMAGE_REQUEST_CODE = 2212
    var profile_image_selected: ArrayList<Uri>? = null
    private var mMediaData = ArrayList<MediaModel>()

    private var mDataSideItem = ArrayList<SideItems>()
    private var mTopDataSideItem = ArrayList<SideItems>()
    private var mSideAdapter: SideMenuAdapter? = null

    private var mPropertySizeAdapter: AddPropertySizeAdapter? = null
    private var sizeArary = ArrayList<String>()

    companion object {
         var mlist = ArrayList<UnitSizeModel>()
        val scrollImageList = ArrayList<MediaModel>()
        val mData = ArrayList<File>()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        statusBarColor(getColor(R.color.darkBlue))
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_property)
        super.onCreate(savedInstanceState)

        initTopBar()
        checkPermissions()
        setImageAdapter()
        setSizeAdapter()
        setDummyData()

        when (roleProfile) {
            "Landlord" -> {
//                binding?.tvPropertyManagerLabel?.text = "Add Or Select Property Manager"
//                binding?.spPropertyManager?.setItems("Alex Dover", "James", "Robert")
            }
            "Property Manager" -> {
//                binding?.tvPropertyManagerLabel?.text = "Add Or Select Owner"
//                binding?.spPropertyManager?.setItems("Alex Dover", "James", "Robert")
            }
        }

        binding?.spAddress?.setItems(
            "33 Street US",
            "34 Street US"
        )

        binding?.topBar?.ivAdd?.setOnClickListener { onBackPressed() }

        binding?.dashboardNavMenuLayout?.ivCross?.setOnClickListener {
            when (roleProfile) {
                "Landlord" -> {
                    val intent = Intent(this, Dashboard::class.java)
                    startActivity(intent)
                }
                "Property Manager" -> {
                    val intent = Intent(this, Dashboard::class.java)
                    startActivity(intent)
                }
                "Contractor" -> {
                    val intent = Intent(this, ContractorDashboard::class.java)
                    startActivity(intent)
                }
                "Renter" -> {
                    val intent = Intent(this, RenterDashboard::class.java)
                    startActivity(intent)
                }
            }
        }

        binding?.dashboardNavMenuLayout?.clickaBleNav?.setOnClickListener{
            when (roleProfile) {
                "Landlord" -> {
                    val intent = Intent(this, Dashboard::class.java)
                    startActivity(intent)
                }
                "Property Manager" -> {
                    val intent = Intent(this, Dashboard::class.java)
                    startActivity(intent)
                }
                "Contractor" -> {
                    val intent = Intent(this, ContractorDashboard::class.java)
                    startActivity(intent)
                }
                "Renter" -> {
                    val intent = Intent(this, RenterDashboard::class.java)
                    startActivity(intent)
                }
            }

        }
        binding?.dashboardNavMenuLayout?.rlProfilePic?.setOnClickListener{

        }
        binding?.dashboardNavMenuLayout?.tvName?.setOnClickListener{

        }

        binding?.topBar?.ivMenu?.setOnClickListener {
            sideMenuDrawer(
                binding?.dashboardDrawerLayout,
                mDataSideItem,
                binding?.dashboardNavMenuLayout,
                binding?.dashboardNavView,
                this@AddProperty,
                mTopDataSideItem
            )

        }

        binding?.contentAddProperty?.setOnClickListener {
            try {
                if (binding?.dashboardDrawerLayout?.isDrawerOpen(GravityCompat.END) == true) {
                    binding?.dashboardDrawerLayout?.closeDrawer(GravityCompat.END)
                }
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }

        }

        binding?.dashboardDrawerLayout?.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {
                sideMenuDrawer(
                    binding?.dashboardDrawerLayout,
                    mDataSideItem,
                    binding?.dashboardNavMenuLayout,
                    binding?.dashboardNavView,
                    this@AddProperty,
                    mTopDataSideItem,
                    true
                )

            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerStateChanged(newState: Int) {

                Log.d("getState", "onDrawerStateChanged: " + newState)
            }

        })

        binding?.btnSave?.setOnClickListener {
            val intent = Intent(this, UnitPreview::class.java)
            startActivity(intent)
            finish()
        }

        binding?.btnAdd?.setOnClickListener {
//            val dialog = AddSizeDialog.newInstance { addoption, sizeName ->
//                when (addoption) {
//                    ADDOPTION.SAVE -> {
////                        sizeArary.clear()
////                        sizeArary.add("01")
////                        sizeArary.add("02")
////                        sizeArary.add("03")
////                        sizeArary.add("04")
////                        sizeArary.add("05")
////                        sizeArary.add("06")
////                        sizeArary.add("07")
////                        sizeArary.add("08")
////                        sizeArary.add("09")
////                        sizeArary.add("10")
//                        mSizeData.add(SizeDummyModel(sizeName ?: "", sizeArary))
//                        binding?.rvUnits?.adapter = null
//                        binding?.rvUnits?.adapter = mPropertySizeAdapter
//                        mPropertySizeAdapter?.notifyDataSetChanged()
//                    }
//                }
//            }
//            dialog.show(supportFragmentManager, "")

openDialog()

        }


    }

    private fun setSizeAdapter() {
        mPropertySizeAdapter = AddPropertySizeAdapter(this, mlist){mItem , position ->
            val builder = AlertDialog.Builder(this)
            //set title for alert dialog
            builder.setTitle("Edit or Delete")
            //set message for alert dialog
            builder.setMessage("Update or Delete Item")
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            //performing positive action
            builder.setPositiveButton("Edit"){ _, which ->

                val view = View.inflate(this, R.layout.dialog_building, null)
                val builder = AlertDialog.Builder(this)
                builder.setView(view)
                builder.setCancelable(false)
                val dialog = builder.create()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.show()

                //Register Done Button & Cancel Icon from Dialog
                var doneBtn = view?.findViewById<Button>(R.id.doneBtn)
                var cancelIcon = view?.findViewById<ImageView>(R.id.cancelIcon)
                var etAmenity = view?.findViewById<AppCompatEditText>(R.id.etAmenity)

                etAmenity?.setText(mItem.name)

                //Dialog Done Button
                doneBtn?.setOnClickListener {
                    val getAmenity : String? = etAmenity?.text.toString()
                    if (getAmenity.isNullOrEmpty()){
                        etAmenity?.error = "Required"
                    }else{
                        mItem.name = getAmenity
                        mPropertySizeAdapter?.notifyDataSetChanged()
                        dialog.dismiss()
                    }
                }
                //Dialog Cancel Icon
                cancelIcon?.setOnClickListener {
                    dialog.dismiss()
                }

            }
            //performing cancel action
            builder.setNeutralButton("Cancel"){ _, which ->
                Toast.makeText(this,"clicked cancel\n operation cancel", Toast.LENGTH_LONG).show()
            }
            //performing negative action
            builder.setNegativeButton("Delete"){ _, which ->
                mData.removeAt(position)
                mPropertySizeAdapter?.notifyDataSetChanged()
                Toast.makeText(this,"Deleted", Toast.LENGTH_LONG).show()
            }
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()

        }
        binding?.rvUnits?.layoutManager = GridLayoutManager(applicationContext,2)
        binding?.rvUnits?.adapter = mPropertySizeAdapter
        mPropertySizeAdapter?.notifyDataSetChanged()

    }

    private fun openDialog() {
        val view = View.inflate(this, R.layout.dialog_building, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()


        //Register Done Button & Cancel Icon from Dialog
        var doneBtn = view?.findViewById<AppCompatButton>(R.id.doneBtn)
        var etAmenity = view?.findViewById<AppCompatEditText>(R.id.etAmenity)
        var cancelIcon = view?.findViewById<ImageView>(R.id.cancelIcon)

        //Dialog Done Button
        doneBtn?.setOnClickListener {

            val getAmenity : String? = etAmenity?.text.toString()
            if (getAmenity.isNullOrEmpty()){
                etAmenity?.setError("Required")
            }else{
                mlist.add(UnitSizeModel(getAmenity))
                mSideAdapter?.notifyDataSetChanged()
                dialog.dismiss()
            }

        }
        //Dialog Cancel Icon
        cancelIcon?.setOnClickListener {
            dialog.dismiss()
        }

    }

    private fun setDummyData(){
       mlist.add(
           UnitSizeModel(
           "Units"
       )
       )
        mlist.add(UnitSizeModel(
            "Kitchen"
        ))
        mlist.add(UnitSizeModel(
            "Garage"
        ))
        mlist.add(UnitSizeModel(
            "Parking"
        ))
        mlist.add(UnitSizeModel(
            "Wifi"
        ))
        mlist.add(UnitSizeModel(
            "Patio"
        ))
        mlist.add(UnitSizeModel(
            "Units"
        ))
        mlist.add(UnitSizeModel(
            "Furniture"
        ))
        mlist.add(UnitSizeModel(
            "Fireplace"
        ))
        mlist.add(UnitSizeModel(
            "Hardwood Floors"
        ))
        mlist.add(UnitSizeModel(
            "Dishwasher"
        ))
        mlist.add(UnitSizeModel(
            "Walk-in-Closets"
        ))
    }

    private fun checkPermissions() {
        Dexter.withContext(this@AddProperty)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {

                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    // Remember to invoke this method when the custom rationale is closed
                    // or just by default if you don't want to use any custom rationale.
                    token?.continuePermissionRequest()
                }
            })
            .withErrorListener {
            }
            .check()
    }

    private fun setImageAdapter() {
        mImageAdapter =
            ImageUploadAdapter(this, mData) { position, forDelete ->
                if (forDelete) {
                    if (position == null) {
                        showToast("System Having Some Issue")
                    } else {
                        deleteImage(position)
                    }
                } else {
                    FilePickerBuilder.instance
                        .setMaxCount(5)
                        .setActivityTheme(R.style.LibAppTheme) //optional
                        .pickPhoto(this, IMAGE_REQUEST_CODE)


                }
                mImageAdapter?.notifyDataSetChanged()
            }

        binding?.rvPropertyImages?.layoutManager =
            GridLayoutManager(this, 3)
        binding?.rvPropertyImages?.adapter = mImageAdapter
        mImageAdapter?.notifyDataSetChanged()

    }

    private fun deleteImage(position: Int) {
//        mMediaData.removeAt(position - 1)
        mData.removeAt(position - 1)
        mImageAdapter?.notifyDataSetChanged()
    }


    private fun initTopBar() {
        binding?.topBar?.rlSearch?.gone()
        binding?.topBar?.tvText?.text = "Add Unit"
        binding?.topBar?.rlCounter?.gone()
        binding?.topBar?.ivAdd?.setImageResource(R.drawable.ic_back)


    }

    override fun onSetupViewGroup() {
        mViewGroup = binding?.contentAddProperty
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && data != null) {
            val array = ArrayList<Uri>()
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
                    mData.add(file!!)
                }

                this.runOnUiThread {
                    mImageAdapter?.notifyDataSetChanged()

                }

            }
            profile_image_selected?.addAll(array)
            Log.d("ProfileTAG", array.toString())
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding?.dashboardDrawerLayout?.closeDrawer(GravityCompat.START)
        return true
    }
}


object FileUtilityClass {
    fun getFileFromUri(mContxt: Context, selectedImageUri: Uri): File? {
//        val selectedImageUri = result!![0].uri
        val parcelFileDescriptor =
            mContxt.contentResolver.openFileDescriptor(selectedImageUri, "r", null)
                ?: return null
        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(mContxt.cacheDir, mContxt.contentResolver.getFileName(selectedImageUri))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        return file
    }

}


fun ContentResolver.getFileName(fileUri: Uri): String {
    var name = ""
    val returnCursor = this.query(fileUri, null, null, null, null)
    if (returnCursor != null) {
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        name = returnCursor.getString(nameIndex)
        returnCursor.close()
    }
    return name
}

data class SizeDummyModel(
    var name: String? = null,
//    var sizeArary: ArrayList<String>? = ArrayList(),
    var selectedIndex: Int? = -1
) : Serializable
