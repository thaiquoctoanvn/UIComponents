package com.example.uicomponents

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.uicomponents.adapter.RecyclerViewLeftFragmentAdapter
import com.example.uicomponents.listener.RecyclerViewItemClickListener
import kotlinx.android.synthetic.main.fragment_left.*
import kotlinx.coroutines.*

class LeftFragment :
    Fragment(),
    SwipeRefreshLayout.OnRefreshListener,
    RecyclerViewItemClickListener {

    private lateinit var exampleAdapter: RecyclerViewLeftFragmentAdapter
    private val exampleList = ArrayList<ExampleObject>()
    private val mainList = ArrayList<ExampleObject>()
    private var isLoading = false
    private var isScrolling = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addExampleData()
        return inflater.inflate(R.layout.fragment_left, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpAdapterToRecyclerView()
        setViewOnClickListener()
    }

    private fun setUpAdapterToRecyclerView() {
        exampleAdapter = RecyclerViewLeftFragmentAdapter(mainList, this)
        rvLeftFragment.apply {
            adapter = exampleAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun setViewOnClickListener() {
        swipeContainer.setOnRefreshListener(this)
        rvLeftFragment.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(!isLoading &&
                    !recyclerView.canScrollVertically(1) &&
                    exampleList.size > mainList.size &&
                    isScrolling
                ) {
                    Log.d("###", "Loading more")
                    loadMore()
                } else if(exampleList.size == mainList.size) {

                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                isScrolling = newState == RecyclerView.SCROLL_STATE_DRAGGING
                Log.d("isScrolling", "$isScrolling")
            }
        })
    }

    private fun loadMore() {
        isLoading = true
        mainList.add(ExampleObject("", "null", false))
        exampleAdapter.notifyItemInserted(mainList.size - 1)
        GlobalScope.launch(Dispatchers.IO) {
            Log.d("###", "Loading more data")
            delay(2000)
            withContext(Dispatchers.Main) {
                mainList.removeAt(mainList.size - 1)
                exampleAdapter.notifyItemRemoved(mainList.size - 1)
                addDataToMainList(mainList.size)
                exampleAdapter.notifyDataSetChanged()
                isLoading = false
            }
        }
    }

    private fun pullToRefresh() {
        mainList.clear()
        exampleAdapter.notifyDataSetChanged()
        // Đánh dấu để ko load more khi đang refresh lại list
        isLoading = true
        GlobalScope.launch(Dispatchers.IO) {
            delay(2000)
            Log.d("###", "Refreshing data")
            addDataToMainList(mainList.size)
            withContext(Dispatchers.Main) {
                exampleAdapter.notifyDataSetChanged()
                swipeContainer.isRefreshing = false
                isLoading = false
            }
        }
    }

    private fun addExampleData() {
        val img1 = "https://media.istockphoto.com/vectors/2019ncov-coronovirus-alert-dangerous-virus-epidemic-chinese-pneumonia-vector-id1265247958"
        val img2 = "https://media.istockphoto.com/vectors/the-new-normal-related-flat-style-banner-design-with-icons-vector-vector-id1257424769"
        val textContent = "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged."

        exampleList.apply {
            for(index in 0 until 45) {
                if(index % 2 == 0) {
                    add(ExampleObject(img1, textContent, true))
                } else {
                    add(ExampleObject(img2, textContent, false))
                }
            }
        }

        for(index in 0 until 10) {
            mainList.add(exampleList[index])
        }
    }

    private fun addDataToMainList(mainListSize: Int) {
        if(exampleList.size - mainListSize < 10) {
            for(index in mainListSize until exampleList.size) {
                mainList.add(exampleList[index])
            }
        } else {
            for(index in mainListSize until mainListSize + 10) {
                mainList.add(exampleList[index])
            }
        }
    }

    override fun onRefresh() {
        swipeContainer.setColorSchemeResources(
            R.color.colorPrimary,
            R.color.colorPrimaryDark,
            R.color.colorAccent,
            R.color.colorSubAccent
        )
        pullToRefresh()
    }

    override fun setOnItemClickListener(itemView: View, position: Int) {
        val intent = Intent(activity, DetailActivity::class.java)
        startActivity(intent)
    }
}
