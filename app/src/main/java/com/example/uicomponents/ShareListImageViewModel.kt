package com.example.uicomponents

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.uicomponents.model.ImageItem

class ShareListImageViewModel : ViewModel() {
    private val listImageItem = MutableLiveData<MutableList<ImageItem>>()
    fun getListImageItem(): MutableLiveData<MutableList<ImageItem>> {
        return listImageItem
    }
}