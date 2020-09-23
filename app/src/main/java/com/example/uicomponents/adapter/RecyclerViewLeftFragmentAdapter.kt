package com.example.uicomponents.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.uicomponents.model.ExampleObject
import com.example.uicomponents.R
import com.example.uicomponents.listener.RecyclerViewItemClickListener
import kotlinx.android.synthetic.main.item_viewholder.view.*
import kotlinx.android.synthetic.main.item_viewholder_loading.view.*
import kotlinx.android.synthetic.main.item_viewholder_without_checkbox.view.*

class RecyclerViewLeftFragmentAdapter(
    private val dataList: ArrayList<ExampleObject>,
    private var listener: RecyclerViewItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var context: Context

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun getItemViewType(position: Int): Int {
        return if(dataList[position].textContent == "null") 0 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        return if(viewType == 0) {
            ViewHolderLoading(
                inflater.inflate(
                    R.layout.item_viewholder_loading,
                    parent,
                    false
                )
            )
        } else {
            ViewHolderWithCheckBox(
                inflater.inflate(
                    R.layout.item_viewholder,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataList[position]
        when(holder) {
            is ViewHolderWithCheckBox -> holder.bindDataForOddItem(context, item)
            is ViewHolderWithoutCheckBox -> holder.bindDataForEvenItem(context, item)
            is ViewHolderLoading -> holder.showLoading()
            else -> Log.d("###", "No suitable type")
        }
        holder.itemView.setOnClickListener {
            listener.setOnItemClickListener(it, position)
        }
    }
}

class ViewHolderWithCheckBox(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindDataForOddItem(context: Context, exampleObject: ExampleObject) {
        itemView.cbViewHolder.isChecked = exampleObject.isSelected
        itemView.tvViewHolder.text = exampleObject.textContent
        Glide.with(context)
            .load(exampleObject.imageSource)
            .into(itemView.ivViewHolder)
    }
}

class ViewHolderWithoutCheckBox(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindDataForEvenItem(context: Context, exampleObject: ExampleObject) {
        itemView.tvViewHolderWithoutCheckBox.text = exampleObject.textContent
        Glide.with(context)
            .load(exampleObject.imageSource)
            .into(itemView.ivViewHolderWithoutCheckBox)
    }
}

class ViewHolderLoading(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun showLoading() {
        itemView.pbViewHolderLoading.show()
    }
}
