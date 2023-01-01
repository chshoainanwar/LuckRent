package com.example.luck_rent_android.ui.main.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityForgetPasswordBinding
import com.example.luck_rent_android.databinding.ActivityLoginBinding
import com.kodextech.project.kodexlib.base.BaseActivity

class ForgetPassword : BaseActivity() {
    private var binding: ActivityForgetPasswordBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_forget_password)
        super.onCreate(savedInstanceState)

        //Send Code Button
        binding?.sendCodeBtn?.setOnClickListener{
            val intent = Intent(this, OtpCode::class.java)
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