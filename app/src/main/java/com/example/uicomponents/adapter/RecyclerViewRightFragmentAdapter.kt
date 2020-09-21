package com.example.uicomponents.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uicomponents.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList

class RecyclerViewRightFragmentAdapter() : RecyclerView.Adapter<RecyclerViewRightFragmentAdapter.ViewHolderRightFragment>() {
    //diff util callback
    //list adapter
    //nên dùng hàm unit truyền vào hơn interface, interface phổ biến hơn trong java
    //dạng hàm unit tương tự void (higher function)
    //nếu item cần nhiều click listener thì dùng interface, ít thì dùng higher function
    private val listString = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderRightFragment {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolderRightFragment(inflater.inflate(R.layout.item_viewholder_fragment_right, parent, false))
    }

    override fun getItemCount(): Int {
        return listString.size
    }

    override fun onBindViewHolder(holder: ViewHolderRightFragment, position: Int) {
        val itemString = listString[position]
        holder.bindData(itemString)
    }

    fun addList(position: Int, itemString: String) {
        listString.add(itemString)
//        notifyDataSetChanged() khi gọi sẽ load lại toàn bộ recyclerview
        notifyItemRangeInserted(position, 1)

    }

    fun clearList() {
        if(listString.size != 0) {
            listString.clear()
            notifyDataSetChanged()
        }
    }

    fun showItemOneByOne() {
    }

    inner class ViewHolderRightFragment(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvViewHolderFragmentRight = itemView.findViewById<TextView>(R.id.tvViewHolderFragmentRight)
        fun bindData(itemString: String) {
            tvViewHolderFragmentRight.text = itemString

        }
    }
}