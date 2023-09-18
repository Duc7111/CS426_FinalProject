package com.example.ash

import android.icu.util.Calendar
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.Vector

class Schedule private constructor(
    private val events: Vector<Event> = Vector<Event>(),
    private val yearlyEvents:Vector<Event> = Vector<Event>(),
    private val monthlyEvents:Vector<Event> = Vector<Event>(),
    private val weeklyEvents:Vector<Event> = Vector<Event>(),
    private val dailyEvents:Vector<Event> = Vector<Event>(),
)
{
    private fun addOnceEvent(event: Event): Event?
    {
        for(e in events) if(event.isOnceOnceConflict(e)) return e
        for(e in yearlyEvents) if(event.isYearConflict(e)) return e
        for(e in monthlyEvents) if(event.isMonthConflict(e)) return e
        for(e in weeklyEvents) if(event.isWeekConflict(e)) return e
        for(e in dailyEvents) if(event.isDayConflict(e)) return e

        events.addElement(event)
        return null
    }

    private fun addYearlyEvent(event: Event): Event?
    {
        for(e in events) if(e.isYearConflict(event)) return e
        for(e in yearlyEvents) if(event.isYearConflict(e)) return e
        for(e in monthlyEvents) if(event.isMonthConflict(e)) return e
        for(e in weeklyEvents) if(event.isDayConflict(e)) return e
        for(e in dailyEvents) if(event.isDayConflict(e)) return e

        yearlyEvents.addElement(event)
        return null
    }

    private fun addMonthlyEvent(event: Event): Event?
    {
        for(e in events) if(e.isYearConflict(event)) return e
        for(e in yearlyEvents) if(event.isMonthConflict(e)) return e
        for(e in monthlyEvents) if(event.isMonthConflict(e)) return e
        for(e in weeklyEvents) if(event.isDayConflict(e)) return e
        for(e in dailyEvents) if(event.isDayConflict(e)) return e

        monthlyEvents.addElement(event)
        return null
    }

    private fun addWeeklyEvent(event: Event): Event?
    {
        for(e in events) if(e.isWeekConflict(event)) return e
        for(e in yearlyEvents) if(event.isDayConflict(e)) return e
        for(e in monthlyEvents) if(event.isDayConflict(e)) return e
        for(e in weeklyEvents) if(event.isDayConflict(e)) return e
        for(e in dailyEvents) if(event.isDayConflict(e)) return e

        weeklyEvents.addElement(event)
        return null
    }

    private fun addDailyEvent(event: Event): Event?
    {
        for(e in events) if(e.isDayConflict(event)) return e
        for(e in yearlyEvents) if(event.isDayConflict(e)) return e
        for(e in monthlyEvents) if(event.isDayConflict(e)) return e
        for(e in weeklyEvents) if(event.isDayConflict(e)) return e
        for(e in dailyEvents) if(event.isDayConflict(e)) return e

        dailyEvents.addElement(event)
        return null
    }

    companion object{

        @Volatile
        private var instance: Schedule? = null

        fun getInstance(file: File? = null) =
            instance ?: synchronized(this){
                instance ?: (if(file == null || !file.isFile) Schedule() else read(file)).also{instance = it}
            }

        private fun read(file: File): Schedule
        {
            if(!file.isFile) instance = Schedule()
            val fin = file.inputStream()
            val fbr = fin.bufferedReader()
            var size = DataHandler.readInt(fbr)
            val events = Vector<Event>(size)
            for(i in 0 until size) events.addElement(Event.read(fbr))

            size = DataHandler.readInt(fbr)
            val yearlyEvents = Vector<Event>(size)
            for(i in 0 until size) yearlyEvents.addElement(Event.read(fbr))

            size = DataHandler.readInt(fbr)
            val monthlyEvents = Vector<Event>(size)
            for(i in 0 until size) monthlyEvents.addElement(Event.read(fbr))

            size = DataHandler.readInt(fbr)
            val weeklyEvents = Vector<Event>(size)
            for(i in 0 until size) weeklyEvents.addElement(Event.read(fbr))

            size = DataHandler.readInt(fbr)
            val dailyEvents = Vector<Event>(size)
            for(i in 0 until size) dailyEvents.addElement(Event.read(fbr))

            fin.close()
            instance = Schedule(events, yearlyEvents, monthlyEvents, weeklyEvents, dailyEvents)
            return instance as Schedule
        }
    }

    fun write(fout: FileOutputStream)
    {
        DataHandler.write(events.size, fout)
        for(event in events) event.write(fout)

        DataHandler.write(yearlyEvents.size, fout)
        for(event in yearlyEvents) event.write(fout)

        DataHandler.write(monthlyEvents.size, fout)
        for(event in monthlyEvents) event.write(fout)

        DataHandler.write(weeklyEvents.size, fout)
        for(event in weeklyEvents) event.write(fout)

        DataHandler.write(dailyEvents.size, fout)
        for(event in dailyEvents) event.write(fout)
    }

    fun addEvent(event: Event): Event?
    {
        return when(event.getFrequency()) {
            Event.Frequency.ONCE -> addOnceEvent(event)
            Event.Frequency.YEARLY -> addYearlyEvent(event)
            Event.Frequency.MONTHLY -> addMonthlyEvent(event)
            Event.Frequency.WEEKLY -> addWeeklyEvent(event)
            Event.Frequency.DAILY -> addDailyEvent(event)
        }
    }

    fun removeEvent(event: Event): Boolean
    {
        return when(event.getFrequency()) {
            Event.Frequency.ONCE -> events.remove(event)
            Event.Frequency.YEARLY -> yearlyEvents.remove(event)
            Event.Frequency.MONTHLY -> monthlyEvents.remove(event)
            Event.Frequency.WEEKLY -> weeklyEvents.remove(event)
            Event.Frequency.DAILY -> dailyEvents.remove(event)
        }
    }

    fun modifyEvent(event: Event, modifiedEvent: Event): Event?
    {
        if (event.getFrequency() != modifiedEvent.getFrequency()) {
            if (!removeEvent(event)) {
                return null
                println("Remove event fail")
            }
            val temp = addEvent(modifiedEvent)
            if (temp != null) {
                addEvent(event)
                return temp
            }
            return null
        }
        else {
            // Find the index of the event to be modified in the appropriate vector
            val eventVector = when (event.getFrequency()) {
                Event.Frequency.ONCE -> events
                Event.Frequency.YEARLY -> yearlyEvents
                Event.Frequency.MONTHLY -> monthlyEvents
                Event.Frequency.WEEKLY -> weeklyEvents
                Event.Frequency.DAILY -> dailyEvents
            }

            val index = eventVector.indexOf(event)

            // If the event is found, replace it with the modified event
            if (index != -1) {
                eventVector[index] = modifiedEvent
                return modifiedEvent // Event modified successfully
            }

            return null // Event not found, modification failed
        }
    }

    fun getOnceEvents() = events.toList()
    fun getYearlyEvents() = yearlyEvents.toList()
    fun getMonthlyEvents() = monthlyEvents.toList()
    fun getWeeklyEvents() = weeklyEvents.toList()
    fun getDailyEvents() = dailyEvents.toList()

    fun getDaySchedule(calendar: Calendar): List<Event>
    {
        val schedule = Vector(dailyEvents)

        for(e in weeklyEvents)
            if(e.getWeekDay() == calendar.get(Calendar.DAY_OF_WEEK)) schedule.addElement(e)
        for(e in monthlyEvents)
            if(e.getDate() == calendar.get(Calendar.DATE)) schedule.addElement(e)
        for(e in yearlyEvents)
            if(e.getDate() == calendar.get(Calendar.DATE) && e.getMonth() == calendar.get(Calendar.MONTH)) schedule.addElement(e)
        for(e in events)
            if(e.getDate() == calendar.get(Calendar.DATE) && e.getMonth() == calendar.get(Calendar.MONTH) && e.getYear() == calendar.get(Calendar.YEAR) ) schedule.addElement(e)

        return schedule.toList()
    }
}