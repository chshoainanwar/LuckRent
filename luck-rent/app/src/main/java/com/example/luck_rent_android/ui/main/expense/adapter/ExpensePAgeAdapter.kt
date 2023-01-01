package com.example.luck_rent_android.ui.main.expense.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kodextech.project.kodexlib.base.BaseActivity

class ExpensePAgeAdapter(
    val items: ArrayList<Fragment>,
    activity: BaseActivity
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        return items[position]
    }
}