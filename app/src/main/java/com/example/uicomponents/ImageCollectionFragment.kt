package com.example.uicomponents

import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uicomponents.adapter.ImageCollectionAdapter
import com.example.uicomponents.model.ImageItem
import kotlinx.android.synthetic.main.fragment_image_collection.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.ArrayList
import java.util.jar.Manifest

class ImageCollectionFragment : Fragment(), View.OnClickListener {

    private val GALLERY_REQUEST = 10
    private val PERMISSION = android.Manifest.permission.READ_EXTERNAL_STORAGE
    private lateinit var imageCollectionAdapter: ImageCollectionAdapter
    private val listImageItem = mutableListOf<ImageItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_collection, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        setUpAdapter()
        setViewOnClickListener()
    }

    private fun setViewOnClickListener() {
        fabAddImageCollection.setOnClickListener(this)
    }

    private fun readImageFromDeviceStorage(): MutableList<String> {
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val uri2 = MediaStore.Images.Media.DOCUMENT_ID
        Log.d("uri2", uri2.toString())
        val cursor: Cursor?
        val columnIndexData: Int
        val listAllImages = mutableListOf<String>()
        var imageId: Long

        val projection = arrayOf(MediaStore.Images.Media._ID)
        activity?.let {
            cursor = it.contentResolver.query(uri, projection, null, null, null)
            if(cursor != null) {
                columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                while(cursor.moveToNext()) {
                    imageId = cursor.getLong(columnIndexData)
                    Log.d("imageId", imageId.toString())
                    val uriImage = Uri.withAppendedPath(uri, "" + imageId)
                    Log.d("uriImage", uriImage.toString())
                    listAllImages.add(uriImage.toString())
                }
                cursor.close()
            }
        }
        return listAllImages
    }

    private fun checkPermission() {
        when {
            activity?.let {
                ContextCompat.checkSelfPermission(it, PERMISSION) == PackageManager.PERMISSION_GRANTED
            }!! -> {
                loadAllImage()
            }
            else -> {
                requestPermissions(arrayOf(PERMISSION), GALLERY_REQUEST)
            }
        }
    }

    private fun loadAllImage() {
        CoroutineScope(Dispatchers.IO).launch {
            val tempList = readImageFromDeviceStorage()
            tempList.forEachIndexed { index, item ->
                listImageItem.add(ImageItem(index, item))
            }
            withContext(Dispatchers.Main) {
                imageCollectionAdapter.submitList(listImageItem)
            }
        }
    }

    private fun setUpAdapter() {
        val dpi = resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT
        val numberItemInRow = (resources.displayMetrics.widthPixels / dpi) / 160
        imageCollectionAdapter = ImageCollectionAdapter(onItemClickListener, onItemDeleteListener)
        rvImageCollection.apply {
            adapter = imageCollectionAdapter
            layoutManager = GridLayoutManager(context, numberItemInRow)
        }
    }

    private val onItemClickListener: (position: Int) -> Unit = {
        val vmShareListImageViewModel by activityViewModels<ShareListImageViewModel>()
        vmShareListImageViewModel.getListImageItem().value = listImageItem
        Log.d("setVMList", vmShareListImageViewModel.getListImageItem().value!!.size.toString())
        val intent = Intent(activity, FullImageActivity::class.java)
        intent.putParcelableArrayListExtra("listImage", listImageItem as ArrayList<ImageItem>)
        startActivity(intent)
    }

    private val onItemDeleteListener: (position: Int) -> Unit = {
        listImageItem.removeAt(it)
        imageCollectionAdapter.notifyItemRemoved(it)
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.fabAddImageCollection -> checkPermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadAllImage()
        }
    }
}
