package com.example.ash

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class BubbleViewModel(application: Application) :AndroidViewModel(application) {
    private var job = Job()
    private val coroutineContext: CoroutineContext get() = job + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)
    val notificationHelper = NotificationHelper(getApplication())

    init {
        notificationHelper.setUpNotificationChannels()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun showBubble() = scope.launch (Dispatchers.Default){

        if(notificationHelper.canBubble()){
            notificationHelper.showNotification(false)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}