package com.example.ash

import android.icu.util.Calendar
import java.util.Vector

class Schedule(
    private val events: Vector<Event> = Vector<Event>(),
    private val dailyEvents:Vector<Event> = Vector<Event>(),
    private val weeklyEvents:Vector<Event> = Vector<Event>(),
    private val monthlyEvents:Vector<Event> = Vector<Event>(),
    private val yearlyEvent:Vector<Event> = Vector<Event>(),
)
{
    fun addEvent(event: Event): Event?
    {
        return null
    }

    fun getDaySchedule(calendar: Calendar): Vector<Event>
    {
        val schedule = Vector(dailyEvents)

        for(e in weeklyEvents)
            if(e.getWeekDay() == calendar.get(Calendar.DAY_OF_WEEK)) schedule.addElement(e)
        for(e in monthlyEvents)
            if(e.getDate() == calendar.get(Calendar.DATE)) schedule.addElement(e)
        for(e in yearlyEvent)
            if(e.getDate() == calendar.get(Calendar.DATE) && e.getMonth() == calendar.get(Calendar.MONTH)) schedule.addElement(e)
        for(e in events)
            if(e.getDate() == calendar.get(Calendar.DATE) && e.getMonth() == calendar.get(Calendar.MONTH) && e.getYear() == calendar.get(Calendar.YEAR) ) schedule.addElement(e)

        return schedule
    }

}