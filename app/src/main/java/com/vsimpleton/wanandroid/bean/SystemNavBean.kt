package com.vsimpleton.wanandroid.bean

data class SystemNavBean(
    val articles: MutableList<Article> = mutableListOf(),
    val cid: Int = 0,
    val name: String = ""
)
