package com.vsimpleton.wanandroid.api

import com.vsimpleton.wanandroid.bean.Article
import com.vsimpleton.wanandroid.bean.ArticleBean
import com.vsimpleton.wanandroid.bean.BannerBean
import com.vsimpleton.wanandroid.bean.BaseModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET(GET_ARTICLE_LIST)
    suspend fun getArticleList(@Path("page") page: Int): BaseModel<ArticleBean>

    @GET(GET_ARTICLE_TOP)
    suspend fun getArticleTop(): BaseModel<MutableList<Article>>

    @GET(GET_BANNER)
    suspend fun getBanner(): BaseModel<MutableList<BannerBean>>

    @GET(GET_ANSWER_LIST)
    suspend fun getAnswerList(@Path("pageId") pageId: Int): BaseModel<ArticleBean>

}