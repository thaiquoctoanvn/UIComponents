package com.example.uicomponents.tab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uicomponents.R
import com.example.uicomponents.ReusedFunctions
import com.example.uicomponents.adapter.RecyclerViewColorFragmentAdapter
import com.example.uicomponents.model.ExampleObject
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_color.*
import kotlinx.coroutines.*

class ColorFragment : Fragment(), View.OnClickListener {

    private var isTimerRunning = false
    private lateinit var colorAdapter: RecyclerViewColorFragmentAdapter
    private var sum = 0
    private var dataList = ArrayList<ExampleObject>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_color, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnStartTime.isEnabled = false
        setAdapterToRecyclerView()
        setViewOnClickListener()
    }

    private fun setViewOnClickListener() {
        btnCreateData.setOnClickListener(this)
        btnStartTime.setOnClickListener(this)
    }

    private fun setAdapterToRecyclerView() {
        colorAdapter = RecyclerViewColorFragmentAdapter(onItemClickListener)
        rvTextColor.apply {
            adapter = colorAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun createData() {
        if(!isTimerRunning) {
            val range = etNumberOfDataColorFragment.text.toString()
            if(range == "") {
                Toast.makeText(activity, "Fill a number before creating data", Toast.LENGTH_SHORT).show()
            } else {
                dataList =
                    ReusedFunctions.addExampleStringData(
                        range.toInt()
                    )
                colorAdapter.submitList(dataList.toMutableList())
                btnStartTime.isEnabled = true
            }
        }
    }

    private fun startTimer() {
        if(!isTimerRunning) {
            isTimerRunning = true
            var timer = 0
            CoroutineScope(Dispatchers.IO).launch {
                while(timer <= 10) {
                    withContext(Dispatchers.Main) {
                        btnStartTime.text = timer.toString()
                    }
                    colorAdapter.displayCheckByTime(timer)
                    delay(1000)
                    timer++
                }
                withContext(Dispatchers.Main) {
                    Toast.makeText(activity, "Time's up", Toast.LENGTH_SHORT).show()
                    delay(2000)
                    btnStartTime.text = "Start timer"
                    isTimerRunning = false
                }
            }
        }
    }

    private val onItemClickListener: (position: Int) -> Unit = { position ->
        activity?.let {
            //Message trong snackbar phải là string, nếu không sẽ gây crash
            sum += dataList[position].textContent.toInt()
            Snackbar.make(it.findViewById(android.R.id.content), sum.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.btnCreateData -> createData()
            R.id.btnStartTime -> startTimer()
        }
    }
}
