package com.example.luck_rent_android.ui.main.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.AcitivitySignupBinding
import com.kodextech.project.kodexlib.base.BaseActivity

class SignupActivity : BaseActivity() {
    private var binding: AcitivitySignupBinding? = null
    private var checkTerms: Boolean? = false

    private var firstName: String? = null
    private var lastName: String? = null
    private var phone: String? = null
    private var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.acitivity_signup)
        super.onCreate(savedInstanceState)

        binding?.btnSignUp?.setOnClickListener {
            checkSignUpValidations()
        }
        binding?.checkBox?.setOnCheckedChangeListener { compoundButton, b ->
            checkTerms = b
        }
    }

    private fun checkSignUpValidations() {
        firstName = binding?.etFirstName?.text.toString()
        lastName = binding?.etLastName?.text.toString()
        phone = binding?.etPhone?.text.toString()
        email = binding?.etEmail?.text.toString()

        if (firstName.isNullOrEmpty()) {
            showToast("First Name Required")
        } else if (lastName.isNullOrEmpty()) {
            showToast("Last Name Required")
        } else if (phone.isNullOrEmpty()) {
            showToast("Phone Number Required")
        } else if (phone!!.length != 11) {
            showToast("Invalid Number")
        } else if (email.isNullOrEmpty()) {
            showToast("Email Required")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding?.etEmail?.text.toString())
                .matches()
        ) {
            showToast("Invalid Format")
        } else if (checkTerms == false) {
            showToast("Please Accept LuckRent Terms And Condition")
        } else {
            callSignUp()

        }

    }
    private fun callSignUp(){
        val intent = Intent(this, Language::class.java)
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