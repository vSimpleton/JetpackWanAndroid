package com.vsimpleton.wanandroid

import android.view.View
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class BaseVBViewHolder<VB : ViewBinding>(var binding: VB, itemView: View) : BaseViewHolder(itemView)