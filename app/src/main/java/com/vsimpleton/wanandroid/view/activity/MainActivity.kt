package com.vsimpleton.wanandroid.view.activity

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.gyf.immersionbar.ImmersionBar
import com.vsimpleton.wanandroid.R
import com.vsimpleton.wanandroid.base.BaseActivity
import com.vsimpleton.wanandroid.databinding.ActivityMainBinding
import com.vsimpleton.wanandroid.databinding.LayoutMainTabBinding
import com.vsimpleton.wanandroid.view.adapter.MainVpAdapter
import com.vsimpleton.wanandroid.view.fragment.AnswerFragment
import com.vsimpleton.wanandroid.view.fragment.ArticleFragment
import com.vsimpleton.wanandroid.view.fragment.MineFragment
import com.vsimpleton.wanandroid.view.fragment.TypeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private var mTabViews: ArrayList<View> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImmersionBar.with(this).titleBarMarginTop(mBinding.viewPager).statusBarDarkFont(true).init()
        initViewPager()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initViewPager() {
        addTabView("首页", resources.getDrawable(R.drawable.selector_tab_article))
        addTabView("问答", resources.getDrawable(R.drawable.selector_tab_answer))
        addTabView("体系", resources.getDrawable(R.drawable.selector_tab_type))
        addTabView("我的", resources.getDrawable(R.drawable.selector_tab_mine))

        mBinding.viewPager.adapter = MainVpAdapter(
            this,
            listOf(
                ArticleFragment.newInstance(),
                AnswerFragment.newInstance(),
                TypeFragment.newInstance(),
                MineFragment.newInstance()
            )
        )
        TabLayoutMediator(
            mBinding.tabLayout,
            mBinding.viewPager
        ) { tab, position -> tab.customView = mTabViews[position] }.attach()
    }

    private fun addTabView(title: String, tabIcon: Drawable) {
        val mBinding = LayoutMainTabBinding.inflate(layoutInflater, null, false)
        mBinding.ivTabIcon.setImageDrawable(tabIcon)
        mBinding.tvTab.text = title
        mTabViews.add(mBinding.root)
    }
}