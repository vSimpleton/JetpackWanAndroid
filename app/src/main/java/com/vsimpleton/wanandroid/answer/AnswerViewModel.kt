package com.vsimpleton.wanandroid.answer

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vsimpleton.wanandroid.base.BaseViewModel
import com.vsimpleton.wanandroid.data.bean.ArticleBean
import com.vsimpleton.wanandroid.data.bean.BaseModel
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * NAME: vSimpleton
 * DATE: 2021/5/1
 * DESC:
 */

class AnswerViewModel @ViewModelInject constructor(private val repository: AnswerRepository) :
    BaseViewModel() {

    private val _answerLiveData = MutableLiveData<BaseModel<ArticleBean>>()

    val answerLiveData: LiveData<BaseModel<ArticleBean>>
        get() = _answerLiveData

    fun getAnswerList(pageId: Int) {
        viewModelScope.launch {
            val result = try {
                repository.getAnswerList(pageId)
            } catch (e: Exception) {
                BaseModel(-1, e.message.toString(), ArticleBean())
            }

            _answerLiveData.value = result
        }
    }

}