package com.vsimpleton.wanandroid.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.vsimpleton.wanandroid.*
import com.vsimpleton.wanandroid.data.bean.Article
import com.vsimpleton.wanandroid.databinding.ItemArticleListBinding
import com.vsimpleton.wanandroid.databinding.ItemArticleProjectBinding

class ArticleListAdapter(private val context: Context, private var lists: MutableList<Article>) :
    RecyclerView.Adapter<TypeViewHolder>() {

    companion object {
        private const val TYPE_ARTICLE_NORMAL = 1
        private const val TYPE_ARTICLE_PROJECT = 2
    }

    private lateinit var mNormalBinding: ItemArticleListBinding
    private lateinit var mProjectBinding: ItemArticleProjectBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeViewHolder {
        return if (viewType == TYPE_ARTICLE_NORMAL) {
            mNormalBinding =
                ItemArticleListBinding.inflate(LayoutInflater.from(context), parent, false)
            NormalViewHolder(mNormalBinding.root)
        } else {
            mProjectBinding =
                ItemArticleProjectBinding.inflate(LayoutInflater.from(context), parent, false)
            ProjectViewHolder(mProjectBinding.root)
        }
    }

    override fun onBindViewHolder(holder: TypeViewHolder, position: Int) {
        val data = lists[position]
        when (holder) {
            is NormalViewHolder -> {
                setNormalItemData(data)
            }

            is ProjectViewHolder -> {
                setProjectItemData(data)
            }
        }
        holder.itemView.setOnClickListener {
            itemClick?.let { it(position) }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setNormalItemData(data: Article) {
        mNormalBinding.tvTitle.text = data.title
        mNormalBinding.tvPublishTime.text = data.niceDate
        mNormalBinding.tvChapterName.text = "${data.superChapterName} · ${data.chapterName}"

        mNormalBinding.tvAuthorName.text = if (data.author.isNotEmpty()) {
            data.author
        } else {
            data.shareUser
        }
        mNormalBinding.tvNewTips.visibility = if (data.fresh) {
            View.VISIBLE
        } else {
            View.GONE
        }

        if (data.tags.size == 0) {
            mNormalBinding.tvType.visibility = View.GONE
        } else {
            mNormalBinding.tvType.visibility = View.VISIBLE
            mNormalBinding.tvType.text = data.tags[0].name
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setProjectItemData(data: Article) {
        mProjectBinding.tvChapterName.text =
            "${data.superChapterName} · ${data.chapterName}"
        mProjectBinding.tvAuthorName.text = if (data.author.isNotEmpty()) {
            data.author
        } else {
            data.shareUser
        }
        mProjectBinding.ivEnvelopePic.load(data.envelopePic) {
            transformations(RoundedCornersTransformation(dp2px(1.5f).toFloat()))
        }
        mProjectBinding.tvTitle.text = data.title
        mProjectBinding.tvDesc.text = data.desc
        mProjectBinding.tvPublishTime.text = data.niceDate

        mProjectBinding.tvNewTips.visibility = if (data.fresh) {
            View.VISIBLE
        } else {
            View.GONE
        }

        if (data.tags.size == 0) {
            mProjectBinding.tvType.visibility = View.GONE
        } else {
            mProjectBinding.tvType.visibility = View.VISIBLE
            mProjectBinding.tvType.text = data.tags[0].name
        }
    }

    override fun getItemCount(): Int = lists.size

    override fun getItemViewType(position: Int): Int =
        if (lists[position].envelopePic.isNotEmpty()) {
            TYPE_ARTICLE_PROJECT
        } else {
            TYPE_ARTICLE_NORMAL
        }

    fun setNewData(lists: MutableList<Article>) {
        this.lists = lists
        notifyDataSetChanged()
    }

    private var itemClick: ((Int) -> Unit)? = null
    private var itemLongClick: ((Int) -> Unit)? = null

    fun itemClick(itemClick: (Int) -> Unit) {
        this.itemClick = itemClick
    }

    fun itemLongClick(itemLongClick: (Int) -> Unit) {
        this.itemLongClick = itemLongClick
    }

}