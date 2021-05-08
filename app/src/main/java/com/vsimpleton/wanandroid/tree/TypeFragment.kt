package com.vsimpleton.wanandroid.tree

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.vsimpleton.wanandroid.R
import com.vsimpleton.wanandroid.base.BaseFragment
import com.vsimpleton.wanandroid.databinding.FragmentTypeBinding
import com.vsimpleton.wanandroid.databinding.LayoutSystemTabBinding
import com.vsimpleton.wanandroid.view.adapter.MainVpAdapter

class TypeFragment : BaseFragment<FragmentTypeBinding>() {

    private var mTabViews: ArrayList<View> = ArrayList()

    companion object {
        fun newInstance() = TypeFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTabLayout()
    }

    private fun initTabLayout() {
        addTabView("体系")
        addTabView("导航")

        mBinding.viewPager.adapter = MainVpAdapter(
            requireActivity(),
            listOf(SystemFragment.newInstance(PAGE_TREE), SystemFragment.newInstance(PAGE_NAVIGATION))
        )

        val mediator = TabLayoutMediator(
            mBinding.tabLayout, mBinding.viewPager
        ) { tab, position -> tab.customView = mTabViews[position] }

        mBinding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                changeState(position)
            }
        })

        mediator.attach()

    }

    private fun changeState(position: Int) {
        for (i in 0 until mTabViews.size) {
            val textView = mTabViews[i].findViewById<TextView>(R.id.tvTab)
            if (position == i) {
                textView.textSize = 16f
                textView.isSelected = true
            } else {
                textView.isSelected = false
                textView.textSize = 14f
            }
        }
    }

    private fun addTabView(title: String) {
        val mBinding = LayoutSystemTabBinding.inflate(layoutInflater, null, false)
        mBinding.tvTab.text = title
        mTabViews.add(mBinding.root)
    }

}