package com.example.uicomponents.tab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uicomponents.R
import com.example.uicomponents.ReusedFunctions
import com.example.uicomponents.adapter.RecyclerViewRightFragmentAdapter
import com.example.uicomponents.model.ExampleObject
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
        val tempList =
            ReusedFunctions.addExampleStringData(
                10
            )
        println("temp list $tempList")
        CoroutineScope(Dispatchers.IO).launch {
            tempList.forEach {
                dataList.add(it)
                withContext(Dispatchers.Main) {
                    rightFragmentAdapter?.submitList(dataList.toMutableList())
                }
                delay(1000)
            }
        }
    }
    
    private val onItemClickListener: (position: Int) -> Unit = { position ->
        dataList[position].textContent = "change"
        CoroutineScope(Dispatchers.Main).launch {
            rightFragmentAdapter?.notifyItemChanged(position)
        }
    }

    companion object {}
}
