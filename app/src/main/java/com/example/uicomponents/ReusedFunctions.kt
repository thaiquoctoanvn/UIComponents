package com.example.uicomponents

import android.util.Log
import com.example.uicomponents.model.ExampleObject
import kotlin.random.Random

class ReusedFunctions {
    companion object {
        fun addExampleStringData(range: Int): ArrayList<ExampleObject> {
            val dataList = ArrayList<ExampleObject>()
            val tempList = ArrayList<Int>()
            tempList.addAll(List(range) { Random.nextInt(0, 100) })
            tempList.forEach {
                dataList.add(
                    ExampleObject(
                        "zzz",
                        it.toString(),
                        false
                    )
                )
            }
            Log.d("dataListSize", dataList.size.toString())
            return dataList
        }
    }
}