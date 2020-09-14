package com.example.uicomponents

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.uicomponents.adapter.RecyclerViewLeftFragmentAdapter
import com.example.uicomponents.listener.RecyclerViewItemClickListener
import kotlinx.android.synthetic.main.fragment_left.*
import kotlinx.coroutines.*

class LeftFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener,
    RecyclerViewItemClickListener {

    private lateinit var exampleAdapter: RecyclerViewLeftFragmentAdapter
    private val exampleList = ArrayList<ExampleObject>()
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
       exampleAdapter =
           RecyclerViewLeftFragmentAdapter(
               exampleList,
               this
           )
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
                if(!isLoading && !recyclerView.canScrollVertically(1)) {
                    Log.d("###", "Loading more")
                    isLoading = true
                    loadMore()
                } else if(!recyclerView.canScrollVertically(-1)) {
                    exampleList.clear()
                    exampleAdapter.notifyDataSetChanged()
                    addExampleData()
                    exampleAdapter.notifyDataSetChanged()
                }

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    private fun loadMore() {
        exampleList.add(ExampleObject("", "null", false))
        exampleAdapter.notifyItemInserted(exampleList.size - 1)
        GlobalScope.launch(Dispatchers.Main) {
            Log.d("###", "Refreshing data")
            delay(2000)
            exampleList.removeAt(exampleList.size - 1)
            exampleAdapter.notifyItemRemoved(exampleList.size - 1)
            exampleList.add(ExampleObject(
                "https://thatgrapejuice.net/wp-content/uploads/2020/01/justin-bieber-changes-album-tgj.jpg",
                "load more 1",
                false)
            )
            exampleList.add(ExampleObject(
                "https://thatgrapejuice.net/wp-content/uploads/2020/01/justin-bieber-changes-album-tgj.jpg",
                "load more 2",
                false)
            )
            exampleAdapter.notifyDataSetChanged()
            isLoading = false
        }
    }

    private fun pullToRefresh() {
        exampleList.clear()
        exampleAdapter.notifyDataSetChanged()
        GlobalScope.launch(Dispatchers.Main) {
            delay(2000)
            addExampleData()
            exampleAdapter.notifyDataSetChanged()
        }
        swipeContainer.isRefreshing = false
    }

    private fun addExampleData() {
        val img1 = "https://media.istockphoto.com/vectors/2019ncov-coronovirus-alert-dangerous-virus-epidemic-chinese-pneumonia-vector-id1265247958"
        val img2 = "https://media.istockphoto.com/vectors/the-new-normal-related-flat-style-banner-design-with-icons-vector-vector-id1257424769"
        val img3 = "https://media.istockphoto.com/vectors/uncertainty-in-business-due-covid19-pandemic-concept-vector-id1258944264"
        val img4 = "https://media.istockphoto.com/vectors/health-worker-points-a-temperature-gun-to-passengers-screening-for-vector-id1260169325"
        val img5 = "https://media.istockphoto.com/illustrations/faces-of-various-people-wearing-masks-illustration-id1257530912"
        val img6 = "https://media.istockphoto.com/vectors/quarantine-vector-id1255951361"
        val img7 = "https://media.istockphoto.com/vectors/firefighters-in-covid19-pandemic-flat-isolated-vector-illustration-vector-id1253986851"
        val img8 = "https://media.istockphoto.com/vectors/social-distancing-w-masks-imperial-vector-id1257428548"
        val img9 = "https://media.istockphoto.com/vectors/hospital-staff-pattern-doctors-and-nurses-in-protective-suits-and-vector-id1251354378"
        val img10 = "https://media.istockphoto.com/vectors/stay-home-card-girl-self-isolation-in-room-with-cup-of-coffee-vector-id1251354243"

        val textContent = "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged."

        exampleList.apply {
            add(ExampleObject(img1, textContent, false))
            add(ExampleObject(img2, textContent, true))
            add(ExampleObject(img3, textContent, false))
            add(ExampleObject(img4, textContent, false))
            add(ExampleObject(img5, textContent, false))
            add(ExampleObject(img6, textContent, true))
            add(ExampleObject(img7, textContent, false))
            add(ExampleObject(img8, textContent, false))
            add(ExampleObject(img9, textContent, false))
            add(ExampleObject(img10, textContent, false))
        }
    }

    companion object {}

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
