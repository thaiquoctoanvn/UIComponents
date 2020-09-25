package com.example.uicomponents.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageItem(var id: Int, var url: String) : Parcelable {

}