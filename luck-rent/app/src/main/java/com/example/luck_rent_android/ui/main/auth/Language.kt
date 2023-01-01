package com.example.luck_rent_android.ui.main.auth

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityLanguageBinding
import com.example.luck_rent_android.ui.main.property.LeaseActivity
import com.example.luck_rent_android.ui.main.renter.Renter
import com.kodextech.project.kodexlib.base.BaseActivity
import java.util.concurrent.Flow

class Language : BaseActivity() {
    private var binding: ActivityLanguageBinding? = null
    var language : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_language)
        super.onCreate(savedInstanceState)

        binding?.nextBtn?.setOnClickListener {
            val intent = Intent(this@Language, LocationSelection::class.java)
            startActivity(intent)
            finish()
        }

        setSpinnerValue()

    }

    private fun setSpinnerValue() {
        binding?.spLanguage?.setItems(
            "English",
            "Chinese",
        )
        binding?.spLanguage?.setOnItemSelectedListener { view, position, id, item ->
            language = binding?.spLanguage?.text.toString()
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