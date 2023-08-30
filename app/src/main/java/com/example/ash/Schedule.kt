package com.example.ash

import android.icu.util.Calendar
import java.util.Vector

class Schedule(
    private val events: Vector<Event> = Vector<Event>(),
    private val dayEvents:Vector<Event> = Vector<Event>(),
    private val weekEvents:Vector<Event> = Vector<Event>(),
    private val monthEvents:Vector<Event> = Vector<Event>(),
    private val yearEvent:Vector<Event> = Vector<Event>(),
)
{

    fun getDaySchedule(calendar: Calendar): Vector<Event>
    {
        val schedule = Vector(dayEvents)
        for(e in monthEvents)
            if(e.getDate() == calendar.get(Calendar.DATE)) schedule.addElement(e)
        for(e in yearEvent)
            if(e.getDate() == calendar.get(Calendar.DATE) && e.getMonth() == calendar.get(Calendar.MONTH)) schedule.addElement(e)
        for(e in events)
            if(e.getDate() == calendar.get(Calendar.DATE) && e.getMonth() == calendar.get(Calendar.MONTH) && e.getYear() == calendar.get(Calendar.YEAR) ) schedule.addElement(e)
        return schedule
    }
}