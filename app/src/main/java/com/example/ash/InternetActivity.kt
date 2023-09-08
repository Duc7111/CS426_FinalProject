package com.example.ash

import android.content.pm.PackageManager
import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.net.URL

class InternetActivity: AppCompatActivity() {
    private val fileUrl = "https://example.com/file.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Request the internet permission


        // Read the file
        val inputStream = URL(fileUrl).openStream()
        var fileContent = inputStream.bufferedReader().readLine()

        // Modify the file
        fileContent = fileContent.replace("Hello, world!", "Goodbye, world!")

        // Write the file
        val outputStream = URL(fileUrl).openConnection().outputStream
        outputStream.write(fileContent.toByteArray())
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // The internet permission has been granted
        } else {
            // The internet permission has been denied
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.i("Permission: ", "Granted")
            } else {
                Log.i("Permission: ", "Denied")
            }
        }

    fun onClickRequestPermission(view: View) {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.INTERNET
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.i("Permission: ", "Granted")
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.INTERNET
            ) -> {
                Log.i("Permission: ", "Denied")
                requestPermissionLauncher.launch(
                    Manifest.permission.INTERNET
                )
            }

            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.INTERNET
                )
            }
        }
    }
}