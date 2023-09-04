package com.example.ash

import android.icu.util.Calendar
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.Vector

class DataHandler {
    companion object{

        private fun dtToCalendar(dt: String): Calendar
        {
            val calendar = Calendar.getInstance()

            return calendar
        }

        private fun stringToAttendee(string: String): Attendee
        {
            return Attendee()
        }

        fun write(int: Int, fout: FileOutputStream)
        {
            val data = (int.toString() + '\n' ).toByteArray()
            fout.write(data)
        }
        fun write(string: String, fout:FileOutputStream)
        {
            val data = (string + '\n').toByteArray()
            fout.write(data)
        }
        fun write(calendar: Calendar, fout: FileOutputStream)
        {
            write(calendar.get(Calendar.YEAR), fout)
            write(calendar.get(Calendar.MONTH), fout)
            write(calendar.get(Calendar.DATE), fout)
            write(calendar.get(Calendar.HOUR_OF_DAY), fout)
            write(calendar.get(Calendar.MINUTE), fout)
        }
        fun write(frequency: Event.Frequency, fout: FileOutputStream)
        {
            val data = when(frequency)
            {
                Event.Frequency.ONCE -> "once\n".toByteArray()
                Event.Frequency.YEARLY -> "yearly\n".toByteArray()
                Event.Frequency.MONTHLY -> "monthly\n".toByteArray()
                Event.Frequency.WEEKLY -> "weekly\n".toByteArray()
                Event.Frequency.DAILY ->"daily\n".toByteArray()
            }
            fout.write(data)
        }

        fun readInt(fin: FileInputStream): Int
        {
            val data = fin.bufferedReader().readLine()
            return data.toInt()
        }
        fun readString(fin: FileInputStream): String {
            return fin.bufferedReader().readLine()
        }
        fun readCalendar(fin: FileInputStream): Calendar
        {
            val year = readInt(fin)
            val month = readInt(fin)
            val date = readInt(fin)
            val hour = readInt(fin)
            val minute = readInt(fin)
            val calendar = Calendar.getInstance()

            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DATE, date)
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)

            return calendar
        }
        fun readFrequency(fin: FileInputStream): Event.Frequency
        {
            return when(readString(fin))
            {
                "once" -> Event.Frequency.ONCE
                "yearly" -> Event.Frequency.YEARLY
                "monthly" -> Event.Frequency.MONTHLY
                "weekly" -> Event.Frequency.WEEKLY
                "daily" -> Event.Frequency.DAILY
                else -> Event.Frequency.ONCE
            }
        }

        fun icsToEvent(file: File): Event
        {
            if(!file.isFile) return Event()
            var summary: String = ""
            var description: String = ""
            var location: String = ""
            var startTime: Calendar = Calendar.getInstance()
            var endTime: Calendar? = null
            val attendees: Vector<Attendee> = Vector<Attendee>()
            val fin = file.inputStream()
            fin.bufferedReader().lineSequence().forEach{
                val data = it.substringAfter(':')
                when(it.substringBefore(':'))
                {
                    "SUMMARY" -> summary = data
                    "DESCRIPTION" -> description = data
                    "LOCATION" -> location = data
                    "DTSTART" -> startTime = dtToCalendar(data)
                    "DTEND" -> endTime = dtToCalendar(data)
                    "ATTENDEE" -> attendees.addElement(stringToAttendee(data))
                }
            }
            return Event(Event.Frequency.ONCE, summary, description, location, startTime, endTime, attendees)
        }
    }
}