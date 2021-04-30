package com.vsimpleton.wanandroid.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter

abstract class BaseVBAdapter<VB : ViewBinding, T>(lists: MutableList<T>? = null) :
    BaseQuickAdapter<T, BaseVBViewHolder<VB>>(0, lists) {

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): BaseVBViewHolder<VB> {
        val binding = createViewBinding(LayoutInflater.from(context), parent)
        return BaseVBViewHolder(binding, binding.root)
    }

    abstract fun createViewBinding(inflater: LayoutInflater, parent: ViewGroup): VB

}