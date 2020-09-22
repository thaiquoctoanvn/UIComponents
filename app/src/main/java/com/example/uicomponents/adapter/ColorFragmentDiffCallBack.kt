package com.example.uicomponents.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.uicomponents.ExampleObject

class ColorFragmentDiffCallBack : DiffUtil.ItemCallback<ExampleObject>() {
    override fun areItemsTheSame(oldItem: ExampleObject, newItem: ExampleObject): Boolean {
        return oldItem.textContent == newItem.textContent
    }

    override fun areContentsTheSame(oldItem: ExampleObject, newItem: ExampleObject): Boolean {
        return true
    }

}