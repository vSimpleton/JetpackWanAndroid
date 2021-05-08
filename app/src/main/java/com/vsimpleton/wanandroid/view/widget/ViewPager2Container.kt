package com.vsimpleton.wanandroid.view.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

/**
 * 解决ViewPager2嵌套ViewPager2的滑动冲突问题
 *
 * 使用内部拦截法
 */
class ViewPager2Container @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var mViewPager2: ViewPager2? = null
    private var mStartX = 0
    private var mStartY = 0

    override fun onFinishInflate() {
        super.onFinishInflate()
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            if (childView is ViewPager2) {
                mViewPager2 = childView
                break
            }
        }
        if (mViewPager2 == null) {
            throw IllegalStateException("The root child of ViewPager2Container must contains a ViewPager2")
        }
    }

    /**
     * 不需要拦截的场景有：
     *
     * 1. userInputEnable = false
     * 2. 内部只有一个Item
     * 3. 当有多个Item时，若当前是第一个页面，则只需要拦截向左的滑动事件
     * 4. 当有多个Item时，若当前是最后一个页面，则只需要拦截向右的滑动事件
     * 5. 当有多个Item时，若当前是中间的页面，则需要拦截向左和向右的滑动事件
     * 6. 由于ViewPager2是支持竖直滑动的，那么竖直滑动也应该考虑以上条件
     */
    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        val doNotIntercept = mViewPager2?.let { it ->
            !it.isUserInputEnabled || (it.adapter != null && it.adapter!!.itemCount <= 1)
        }

        if (doNotIntercept == true) {
            return super.onInterceptTouchEvent(event)
        }

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mStartX = event.x.toInt()
                mStartY = event.y.toInt()
                parent.requestDisallowInterceptTouchEvent(true)
            }

            MotionEvent.ACTION_MOVE -> {
                val endX = event.x.toInt()
                val endY = event.y.toInt()
                val disX = abs(endX - mStartX)
                val disY = abs(endY - mStartY)
                mViewPager2?.let {
                    if (it.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
                        onHorizontalActionMove(endX, disX, disY)
                    } else if (it.orientation == ViewPager2.ORIENTATION_VERTICAL) {
                        onVerticalActionMove(endY, disX, disY)
                    }
                }
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                parent.requestDisallowInterceptTouchEvent(false)
            }
        }

        return super.onInterceptTouchEvent(event)
    }

    private fun onHorizontalActionMove(endX: Int, disX: Int, disY: Int) {
        if (mViewPager2?.adapter == null) {
            return
        }
        if (disX > disY) {
            var currentItem = 0
            var itemCount = 0

            mViewPager2?.let {
                currentItem = it.currentItem
                it.adapter?.let { adapter ->
                    itemCount = adapter.itemCount
                }
            }

            if (currentItem == 0 && endX - mStartX > 0) {
                parent.requestDisallowInterceptTouchEvent(false)
            } else {
                parent.requestDisallowInterceptTouchEvent(
                    currentItem != itemCount - 1 || endX - mStartX >= 0
                )
            }
        } else if (disY > disX) {
            parent.requestDisallowInterceptTouchEvent(false)
        }
    }

    private fun onVerticalActionMove(endY: Int, disX: Int, disY: Int) {
        if (mViewPager2?.adapter == null) {
            return
        }
        var currentItem = 0
        var itemCount = 0

        mViewPager2?.let {
            currentItem = it.currentItem
            it.adapter?.let { adapter ->
                itemCount = adapter.itemCount
            }
        }

        if (disY > disX) {
            if (currentItem == 0 && endY - mStartY > 0) {
                parent.requestDisallowInterceptTouchEvent(false)
            } else {
                parent.requestDisallowInterceptTouchEvent(
                    currentItem != itemCount - 1 || endY - mStartY >= 0
                )
            }
        } else if (disX > disY) {
            parent.requestDisallowInterceptTouchEvent(false)
        }
    }

}