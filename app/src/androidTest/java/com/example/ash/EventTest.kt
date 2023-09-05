package com.example.ash

import android.content.Context
import android.icu.util.Calendar
import androidx.compose.runtime.collection.mutableVectorOf
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.*

import org.junit.Test
import java.io.File
import java.util.Vector

class EventTest {
    private val attendee1 = Attendee("attendee 1", "role 1", "contact 1")
    private val attendee2 = Attendee("attendee 2", "role 2", "contact 2")
    private val attendee3 = Attendee("attendee 3", "role 3", "contact 3")
    private val attendees = Vector<Attendee>().also{it.addAll(listOf(attendee1, attendee2, attendee3))}
    private val context = ApplicationProvider.getApplicationContext() as Context
    private val event = Event(
        Event.Frequency.DAILY,
        "A short summary",
        "This is a quite long description of a random event",
        "Somewhere",
        Calendar.getInstance(),
        null,
        attendees
    )
    private val file = File(context.filesDir,"test.txt")

    @Test
    fun write() {
        val fout = file.outputStream()
        event.write(fout)
        fout.flush()
        fout.close()
        val fin = file.inputStream()
        val otherEvent = Event.read(fin.bufferedReader())

        assert(otherEvent.getSummary() == event.getSummary() &&
            otherEvent.getDescription() == event.getDescription() &&
            otherEvent.getLocation() == event.getLocation()
            )
        getAttendees()
    }

    @Test
    fun getAttendees() {
        val attendee = event.getAttendees()
        assert(attendee1.getName() == attendee[0].getName())
    }

    @Test
    fun addAttendee() {
        event.addAttendee(Attendee("attendee 4", "role 4", "contact 4"))
        assert(event.getAttendees()[3].getName() == "attendee 4")
    }

    @Test
    fun removeAttendee() {
        assert(event.removeAttendee(Attendee("attendee 3", "role 3", "contact 3")))
    }
}