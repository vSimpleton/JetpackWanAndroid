package com.vsimpleton.wanandroid.api

import com.vsimpleton.wanandroid.data.bean.ArticleBean
import com.vsimpleton.wanandroid.data.bean.BaseModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET(GET_ARTICLE_LIST)
    suspend fun getArticleList(@Path("page") page: Int): BaseModel<ArticleBean>

}