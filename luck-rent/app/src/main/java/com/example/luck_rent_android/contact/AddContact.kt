package com.example.luck_rent_android.contact

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityAddContactBinding
import com.example.luck_rent_android.ui.main.property.TenantContact
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone

class AddContact : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var binding: ActivityAddContactBinding? = null

    private var mDataSideItem = ArrayList<SideItems>()
    private var mTopDataSideItem = ArrayList<SideItems>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_contact)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))

        setTopBar()
        sideMenuClicks()

        binding?.btnTenant?.setOnClickListener {
            val intent = Intent(this, TenantContact::class.java)
            intent.putExtra("Tenant","TenantContact")
            startActivity(intent)
        }
        binding?.btnTenantEmergency?.setOnClickListener {
            val intent = Intent(this, TenantContact::class.java)
            intent.putExtra("Tenant","EmergencyContact")
            startActivity(intent)
        }
        binding?.btnManager?.setOnClickListener {
            val intent = Intent(this, TenantContact::class.java)
            intent.putExtra("Tenant","ManagerContact")
            startActivity(intent)
        }
        binding?.btnCareTaker?.setOnClickListener {
            val intent = Intent(this, TenantContact::class.java)
            intent.putExtra("Tenant","CareTakerContact")
            startActivity(intent)
        }
        binding?.btnContractor?.setOnClickListener {
            val intent = Intent(this, TenantContact::class.java)
            intent.putExtra("Tenant","ContractorContact")
            startActivity(intent)
        }
        binding?.btnOther?.setOnClickListener {
            val intent = Intent(this, TenantContact::class.java)
            intent.putExtra("Tenant","OtherContact")
            startActivity(intent)
        }

    }

    private fun setTopBar() {
        binding?.topBar?.tvText?.text = "Add Contact"
        binding?.topBar?.ivAdd?.setImageResource(R.drawable.ic_back)
        binding?.topBar?.rlSearch?.gone()
    }

    private fun sideMenuClicks() {
        binding?.topBar?.ivAdd?.setOnClickListener { onBackPressed() }

        binding?.topBar?.ivMenu?.setOnClickListener {
            sideMenuDrawer(
                binding?.dashboardDrawerLayout,
                mDataSideItem,
                binding?.dashboardNavMenuLayout,
                binding?.dashboardNavView,
                this,
                mTopDataSideItem
            )

        }
        binding?.dashboardDrawerLayout?.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {

                sideMenuDrawer(
                    binding?.dashboardDrawerLayout,
                    mDataSideItem,
                    binding?.dashboardNavMenuLayout,
                    binding?.dashboardNavView,
                    this@AddContact,
                    mTopDataSideItem,
                    true
                )


            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerStateChanged(newState: Int) {

                Log.d("getState", "onDrawerStateChanged: " + newState)
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding?.dashboardDrawerLayout?.closeDrawer(GravityCompat.START)
        return true
    }
}