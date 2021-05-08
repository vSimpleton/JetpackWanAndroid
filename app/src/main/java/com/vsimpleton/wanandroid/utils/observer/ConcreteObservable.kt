package com.vsimpleton.wanandroid.utils.observer

object ConcreteObservable : Observable() {
    override fun register(observer: Observer) {
        observerList.add(observer)
    }

    override fun unRegister(observer: Observer) {
        observerList.remove(observer)
    }

    override fun notifyObservers(msg: MessageEvent) {
        for (observer in observerList) {
            observer.update(msg)
        }
    }

}