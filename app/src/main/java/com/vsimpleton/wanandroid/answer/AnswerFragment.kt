package com.vsimpleton.wanandroid.answer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsimpleton.wanandroid.base.BaseFragment
import com.vsimpleton.wanandroid.bean.Article
import com.vsimpleton.wanandroid.databinding.FragmentAnswerBinding
import com.vsimpleton.wanandroid.view.activity.WebActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnswerFragment : BaseFragment<FragmentAnswerBinding>() {

    private val mViewModel: AnswerViewModel by viewModels()
    private var mArticleLists = mutableListOf<Article>()
    private val mAdapter by lazy {
        AnswerAdapter(mArticleLists)
    }
    private var mPage = 0
    private var isLoadMore = false

    companion object {
        fun newInstance() = AnswerFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTitleBar()
        initViewModel()
        initRecyclerView()
    }

    private fun initTitleBar() {
        mBinding.titleBar.tvTitle.text = "问答"
    }

    private fun initViewModel() {
        mViewModel.getAnswerList(mPage)

        mViewModel.answerLiveData.observe(requireActivity(), Observer {
            if (it.errorCode == 0 && it.data.datas.size > 0) {
                mPage++
                mArticleLists = it.data.datas
                if (isLoadMore) {
                    mAdapter.addData(mArticleLists)
                    isLoadMore = false
                    mAdapter.loadMoreComplete()
                } else {
                    mAdapter.setNewData(mArticleLists)
                }
            } else {
                mAdapter.loadMoreEnd()
            }
        })
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(requireActivity())
        mBinding.rcyAnswer.layoutManager = layoutManager
        mBinding.rcyAnswer.adapter = mAdapter

        mAdapter.setOnItemClickListener { _, _, position ->
            WebActivity.start(
                requireActivity(),
                mAdapter.data[position].link
            )
        }

        mAdapter.setOnLoadMoreListener({
            isLoadMore = true
            mViewModel.getAnswerList(mPage)
        }, mBinding.rcyAnswer)
    }
}