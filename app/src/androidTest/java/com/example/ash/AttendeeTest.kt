package com.example.ash

import android.content.Context
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import org.junit.Test
import java.io.File

class AttendeeTest {

    private val context = getApplicationContext() as Context
    private val attendee = Attendee("Duc", "Creator", "Contact")
    private val file = File(context.filesDir,"test.txt")

    @Test
    fun write() {
        val fout = file.outputStream()
        attendee.write(fout)
        fout.flush()
        fout.close()
        val fin = file.inputStream()
        val otherAttendee = Attendee.read(fin.bufferedReader())
        fin.close()
        assert(
            otherAttendee.getName() == "Duc" &&
                otherAttendee.getRole() == "Creator" &&
                otherAttendee.getContact() == "Contact")
    }

    @Test
    fun getName() {
        assert(attendee.getName() == "Duc")
    }

    @Test
    fun getRole() {
        assert(attendee.getRole() == "Creator")
    }

    @Test
    fun getContact() {
        assert(attendee.getContact() == "Contact")
    }
}