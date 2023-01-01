package com.example.luck_rent_android.ui.main.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityOpenCameraBinding
import com.kodextech.project.kodexlib.base.BaseActivity

class OpenCamera : BaseActivity() {
    private var binding : ActivityOpenCameraBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_open_camera)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))

        binding?.ivCancel?.setOnClickListener{
            onBackPressed()
        }

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
}