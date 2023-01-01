package com.example.luck_rent_android.ui.main.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityOfflineBinding
import com.kodextech.project.kodexlib.base.BaseActivity

class Offline : BaseActivity() {
    private var binding: ActivityOfflineBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_offline)
        super.onCreate(savedInstanceState)

        binding?.easyToggle?.setOnToggledListener {


        }

        binding?.nextBtn?.setOnClickListener{
//            val intent = Intent(this,Subs::class.java)
//            startActivity(intent)
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