package com.example.ash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.app.Person
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.ash.ui.theme.ASHTheme
import com.example.ash.ui.theme.DeepBlue
import com.example.ash.ui.theme.EventButton
import com.example.ash.ui.theme.Greeting
import com.example.ash.ui.theme.MediumBlue
import com.example.ash.ui.theme.OptionButtons
import com.example.ash.ui.theme.TextWhite
import java.io.File
import java.util.Vector

@RequiresApi(Build.VERSION_CODES.P)
val person = Person.Builder()
    .setName("A.S.H")
    .setImportant(true)
    .build()

class MainActivity : ComponentActivity() {

    private lateinit var bubbleViewModel: BubbleViewModel

    var TheSchedule = Schedule.getInstance()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bubbleViewModel = ViewModelProvider(this)[BubbleViewModel::class.java]

        testData(TheSchedule)
        if (TheSchedule.getOnceEvents().isEmpty()) println("onCreate()")
        setContent {
            ASHTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Homescreen(schedule = TheSchedule ,name = "Phoenix")
                    {
                        bubbleViewModel.showBubble()
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        println("onStart()")
    }

    override fun onResume() {
        super.onResume()
        println("onResume()")
    }

    override fun onPause() {
        super.onPause()
        println("onPause()")
    }

    override fun onStop() {
        super.onStop()
        println("onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy()")
    }

    override fun onRestart() {
        super.onRestart()
        println("onRestart()")
    }
}

@Composable
fun Homescreen(schedule: Schedule,name: String, modifier: Modifier = Modifier, onBubbleDisplay: () -> Unit) {

    var Schedule by remember { mutableStateOf(schedule) }
    var OnceEvents by remember { mutableStateOf(Schedule.getOnceEvents())}
    var DailyEvents by remember { mutableStateOf(Schedule.getDailyEvents())}
    var WeeklyEvents by remember { mutableStateOf(Schedule.getWeeklyEvents())}
    var MonthlyEvents by remember { mutableStateOf(Schedule.getMonthlyEvents())}
    var YearlyEvents by remember { mutableStateOf(Schedule.getYearlyEvents())}


    Box(modifier = Modifier
        .background(DeepBlue)
        .fillMaxSize()
        .padding(30.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 10.dp) // Add padding to separate Greeting from LazyColumn
        ) {
            Greeting(name)
            LazyColumn(modifier = modifier.fillMaxSize()) {
                item {
                    Text(
                        text = "One time events",
                        color = TextWhite,
                        //style = MaterialTheme.typography.body1
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                    )
                }
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(
                                color = MediumBlue,
                                shape = RoundedCornerShape(16.dp)
                            ), // Set the background color
                        contentAlignment = Alignment.TopStart
                    ) {
                        LazyColumn(modifier = modifier.padding(vertical = 5.dp)) {
                            items(OnceEvents) { onceEvent ->
                                var mutableOnceEvent by remember { mutableStateOf(onceEvent) }

                                EventButton(
                                    event = mutableOnceEvent,
                                    modifier = modifier,
                                    onSave = {
                                        Schedule.modifyEvent(onceEvent, it)
                                        when (onceEvent.getFrequency()) {
                                            Event.Frequency.ONCE -> OnceEvents = Schedule.getOnceEvents()
                                            Event.Frequency.YEARLY -> YearlyEvents = Schedule.getYearlyEvents()
                                            Event.Frequency.MONTHLY -> MonthlyEvents = Schedule.getMonthlyEvents()
                                            Event.Frequency.WEEKLY -> WeeklyEvents = Schedule.getWeeklyEvents()
                                            Event.Frequency.DAILY -> DailyEvents = Schedule.getDailyEvents()
                                        }
                                        when (it.getFrequency()) {
                                            Event.Frequency.ONCE -> OnceEvents = Schedule.getOnceEvents()
                                            Event.Frequency.YEARLY -> YearlyEvents = Schedule.getYearlyEvents()
                                            Event.Frequency.MONTHLY -> MonthlyEvents = Schedule.getMonthlyEvents()
                                            Event.Frequency.WEEKLY -> WeeklyEvents = Schedule.getWeeklyEvents()
                                            Event.Frequency.DAILY -> DailyEvents = Schedule.getDailyEvents()
                                        }
                                    },
                                    onDelete = {
                                        if(Schedule.removeEvent(it) == true) {
                                            println("Delete once event sucessfully")
                                            OnceEvents = Schedule.getOnceEvents()
                                        }
                                    }
                                )
                            }
                            }
                        }
                    }

                item {
                    Text(
                        text = "Daily events",
                        color = TextWhite,
                        //style = MaterialTheme.typography.body1
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                    )
                }
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(
                                color = MediumBlue,
                                shape = RoundedCornerShape(16.dp)
                            ), // Set the background color
                        contentAlignment = Alignment.TopStart
                    ) {
                        LazyColumn(modifier = modifier.padding(vertical = 5.dp)) {
                            items(DailyEvents) {DailyEvent ->
                                var mutableDailyEvent by remember { mutableStateOf(DailyEvent) }

                                EventButton(event = mutableDailyEvent,
                                    modifier = modifier,
                                    onSave = {
                                        Schedule.modifyEvent(DailyEvent, it)
                                        when (DailyEvent.getFrequency()) {
                                            Event.Frequency.ONCE -> OnceEvents = Schedule.getOnceEvents()
                                            Event.Frequency.YEARLY -> YearlyEvents = Schedule.getYearlyEvents()
                                            Event.Frequency.MONTHLY -> MonthlyEvents = Schedule.getMonthlyEvents()
                                            Event.Frequency.WEEKLY -> WeeklyEvents = Schedule.getWeeklyEvents()
                                            Event.Frequency.DAILY -> DailyEvents = Schedule.getDailyEvents()
                                        }
                                        when (it.getFrequency()) {
                                            Event.Frequency.ONCE -> OnceEvents = Schedule.getOnceEvents()
                                            Event.Frequency.YEARLY -> YearlyEvents = Schedule.getYearlyEvents()
                                            Event.Frequency.MONTHLY -> MonthlyEvents = Schedule.getMonthlyEvents()
                                            Event.Frequency.WEEKLY -> WeeklyEvents = Schedule.getWeeklyEvents()
                                            Event.Frequency.DAILY -> DailyEvents = Schedule.getDailyEvents()
                                        }
                                    },
                                    onDelete = {
                                        if(Schedule.removeEvent(DailyEvent)) {
                                            println("Delete daily event sucessfully")
                                            DailyEvents = Schedule.getDailyEvents()
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
                item {
                    Text(
                        text = "Weekly events",
                        color = TextWhite,
                        //style = MaterialTheme.typography.body1
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                    )
                }
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(
                                color = MediumBlue,
                                shape = RoundedCornerShape(16.dp)
                            ), // Set the background color
                        contentAlignment = Alignment.TopStart
                    ) {
                        LazyColumn(modifier = modifier.padding(vertical = 5.dp)) {
                            items(WeeklyEvents) {WeeklyEvent ->
                                var mutableWeeklyEvent by remember { mutableStateOf(WeeklyEvent) }
                                EventButton(
                                    event = mutableWeeklyEvent,
                                    modifier = modifier,
                                    onSave = {
                                        Schedule.modifyEvent(WeeklyEvent, it)
                                        when (WeeklyEvent.getFrequency()) {
                                            Event.Frequency.ONCE -> OnceEvents = Schedule.getOnceEvents()
                                            Event.Frequency.YEARLY -> YearlyEvents = Schedule.getYearlyEvents()
                                            Event.Frequency.MONTHLY -> MonthlyEvents = Schedule.getMonthlyEvents()
                                            Event.Frequency.WEEKLY -> WeeklyEvents = Schedule.getWeeklyEvents()
                                            Event.Frequency.DAILY -> DailyEvents = Schedule.getDailyEvents()
                                        }
                                        when (it.getFrequency()) {
                                            Event.Frequency.ONCE -> OnceEvents = Schedule.getOnceEvents()
                                            Event.Frequency.YEARLY -> YearlyEvents = Schedule.getYearlyEvents()
                                            Event.Frequency.MONTHLY -> MonthlyEvents = Schedule.getMonthlyEvents()
                                            Event.Frequency.WEEKLY -> WeeklyEvents = Schedule.getWeeklyEvents()
                                            Event.Frequency.DAILY -> DailyEvents = Schedule.getDailyEvents()
                                        }
                                    },
                                    onDelete = {
                                        if(Schedule.removeEvent(WeeklyEvent)) {
                                            println("Delete weekly event sucessfully")
                                            WeeklyEvents = Schedule.getWeeklyEvents()
                                        }
                                    })
                            }
                        }
                    }
                }
                item {
                    Text(
                        text = "Monthly events",
                        color = TextWhite,
                        //style = MaterialTheme.typography.body1
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                    )
                }
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(
                                color = MediumBlue,
                                shape = RoundedCornerShape(16.dp)
                            ), // Set the background color
                        contentAlignment = Alignment.TopStart
                    ) {
                        LazyColumn(modifier = modifier.padding(vertical = 5.dp)) {
                            items(MonthlyEvents) {MonthlyEvent ->
                                var mutableMonthlyEvent by remember { mutableStateOf(MonthlyEvent) }
                                EventButton(
                                    event = mutableMonthlyEvent,
                                    modifier = modifier,
                                    onSave = {
                                        Schedule.modifyEvent(MonthlyEvent, it)
                                        when (MonthlyEvent.getFrequency()) {
                                            Event.Frequency.ONCE -> OnceEvents = Schedule.getOnceEvents()
                                            Event.Frequency.YEARLY -> YearlyEvents = Schedule.getYearlyEvents()
                                            Event.Frequency.MONTHLY -> MonthlyEvents = Schedule.getMonthlyEvents()
                                            Event.Frequency.WEEKLY -> WeeklyEvents = Schedule.getWeeklyEvents()
                                            Event.Frequency.DAILY -> DailyEvents = Schedule.getDailyEvents()
                                        }
                                        when (it.getFrequency()) {
                                            Event.Frequency.ONCE -> OnceEvents = Schedule.getOnceEvents()
                                            Event.Frequency.YEARLY -> YearlyEvents = Schedule.getYearlyEvents()
                                            Event.Frequency.MONTHLY -> MonthlyEvents = Schedule.getMonthlyEvents()
                                            Event.Frequency.WEEKLY -> WeeklyEvents = Schedule.getWeeklyEvents()
                                            Event.Frequency.DAILY -> DailyEvents = Schedule.getDailyEvents()
                                        }
                                    },
                                    onDelete = {
                                        if(Schedule.removeEvent(MonthlyEvent)) {
                                            println("Delete weekly event sucessfully")
                                            MonthlyEvents = Schedule.getMonthlyEvents()
                                        }
                                    })
                            }
                        }
                    }
                }
                item {
                    Text(
                        text = "Yearly events",
                        color = TextWhite,
                        //style = MaterialTheme.typography.body1
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                    )
                }
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(
                                color = MediumBlue,
                                shape = RoundedCornerShape(16.dp)
                            ), // Set the background color
                        contentAlignment = Alignment.TopStart
                    ) {
                        LazyColumn(modifier = modifier.padding(vertical = 5.dp)) {
                            items(YearlyEvents) {YearlyEvent ->
                                var mutableYearlyEvent by remember { mutableStateOf(YearlyEvent) }
                                EventButton(
                                    event = mutableYearlyEvent,
                                    modifier = modifier,
                                    onSave = {
                                        Schedule.modifyEvent(YearlyEvent, it)
                                        when (YearlyEvent.getFrequency()) {
                                            Event.Frequency.ONCE -> OnceEvents = Schedule.getOnceEvents()
                                            Event.Frequency.YEARLY -> YearlyEvents = Schedule.getYearlyEvents()
                                            Event.Frequency.MONTHLY -> MonthlyEvents = Schedule.getMonthlyEvents()
                                            Event.Frequency.WEEKLY -> WeeklyEvents = Schedule.getWeeklyEvents()
                                            Event.Frequency.DAILY -> DailyEvents = Schedule.getDailyEvents()
                                        }
                                        when (it.getFrequency()) {
                                            Event.Frequency.ONCE -> OnceEvents = Schedule.getOnceEvents()
                                            Event.Frequency.YEARLY -> YearlyEvents = Schedule.getYearlyEvents()
                                            Event.Frequency.MONTHLY -> MonthlyEvents = Schedule.getMonthlyEvents()
                                            Event.Frequency.WEEKLY -> WeeklyEvents = Schedule.getWeeklyEvents()
                                            Event.Frequency.DAILY -> DailyEvents = Schedule.getDailyEvents()
                                        }
                                    },
                                    onDelete = {
                                        if(Schedule.removeEvent(YearlyEvent)) {
                                            println("Delete weekly event sucessfully")
                                            YearlyEvents = Schedule.getYearlyEvents()
                                        }
                                    })
                            }
                        }
                    }
                }
                item {
                    Box (
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.TopStart
                    ) {}
                }
            }

        }
    }
    OptionButtons(
        onAddNewEvent = {
            Schedule.addEvent(it)
            when (it.getFrequency()) {
                Event.Frequency.ONCE -> OnceEvents = Schedule.getOnceEvents()
                Event.Frequency.YEARLY -> YearlyEvents = Schedule.getYearlyEvents()
                Event.Frequency.MONTHLY -> MonthlyEvents = Schedule.getMonthlyEvents()
                Event.Frequency.WEEKLY -> WeeklyEvents = Schedule.getWeeklyEvents()
                Event.Frequency.DAILY -> DailyEvents = Schedule.getDailyEvents()
            }
        }
    )

}

fun testData(schedule: Schedule) {

    val attendee1 = Attendee("John Doe", "Organizer", "john@example.com")
    val attendee2 = Attendee("Alice Smith", "Participant", "alice@example.com")
    val attendee3 = Attendee("Bob Johnson", "Participant", "bob@example.com")
    val attendee4 = Attendee("Eve Williams", "Participant", "eve@example.com")
    val attendee5 = Attendee("Charlie Brown", "Participant", "charlie@example.com")
    val attendee6 = Attendee("Grace Davis", "Participant", "grace@example.com")

    val attendees = Vector<Attendee>()
    attendees.addAll(listOf(attendee1, attendee2, attendee3, attendee4, attendee5, attendee6))

    var oncevent1 = Event(
        frequency = Event.Frequency.ONCE,
        summary = "Birthday party 1",
        description = "Celebrate John's birthday",
        location = "123 Main Street",
        attendees = attendees
        )
    var oncevent2 = Event(
        frequency = Event.Frequency.ONCE,
        summary = "Birthday party 2",
        description = "Celebrate John's birthday",
        location = "123 Main Street",
        attendees = attendees
    )
    var oncevent3 = Event(
        frequency = Event.Frequency.ONCE,
        summary = "Birthday party 3",
        description = "Celebrate John's birthday",
        location = "123 Main Street",
        attendees = attendees
    )
    var oncevent4 = Event(
        frequency = Event.Frequency.ONCE,
        summary = "Birthday party 4",
        description = "Celebrate John's birthday",
        location = "123 Main Street",
        attendees = attendees
    )
    var oncevent5 = Event(
        frequency = Event.Frequency.ONCE,
        summary = "Birthday party 5",
        description = "Celebrate John's birthday",
        location = "123 Main Street",
        attendees = attendees
    )
    var oncevent6 = Event(
        frequency = Event.Frequency.ONCE,
        summary = "Birthday party 6",
        description = "Celebrate John's birthday",
        location = "123 Main Street",
        attendees = attendees
    )


    var yearevent = Event(
        frequency = Event.Frequency.YEARLY,
        summary = "Anniversary",
        description = "Celebrate 5th Anniversary",
        location = "456 Elm Street",
        attendees = attendees
    )
    var monthevent = Event(
        frequency = Event.Frequency.WEEKLY,
        summary = "Team Meeting",
        description = "Discuss project updates",
        location = "Conference Room A",
        attendees = attendees
    )
    var weekevent = Event(
        frequency = Event.Frequency.MONTHLY,
        summary = "Gym Session",
        description = "Stay fit",
        location = "Fit Life Gym",
        attendees = attendees
    )
    var dailyevent = Event(
        frequency = Event.Frequency.DAILY,
        summary = "Morning Jog",
        description = "Stay healthy",
        location = "Local Park",
        attendees = attendees
    )

    schedule.addEvent(oncevent1)
    schedule.addEvent(oncevent2)
    schedule.addEvent(oncevent3)
    schedule.addEvent(oncevent4)
    schedule.addEvent(oncevent5)
    schedule.addEvent(oncevent6)
    schedule.addEvent(yearevent)
    schedule.addEvent(monthevent)
    schedule.addEvent(weekevent)
    schedule.addEvent(dailyevent)

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    ASHTheme {
        Homescreen(schedule = Schedule.getInstance(),name = "Andrew")
        {

        }
    }
}