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
    private var endTime: Calendar? = null,

    private val attendees: Vector<Attendee> = Vector<Attendee>()
)
{
    @Composable
    private fun ComposeTime(calendar: Calendar?)
    {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(150.dp)
        )
        {
            if(calendar == null)
            {
                Text(text = "NA")
            }
            else
            {
                Text(
                    text = calendarToDate(calendar),
                )
                Text(
                    text = calendarToTime(calendar),
                )
            }
        }
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
                    ComposeTime(calendar = endTime)
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