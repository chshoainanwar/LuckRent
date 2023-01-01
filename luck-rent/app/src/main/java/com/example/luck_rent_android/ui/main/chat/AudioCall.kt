package com.example.luck_rent_android.ui.main.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityAudioCallBinding
import com.kodextech.project.kodexlib.base.BaseActivity

class AudioCall : BaseActivity() {
    private var binding: ActivityAudioCallBinding? = null
    private var boolean: Boolean? = false

    override fun onSetupViewGroup() {
        mViewGroup = binding?.content
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_audio_call)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.orange))

        binding?.ivCancel?.setOnClickListener {
            onBackPressed()
        }

        binding?.ivMic?.setOnClickListener {
            if (boolean == true) {
                boolean = false
                binding?.ivMic?.setImageResource(R.drawable.ic_mic1)
            } else {
                boolean = true
                binding?.ivMic?.setColorFilter(getColor(R.color.Lightgrey))

            }
        }

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