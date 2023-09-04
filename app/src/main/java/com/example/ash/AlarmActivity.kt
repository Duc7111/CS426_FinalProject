package com.example.ash

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class AlarmActivity(private val event: Event): AppCompatActivity() {

    private val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, event.getStartTime().timeInMillis, pendingIntent)
    }

    class AlarmReceiver: BroadcastReceiver()
    {
        override fun onReceive(context: Context, intent: Intent) {
            val ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            val ringtonePlayer = RingtoneManager.getRingtone(context, ringtone)
            ringtonePlayer.play()
        }
    }
}