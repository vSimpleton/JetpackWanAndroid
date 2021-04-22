package com.vsimpleton.wanandroid.observer

import com.vsimpleton.wanandroid.observer.MessageEvent

interface Observer {

    fun update(msg: MessageEvent)

}