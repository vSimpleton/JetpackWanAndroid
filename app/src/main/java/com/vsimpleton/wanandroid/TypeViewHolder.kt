package com.vsimpleton.wanandroid

import android.view.View
import androidx.recyclerview.widget.RecyclerView

sealed class TypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class NormalViewHolder(itemView: View) : TypeViewHolder(itemView)

class ProjectViewHolder(itemView: View) : TypeViewHolder(itemView)