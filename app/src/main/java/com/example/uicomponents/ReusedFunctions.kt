package com.example.uicomponents

import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uicomponents.adapter.RecyclerViewRightFragmentAdapter
import kotlinx.android.synthetic.main.fragment_right.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class ReusedFunctions {
    companion object {
        fun addExampleStringData(range: Int): ArrayList<String> {
            val dataList = ArrayList<String>()
            CoroutineScope(Dispatchers.IO).launch {
                dataList.addAll(List(range) { Random.nextInt(0, 100).toString() })
            }
            return dataList
        }
    }
}