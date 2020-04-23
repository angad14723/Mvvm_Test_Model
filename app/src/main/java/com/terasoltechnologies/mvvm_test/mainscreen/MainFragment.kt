package com.terasoltechnologies.mvvm_test.mainscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.terasoltechnologies.mvvm_test.R
import com.terasoltechnologies.mvvm_test.databinding.FragmentMainBinding

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_main, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPagerAdapter = ViewPagerAdapter(childFragmentManager, 3)
        // view_pager.adapter = viewPagerAdapter

        binding.viewPager.adapter = viewPagerAdapter

        //tab_layout.setupWithViewPager(view_pager)

        binding.tabLayout.setupWithViewPager(binding.viewPager)


    }

}
