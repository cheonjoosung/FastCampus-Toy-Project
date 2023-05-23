package com.example.fastcampus.ch07_my_gallery

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.fastcampus.R
import com.example.fastcampus.databinding.ActivityMyGalleryBinding

class MyGalleryActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_READ_EXTERNAL_STORAGE = 100
    }

    private lateinit var binding: ActivityMyGalleryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            loadImageButton.setOnClickListener {
                checkPermission()
            }

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
            setMessage("이미지를 가져오기 위해서 외부 저장소 읽기 권한이 필요합니다.")
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
        Toast.makeText(this, "이미지 가져오기", Toast.LENGTH_SHORT).show()
    }

}