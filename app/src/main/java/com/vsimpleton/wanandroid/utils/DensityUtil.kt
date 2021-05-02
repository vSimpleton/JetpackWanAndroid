package com.vsimpleton.wanandroid.utils

import android.content.res.Resources

/**
 * NAME: vSimpleton
 * DATE: 2021/5/2
 * DESC:
 */

fun dp2px(dpValue: Float): Int {
    return (0.5f + dpValue * Resources.getSystem().displayMetrics.density).toInt()
}

fun px2dp(pxValue: Float): Int {
    return (pxValue / Resources.getSystem().displayMetrics.density).toInt()
}

fun sp2px(spValue: Float): Int {
    return (spValue * Resources.getSystem().displayMetrics.scaledDensity + 0.5f).toInt()
}

fun px2sp(pxValue: Float): Int {
    return (pxValue / Resources.getSystem().displayMetrics.scaledDensity + 0.5f).toInt()
}