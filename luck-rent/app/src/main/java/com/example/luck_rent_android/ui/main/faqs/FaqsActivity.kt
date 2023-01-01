package com.example.luck_rent_android.ui.main.faqs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityFaqsBinding
import com.kodextech.project.kodexlib.base.BaseActivity

class FaqsActivity : BaseActivity() {

    private var binding: ActivityFaqsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_faqs)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))

        binding?.ivBack?.setOnClickListener { onBackPressed() }

    }

    override fun onSetupViewGroup() {
        mViewGroup = binding?.contentFaqs
        makeTopBottomTransparent()
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