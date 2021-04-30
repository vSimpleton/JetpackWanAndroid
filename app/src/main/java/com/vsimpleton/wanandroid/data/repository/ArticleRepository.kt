package com.vsimpleton.wanandroid.data.repository

import com.vsimpleton.wanandroid.api.ApiService
import com.vsimpleton.wanandroid.api.BASE_URL
import com.vsimpleton.wanandroid.data.bean.Article
import com.vsimpleton.wanandroid.data.bean.ArticleBean
import com.vsimpleton.wanandroid.data.bean.BaseModel
import com.vsimpleton.wanandroid.utils.createApiFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleRepository @Inject constructor() {

    private val apiService = createApiFactory<ApiService>(BASE_URL)

    suspend fun getArticleList(page: Int): BaseModel<ArticleBean> {
        return apiService.getArticleList(page)
    }

    suspend fun getArticleTop(): BaseModel<List<Article>> {
        return apiService.getArticleTop()
    }

}