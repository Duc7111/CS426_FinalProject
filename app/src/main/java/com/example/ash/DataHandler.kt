package com.example.ash

import android.icu.util.Calendar
import android.icu.util.TimeZone
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.util.Vector

class DataHandler {
    companion object{

        private fun dtToCalendar(timezone: String = TimeZone.getDefault().id, dt: String): Calendar
        {
            val calendar = Calendar.getInstance()
            calendar.timeZone = if(dt.last() == 'Z') TimeZone.getTimeZone("UTC") else TimeZone.getTimeZone(timezone)
            val date = dt.substringBefore('T')
                val year = date.substring(0, 3).toInt()
                val month = date.substring(4, 5).toInt() - 1
                val day = date.substring(5, 6).toInt()
            val time = dt.substringAfter('T')
                val hour = time.substring(0, 3).toInt()
                val minute = time.substring(4, 5).toInt()
            calendar.set(year, month, day, hour, minute)
            calendar.timeZone = TimeZone.getDefault()
            return calendar
        }

        private fun stringToAttendee(tags: List<String>, data: String): Attendee
        {

            var name = ""
            var role = ""
            var contact = ""
            for (s in tags)
            {
                val d = s.substringAfter('=')
                when(s.substringBefore('='))
                {
                    "CN" -> name = d
                    "ROLE" -> role = d
                }

            }
            val parse = data.split(':')
            for (i in 0 until parse.size - 1)
            {
                if((parse[i] == "MAILTO" || parse[i] == "mailto")) contact += parse[i + 1]
            }
            return Attendee(name, role, contact)
        }

        fun write(int: Int, fout: FileOutputStream)
        {
            val data = (int.toString() + "\n" ).toByteArray()
            fout.write(data)
        }
        fun write(string: String, fout:FileOutputStream)
        {
            val data = (string + "\n").toByteArray()
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

        fun readInt(fbr: BufferedReader): Int
        {
            return fbr.readLine().toInt()
        }
        fun readString(fbr: BufferedReader): String {
            return fbr.readLine()
        }
        fun readCalendar(fbr: BufferedReader): Calendar
        {
            val year = readInt(fbr)
            val month = readInt(fbr)
            val date = readInt(fbr)
            val hour = readInt(fbr)
            val minute = readInt(fbr)
            val calendar = Calendar.getInstance()

            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DATE, date)
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)

            return calendar
        }
        fun readFrequency(fbr: BufferedReader): Event.Frequency
        {
            return when(readString(fbr))
            {
                "once" -> Event.Frequency.ONCE
                "yearly" -> Event.Frequency.YEARLY
                "monthly" -> Event.Frequency.MONTHLY
                "weekly" -> Event.Frequency.WEEKLY
                "daily" -> Event.Frequency.DAILY
                else -> Event.Frequency.ONCE
            }
        }

        fun icsToEvent(file: File): List<Event>
        {
            val events = Vector<Event>()
            if(!file.isFile) return events
            var pointer = 0
            var temp = ""
            var summary = ""
            var description = ""
            var location = ""
            var startTime: Calendar = Calendar.getInstance()
            var endTime: Calendar? = null
            val attendees: Vector<Attendee> = Vector<Attendee>()
            val fin = file.inputStream()

            var skip = true
            for(string in fin.bufferedReader().lineSequence())
            {
                if(string == "BEGIN:VEVENT") skip = false
                if(skip) continue
                if(string == "END:VEVENT") events.addElement(Event(Event.Frequency.ONCE, summary, description, location, startTime, endTime, attendees))
                val tag = string.substringBefore(':', "No tag")
                val data = string.substringAfter(':')
                if(tag.all{
                    it != ';'
                })
                    when(tag)
                    {
                        "SUMMARY" ->
                        {
                            when (pointer)
                            {
                                2 -> description = temp
                                3 -> location = temp
                                4 -> startTime = dtToCalendar(dt = temp)
                                5 -> endTime = dtToCalendar(dt = temp)
                            }
                            pointer = 1
                            temp = data
                        }
                        "DESCRIPTION" ->
                        {
                            when (pointer)
                            {
                                1 -> summary = temp
                                3 -> location = temp
                                4 -> startTime = dtToCalendar(dt = temp)
                                5 -> endTime = dtToCalendar(dt = temp)
                            }
                            pointer = 2
                            temp = data
                        }
                        "LOCATION" ->
                        {
                            when (pointer)
                            {
                                1 -> summary = temp
                                2 -> description = temp
                                4 -> startTime = dtToCalendar(dt = temp)
                                5 -> endTime = dtToCalendar(dt = temp)
                            }
                            pointer = 3
                            temp = data
                        }
                        "DTSTART" ->
                        {
                            when (pointer)
                            {
                                1 -> summary = temp
                                2 -> description = temp
                                3 -> location = temp
                                5 -> endTime = dtToCalendar(dt = temp)
                            }
                            pointer = 4
                            temp = data
                        }
                        "DTEND" ->
                        {
                            when (pointer)
                            {
                                1 -> summary = temp
                                2 -> description = temp
                                3 -> location = temp
                                4 -> startTime = dtToCalendar(dt = temp)
                            }
                            pointer = 5
                            temp = data
                        }
                        "No tag" -> if(pointer != 0) temp += string
                    }
                else
                {
                    val tags = tag.split(';')
                    when(tags[0])
                    {
                        "DTSTART" -> startTime = dtToCalendar(tags[1], data)
                        "DTEND" -> endTime = dtToCalendar(tags[1], data)
                        "Attendee" -> attendees.addElement(stringToAttendee(tags, data))
                    }
                }
            }
            return events.toList()
        }
    }
}