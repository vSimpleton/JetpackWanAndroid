package com.vsimpleton.wanandroid.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import kotlin.math.max

/**
 * NAME: vSimpleton
 * DATE: 2021/5/3
 * DESC:
 */

class FlowLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val measureWidthSize = MeasureSpec.getSize(widthMeasureSpec)
        val measureHeightSize = MeasureSpec.getSize(heightMeasureSpec)

        val measureWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        val measureHeightMode = MeasureSpec.getMode(heightMeasureSpec)

        var lineWidth = 0 // 记录每一行的宽度
        var lineHeight = 0 // 记录每一行的高度
        var totalWidth = 0 // 记录整体的宽度
        var totalHeight = 0 // 记录整体的高度

        val count = childCount
        for (i in 0 until count) {
            val view = getChildAt(i)
            measureChild(view, widthMeasureSpec, heightMeasureSpec)
            val params = view.layoutParams as MarginLayoutParams
            val viewWidth = view.measuredWidth + params.leftMargin + params.rightMargin
            val viewHeight = view.measuredHeight + params.topMargin + params.bottomMargin

            // 若当前宽度超过最大的宽度，则需要换行
            if (lineWidth + viewWidth > measureWidthSize) {
                totalWidth = max(lineWidth, viewWidth)
                totalHeight += viewHeight
                lineWidth = viewWidth
                lineHeight = viewHeight
            } else { // 不需要换行
                lineWidth += viewWidth
                lineHeight = max(lineHeight, viewHeight)
            }

            if (i == count - 1) {
                totalWidth = max(lineWidth, totalWidth)
                totalHeight += lineHeight
            }
        }

        setMeasuredDimension(
            if (measureWidthMode == MeasureSpec.EXACTLY) measureWidthSize else totalWidth,
            if (measureHeightMode == MeasureSpec.EXACTLY) measureHeightSize else totalHeight
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var lineWidth = 0 //累加当前行的行宽
        var lineHeight = 0 //累加当前的行高

        // 当前空间的top坐标和left坐标
        var top = 0
        var left = 0

        val count = childCount
        for (i in 0 until count) {
            val view = getChildAt(i)
            val lp = view.layoutParams as MarginLayoutParams
            val viewWidth = view.measuredWidth + lp.rightMargin + lp.leftMargin
            val viewHeight = view.measuredHeight + lp.topMargin + lp.bottomMargin
            if (viewWidth + lineWidth > measuredWidth) {
                top += lineHeight
                left = 0
                lineHeight = viewHeight
                lineWidth = viewWidth
            } else {
                lineHeight = max(lineHeight, viewHeight)
                lineWidth += viewWidth
            }

            val lc = left + lp.leftMargin
            val tc = top + lp.topMargin
            val rc = lc + view.measuredWidth
            val bc = tc + view.measuredHeight
            view.layout(lc, tc, rc, bc)

            left += viewWidth
        }
    }

}