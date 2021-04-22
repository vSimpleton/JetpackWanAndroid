package com.vsimpleton.template.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.vsimpleton.wanandroid.observer.ConcreteObservable
import com.vsimpleton.wanandroid.observer.MessageEvent
import com.vsimpleton.wanandroid.observer.Observer
import java.lang.reflect.ParameterizedType

open class BaseFragment<VB : ViewBinding> : Fragment(), Observer {

    lateinit var mContext: Context
    lateinit var mBinding: VB

    private var isViewCreated = false
    private var isUIVisible = false
    var isVisibleToUser = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz = type.actualTypeArguments[0] as Class<VB>
        val method = clazz.getMethod("inflate", LayoutInflater::class.java)
        mBinding = method.invoke(null, layoutInflater) as VB

        mContext = context as AppCompatActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ConcreteObservable.register(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ConcreteObservable.unRegister(this)
    }

    override fun update(msg: MessageEvent) {
        handleEvent(msg)
    }

    open fun handleEvent(msg: MessageEvent) {

    }

}