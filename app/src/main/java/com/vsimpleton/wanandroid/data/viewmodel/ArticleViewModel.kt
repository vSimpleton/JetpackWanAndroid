package com.vsimpleton.wanandroid.data.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vsimpleton.wanandroid.base.BaseViewModel
import com.vsimpleton.wanandroid.data.bean.Article
import com.vsimpleton.wanandroid.data.bean.ArticleBean
import com.vsimpleton.wanandroid.data.bean.BaseModel
import com.vsimpleton.wanandroid.data.repository.ArticleRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class ArticleViewModel @ViewModelInject constructor(private val repository: ArticleRepository) :
    BaseViewModel() {

    private val _articleLiveData = MutableLiveData<BaseModel<ArticleBean>>()

    val articleLiveData: LiveData<BaseModel<ArticleBean>>
        get() = _articleLiveData

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

}