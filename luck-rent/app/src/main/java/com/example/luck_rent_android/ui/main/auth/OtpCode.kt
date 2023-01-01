package com.example.luck_rent_android.ui.main.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityOtpCodeBinding
import com.example.luck_rent_android.databinding.ActivitySplashBinding
import com.kodextech.project.kodexlib.base.BaseActivity

class OtpCode : BaseActivity() {
    private var binding: ActivityOtpCodeBinding? = null
   override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_otp_code)
        super.onCreate(savedInstanceState)

        binding?.otpTV?.setOnClickListener{
            val intent = Intent(this, NewPassword::class.java)
            startActivity(intent)
            finish()
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