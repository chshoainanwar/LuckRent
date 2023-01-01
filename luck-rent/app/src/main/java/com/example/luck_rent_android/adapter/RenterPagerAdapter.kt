package com.example.luck_rent_android.adapter

import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter.POSITION_NONE
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kodextech.project.kodexlib.base.BaseActivity

class RenterPagerAdapter(
    val items: ArrayList<Fragment> = ArrayList(),
    activity: BaseActivity
) : FragmentStateAdapter(activity.supportFragmentManager, activity.lifecycle) {
    override fun getItemCount(): Int {

        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        return items[position]
    }


}