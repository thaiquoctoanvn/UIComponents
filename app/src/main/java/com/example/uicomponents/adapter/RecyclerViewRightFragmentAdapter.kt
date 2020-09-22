package com.example.uicomponents.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uicomponents.ExampleObject
import com.example.uicomponents.R
import kotlinx.android.synthetic.main.item_viewholder_fragment_right.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList

class RecyclerViewRightFragmentAdapter(
    private val onItemClick: (itemView: View, position: Int) -> Unit
) : androidx.recyclerview.widget.ListAdapter<ExampleObject, ViewHolderRightFragment>(RightFragmentDiffCallBack()) {
    //diff util callback
    //list adapter

    private lateinit var rv: RecyclerView
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        rv = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderRightFragment {
        return ViewHolderRightFragment.from(parent)
    }

    fun swapData(data: List<ExampleObject>) {
        submitList(data.toMutableList())
    }

    override fun onBindViewHolder(
        holder: ViewHolderRightFragment,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        if(!payloads.isNullOrEmpty()) {
            val temp = payloads[0] as ExampleObject
            holder.bindData(temp)
            println(temp.textContent)
            holder.itemView.setOnClickListener { onItemClick(holder.itemView, position) }
            rv.layoutManager?.scrollToPosition(position)
        }
    }

    override fun onBindViewHolder(holder: ViewHolderRightFragment, position: Int) {
        holder.bindData(getItem(position))
        holder.itemView.setOnClickListener { onItemClick(holder.itemView, position) }
        rv.layoutManager?.scrollToPosition(position)
    }

}

class ViewHolderRightFragment(itemView: View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        fun from(parent: ViewGroup): ViewHolderRightFragment {
            val layoutInflater = LayoutInflater.from(parent.context)
            return ViewHolderRightFragment(layoutInflater.inflate(
                R.layout.item_viewholder_fragment_right,
                parent,
                false))
        }
    }
    fun bindData(item: ExampleObject) {
        itemView.tvViewHolderFragmentRight.text = item.textContent
    }
}