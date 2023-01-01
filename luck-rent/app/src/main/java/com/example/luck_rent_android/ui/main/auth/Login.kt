package com.example.luck_rent_android.ui.main.auth

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Patterns
import androidx.databinding.DataBindingUtil
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityLoginBinding
import com.example.luck_rent_android.ui.main.contractormodule.ContractorDashboard
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.description.Description
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.kodextech.project.kodexlib.base.BaseActivity

class Login : BaseActivity() {

    private var binding: ActivityLoginBinding? = null
    var email: String? = null
    var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        super.onCreate(savedInstanceState)

        //Forget Password Text View
        binding?.forgetPasswordTV?.setOnClickListener {
            val intent = Intent(this, ForgetPassword::class.java)
            startActivity(intent)
            finish()
        }

        //Login Button
        binding?.loginBtn?.setOnClickListener {

            checkLoginValidation()

        }

    }
    private fun checkLoginValidation(){
     email = binding?.etEmail?.text.toString().trim()
     password = binding?.etPassword?.text.toString().trim()
        if (email.isNullOrEmpty()){
            binding?.etEmail?.error = "Required"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding?.etEmail?.text.toString())
                .matches()
        ){
            binding?.etEmail?.error = "Invalid Format"
        }else if (password.isNullOrEmpty()){
            binding?.etPassword?.error = "Required"
        }else{
            callLogin()
        }
    }

    private fun callLogin(){
        val intent = Intent(this, Description::class.java)
        startActivity(intent)
        finish()
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