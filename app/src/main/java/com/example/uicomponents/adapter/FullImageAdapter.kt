package com.example.uicomponents.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.uicomponents.R
import com.example.uicomponents.model.ImageItem
import kotlinx.android.synthetic.main.item_full_image.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FullImageAdapter : ListAdapter<ImageItem, ViewHolderFullImage>(FullImageDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFullImage {
        return ViewHolderFullImage.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolderFullImage, position: Int) {
        holder.bindData(getItem(position))
    }
}

class ViewHolderFullImage(itemView: View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        fun from(parent: ViewGroup): ViewHolderFullImage {
            val layoutInflater = LayoutInflater.from(parent.context)
            return ViewHolderFullImage(layoutInflater.inflate(R.layout.item_full_image, parent, false))
        }
    }
    fun bindData(item: ImageItem) {
        CoroutineScope(Dispatchers.Main).launch {
            Glide.with(itemView.context)
                .load(item.url)
                .into(itemView.ivItemFullImage)
        }
    }
}