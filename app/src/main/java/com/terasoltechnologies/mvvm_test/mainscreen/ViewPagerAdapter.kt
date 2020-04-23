package com.terasoltechnologies.mvvm_test.mainscreen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.terasoltechnologies.mvvm_test.roomfragment.RoomFragment
import com.terasoltechnologies.mvvm_test.networkdfragment.HomeFragment
import com.terasoltechnologies.mvvm_test.paging.ui.PagingMainFragment

class ViewPagerAdapter(fm: FragmentManager, var tabCount: Int) :
    FragmentPagerAdapter(fm, tabCount) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                HomeFragment()
            }
            1 -> {
                RoomFragment()
            }
            else -> PagingMainFragment()
        }
    }

    override fun getCount(): Int {
        return tabCount
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> {
                "Network Data"
            }
            1 -> {
                "Room Database"
            }
            else ->
                "By Paging"

        }

    }
}