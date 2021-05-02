package com.vsimpleton.wanandroid.utils

import android.util.Log
import com.vsimpleton.wanandroid.BuildConfig

/**
 * NAME: vSimpleton
 * DATE: 2021/5/2
 * DESC:
 */

fun printDebug(msg: String) {
    if (BuildConfig.DEBUG) {
        Log.d("youzi", msg)
    }
}

fun printError(msg: String) {
    if (BuildConfig.DEBUG) {
        Log.e("youzi", msg)
    }
}