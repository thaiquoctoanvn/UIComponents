package com.example.uicomponents

import android.util.Log
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
        fun addExampleStringData(range: Int): ArrayList<ExampleObject> {
            val dataList = ArrayList<ExampleObject>()
            val tempList = ArrayList<Int>()
            tempList.addAll(List(range) { Random.nextInt(0, 100) })
            tempList.forEach {
                dataList.add(ExampleObject("zzz", it.toString(), false))
            }
            Log.d("dataListSize", dataList.size.toString())
            return dataList
        }
    }
}