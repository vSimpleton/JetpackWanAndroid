package com.vsimpleton.wanandroid.article

import com.vsimpleton.wanandroid.api.ApiService
import com.vsimpleton.wanandroid.api.BASE_URL
import com.vsimpleton.wanandroid.bean.Article
import com.vsimpleton.wanandroid.bean.ArticleBean
import com.vsimpleton.wanandroid.bean.BannerBean
import com.vsimpleton.wanandroid.bean.BaseModel
import com.vsimpleton.wanandroid.utils.createApiFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleRepository @Inject constructor() {

    private val apiService = createApiFactory<ApiService>(BASE_URL)

    suspend fun getArticleList(page: Int): BaseModel<ArticleBean> {
        return apiService.getArticleList(page)
    }

    suspend fun getArticleTop(): BaseModel<MutableList<Article>> {
        return apiService.getArticleTop()
    }

    suspend fun getBannerList(): BaseModel<MutableList<BannerBean>> {
        return apiService.getBanner()
    }

}