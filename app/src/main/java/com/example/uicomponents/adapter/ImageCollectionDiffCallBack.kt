package com.example.uicomponents.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.uicomponents.model.ImageItem

class ImageCollectionDiffCallBack : DiffUtil.ItemCallback<ImageItem>() {
    override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
        return oldItem.url == newItem.url
    }
}