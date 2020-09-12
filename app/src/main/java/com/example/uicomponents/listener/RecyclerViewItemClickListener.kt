package com.example.uicomponents.listener

import android.view.View

interface RecyclerViewItemClickListener {
    fun setOnItemClickListener(itemView: View, position: Int)
}