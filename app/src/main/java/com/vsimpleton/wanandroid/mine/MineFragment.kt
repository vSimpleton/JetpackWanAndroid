package com.vsimpleton.wanandroid.mine

import android.os.Bundle
import android.view.View
import coil.load
import com.gyf.immersionbar.ImmersionBar
import com.vsimpleton.wanandroid.R
import com.vsimpleton.wanandroid.base.BaseFragment
import com.vsimpleton.wanandroid.databinding.FragmentMineBinding

class MineFragment : BaseFragment<FragmentMineBinding>() {

    companion object {
        fun newInstance() = MineFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ImmersionBar.with(this).statusBarDarkFont(true).init()
        initView()
    }

    private fun initView() {

    }
}