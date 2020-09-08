package com.example.uicomponents

import android.content.Context
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecyclerViewLeftFragmentAdapter(
    private val dataList: ArrayList<ExampleObject>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var context: Context

    override fun getItemViewType(position: Int): Int {
        return position % 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        return if(viewType == 0) {
            ViewHolderWithoutCheckBox(inflater.inflate(R.layout.item_viewholder_without_checkbox, parent, false))
        } else {
            ViewHolderWithCheckBox(inflater.inflate(R.layout.item_viewholder, parent, false))
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
            else -> Log.d("###", "No suitable type")
        }
    }
}

class ViewHolderWithCheckBox(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val ivViewHolder = itemView.findViewById<ImageView>(R.id.ivViewHolder)
    private val tvViewHolder = itemView.findViewById<TextView>(R.id.tvViewHolder)
    private val cbViewHolder = itemView.findViewById<CheckBox>(R.id.cbViewHolder)

    fun bindDataForOddItem(context: Context, exampleObject: ExampleObject) {
        cbViewHolder.isChecked = exampleObject.isSelected
        tvViewHolder.text = exampleObject.textContent
        Glide.with(context)
            .load(exampleObject.imageSource)
            .into(ivViewHolder)
    }
}

class ViewHolderWithoutCheckBox(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val ivViewHolderWithoutCheckBox = itemView.findViewById<ImageView>(R.id.ivViewHolderWithoutCheckBox)
    private val tvViewHolderWithoutCheckBox = itemView.findViewById<TextView>(R.id.tvViewHolderWithoutCheckBox)

    fun bindDataForEvenItem(context: Context, exampleObject: ExampleObject) {
        tvViewHolderWithoutCheckBox.text = exampleObject.textContent
        Glide.with(context)
            .load(exampleObject.imageSource)
            .into(ivViewHolderWithoutCheckBox)
    }
}
