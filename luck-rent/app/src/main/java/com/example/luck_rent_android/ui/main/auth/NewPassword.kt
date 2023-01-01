package com.example.luck_rent_android.ui.main.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.luck_rent_android.R
//import com.example.luck_rent_android.databinding.ActivityForgetPasswordBinding
import com.example.luck_rent_android.databinding.ActivityNewPasswordBinding
import com.kodextech.project.kodexlib.base.BaseActivity

class NewPassword : BaseActivity() {
    private var binding: ActivityNewPasswordBinding? = null
    private var isFor: String? = null
    var password: String? = null
    var confirmPass: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_password)
        super.onCreate(savedInstanceState)


        isFor = intent.getStringExtra("isFrom")

        if (isFor == "newPassword") {
            binding?.SetPasswordBtn?.text = "Save Password"
            binding?.SetPasswordBtn?.text = "Save Password"
            binding?.text2?.text =
                "You can make password strong using \ndifferent signs such as @ or !"
        } else {
            binding?.SetPasswordBtn?.text = "Set New Password"
        }

        binding?.SetPasswordBtn?.setOnClickListener {
            checkPasswordValidation()
        }
    }

    private fun checkPasswordValidation(){
        password = binding?.etPassword?.text.toString().trim()
        confirmPass = binding?.etConfirmPassword?.text.toString().trim()

        if (password.isNullOrEmpty()){
            binding?.etPassword?.error = "Required"
        }else if (confirmPass.isNullOrEmpty()){
            binding?.etConfirmPassword?.error = "Required"
        }else if (password != confirmPass){
            binding?.etConfirmPassword?.error = "Password Don't Matched"
        }else{
            if (isFor == "newPassword") {
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            } else {
                openDialog()
            }
        }

    }

    private fun openDialog() {
        val view = View.inflate(this, R.layout.new_password_dialog, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()


        //Register Done Button & Cancel Icon from Dialog
        var doneBtn = view?.findViewById<Button>(R.id.doneBtn)
        var cancelIcon = view?.findViewById<ImageView>(R.id.cancelIcon)

        //Dialog Done Button
        doneBtn?.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
        //Dialog Cancel Icon
        cancelIcon?.setOnClickListener {
            dialog.dismiss()
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