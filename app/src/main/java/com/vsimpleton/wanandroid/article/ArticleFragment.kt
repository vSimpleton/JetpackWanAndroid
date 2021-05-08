package com.vsimpleton.wanandroid.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.gyf.immersionbar.ImmersionBar
import com.vsimpleton.wanandroid.base.BaseFragment
import com.vsimpleton.wanandroid.bean.Article
import com.vsimpleton.wanandroid.bean.BannerBean
import com.vsimpleton.wanandroid.databinding.BannerArticleTopBinding
import com.vsimpleton.wanandroid.databinding.FragmentArticleBinding
import com.vsimpleton.wanandroid.utils.dp2px
import com.vsimpleton.wanandroid.view.activity.WebActivity
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment : BaseFragment<FragmentArticleBinding>(), View.OnClickListener {

    private val mViewModel: ArticleViewModel by viewModels()
    private var mArticleLists = mutableListOf<Article>()
    private val mAdapter by lazy {
        ArticleListAdapter(mArticleLists)
    }
    private var mPage = 0
    private var isLoadMore = false
    private val mBannerBinding by lazy {
        BannerArticleTopBinding.inflate(LayoutInflater.from(requireActivity()))
    }

    companion object {
        fun newInstance() = ArticleFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ImmersionBar.with(this).titleBarMarginTop(mBinding.titleBar.titleLayout)
            .statusBarDarkFont(true).init()

        initTitleBar()
        initViewModel()
        initRecyclerView()
        initListener()
    }

    private fun initListener() {
        mBinding.titleBar.ivLeft.setOnClickListener(this)
        mBinding.titleBar.ivRight.setOnClickListener(this)
    }

    private fun initTitleBar() {
        mBinding.titleBar.tvTitle.text = "首页"
        mBinding.titleBar.ivLeft.visibility = View.VISIBLE
        mBinding.titleBar.ivRight.visibility = View.VISIBLE
    }

    private fun initViewModel() {
        mViewModel.getBannerList()
        mViewModel.getArticleList(mPage)

        mViewModel.articleLiveData.observe(requireActivity(), Observer {
            if (it.errorCode == 0) {
                mPage++
                mArticleLists = it.data.datas
                if (isLoadMore) {
                    mAdapter.addData(mArticleLists)
                    isLoadMore = false
                    mAdapter.loadMoreComplete()
                } else {
                    mAdapter.setNewData(mArticleLists)
                }
            }
        })

        mViewModel.bannerLiveData.observe(requireActivity(), Observer {
            if (it.errorCode == 0) {
                mBannerBinding.banner.apply {
                    adapter = object : BannerImageAdapter<BannerBean>(it.data) {
                        override fun onBindView(
                            holder: BannerImageHolder,
                            data: BannerBean,
                            position: Int,
                            size: Int
                        ) {
                            holder.imageView.load(data.imagePath)
                            holder.imageView.setOnClickListener {
                                WebActivity.start(requireActivity(), data.url)
                            }
                        }
                    }
                    addBannerLifecycleObserver(requireActivity())
                    setIndicator(CircleIndicator(requireActivity()))
                }
            }
        })

    }

    private fun initBannerView(): View {
        val params = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            dp2px(220f)
        )
        mBannerBinding.root.layoutParams = params

        return mBannerBinding.root
    }

    private fun initRecyclerView() {
        mAdapter.removeAllHeaderView()
        mAdapter.addHeaderView(initBannerView())
        val layoutManager = LinearLayoutManager(requireActivity())
        mBinding.rcyArticle.layoutManager = layoutManager
        mBinding.rcyArticle.adapter = mAdapter

        mAdapter.setOnItemClickListener { _, _, position ->
            WebActivity.start(
                requireActivity(),
                mAdapter.data[position].link
            )
        }

        mAdapter.setOnLoadMoreListener({
            isLoadMore = true
            mViewModel.getArticleList(mPage)
        }, mBinding.rcyArticle)

    }

    override fun onClick(v: View) {
        when (v) {
            mBinding.titleBar.ivLeft -> {

            }
            mBinding.titleBar.ivRight -> {

            }
        }
    }
}
