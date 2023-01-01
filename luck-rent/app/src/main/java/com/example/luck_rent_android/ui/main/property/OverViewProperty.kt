package com.example.luck_rent_android.ui.main.property

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.net.toUri
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.contact.AddContact
import com.example.luck_rent_android.databinding.ActivityOverViewPropertyBinding
import com.example.luck_rent_android.dialog.AddDocumentDialog
import com.example.luck_rent_android.dialog.CreateFolderDialog
import com.example.luck_rent_android.ui.main.adds.Details
import com.example.luck_rent_android.ui.main.adds.MakeAdd
import com.example.luck_rent_android.ui.main.auth.Login
import com.example.luck_rent_android.ui.main.chat.Chat
import com.example.luck_rent_android.ui.main.chat.Messages
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.expense.AddExpense
import com.example.luck_rent_android.ui.main.inspectionreport.InspectionReport
import com.example.luck_rent_android.ui.main.inventory.InventoryActivity
import com.example.luck_rent_android.ui.main.inventory.InventoryMain
import com.example.luck_rent_android.ui.main.invoice.AddInvoice
import com.example.luck_rent_android.ui.main.order.adapter.OrderAdapter
import com.example.luck_rent_android.ui.main.payment.AddPayment
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.profile.Profile.Companion.roleProfile
import com.example.luck_rent_android.ui.main.property.adapter.DocumentAdapter
import com.example.luck_rent_android.ui.main.property.adapter.FolderAdapter
import com.example.luck_rent_android.ui.main.property.adapter.PropertyImageListing
import com.example.luck_rent_android.ui.main.property.model.DocumentModel
import com.example.luck_rent_android.ui.main.property.model.FolderModel
import com.example.luck_rent_android.ui.main.renter.Renter
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.example.luck_rent_android.ui.main.task.TaskList
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import com.kodextech.project.kodexlib.utils.visible
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.FilePickerConst
import java.io.ByteArrayOutputStream
import java.io.File
import java.lang.IllegalStateException
import java.util.*

class OverViewProperty : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var binding: ActivityOverViewPropertyBinding? = null

    private var creditState: String? = ""
    private var leaseState: String? = ""
    private var recState: String? = ""
    private var folderState: String? = ""
    private var REQUEST_CODE = 111
    private var REQUEST_CODE1 = 122

    private var isAddLayout: Boolean? = false

    private var mDataSideItem = ArrayList<SideItems>()
    private var mTopDataSideItem = ArrayList<SideItems>()
    private var imgList = ArrayList<File>()
    private var imgAdapter: PropertyImageListing? = null

    var profile_image_selected: ArrayList<Uri>? = null
    private var adapter: DocumentAdapter? = null
    private var list = ArrayList<DocumentModel>()

    private var folderAdapter: FolderAdapter? = null
    private var folderList = ArrayList<FolderModel>()
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        statusBarColor(getColor(R.color.darkBlue))
        binding = DataBindingUtil.setContentView(this, R.layout.activity_over_view_property)
        super.onCreate(savedInstanceState)

        setTopBar()
        sideMenuClicks()
        setPhotoRecycler()

        binding?.topBar?.ivAdd?.setOnClickListener {
            val intent = Intent(this, PropertiesActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding?.tvTenantName?.setOnLongClickListener {
            val intent = Intent(this, Chat::class.java)
            startActivity(intent)
            return@setOnLongClickListener true
        }
        binding?.tvAddress?.setOnLongClickListener {
            val uri: String =
                java.lang.String.format(Locale.ENGLISH, "geo:%f,%f", 37.7749, -122.4194)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
            return@setOnLongClickListener true
        }



        when (roleProfile) {
            "Property Manager" -> {
                binding?.tvPropertyManagerLabel?.text = "Owner"
            }
        }

        binding?.btnAddContact?.setOnClickListener {

            if (roleProfile == "Landlord") {
                val intent = Intent(this, AddContact::class.java)
                startActivity(intent)
            } else if (roleProfile == "Property Manager") {
                val intent = Intent(this, AddContact::class.java)
                startActivity(intent)
            }
        }
        binding?.btnInventory?.setOnClickListener {

            if (roleProfile == "Landlord") {
                val intent = Intent(this, InventoryMain::class.java)
                startActivity(intent)
            } else if (roleProfile == "Property Manager") {
                val intent = Intent(this, InventoryMain::class.java)
                startActivity(intent)
            }
        }
        binding?.btnLease?.setOnClickListener {

            if (roleProfile == "Landlord") {
                val intent = Intent(this, LeaseActivity::class.java)
                startActivity(intent)
            } else if (roleProfile == "Property Manager") {
                val intent = Intent(this, LeaseActivity::class.java)
                startActivity(intent)
            }


        }
//        binding?.btnInspectionReport?.setOnClickListener {
//
//            if (roleProfile == "Landlord") {
//                val intent = Intent(this, InspectionReport::class.java)
//                startActivity(intent)
//                finish()
//            } else if (roleProfile == "Property Manager") {
//                val intent = Intent(this, InspectionReport::class.java)
//                startActivity(intent)
//                finish()
//            }
//
//
//        }

        binding?.btnWriteAnAdd?.setOnClickListener {
            val intent = Intent(this, MakeAdd::class.java)
            startActivity(intent)
        }

        binding?.topBar?.ivPropertyAdd?.setOnClickListener {

            if (isAddLayout == true) {
                isAddLayout = false
                binding?.rlAdds?.gone()
                binding?.btnPlus?.gone()

            } else {
                isAddLayout = true
                binding?.rlAdds?.visible()
                binding?.btnPlus?.visible()
            }
        }

        binding?.btnPlus?.setOnClickListener {

            if (isAddLayout == true) {
                binding?.rlAdds?.gone()
                binding?.btnPlus?.gone()
            } else {

            }
        }

        binding?.tvAddInvoice?.setOnClickListener {
            binding?.rlAdds?.gone()
            binding?.btnPlus?.gone()
            val intent = Intent(this, AddInvoice::class.java)
            startActivity(intent)
        }
        binding?.tvAddPayment?.setOnClickListener {
            binding?.rlAdds?.gone()
            binding?.btnPlus?.gone()
            val intent = Intent(this, AddPayment::class.java)
            startActivity(intent)
        }
        binding?.tvAddExpence?.setOnClickListener {
            binding?.rlAdds?.gone()
            binding?.btnPlus?.gone()
            val intent = Intent(this, AddExpense::class.java)
            startActivity(intent)
        }
        binding?.tvAddTask?.setOnClickListener {
            binding?.rlAdds?.gone()
            binding?.btnPlus?.gone()
            val intent = Intent(this, TaskList::class.java)
            startActivity(intent)
        }

        binding?.btnAddDocument?.setOnClickListener {
            val dialog = AddDocumentDialog.newInstance { listng ->
                list.addAll(listng)
                binding?.tvDocumentLabel?.visible()
                adapter = DocumentAdapter(this, list)
                binding?.rvDocument?.layoutManager = GridLayoutManager(this, 3)
                binding?.rvDocument?.adapter = adapter
                adapter?.notifyItemRangeInserted(0, list.count())
            }
            dialog.show(supportFragmentManager, "")
        }

        binding?.btnCreateFolder?.setOnClickListener {

            openFolderDialog()


//            val dialog = CreateFolderDialog.newInstance {folder ->
//
//                folderList.addAll(folder)
//                folderAdapter = FolderAdapter(this, folderList)
//                binding?.rvFolder?.layoutManager = GridLayoutManager(this,3)
//                binding?.rvFolder?.adapter = adapter
//                adapter?.notifyItemRangeInserted(0 , folderList.count())
//
//            }
//            dialog.show(supportFragmentManager, "")

        }

        binding?.btnAddPhoto?.setOnClickListener {
            addPhotoBottomSheetDialog()
        }

    }

    private fun addPhotoBottomSheetDialog() {
        val dialog = BottomSheetDialog(this, R.style.MyBottomSheetDialogTheme)
        val layoutDialog =
            LayoutInflater.from(this).inflate(R.layout.add_photo_dialog, null, false)
        dialog.setContentView(layoutDialog)
        dialog.show()

        var ivGallery = layoutDialog.findViewById<ImageView>(R.id.ivGallery);
        var ivCamera = layoutDialog.findViewById<ImageView>(R.id.ivCamera);

        ivGallery.setOnClickListener {
            openGalleryImages()
            dialog.dismiss()
        }
        ivCamera.setOnClickListener {
            capturePhoto()
            dialog.dismiss()
        }

    }

    private fun setPhotoRecycler() {
        imgAdapter = PropertyImageListing(this, imgList)
        binding?.rvPropertyImages?.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding?.rvPropertyImages?.adapter = adapter
        imgAdapter?.notifyDataSetChanged()

    }

    private fun openGalleryImages() {
        FilePickerBuilder.instance
            .setMaxCount(5)
            .setActivityTheme(R.style.LibAppTheme) //optional
            .pickPhoto(this, REQUEST_CODE)
    }

    fun capturePhoto() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CODE1)
    }

    private fun setTopBar() {
        binding?.topBar?.ivAdd?.setImageResource(R.drawable.ic_back)
        binding?.topBar?.ivPropertyAdd?.visible()
        binding?.topBar?.etSearch?.gone()
        binding?.topBar?.rlCounter?.gone()
        binding?.topBar?.tvPropertyAddress?.visible()
        binding?.topBar?.tvText?.text = ""
        binding?.topBar?.tvPropertyAddress?.text = "0312-545 \n New Colony DHA Phase 1"
    }

    private fun sideMenuClicks() {
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
        binding?.dashboardNavMenuLayout?.clickaBleNav?.setOnClickListener {
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
        binding?.dashboardNavMenuLayout?.rlProfilePic?.setOnClickListener {

        }
        binding?.dashboardNavMenuLayout?.tvName?.setOnClickListener {

        }
        binding?.topBar?.ivMenu?.setOnClickListener {

            sideMenuDrawer(
                binding?.dashboardDrawerLayout,
                mDataSideItem,
                binding?.dashboardNavMenuLayout,
                binding?.dashboardNavView,
                this,
                mTopDataSideItem
            )


        }
        binding?.contentOverViewProperty?.setOnClickListener {
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
                    this@OverViewProperty,
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
    }

    private fun openFolderDialog() {
        val view = View.inflate(this, R.layout.dialog_create_folder, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()


        //Register Done Button & Cancel Icon from Dialog
        var btnAdd = view?.findViewById<Button>(R.id.btnAdd)
        var cancelIcon = view?.findViewById<ImageView>(R.id.cancelIcon)
        var etFolderName = view?.findViewById<AppCompatEditText>(R.id.etFolderName)

        //Dialog Done Button
        btnAdd?.setOnClickListener {
            var getTaskName = etFolderName?.text.toString()
            if (getTaskName.isNullOrEmpty()) {
                etFolderName?.error = "Required"
            } else {
                binding?.rvFolder?.visible()
                binding?.tvFolderLabel?.visible()
                folderList.add(FolderModel(getTaskName))
                folderAdapter = FolderAdapter(this, folderList)
                binding?.rvFolder?.layoutManager = GridLayoutManager(this, 2)
                binding?.rvFolder?.adapter = folderAdapter
                folderAdapter?.notifyDataSetChanged()
                dialog.dismiss()
            }
        }
        //Dialog Cancel Icon
        cancelIcon?.setOnClickListener {
            dialog.dismiss()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE && data != null) {
            val array = ArrayList<Uri>()
            data.getParcelableArrayListExtra<Uri>(FilePickerConst.KEY_SELECTED_MEDIA)?.let {
                array.addAll(
                    it
                )
            }
            imgList.clear()
            array.forEachIndexed { index, uri ->
                val file =
                    FileUtilityClass.getFileFromUri(
                        this,
                        uri
                    )
                imgList.add(file!!)


            }
            runOnUiThread {
                imgAdapter?.notifyDataSetChanged()
            }

        } else if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE1 && data != null) {
            val getData = (data?.extras?.get("data") as Bitmap)
            val file =
                File(cacheDir, "cameraImage${UUID.randomUUID()}") //Get Access to a local file.

            file.delete() // Delete the File, just in Case, that there was still another File
            file.createNewFile()
            val fileOutputStream = file.outputStream()
            val byteArrayOutputStream = ByteArrayOutputStream()
            getData.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val bytearray = byteArrayOutputStream.toByteArray()
            fileOutputStream.write(bytearray)
            fileOutputStream.flush()
            fileOutputStream.close()
            byteArrayOutputStream.close()

            val URI = file.toUri()
            imgList.add(file)
            imgAdapter?.notifyDataSetChanged()
        }


    }

    override fun onSetupViewGroup() {
        mViewGroup = binding?.contentOverViewProperty

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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding?.dashboardDrawerLayout?.closeDrawer(GravityCompat.START)
        return true
    }


}


