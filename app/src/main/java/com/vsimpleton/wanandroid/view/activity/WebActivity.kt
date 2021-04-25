package com.vsimpleton.wanandroid.view.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.*
import com.vsimpleton.wanandroid.base.BaseActivity
import com.vsimpleton.wanandroid.databinding.ActivityWebBinding

class WebActivity : BaseActivity<ActivityWebBinding>() {

    private var mWebViewUrl = ""

    companion object {
        @JvmStatic
        fun start(context: Context, url: String) {
            val starter = Intent(context, WebActivity::class.java)
                .putExtra("mWebViewUrl", url)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        mWebViewUrl = intent.getStringExtra("mWebViewUrl").toString()
        mBinding.webView.loadUrl(mWebViewUrl)

        mBinding.webView.settings.apply {
            allowContentAccess = true
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = true

            // 兼容https和http的网络链接
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
        }

        mBinding.webView.webChromeClient = WebChromeClient()
        mBinding.webView.webViewClient = WebViewClient()

    }

    override fun onBackPressed() {
        if (mBinding.webView.canGoBack()) {
            mBinding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val parent = mBinding.webView.parent
        if (parent != null) {
            (parent as ViewGroup).removeView(mBinding.webView)
        }
        mBinding.webView.stopLoading()
        mBinding.webView.settings.javaScriptEnabled = false
        mBinding.webView.clearCache(true)
        mBinding.webView.clearHistory()
        mBinding.webView.removeAllViews()
        mBinding.webView.destroy()
    }

}