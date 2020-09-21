package com.example.uicomponents.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uicomponents.R
import com.example.uicomponents.listener.RecyclerViewItemClickListener
import com.google.android.material.checkbox.MaterialCheckBox
import kotlinx.android.synthetic.main.item_viewholder_text_color.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecyclerViewColorFragmentAdapter(
    private val listString: ArrayList<String>,
    private val onItemClick: (view: View, position: Int) -> Unit
) : RecyclerView.Adapter<ViewHolderTextColor>() {

    private lateinit var rv: RecyclerView

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        rv = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTextColor {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolderTextColor(inflater.inflate(R.layout.item_viewholder_text_color, parent, false))
    }

    override fun getItemCount(): Int {
        return listString.size
    }

    override fun onBindViewHolder(holder: ViewHolderTextColor, position: Int) {
        val textItem = listString[position]
        holder.bindData(textItem)
        holder.itemView.setOnClickListener { onItemClick(holder.itemView, position) }
    }

    fun displayCheckByTime(second: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            if(second % 2 == 0) {
                listString.forEachIndexed { index, item ->
                    if(item.toInt() % 2 ==0) {
                        setCheckToHolder(index, true)
                    } else {
                        setCheckToHolder(index, false)
                    }
                }
            } else {
                listString.forEachIndexed { index, item ->
                    if(item.toInt() % 2 != 0) {
                        setCheckToHolder(index, true)
                    } else {
                        setCheckToHolder(index, false)
                    }
                }
            }
        }
    }

    private suspend fun setCheckToHolder(position: Int, checkState: Boolean) {
        withContext(Dispatchers.Main) {
            rv.findViewHolderForAdapterPosition(position)?.let {
                it.itemView.cbViewHolderTextColor.isChecked = checkState
            }
        }
    }
}

class ViewHolderTextColor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindData(textItem: String) {
        itemView.tvViewHolderTextColor.text = textItem
    }
}