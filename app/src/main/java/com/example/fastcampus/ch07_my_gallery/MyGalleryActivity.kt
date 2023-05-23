package com.example.fastcampus.ch07_my_gallery

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fastcampus.R
import com.example.fastcampus.ch07_my_gallery.FrameActivity.Companion.IMAGES
import com.example.fastcampus.databinding.ActivityMyGalleryBinding

class MyGalleryActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_READ_EXTERNAL_STORAGE = 100
    }

    private lateinit var imageAdapter: ImageAdapter

    private lateinit var binding: ActivityMyGalleryBinding

    private val imageLoadLauncher =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uriList ->
            uriList?.let { updateImages(it) }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            loadImageButton.setOnClickListener {
                checkPermission()
            }

            navigateFrameActivityButton.setOnClickListener {
                val images = imageAdapter.currentList.filterIsInstance<ImageItems.Image>()
                    .map { it.uri.toString() }.toTypedArray()

                if (images.isNullOrEmpty()) {
                    Toast.makeText(applicationContext, "선택된 이미지가 없습니다.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val intent = Intent(applicationContext, FrameActivity::class.java)
                    .putExtra(IMAGES, images)
                startActivity(intent)
            }
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        imageAdapter = ImageAdapter(object : ImageAdapter.ItemClickListener {
            override fun onLoadMoreClick() {
                checkPermission()
            }
        })

        binding.imageRecyclerView.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(context, 3)
        }
    }

    private fun checkPermission() {
        Log.e(localClassName, "checkPermission")
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.e(localClassName, "checkPermission Granted")
                loadImage()
            }

            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.e(localClassName, "checkPermission Granted for android 13")
                loadImage()
            }

            shouldShowRequestPermissionRationale(
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {
                Log.e(localClassName, "checkPermission should")
                showPermissionInformDialog()
            }

            shouldShowRequestPermissionRationale(
                Manifest.permission.READ_MEDIA_IMAGES
            ) -> {
                Log.e(localClassName, "checkPermission should for android 13")
                showPermissionInformDialog()
            }

            else -> {
                Log.e(localClassName, "checkPermission else")
                requestReadExternalStorage()
            }
        }
    }

    private fun showPermissionInformDialog() {
        Log.e(localClassName, "showPermissionInformDialog()")
        AlertDialog.Builder(this).apply {
            setMessage(getString(R.string.msg_permission_for_read_image))
            setNegativeButton("취소", null)
            setPositiveButton("동의") { _, _ ->
                requestReadExternalStorage()
            }
        }.show()
    }

    private fun requestReadExternalStorage() {
        Log.e(localClassName, "requestReadExternalStorage()")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                REQUEST_READ_EXTERNAL_STORAGE
            )
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_READ_EXTERNAL_STORAGE
            )
        }
    }

    private fun loadImage() {
        Toast.makeText(this, getString(R.string.msg_load_image), Toast.LENGTH_SHORT).show()
        imageLoadLauncher.launch("image/*")
    }

    private fun updateImages(uriList: List<Uri>) {
        val images = uriList.map { ImageItems.Image(it) }
        val updatedImages = imageAdapter.currentList.toMutableList().apply {
            addAll(images)
        }
        imageAdapter.submitList(updatedImages)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_READ_EXTERNAL_STORAGE -> {
                val resultCode = grantResults.firstOrNull() ?: PackageManager.PERMISSION_DENIED
                if (resultCode == PackageManager.PERMISSION_GRANTED) {
                    loadImage()
                }
            }
        }
    }
}