package com.vsimpleton.wanandroid.article

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vsimpleton.wanandroid.base.BaseViewModel
import com.vsimpleton.wanandroid.bean.ArticleBean
import com.vsimpleton.wanandroid.bean.BannerBean
import com.vsimpleton.wanandroid.bean.BaseModel
import kotlinx.coroutines.launch
import java.lang.Exception

class ArticleViewModel @ViewModelInject constructor(private val repository: ArticleRepository) :
    BaseViewModel() {

    private val _articleLiveData = MutableLiveData<BaseModel<ArticleBean>>()
    private val _bannerLiveData = MutableLiveData<BaseModel<MutableList<BannerBean>>>()

    val articleLiveData: LiveData<BaseModel<ArticleBean>>
        get() = _articleLiveData

    val bannerLiveData: LiveData<BaseModel<MutableList<BannerBean>>>
        get() = _bannerLiveData

    fun getArticleList(page: Int) {
        viewModelScope.launch {
            val result = try {
                val lists = repository.getArticleList(page)
                if (page == 0) {
                    val topList = repository.getArticleTop()
                    lists.data.datas.addAll(0, topList.data)
                }
                lists
            } catch (e: Exception) {
                // 若发生异常，则返回默认值
                BaseModel(-1, e.message.toString(), ArticleBean())
            }
            _articleLiveData.value = result
        }
    }

    fun getBannerList() {
        viewModelScope.launch {
            val result: BaseModel<MutableList<BannerBean>> = try {
                repository.getBannerList()
            } catch (e: Exception) {
                BaseModel(-1, e.message.toString(), mutableListOf())
            }

            _bannerLiveData.value = result
        }
    }

}