package com.example.uicomponents.tab

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uicomponents.R
import com.example.uicomponents.ReusedFunctions
import com.example.uicomponents.adapter.RecyclerViewSlowMotionAddingAdapter
import com.example.uicomponents.model.ExampleObject
import kotlinx.android.synthetic.main.fragment_slow_motion_adding.*
import kotlinx.coroutines.*

class SlowMotionAddingFragment : Fragment(), View.OnClickListener {

    private var numberList = ArrayList<ExampleObject>()
    private var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_slow_motion_adding, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setViewOnClickListener()
        setViewOnClickListener()
    }

    private fun setViewOnClickListener() {
        btnRandomData.setOnClickListener(this)
        btnTotal.setOnClickListener(this)
        btnTotal.isEnabled = false
    }

    private fun setAdapterToView() {
        val slowMotionAddingAdapter = RecyclerViewSlowMotionAddingAdapter(numberList)
        rvSlowMotionAdding.apply {
            adapter = slowMotionAddingAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun randomData() {
        if(!isRunning) {
            val range = etNumberOfDataSlowMotionFragment.text.toString()
            if("" == range) {
                Toast.makeText(activity, "Fill a number of data before creating", Toast.LENGTH_SHORT).show()
            } else {
                numberList =
                    ReusedFunctions.addExampleStringData(
                        range.toInt()
                    )
                setAdapterToView()
                btnTotal.isEnabled = true
            }
        }
    }

    private fun createLoadingDialog(): Dialog {
        val loadingDialog = Dialog(activity as Context)
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        loadingDialog.setCanceledOnTouchOutside(false)
        loadingDialog.setContentView(R.layout.item_viewholder_loading)
        loadingDialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        return loadingDialog
    }

    private fun calculateSum() {
        if(!isRunning) {
            isRunning = true
            val loading = createLoadingDialog()
            CoroutineScope(Dispatchers.IO).launch {
                var sum = 0
                for(number in numberList) {
                    withContext(Dispatchers.Main) {
                        loading.show()
                    }
                    delay(2000)
                    sum += number.textContent.toInt()
                    withContext(Dispatchers.Main) {
                        loading.dismiss()
                    }
                    btnTotal.text = "Sum: $sum"
                    delay(2000)
                }
                isRunning = false
            }
        }
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.btnRandomData -> randomData()
            R.id.btnTotal -> calculateSum()
        }
    }


}
