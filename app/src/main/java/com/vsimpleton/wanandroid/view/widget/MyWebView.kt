package com.vsimpleton.wanandroid.view.widget

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.AttributeSet
import android.webkit.WebView

class MyWebView @JvmOverloads constructor(context: Context, attrs: AttributeSet, defStyle: Int) :
    WebView(context, attrs, defStyle) {

    init {
        getFixedContext(context)
    }

    private fun getFixedContext(context: Context): Context {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            context.createConfigurationContext(Configuration())
        } else {
            context
        }
    }

}