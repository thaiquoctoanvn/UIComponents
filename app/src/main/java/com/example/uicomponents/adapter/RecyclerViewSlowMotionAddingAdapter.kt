package com.example.uicomponents.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uicomponents.ExampleObject
import com.example.uicomponents.R
import kotlinx.android.synthetic.main.item_slowmotion_adding.view.*
import org.w3c.dom.Text

class RecyclerViewSlowMotionAddingAdapter(
    private val numberList: ArrayList<ExampleObject>
) : RecyclerView.Adapter<ViewHolderSlowMotionAdding>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSlowMotionAdding {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolderSlowMotionAdding(inflater.inflate(R.layout.item_slowmotion_adding, parent, false))
    }

    override fun getItemCount(): Int {
        return numberList.size
    }

    override fun onBindViewHolder(holder: ViewHolderSlowMotionAdding, position: Int) {
        holder.bindData(numberList[position])
    }

}

class ViewHolderSlowMotionAdding(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val tvViewHolderSlowMotionAdding = itemView.findViewById<TextView>(R.id.tvViewHolderSlowMotionAdding)

    fun bindData(numberItem: ExampleObject) {
        tvViewHolderSlowMotionAdding.text = numberItem.textContent
    }
}