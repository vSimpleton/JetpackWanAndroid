package com.vsimpleton.wanandroid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import java.lang.reflect.ParameterizedType

@Suppress("UNCHECKED_CAST")
abstract class BaseVBAdapter<VB : ViewBinding, T>(lists: MutableList<T>? = null) :
    BaseQuickAdapter<T, BaseVBViewHolder<VB>>(0, lists) {

    lateinit var mBinding: VB

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): BaseVBViewHolder<VB> {
        mBinding = createViewBinding(LayoutInflater.from(context), parent)
        return BaseVBViewHolder(mBinding, mBinding.root)
    }

    abstract fun createViewBinding(inflater: LayoutInflater, parent: ViewGroup): VB

}