package com.example.ash

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL

class InternetActivity: AppCompatActivity() {

    private val fileUrl = URL("https://drive.google.com/file/d/11kZ_b0JHfB2pOxV-HXyHTiTlaVhCfW3E/view?usp=sharing")
    private val connection = fileUrl.openConnection() as HttpURLConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Request the internet permission
        requestPermissions(arrayOf(Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE), 1)
        if(!hasInternetAccess())
        {
            Log.i("Internet Access", "None")
            return
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1 && grantResults.size == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.i("Permissions", "Granted")
        } else {
            Log.i("Permissions", "Denied")
            requestPermissions(arrayOf(Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE), 1)
        }
    }

    private fun hasInternetAccess(): Boolean {
        val connectivityManager = getSystemService(ConnectivityManager::class.java)
        val currentNetwork = connectivityManager.activeNetwork
        return connectivityManager.getNetworkCapabilities(currentNetwork)?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
    }

    fun read(): String
    {
        connection.requestMethod = "GET"
        return connection.inputStream.bufferedReader().readText()
    }

    fun write(data: String)
    {
        connection.requestMethod = "POST"
        connection.outputStream.write(data.toByteArray())
    }
}