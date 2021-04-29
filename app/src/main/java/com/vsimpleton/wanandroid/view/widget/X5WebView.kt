package com.vsimpleton.wanandroid.view.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.webkit.WebView

class X5WebView : WebView {

    private var mOnDrawListener: OnDrawListener? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun canScrollHorizontally(direction: Int): Boolean {
        return this.canScrollHorizontally(direction)
    }

    override fun canScrollVertically(direction: Int): Boolean {
        return this.canScrollVertically(direction)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mOnDrawListener?.onDrawCallBack()
    }

    fun setOnDrawListener(onDrawListener: OnDrawListener) {
        this.mOnDrawListener = onDrawListener
    }

    interface OnDrawListener {
        fun onDrawCallBack()
    }

}