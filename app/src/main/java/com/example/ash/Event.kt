package com.example.ash

import android.icu.util.Calendar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.Vector

class Event (
    private val summary: String = "",
    private val description: String = "",
    private val location: String = "",

    private val startTime: Calendar = Calendar.getInstance(),
    private val endTime: Calendar = Calendar.getInstance(),
    private var hasAnEnd: Boolean = false,

    private val attendees: Vector<Attendee> = Vector<Attendee>()
)
{
    constructor(event: Event) : this(event.summary, event.description, event.location, event.startTime, event.endTime, event.hasAnEnd, event.attendees)

    @Composable
    private fun ComposeTime(calendar: Calendar)
    {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(150
                    .dp)
        )
        {
            Text(
                text = calendarToDate(calendar),
            )
            Text(
                text = calendarToTime(calendar),
            )
        }
    }

    private fun isTimeConflict(event: Event): Boolean
    {
        val start1 = startTime.get(Calendar.HOUR_OF_DAY)*60 + startTime.get(Calendar.MINUTE)
        val start2 = event.startTime.get(Calendar.HOUR_OF_DAY)*60 + event.startTime.get(Calendar.MINUTE)
        val end1 = endTime.get(Calendar.HOUR_OF_DAY)*60 + endTime.get(Calendar.MINUTE)
        val end2 = event.endTime.get(Calendar.HOUR_OF_DAY)*60 + event.endTime.get(Calendar.MINUTE)

        if(hasAnEnd)
        {
            if(event.hasAnEnd && (start1 in start2 .. end2 || end1 in start2 .. end2)
                || !event.hasAnEnd && end1 > start2)
                return true
        }
        else if(!event.hasAnEnd || end2 > start1) return true
        return false
    }

    @Override
    operator fun compareTo(event: Event): Int
    {
        if(hasAnEnd)
        {
            if(event.hasAnEnd && (startTime in event.startTime..event.endTime || endTime in event.startTime..event.endTime)
            || !event.hasAnEnd && endTime > event.startTime)
                return 0
        }
        else if(!event.hasAnEnd || event.endTime > startTime) return 0
        return startTime.compareTo(event.startTime)
    }

    fun isOnceYearConflict(event: Event): Boolean
    {
        val tempEvent = Event(event)
        tempEvent.startTime.set(Calendar.YEAR, startTime.get(Calendar.YEAR))
        tempEvent.endTime.set(Calendar.YEAR, endTime.get(Calendar.YEAR))
        return this == tempEvent
    }

    fun isOnceMonthConflict(event: Event): Boolean
    {
        val tempEvent = Event(event)
        tempEvent.startTime.set(Calendar.YEAR, startTime.get(Calendar.YEAR))
        tempEvent.startTime.set(Calendar.MONTH, startTime.get(Calendar.MONTH))
        tempEvent.endTime.set(Calendar.YEAR, endTime.get(Calendar.YEAR))
        tempEvent.endTime.set(Calendar.MONTH, endTime.get(Calendar.MONTH))
        return this == tempEvent
    }

    fun isOnceWeekConflict(event: Event): Boolean
    {
        if(startTime.get(Calendar.DAY_OF_WEEK) != event.startTime.get(Calendar.DAY_OF_WEEK)) return false
        return isTimeConflict(event)
    }

    fun isOnceDayConflict(event: Event): Boolean
    {
        return isTimeConflict(event)
    }
    fun getTime(SoE: Boolean): List<Int>
    {
        if(SoE) return listOf(startTime.get(Calendar.HOUR_OF_DAY), startTime.get(Calendar.MINUTE))
        else if(hasAnEnd) listOf(endTime.get(Calendar.HOUR_OF_DAY), endTime.get(Calendar.MINUTE))
        return emptyList()
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

    @Composable
    fun ComposeEvent()
    {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = summary,
                )
            }
            item{
                Text(
                    text = description,
                )
            }
            item{
                Text(
                    text = location,
                )
            }
            item{
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                {
                    ComposeTime(calendar = startTime)
                    Text(
                        text = "to"
                    )
                    if(hasAnEnd) ComposeTime(calendar = endTime)
                    else Text(
                        text = "NA"
                    )
                }
            }
            items(attendees)
            {
                it.ComposeAttendee()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ComposeEvent()
{
    val event = Event()
    event.ComposeEvent()
}

private fun calendarToDate(calendar: Calendar): String
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

private fun calendarToTime(calendar: Calendar): String
{
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)
    return "$hour: $minute"
}