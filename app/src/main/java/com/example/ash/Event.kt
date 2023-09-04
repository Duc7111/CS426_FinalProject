package com.example.ash

import android.icu.util.Calendar
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.Vector

class Event (
    private val frequency: Frequency = Frequency.ONCE,

    private val summary: String = "",
    private val description: String = "",
    private val location: String = "",

    private val startTime: Calendar = Calendar.getInstance(),
    private val endTime: Calendar? = null,

    private val attendees: Vector<Attendee> = Vector<Attendee>()
)
{

    private fun isTimeConflict(event: Event): Boolean
    {
        val start1 = startTime.get(Calendar.HOUR_OF_DAY)*60 + startTime.get(Calendar.MINUTE)
        val start2 = event.startTime.get(Calendar.HOUR_OF_DAY)*60 + event.startTime.get(Calendar.MINUTE)

        return if(endTime != null) {
            val end1 = endTime.get(Calendar.HOUR_OF_DAY)*60 + endTime.get(Calendar.MINUTE)
            if(event.endTime != null) {
                val end2 = event.endTime.get(Calendar.HOUR_OF_DAY)*60 + event.endTime.get(Calendar.MINUTE)
                start1 in start2 .. end2 || end1 in start2 .. end2
            } else end1 > start2
        } else
            if(event.endTime != null) {
                val end2 = event.endTime.get(Calendar.HOUR_OF_DAY)*60 + event.endTime.get(Calendar.MINUTE)
                end2 > start1
            } else false
    }
    enum class Frequency{
        ONCE, YEARLY, MONTHLY, WEEKLY, DAILY
    }

    companion object
    {
        fun read(fin: FileInputStream): Event
        {
            val frequency = DataHandler.readFrequency(fin)
            val summary =  DataHandler.readString(fin)
            val description = DataHandler.readString(fin)
            val location = DataHandler.readString(fin)
            val startTime = DataHandler.readCalendar(fin)
            val year = DataHandler.readString(fin)
            val endTime: Calendar? = if(year == "NA") null else {
                val month = DataHandler.readInt(fin)
                val date = DataHandler.readInt(fin)
                val hour = DataHandler.readInt(fin)
                val minute = DataHandler.readInt(fin)
                val calendar = Calendar.getInstance()

                calendar.set(Calendar.YEAR, year.toInt())
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DATE, date)
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                calendar
            }
            val size = DataHandler.readInt(fin)
            val attendees = Vector<Attendee>(size)
            for(i in 0 until size)
            {
                attendees[i] = Attendee.read(fin)
            }
            return Event(frequency, summary, description, location, startTime, endTime, attendees)
        }

        fun calendarToDate(calendar: Calendar): String
        {
            val date = calendar.get(Calendar.DATE)
            val month = when(calendar.get(Calendar.MONTH))
            {
                Calendar.JANUARY -> "January"
                Calendar.FEBRUARY -> "February"
                Calendar.MARCH -> "March"
                Calendar.APRIL -> "April"
                Calendar.MAY -> "May"
                Calendar.JUNE -> "June"
                Calendar.JULY -> "July"
                Calendar.AUGUST -> "August"
                Calendar.SEPTEMBER -> "September"
                Calendar.OCTOBER -> "October"
                Calendar.NOVEMBER -> "November"
                Calendar.DECEMBER -> "December"
                else -> ""
            }
            val year = calendar.get(Calendar.YEAR)
            return "$month $date, $year"
        }

        fun intToMonth(int: Int) =
            when(int)
            {
                Calendar.JANUARY -> "January"
                Calendar.FEBRUARY -> "February"
                Calendar.MARCH -> "March"
                Calendar.APRIL -> "April"
                Calendar.MAY -> "May"
                Calendar.JUNE -> "June"
                Calendar.JULY -> "July"
                Calendar.AUGUST -> "August"
                Calendar.SEPTEMBER -> "September"
                Calendar.OCTOBER -> "October"
                Calendar.NOVEMBER -> "November"
                Calendar.DECEMBER -> "December"
                else -> "It not a month"
            }

        fun intToWeekDay(int: Int) =
            when(int)
            {
                Calendar.MONDAY -> "Monday"
                Calendar.TUESDAY -> "Tuesday"
                Calendar.WEDNESDAY -> "Wednesday"
                Calendar.THURSDAY -> "Thursday"
                Calendar.FRIDAY -> "Friday"
                Calendar.SATURDAY -> "Saturday"
                Calendar.SUNDAY -> "Sunday"
                else -> "It not a day of week"
            }
    }

    fun write(fout: FileOutputStream)
    {
        DataHandler.write(frequency, fout)
        DataHandler.write(summary, fout)
        DataHandler.write(description, fout)
        DataHandler.write(location, fout)
        DataHandler.write(startTime, fout)
        if(endTime == null) DataHandler.write("NA", fout)
        else DataHandler.write(endTime, fout)
        DataHandler.write(attendees.size, fout)
        for(attendee in attendees)
        {
            attendee.write(fout)
        }
    }

    fun isOnceOnceConflict(event: Event): Boolean
    {
        if(getYear() != event.getYear() || getDate() != event.getDate() || getMonth() != event.getMonth()) return false
        return isTimeConflict(event)
    }

    fun isYearConflict(event: Event): Boolean
    {
        if(getDate() != event.getDate() || getMonth() != event.getMonth()) return false
        return isTimeConflict(event)
    }

    fun isMonthConflict(event: Event): Boolean
    {
        if(getDate() != event.getDate()) return false
        return isTimeConflict(event)
    }

    fun isWeekConflict(event: Event): Boolean
    {
        if(getWeekDay() != event.getWeekDay()) return false
        return isTimeConflict(event)
    }

    fun isDayConflict(event: Event): Boolean
    {
        return isTimeConflict(event)
    }

    fun getTime(SoE: Boolean): List<Int>
    {
        return if(SoE) calendarToTime(startTime)
        else if(endTime != null) calendarToTime(endTime)
        else emptyList()
    }

    fun getWeekDay(): Int
    {
        return startTime.get(Calendar.DAY_OF_WEEK)
    }

    fun getDate(): Int
    {
        return startTime.get(Calendar.DATE)
    }

    fun getMonth(): Int
    {
        return startTime.get(Calendar.MONTH)
    }

    fun getYear(): Int
    {
        return startTime.get(Calendar.YEAR)
    }

    fun getFrequency(): Frequency
    {
        return frequency
    }

    fun getSummary() = summary
    fun getDescription() = description
    fun getLocation() = location
    fun getAttendees(): List<Attendee>
    {
        return attendees.toList()
    }
    fun getStartTime() = startTime

    fun addAttendee(attendee: Attendee)
    {
        attendees.addElement(attendee)
    }
    fun removeAttendee(attendee: Attendee): Boolean
    {
        return attendees.remove(attendee)
    }
}

private fun calendarToTime(calendar: Calendar): List<Int>
{
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)
    return listOf(hour, minute)
}