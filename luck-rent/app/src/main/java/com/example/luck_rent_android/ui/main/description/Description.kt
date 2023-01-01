package com.example.luck_rent_android.ui.main.description

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityDescriptionBinding
import com.example.luck_rent_android.ui.main.profile.Profile
import com.kodextech.project.kodexlib.base.BaseActivity

class Description : BaseActivity() {
    private var binding : ActivityDescriptionBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_description)
        super.onCreate(savedInstanceState)

        binding?.btnNext?.setOnClickListener{
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
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