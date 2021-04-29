package com.vsimpleton.wanandroid.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsimpleton.wanandroid.base.BaseFragment
import com.vsimpleton.wanandroid.data.bean.Article
import com.vsimpleton.wanandroid.data.viewmodel.ArticleViewModel
import com.vsimpleton.wanandroid.databinding.FragmentArticleBinding
import com.vsimpleton.wanandroid.view.activity.WebActivity
import com.vsimpleton.wanandroid.view.adapter.ArticleListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment : BaseFragment<FragmentArticleBinding>() {

    private val mViewModel: ArticleViewModel by viewModels()
    private var mArticleLists = mutableListOf<Article>()
    private val mAdapter by lazy {
        ArticleListAdapter(mArticleLists)
    }

    companion object {
        fun newInstance() = ArticleFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initRecyclerView()

    }

    private fun initData() {
        mViewModel.getArticleList(0)
        mViewModel.articleLiveData.observe(requireActivity(), Observer {
            if (it.errorCode == 0) {
                mArticleLists = it.data.datas
                mAdapter.setNewInstance(it.data.datas)
            }
        })
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(requireActivity())
        mBinding.rcyArticle.layoutManager = layoutManager
        mBinding.rcyArticle.adapter = mAdapter

        mAdapter.setOnItemClickListener { _, _, position ->
            WebActivity.start(
                requireActivity(),
                mAdapter.data[position].link
            )
        }
    }
}
