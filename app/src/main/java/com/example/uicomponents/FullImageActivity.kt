package com.example.uicomponents

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.activityViewModels
import com.example.uicomponents.adapter.FullImageAdapter
import com.example.uicomponents.model.ImageItem
import kotlinx.android.synthetic.main.fragment_full_image.*

class FullImageActivity : AppCompatActivity() {

    private lateinit var fullImageAdapter: FullImageAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_image)
        supportActionBar?.hide()
        getIntentData()
    }

    private fun getIntentData() {
        val listImage = intent.getParcelableArrayListExtra<ImageItem>("listImage")
        setUpAdapter()
        fullImageAdapter.submitList(listImage?.toMutableList())
    }

    private fun setUpAdapter() {
        fullImageAdapter = FullImageAdapter()
        vpFullImage.apply {
            adapter = fullImageAdapter
            setPageTransformer(DepthPageTransformer())
        }
    }
}
