package com.vsimpleton.wanandroid.data.bean

data class ArticleBean(
    val curPage: Int = 0,
    val datas: MutableList<Article> = mutableListOf()
)

data class Article(
    val apkLint: String = "",
    val audit: Int = 0,
    val author: String = "",
    val canEdit: Boolean = false,
    val chapterId: Int = 0,
    val chapterName: String = "",
    val collect: Boolean = false,
    val courseId: Int = 0,
    val desc: String = "",
    val descMd: String = "",
    val envelopePic: String = "",
    val fresh: Boolean = false,
    val host: String = "",
    val id: Long = 0L,
    val link: String = "",
    val niceDate: String = "",
    val niceShareDate: String = "",
    val origin: String = "",
    val prefix: String = "",
    val projectLink: String = "",
    val publishTime: Long = 0L,
    val realSuperChapterId: Int = 0,
    val selfVisible: Int = 0,
    val shareDate: Long = 0L,
    val shareUser: String = "",
    val superChapterId: Long = 0L,
    val superChapterName: String = "",
    val tags: MutableList<Tag> = mutableListOf(),
    val title: String = "",
    val type: Int = 0,
    val userId: Long = 0L,
    val visible: Int = 0,
    val zan: Int = 0
)

data class Tag(
    val name: String = "",
    val url: String = ""
)
