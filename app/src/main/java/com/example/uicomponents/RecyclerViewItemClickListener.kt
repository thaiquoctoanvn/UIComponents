package com.example.uicomponents

import android.view.View

interface RecyclerViewItemClickListener {
    fun setOnItemClickListener(itemView: View, position: Int)
}