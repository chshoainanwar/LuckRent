package com.example.luck_rent_android.ui.main.subscription

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.*
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivitySubscriptionBinding
import com.example.luck_rent_android.ui.main.auth.Login
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.profile.Profile.Companion.roleProfile
import com.kodextech.project.kodexlib.base.BaseActivity
import java.util.concurrent.Flow


class SubscriptionActivity : BaseActivity() {
    var binding: ActivitySubscriptionBinding? = null

    companion object {
        var subscription: String? = "Property Manager"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_subscription)
        super.onCreate(savedInstanceState)


        if (roleProfile == "Landlord") {
            binding?.tvTitle?.text = "Landlord"
            binding?.ivImage?.setImageResource(R.drawable.ic_landlord)

        }else if (roleProfile == "Property Manager"){
            binding?.tvTitle?.text = "Property \nManager"
            binding?.ivImage?.setImageResource(R.drawable.ic_property_manager)
        }
        makeTopBottomTransparent()

        binding?.spinner?.setItems("1 Units", "2 Units", "3 Units", "4 Units")


        binding?.button1?.setOnClickListener {
            openDialog()
        }

    }

    private fun openDialog() {
        val view = View.inflate(this, R.layout.subscription_dialog, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        var cancelIcon = view?.findViewById<ImageView>(R.id.cancelIconSub)
        cancelIcon?.setOnClickListener {
            dialog.dismiss()

            if (roleProfile == "Landlord"){
                val intent = Intent(this, Dashboard::class.java)
                startActivity(intent)
                finish()

            }else if (roleProfile == "Property Manager"){
                val intent = Intent(this, Dashboard::class.java)
                startActivity(intent)
                finish()
            }


        }
        dialog.show()

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