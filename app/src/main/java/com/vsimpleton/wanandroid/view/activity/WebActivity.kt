package com.vsimpleton.wanandroid.view.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.*
import com.gyf.immersionbar.ImmersionBar
import com.vsimpleton.wanandroid.base.BaseActivity
import com.vsimpleton.wanandroid.databinding.ActivityWebBinding
import com.vsimpleton.wanandroid.view.widget.X5WebView

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
        ImmersionBar.with(this).titleBarMarginTop(mBinding.webView).statusBarDarkFont(true).init()

        initWebView()
        initListener()
    }

    private fun initListener() {
        mBinding.ivBack.setOnClickListener { finish() }
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
            useWideViewPort = true //将图片调整到适合webview的大小
            loadWithOverviewMode = true // 缩放至屏幕的大小

            setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
            builtInZoomControls = true //设置内置的缩放控件。若为false，则该WebView不可缩放
            displayZoomControls = false //隐藏原生的缩放控件

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
        }

        mBinding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                mBinding.pbPercent.progress = newProgress
            }
        }
        mBinding.webView.webViewClient = WebViewClient()

        mBinding.webView.setOnDrawListener(object : X5WebView.OnDrawListener {
            override fun onDrawCallBack() {
//                hideTop()
            }
        })

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

    private fun hideTop() {
        val javascript =
            "javascript:function hideTop() { " +
                    "document.getElementsByClassName('main-header main-header visible')[0].style.display='none'" +
                    "}"

        mBinding.webView.loadUrl(javascript)
        mBinding.webView.loadUrl("javascript:hideTop();")
    }

}