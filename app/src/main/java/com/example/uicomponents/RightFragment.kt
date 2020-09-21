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
import kotlinx.android.synthetic.main.fragment_right.*
import kotlinx.coroutines.*

class RightFragment : Fragment() {

    private var rightFragmentAdapter: RecyclerViewRightFragmentAdapter? = null
    private var dataList = ArrayList<String>()
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
        dataList = ReusedFunctions.addExampleStringData(10)
        btnShowData.setOnClickListener {
            showDataOnRecyclerView()
        }
    }

    private fun setUpAdapterToRecyclerView() {
        rightFragmentAdapter = RecyclerViewRightFragmentAdapter()
        rvRightFragment.apply {
            adapter = rightFragmentAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun showDataOnRecyclerView() {
        if(!isDisplaying) {
            if(dataList.size != 0) {
                dataList.clear()
            }
            rightFragmentAdapter?.clearList()
            dataList = ReusedFunctions.addExampleStringData(10)
            isDisplaying = true
            CoroutineScope(Dispatchers.IO).launch {
                dataList.forEachIndexed { index, item ->
                    withContext(Dispatchers.Main) {
                        rightFragmentAdapter?.addList(index, item)
                    }
                    delay(1000)
                    if(dataList.size == rightFragmentAdapter?.itemCount) {
                        withContext(Dispatchers.Main) {
                            activity?.let {
                                Toast.makeText(activity, "Show data completely", Toast.LENGTH_SHORT).show()
                            }
                            isDisplaying = false
                        }
                    }
                }
            }
        }
    }

    companion object {}
}
