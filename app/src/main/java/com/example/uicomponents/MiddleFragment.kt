package com.example.uicomponents

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Filter
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_middle.*
import kotlinx.coroutines.*

class MiddleFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_middle, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        validateInput()
        btnSum.setOnClickListener {
            hideKeyBoard()
            calculate()
        }
    }

    private fun calculate() {
        CoroutineScope(Dispatchers.IO).launch {
            if(!TextUtils.isEmpty(etNumber1.text) && !TextUtils.isEmpty(etNumber2.text)) {
                var result = etNumber1.text.toString().toInt() + etNumber2.text.toString().toInt()
                delay(3000)
                Log.d("###", "Wait for 3 minutes")
                result *= result
                withContext(Dispatchers.Main) {
                    activity?.let {
                        Snackbar.make(
                            it.findViewById(android.R.id.content),
                            "Result: $result",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    activity?.let {
                        Snackbar.make(
                            it.findViewById(android.R.id.content),
                            "Must have two numbers for each text field",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun hideKeyBoard() {
        val inputMethodManager = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
    }

    private fun validateInput() {
        etNumber1.filters = arrayOf(object : InputFilter {
            override fun filter(
                source: CharSequence?,
                start: Int,
                end: Int,
                dest: Spanned?,
                dstart: Int,
                dend: Int
            ): String? {
                return source?.subSequence(start, end)?.replace(Regex("[A-Za-z*/% ]"), "")
            }
        })
    }
    companion object {}
}
