package com.vsimpleton.wanandroid.tree

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.vsimpleton.wanandroid.base.BaseVBAdapter
import com.vsimpleton.wanandroid.base.BaseVBViewHolder
import com.vsimpleton.wanandroid.bean.SystemNavBean
import com.vsimpleton.wanandroid.databinding.ItemSystemListBinding
import com.vsimpleton.wanandroid.databinding.ItemTextLayoutBinding

class SystemNavAdapter : BaseVBAdapter<ItemSystemListBinding, SystemNavBean>() {

    var onItemClickListener: ((String) -> Unit)? = null

    override fun createViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ItemSystemListBinding {
        return ItemSystemListBinding.inflate(inflater, parent, false)
    }

    override fun convert(helper: BaseVBViewHolder<ItemSystemListBinding>, item: SystemNavBean) {
        val mBinding = helper.binding
        mBinding.tvTitle.text = item.name

        mBinding.flowLayout.removeAllViews()
        for (i in 0 until item.articles.size) {
            val textBinding = ItemTextLayoutBinding.inflate(LayoutInflater.from(mContext))
            val params = ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            textBinding.tvContent.text = item.articles[i].title
            textBinding.tvContent.setOnClickListener {
                onItemClickListener?.invoke(item.articles[i].title)
            }
            mBinding.flowLayout.addView(textBinding.root, params)
        }
    }
}