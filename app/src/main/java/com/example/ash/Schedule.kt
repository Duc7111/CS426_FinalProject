package com.example.ash

import android.icu.util.Calendar
import java.util.Vector

class Schedule(
    private val events: Vector<Event> = Vector<Event>(),
    private val yearlyEvent:Vector<Event> = Vector<Event>(),
    private val monthlyEvents:Vector<Event> = Vector<Event>(),
    private val weeklyEvents:Vector<Event> = Vector<Event>(),
    private val dailyEvents:Vector<Event> = Vector<Event>(),
)
{
    private fun addOnceEvent(event: Event): Event?
    {
        for(e in events) if(event.isOnceOnceConflict(e)) return e
        for(e in yearlyEvent) if(event.isYearConflict(e)) return e
        for(e in monthlyEvents) if(event.isMonthConflict(e)) return e
        for(e in weeklyEvents) if(event.isWeekConflict(e)) return e
        for(e in dailyEvents) if(event.isDayConflict(e)) return e

        events.addElement(event)
        return null
    }

    private fun addYearlyEvent(event: Event): Event?
    {
        for(e in events) if(e.isYearConflict(event)) return e
        for(e in yearlyEvent) if(event.isYearConflict(e)) return e
        for(e in monthlyEvents) if(event.isMonthConflict(e)) return e
        for(e in weeklyEvents) if(event.isDayConflict(e)) return e
        for(e in dailyEvents) if(event.isDayConflict(e)) return e

        yearlyEvent.addElement(event)
        return null
    }

    private fun addMonthlyEvent(event: Event): Event?
    {
        for(e in events) if(e.isYearConflict(event)) return e
        for(e in yearlyEvent) if(event.isMonthConflict(e)) return e
        for(e in monthlyEvents) if(event.isMonthConflict(e)) return e
        for(e in weeklyEvents) if(event.isDayConflict(e)) return e
        for(e in dailyEvents) if(event.isDayConflict(e)) return e

        monthlyEvents.addElement(event)
        return null
    }

    private fun addWeeklyEvent(event: Event): Event?
    {
        for(e in events) if(e.isWeekConflict(event)) return e
        for(e in yearlyEvent) if(event.isDayConflict(e)) return e
        for(e in monthlyEvents) if(event.isDayConflict(e)) return e
        for(e in weeklyEvents) if(event.isDayConflict(e)) return e
        for(e in dailyEvents) if(event.isDayConflict(e)) return e

        weeklyEvents.addElement(event)
        return null
    }

    private fun addDailyEvent(event: Event): Event?
    {
        for(e in events) if(e.isDayConflict(event)) return e
        for(e in yearlyEvent) if(event.isDayConflict(e)) return e
        for(e in monthlyEvents) if(event.isDayConflict(e)) return e
        for(e in weeklyEvents) if(event.isDayConflict(e)) return e
        for(e in dailyEvents) if(event.isDayConflict(e)) return e

        dailyEvents.addElement(event)
        return null
    }

    fun addEvent(event: Event): Event?
    {
        return when(event.getFrequency()) {
            Event.once -> addOnceEvent(event)
            Event.yearly -> addYearlyEvent(event)
            Event.monthly -> addMonthlyEvent(event)
            Event.weekly -> addWeeklyEvent(event)
            Event.daily -> addDailyEvent(event)
            else -> null
        }
    }

    fun removeEvent(event: Event): Boolean
    {
        return when(event.getFrequency()) {
            Event.once -> events.remove(event)
            Event.yearly -> yearlyEvent.remove(event)
            Event.monthly -> monthlyEvents.remove(event)
            Event.weekly -> weeklyEvents.remove(event)
            Event.daily -> dailyEvents.remove(event)
            else -> false
        }
    }

    fun modifyEvent(event: Event, modifiedEvent: Event): Event?
    {
        if(!removeEvent(event)) return null
        val temp = addEvent(modifiedEvent)
        if(temp != null)
        {
            addEvent(event)
            return temp
        }
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