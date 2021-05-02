package com.vsimpleton.wanandroid.bean

data class BaseModel<T>(
    val errorCode: Int = 0,
    val errorMsg: String = "",
    val data: T
)
