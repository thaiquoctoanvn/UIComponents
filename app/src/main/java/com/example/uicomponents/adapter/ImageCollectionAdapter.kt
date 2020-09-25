package com.example.uicomponents.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.RectF
import android.net.Uri
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.R
import com.example.uicomponents.model.ImageItem
import kotlinx.android.synthetic.main.item_thumbnail.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.abs
import kotlin.math.max

class ImageCollectionAdapter(
    private val onItemClick: (position: Int) -> Unit,
    private val onItemDelete: (position: Int) -> Unit
) : ListAdapter<ImageItem, ViewHolderThumbnail>(ImageCollectionDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderThumbnail {
        return ViewHolderThumbnail.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolderThumbnail, position: Int) {
        holder.bindData(getItem(position))
        holder.itemView.ivThumbnail.setOnClickListener { onItemClick(position) }
        holder.itemView.ibThumbnailDelete.setOnClickListener { onItemDelete(position) }
    }
}

class ViewHolderThumbnail(itemView: View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        fun from(parent: ViewGroup): ViewHolderThumbnail {
            val layoutInflater = LayoutInflater.from(parent.context)
            return ViewHolderThumbnail(layoutInflater.inflate(com.example.uicomponents.R.layout.item_thumbnail, parent, false))
        }
    }

    fun bindData(imageItem: ImageItem) {
        itemView.tvThumbnailIndex.text = imageItem.id.toString()
        CoroutineScope(Dispatchers.IO).launch {
            val bitmap = scaleBitmap(imageItem.url)
            bitmap?.let {
                withContext(Dispatchers.Main) {
                    Glide.with(itemView.context)
                        .load(it)
                        .placeholder(com.example.uicomponents.R.color.colorPrimary)
                        .into(itemView.ivThumbnail)
                }
            }
        }
    }

    private fun scaleBitmap(uri: String): Bitmap? {
        val requiredSize = 1080
        val inputStream = itemView.context.contentResolver.openInputStream(Uri.parse(uri))
        val srcBitmap = BitmapFactory.decodeStream(inputStream)
        var srcWidth = srcBitmap.width
        var srcHeight = srcBitmap.height

        while(srcWidth / 2 >= requiredSize || srcHeight / 2 >= requiredSize) {
            srcWidth /= 2
            srcHeight /= 2

        }
        return Bitmap.createScaledBitmap(srcBitmap, srcWidth, srcHeight, true)
    }

    private fun scaleCenterCrop(uri: String): Bitmap {
        val requiredSize = 1080
        val inputStream = itemView.context.contentResolver.openInputStream(Uri.parse(uri))
        val originalBitmap = BitmapFactory.decodeStream(inputStream)
        val sourceWidth: Int = originalBitmap.width
        val sourceHeight: Int = originalBitmap.height
        val xScale = (requiredSize / sourceWidth).toFloat()
        val yScale = (requiredSize / sourceHeight).toFloat()
        val scale = max(xScale, yScale)
        val scaleWidth = (sourceWidth * scale).toFloat()
        val scaleHeight = (sourceHeight * scale).toFloat()
        val left: Float = (abs(requiredSize - scaleWidth)) / 2
        val top: Float = (abs(requiredSize - scaleHeight)) / 2
        val targetRect = RectF(left, top, left + scaleWidth, top + scaleHeight)
        val squaredBitmap = Bitmap.createBitmap(requiredSize, requiredSize, originalBitmap.config)
        val canvas = Canvas(squaredBitmap)
        canvas.drawBitmap(originalBitmap, null, targetRect, null)

        return squaredBitmap
    }
}