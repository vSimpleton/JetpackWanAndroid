package com.vsimpleton.wanandroid.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import com.vsimpleton.wanandroid.base.BaseAdapter
import com.vsimpleton.wanandroid.data.bean.Article
import com.vsimpleton.wanandroid.databinding.ItemArticleListBinding

class ArticleListAdapter(context: Context, lists: MutableList<Article>) :
    BaseAdapter<ItemArticleListBinding, Article>(context, lists) {

    @SuppressLint("SetTextI18n")
    override fun convert(binding: ItemArticleListBinding, data: Article, position: Int) {
        binding.tvTitle.text = data.title
        binding.tvChapterName.text = "${data.superChapterName} · ${data.chapterName}"
        binding.tvAuthorName.text = data.author
    }

}