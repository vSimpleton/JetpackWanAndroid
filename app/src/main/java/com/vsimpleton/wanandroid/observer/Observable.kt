package com.vsimpleton.wanandroid.observer

abstract class Observable {

    //观察者的集合
    protected val observerList = arrayListOf<Observer>()

    // 注册
    abstract fun register(observer: Observer)

    // 取消注册
    abstract fun unRegister(observer: Observer)

    // 通知
    abstract fun notifyObservers(msg: MessageEvent)

}