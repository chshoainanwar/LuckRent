package com.example.luck_rent_android.ui.main.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityChooseBinding
import com.kodextech.project.kodexlib.base.BaseActivity

class Choose : BaseActivity() {
    private var binding : ActivityChooseBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = DataBindingUtil.setContentView(this,R.layout.activity_choose)
        super.onCreate(savedInstanceState)


        setListener()

    }

    private fun setListener() {
        binding?.existingAccountBtn?.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        binding?.createAccountBtn?.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
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