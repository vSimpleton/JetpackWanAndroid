package com.vsimpleton.wanandroid.base

import android.view.View
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseViewHolder

class BaseVBViewHolder<VB : ViewBinding>(var binding: VB, itemView: View) : BaseViewHolder(itemView)