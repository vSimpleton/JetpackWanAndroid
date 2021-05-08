package com.vsimpleton.wanandroid.bean

data class SystemTreeBean(
    val children: MutableList<Children> = mutableListOf(),
    val courseId: Int = 0,
    val id: Long = 0L,
    val name: String = "",
    val order: Int = 0,
    val parentChapterId: Long = 0L,
    val userControlSetTop: Boolean = false,
    val visible: Int = 0
)

data class Children(
    val children: MutableList<Children> = mutableListOf(),
    val courseId: Int = 0,
    val id: Long = 0L,
    val name: String = "",
    val order: Int = 0,
    val parentChapterId: Long = 0L,
    val userControlSetTop: Boolean = false,
    val visible: Int = 0
)