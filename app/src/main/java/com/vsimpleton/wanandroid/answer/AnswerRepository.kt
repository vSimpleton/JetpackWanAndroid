package com.vsimpleton.wanandroid.answer

import com.vsimpleton.wanandroid.api.ApiService
import com.vsimpleton.wanandroid.api.BASE_URL
import com.vsimpleton.wanandroid.data.bean.ArticleBean
import com.vsimpleton.wanandroid.data.bean.BaseModel
import com.vsimpleton.wanandroid.utils.createApiFactory
import javax.inject.Inject

/**
 * NAME: vSimpleton
 * DATE: 2021/5/1
 * DESC:
 */

class AnswerRepository @Inject constructor() {

    private val apiService = createApiFactory<ApiService>(BASE_URL)

    suspend fun getAnswerList(pageId: Int): BaseModel<ArticleBean> {
        return apiService.getAnswerList(pageId)
    }

}