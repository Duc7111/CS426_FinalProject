package com.example.ash

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.WorkerThread

class NotificationHelper(private val context: Context) {
    companion object {
        /**
         * The notification channel for messages. This is used for showing Bubbles.
         */
        private const val CHANNEL_SCHEDULE = "schedule"

        private const val REQUEST_CONTENT = 1
        private const val REQUEST_BUBBLE = 2
    }
    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    fun setUpNotificationChannels() {
        if (notificationManager.getNotificationChannel(CHANNEL_SCHEDULE) == null) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_SCHEDULE,
                    "Schedule events",
                    // The importance must be IMPORTANCE_HIGH to show Bubbles.
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "All your events"
                }
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @WorkerThread
    fun showNotification(fromUser : Boolean) {

        val builder = Notification.Builder(context, CHANNEL_SCHEDULE)
            .setBubbleMetadata(
                Notification.BubbleMetadata.Builder()
                    .setDesiredHeight(context.resources.getDimensionPixelSize(R.dimen.bubble_height))
                    .setIcon(Icon.createWithResource(context,R.drawable.ic_file_black_24dp))
                    .apply {
                        // TODO: This does not yet work in Android Q Beta 2.
                        if (fromUser) {
                            setAutoExpandBubble(true)
                            //setSuppressInitialNotification(true)
                        }
                    }
                    .setIntent(
                        PendingIntent.getActivity(
                            context,
                            REQUEST_BUBBLE,
                            // Launch BubbleActivity as the expanded bubble.
                            Intent(context, BubbleDisplay::class.java)
                                .setAction(Intent.ACTION_VIEW),
                            PendingIntent.FLAG_UPDATE_CURRENT
                        )
                    )
                    .build()
            )
            .setContentTitle("Schedule")
            .setSmallIcon(R.drawable.ic_file_black_24dp)
            .setCategory(Notification.CATEGORY_STATUS)
            .setShowWhen(true)
        notificationManager.notify(0, builder.build())
    }

    fun dismissNotification(id: Long) {
        notificationManager.cancel(id.toInt())
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun canBubble(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val channel = notificationManager?.getNotificationChannel(CHANNEL_SCHEDULE)
            return notificationManager?.areBubblesAllowed() == true && channel?.canBubble() == true
        }
        return false
    }
}