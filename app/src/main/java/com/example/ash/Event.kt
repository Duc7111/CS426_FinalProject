package com.example.ash

import android.icu.util.Calendar

class Event() {
    private val summary = ""
    private val description = ""
    private val location = ""

    private val startTime = Calendar.getInstance()
    private var endTime: Calendar? = null

    private val attendees = emptyArray<Attendee>()
}