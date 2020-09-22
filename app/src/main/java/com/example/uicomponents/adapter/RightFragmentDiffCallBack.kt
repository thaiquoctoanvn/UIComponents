package com.example.uicomponents.adapter

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.example.uicomponents.ExampleObject

class RightFragmentDiffCallBack : DiffUtil.ItemCallback<ExampleObject>() {
    override fun areItemsTheSame(oldItem: ExampleObject, newItem: ExampleObject): Boolean {
        return oldItem.imageSource == newItem.imageSource
    }

    override fun areContentsTheSame(oldItem: ExampleObject, newItem: ExampleObject): Boolean {
        return oldItem.textContent == newItem.textContent
    }

    override fun getChangePayload(oldItem: ExampleObject, newItem: ExampleObject): Any? {
        if(oldItem.imageSource == newItem.imageSource) {
            return newItem
        }
        return null
    }
}