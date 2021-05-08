package com.vsimpleton.wanandroid.tree

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.vsimpleton.wanandroid.R
import com.vsimpleton.wanandroid.base.BaseVBAdapter
import com.vsimpleton.wanandroid.base.BaseVBViewHolder
import com.vsimpleton.wanandroid.bean.SystemTreeBean
import com.vsimpleton.wanandroid.databinding.ItemSystemListBinding
import com.vsimpleton.wanandroid.databinding.ItemTextLayoutBinding

class SystemTreeAdapter : BaseVBAdapter<ItemSystemListBinding, SystemTreeBean>() {

    var onItemClickListener: ((String) -> Unit)? = null

    override fun createViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ItemSystemListBinding {
        return ItemSystemListBinding.inflate(inflater, parent, false)
    }

    override fun convert(helper: BaseVBViewHolder<ItemSystemListBinding>, item: SystemTreeBean) {
        val mBinding = helper.binding
        mBinding.tvTitle.text = item.name

        mBinding.flowLayout.removeAllViews()
        for (i in 0 until item.children.size) {
            val textBinding = ItemTextLayoutBinding.inflate(LayoutInflater.from(mContext))
            val params = ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            textBinding.tvContent.text = item.children[i].name
            textBinding.tvContent.setOnClickListener {
                onItemClickListener?.invoke(item.children[i].name)
            }
            mBinding.flowLayout.addView(textBinding.root, params)
        }
    }
}