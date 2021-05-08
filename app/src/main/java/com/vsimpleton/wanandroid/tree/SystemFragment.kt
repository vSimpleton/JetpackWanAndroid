package com.vsimpleton.wanandroid.tree

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsimpleton.wanandroid.base.BaseFragment
import com.vsimpleton.wanandroid.databinding.FragmentSystemBinding
import dagger.hilt.android.AndroidEntryPoint

const val PAGE_TREE = 0
const val PAGE_NAVIGATION = 1

@AndroidEntryPoint
class SystemFragment : BaseFragment<FragmentSystemBinding>() {

    private val mViewModel: SystemViewModel by viewModels()
    private val mTreeAdapter by lazy { SystemTreeAdapter() }
    private val mNavAdapter by lazy { SystemNavAdapter() }
    private var mPage = 0

    companion object {
        fun newInstance(page: Int): SystemFragment {
            return SystemFragment().apply {
                arguments = Bundle().apply {
                    putInt("page", page)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { mPage = it.getInt("page") }

        initViewModel(mPage)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(requireActivity())
        mBinding.rcyTree.layoutManager = layoutManager
        when(mPage) {
            PAGE_TREE -> {
                mBinding.rcyTree.adapter = mTreeAdapter
                mTreeAdapter.onItemClickListener = {
                    Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                }
            }

            PAGE_NAVIGATION -> {
                mBinding.rcyTree.adapter = mNavAdapter
                mNavAdapter.onItemClickListener = {
                    Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initViewModel(page: Int) {
        when (page) {
            PAGE_TREE -> {
                mViewModel.getSystemTreeList()
                mViewModel.systemTreeLiveData.observe(requireActivity(), Observer {
                    if (it.errorCode == 0) {
                        mTreeAdapter.setNewData(it.data)
                    }
                })
            }

            PAGE_NAVIGATION -> {
                mViewModel.getSystemNavList()
                mViewModel.systemNavLiveData.observe(requireActivity(), Observer {
                    if (it.errorCode == 0) {
                        mNavAdapter.setNewData(it.data)
                    }
                })
            }
        }
    }
}