package com.example.uicomponents.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.uicomponents.model.ExampleObject
import com.example.uicomponents.R
import kotlinx.android.synthetic.main.item_viewholder_text_color.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecyclerViewColorFragmentAdapter(
    private val onItemClick: (position: Int) -> Unit
) : ListAdapter<ExampleObject, ViewHolderTextColor>(ColorFragmentDiffCallBack()) {

    private lateinit var rv: RecyclerView

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        rv = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTextColor {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolderTextColor(inflater.inflate(R.layout.item_viewholder_text_color, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderTextColor, position: Int) {
        val textItem = getItem(position)
        holder.bindData(textItem)
        holder.itemView.setOnClickListener { onItemClick(position) }
    }

    fun displayCheckByTime(second: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            if(second % 2 == 0) {
                currentList.forEachIndexed { index, item ->
                    if(item.textContent.toInt() % 2 ==0) {
                        setCheckToHolder(item, index, true)
                    } else {
                        setCheckToHolder(item, index, false)
                    }
                }
            } else {
                currentList.forEachIndexed { index, item ->
                    if(item.textContent.toInt() % 2 != 0) {
                        setCheckToHolder(item, index, true)
                    } else {
                        setCheckToHolder(item, index, false)
                    }
                }
            }
        }
    }

    private suspend fun setCheckToHolder(item: ExampleObject, position: Int, checkState: Boolean) {
        withContext(Dispatchers.Main) {
            rv.findViewHolderForAdapterPosition(position)?.let {
                it.itemView.cbViewHolderTextColor.isChecked = checkState
                item.isSelected = checkState
            }
        }
    }
}

class ViewHolderTextColor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindData(item: ExampleObject) {
        itemView.tvViewHolderTextColor.text = item.textContent
        itemView.cbViewHolderTextColor.isChecked = item.isSelected
    }
}