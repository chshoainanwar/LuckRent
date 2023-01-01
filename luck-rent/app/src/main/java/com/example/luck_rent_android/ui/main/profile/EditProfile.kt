package com.example.luck_rent_android.ui.main.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.databinding.DataBindingUtil
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityEditProfileBinding
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.kodextech.project.kodexlib.base.BaseActivity
import java.io.IOException

class EditProfile : BaseActivity() {
    private var binding: ActivityEditProfileBinding? = null
    private val PICK_IMAGE = 222
    var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        statusBarColor(getColor(R.color.darkBlue))
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)
        super.onCreate(savedInstanceState)

        binding?.ivBack?.setOnClickListener {
           onBackPressed()
        }

        setSpinnerValue()
        pickImage()

    }

    private fun pickImage(){
        binding?.ivCamera?.setOnClickListener{
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), PICK_IMAGE)
        }
    }

    private fun setSpinnerValue() {
        binding?.spLanguage?.setItems(
            "English",
            "Chinese"
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data!!.data != null) {
            imageUri = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                binding?.ivProfile?.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}