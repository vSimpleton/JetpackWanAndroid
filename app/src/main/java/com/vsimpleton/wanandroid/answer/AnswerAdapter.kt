package com.vsimpleton.wanandroid.answer

import android.annotation.SuppressLint
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vsimpleton.wanandroid.base.BaseVBAdapter
import com.vsimpleton.wanandroid.base.BaseVBViewHolder
import com.vsimpleton.wanandroid.bean.Article
import com.vsimpleton.wanandroid.databinding.ItemAnswerListBinding

/**
 * NAME: vSimpleton
 * DATE: 2021/5/1
 * DESC:
 */

class AnswerAdapter(lists: MutableList<Article>) :
    BaseVBAdapter<ItemAnswerListBinding, Article>(lists) {

    override fun createViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ItemAnswerListBinding {
       return ItemAnswerListBinding.inflate(inflater, parent, false)
    }

    @SuppressLint("SetTextI18n")
    override fun convert(helper: BaseVBViewHolder<ItemAnswerListBinding>, item: Article) {
        val mBinding = helper.binding

        mBinding.tvPublishTime.text = item.niceDate
        mBinding.tvChapterName.text = "${item.superChapterName} · ${item.chapterName}"
        mBinding.tvAuthorName.text = if (item.author.isNotEmpty()) item.author else item.shareUser

        if (item.tags.size == 0) {
            mBinding.tvType.visibility = View.GONE
        } else {
            mBinding.tvType.visibility = View.VISIBLE
            mBinding.tvType.text = item.tags[0].name
        }

        mBinding.tvTitle.text = item.title
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mBinding.tvDesc.text = Html.fromHtml(item.desc, Html.FROM_HTML_MODE_COMPACT)
        } else {

        }

    }
}