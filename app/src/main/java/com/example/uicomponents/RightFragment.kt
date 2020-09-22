package com.example.uicomponents

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uicomponents.adapter.RecyclerViewRightFragmentAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_right.*
import kotlinx.coroutines.*

class RightFragment : Fragment() {

    private var rightFragmentAdapter: RecyclerViewRightFragmentAdapter? = null
    private var dataList = ArrayList<ExampleObject>()
    private var isDisplaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_right, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpAdapterToRecyclerView()
        btnShowData.setOnClickListener {
            addData()
        }
    }

    private fun setUpAdapterToRecyclerView() {
        rightFragmentAdapter = RecyclerViewRightFragmentAdapter(onItemClickListener)
        rvRightFragment.apply {
            adapter = rightFragmentAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun addData() {
        val tempList = createExampleObjectList()
        println("temp list $tempList")
        CoroutineScope(Dispatchers.IO).launch {
            tempList.forEach {
                dataList.add(it)
                withContext(Dispatchers.Main) {
                    rightFragmentAdapter?.swapData(dataList)
                }
                delay(1000)
            }
        }
    }

    private fun createExampleObjectList(): ArrayList<ExampleObject> {
        val textContent = "Greeting"
        val listObject = ArrayList<ExampleObject>()
        for(index in 0 until 13) {
            listObject.add(ExampleObject(index.toString(), textContent, true))
        }
        return listObject
    }
    
    private val onItemClickListener: (itemView: View, position: Int) -> Unit = {itemView, position ->  
        val newDataList = ArrayList(rightFragmentAdapter?.currentList)
        newDataList.removeAt(position)
        newDataList.add(position, ExampleObject(dataList[position].imageSource, "change", true))
        CoroutineScope(Dispatchers.Main).launch {
            rightFragmentAdapter?.swapData(newDataList)
        }
    }

    companion object {}
}
