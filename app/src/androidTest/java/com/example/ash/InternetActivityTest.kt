package com.example.ash

import android.util.Log
import org.junit.Assert.*

import org.junit.Test
import java.net.HttpURLConnection
import java.net.URL
import kotlin.math.log

class InternetActivityTest {

    private val fileUrl = URL("https://drive.google.com/file/d/11kZ_b0JHfB2pOxV-HXyHTiTlaVhCfW3E")
    private val connection = fileUrl.openConnection() as HttpURLConnection

    @Test
    fun read() {
        connection.requestMethod = "GET"
        val string = connection.inputStream.bufferedReader().readText()
        Log.i("My Output", string)
    }

    @Test
    fun write() {
    }
}