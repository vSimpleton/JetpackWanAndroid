package com.vsimpleton.wanandroid.tree

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vsimpleton.wanandroid.base.BaseViewModel
import com.vsimpleton.wanandroid.bean.BaseModel
import com.vsimpleton.wanandroid.bean.SystemNavBean
import com.vsimpleton.wanandroid.bean.SystemTreeBean
import kotlinx.coroutines.launch
import java.lang.Exception

class SystemViewModel @ViewModelInject constructor(private val repository: SystemRepository) :
    BaseViewModel() {

    private val _systemTreeLiveData = MutableLiveData<BaseModel<MutableList<SystemTreeBean>>>()
    private val _systemNavLiveData = MutableLiveData<BaseModel<MutableList<SystemNavBean>>>()

    val systemTreeLiveData: LiveData<BaseModel<MutableList<SystemTreeBean>>>
        get() = _systemTreeLiveData

    val systemNavLiveData: LiveData<BaseModel<MutableList<SystemNavBean>>>
        get() = _systemNavLiveData

    fun getSystemTreeList() {
        viewModelScope.launch {
            val result: BaseModel<MutableList<SystemTreeBean>> = try {
                repository.getSystemTreeList()
            } catch (e: Exception) {
                BaseModel(-1, e.message.toString(), mutableListOf())
            }

            _systemTreeLiveData.value = result
        }
    }

    fun getSystemNavList() {
        viewModelScope.launch {
            val result: BaseModel<MutableList<SystemNavBean>> = try {
                repository.getSystemNavList()
            } catch (e: Exception) {
                BaseModel(-1, e.message.toString(), mutableListOf())
            }

            _systemNavLiveData.value = result
        }
    }

}