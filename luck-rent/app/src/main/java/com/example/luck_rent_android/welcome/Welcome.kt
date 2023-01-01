package com.example.luck_rent_android.welcome

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity.apply
import android.view.View
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.luck_rent_android.R
import com.example.luck_rent_android.adapter.PagerAdapter
import com.example.luck_rent_android.databinding.ActivityChooseBinding
import com.example.luck_rent_android.databinding.ActivityWelcomeBinding
import com.example.luck_rent_android.ui.main.auth.Choose
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import com.kodextech.project.kodexlib.utils.visible
import me.relex.circleindicator.CircleIndicator3

class Welcome : BaseActivity() {

    private var binding: ActivityWelcomeBinding? = null
    var adapter: RecyclerView.Adapter<PagerAdapter.ViewHolder>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        statusBarColor(getColor(R.color.smsp_transparent_color))
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome)
        super.onCreate(savedInstanceState)

        checkPermissionCall()

        adapter = PagerAdapter()
        binding?.viewPager?.adapter = adapter

        binding?.indicator?.setViewPager2(binding?.viewPager!!)

        binding?.finishIcon?.setOnClickListener {
            val intent = Intent(this, Choose::class.java)
            startActivity(intent)
        }

        binding?.nextIcon?.setOnClickListener {
            if (binding?.viewPager?.currentItem == 3) {
                val intent = Intent(this, Choose::class.java)
                startActivity(intent)
            } else {
                val currentPosition: Int = binding?.viewPager?.currentItem?.plus(1) ?: 0
                binding?.viewPager?.currentItem = currentPosition
            }
        }

        binding?.skipIcon?.setOnClickListener {
            val intent = Intent(this, Choose::class.java)
            startActivity(intent)
        }


        binding?.viewPager?.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
//                if (position == 0) {
//                    binding?.skipIcon?.gone()
//                } else {
//                    binding?.skipIcon?.visible()
//                }

                if (position == 3) {
                    binding?.nextIcon?.gone()
                    binding?.finishIcon?.visible()
                } else {
                    binding?.finishIcon?.gone()
                    binding?.nextIcon?.visible()
                }


            }
        })


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

    fun checkPermissionCall() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) { /* ... */


                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken?
                ) { /* ... */
                }
            }).check()
    }

}