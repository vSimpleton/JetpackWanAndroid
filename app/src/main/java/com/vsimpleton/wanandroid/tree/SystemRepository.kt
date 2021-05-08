package com.vsimpleton.wanandroid.tree

import com.vsimpleton.wanandroid.api.ApiService
import com.vsimpleton.wanandroid.api.BASE_URL
import com.vsimpleton.wanandroid.bean.BaseModel
import com.vsimpleton.wanandroid.bean.SystemNavBean
import com.vsimpleton.wanandroid.bean.SystemTreeBean
import com.vsimpleton.wanandroid.utils.createApiFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SystemRepository @Inject constructor() {

    private val apiService = createApiFactory<ApiService>(BASE_URL)

    suspend fun getSystemTreeList(): BaseModel<MutableList<SystemTreeBean>> {
        return apiService.getSystemTreeList()
    }

    suspend fun getSystemNavList(): BaseModel<MutableList<SystemNavBean>> {
        return apiService.getSystemNAVList()
    }

}