package com.vsimpleton.wanandroid.utils

import android.content.Context
import android.content.Intent
import com.vsimpleton.wanandroid.api.ApiFactory

inline fun <reified T> startActivity(context: Context) {
    val intent = Intent(context, T::class.java)
    context.startActivity(intent)
}

inline fun <reified T> startActivity(context: Context, block: Intent.() -> Unit) {
    val intent = Intent(context, T::class.java)
    intent.block()
    context.startActivity(intent)
}

inline fun <reified T> createApiFactory(baseUrl: String): T {
    return ApiFactory.create(baseUrl, T::class.java)
}