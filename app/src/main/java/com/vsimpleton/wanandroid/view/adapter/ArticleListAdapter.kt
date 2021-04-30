package com.vsimpleton.wanandroid.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import coil.transform.RoundedCornersTransformation
import com.vsimpleton.wanandroid.base.BaseVBAdapter
import com.vsimpleton.wanandroid.base.BaseVBViewHolder
import com.vsimpleton.wanandroid.data.bean.Article
import com.vsimpleton.wanandroid.databinding.ItemArticleListBinding
import com.vsimpleton.wanandroid.dp2px

class ArticleListAdapter(lists: MutableList<Article>) :
    BaseVBAdapter<ItemArticleListBinding, Article>(lists) {

    override fun createViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ItemArticleListBinding {
        return ItemArticleListBinding.inflate(inflater, parent, false)
    }

    @SuppressLint("SetTextI18n")
    override fun convert(holder: BaseVBViewHolder<ItemArticleListBinding>, item: Article) {
        val mBinding = holder.binding
        mBinding.tvPublishTime.text = item.niceDate
        mBinding.tvChapterName.text = "${item.superChapterName} · ${item.chapterName}"

        mBinding.tvAuthorName.text = if (item.author.isNotEmpty()) item.author else item.shareUser
        mBinding.tvNewTips.visibility = if (item.fresh) View.VISIBLE else View.GONE

        if (item.tags.size == 0) {
            mBinding.tvType.visibility = View.GONE
        } else {
            mBinding.tvType.visibility = View.VISIBLE
            mBinding.tvType.text = item.tags[0].name
        }

        if (item.envelopePic.isNotEmpty()) {
            mBinding.clPicLayout.visibility = View.VISIBLE
            mBinding.tvTitle.visibility = View.GONE

            mBinding.ivEnvelopePic.load(item.envelopePic) {
                transformations(RoundedCornersTransformation(dp2px(1.5f).toFloat()))
            }
            mBinding.tvPicTitle.text = item.title
            mBinding.tvDesc.text = item.desc

        } else {
            mBinding.clPicLayout.visibility = View.GONE
            mBinding.tvTitle.visibility = View.VISIBLE

            mBinding.tvTitle.text = item.title
        }

    }

}