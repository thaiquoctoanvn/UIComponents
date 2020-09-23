package com.example.uicomponents.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uicomponents.model.ExampleObject
import com.example.uicomponents.R
import kotlinx.android.synthetic.main.item_viewholder_fragment_right.view.*

class RecyclerViewRightFragmentAdapter(
    private val onItemClick: (position: Int) -> Unit
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
            holder.itemView.setOnClickListener { onItemClick(position) }
            rv.layoutManager?.scrollToPosition(position)
        }
    }

    override fun onBindViewHolder(holder: ViewHolderRightFragment, position: Int) {
        holder.bindData(getItem(position))
        holder.itemView.setOnClickListener { onItemClick(position) }
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