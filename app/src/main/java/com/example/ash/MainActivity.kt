package com.example.ash

import android.content.Context
import android.os.Bundle
import android.text.TextUtils.indexOf
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ash.ui.theme.ASHTheme
import com.example.ash.ui.theme.DeepBlue
import com.example.ash.ui.theme.EventButton
import com.example.ash.ui.theme.MediumBlue
import com.example.ash.ui.theme.TextWhite
import com.example.ash.ui.theme.Greeting
import com.example.ash.ui.theme.EventButton
import com.example.ash.ui.theme.OptionButtons
import com.example.ash.ui.theme.EventDetailsDialog
import com.example.ash.EventDisplay
import kotlin.collections.EmptyList.indexOf

class MainActivity : ComponentActivity() {

    val scheduleInstance = Schedule.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("onCreate()")
        setContent {
            ASHTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Homescreen(schedule = scheduleInstance ,name = "Phoenix")

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
fun Homescreen(schedule: Schedule,name: String, modifier: Modifier = Modifier) {


    var OnceEvents by remember { mutableStateOf(schedule.getOnceEvents())}
    var DailyEvents by remember { mutableStateOf(schedule.getDailyEvents())}
    var WeeklyEvents by remember { mutableStateOf(schedule.getWeeklyEvents())}
    var MonthlyEvents by remember { mutableStateOf(schedule.getMonthlyEvents())}
    var YearlyEvents by remember { mutableStateOf(schedule.getYearlyEvents())}


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
            LazyColumn(modifier = modifier) {
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
                                    onSave = { updatedEvent ->
                                        // Update the mutable variable with the changes
                                        mutableOnceEvent = updatedEvent
                                        //save the updated event to the original list
                                        var index = OnceEvents.indexOf(onceEvent)

                                        // If the index is valid (not -1), update the original list
                                        if (index != -1) {
                                            //OnceEvents.set(index, updatedEvent)
                                        }
                                    },
                                    onDelete = { schedule.removeEvent(it) }
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
                        contentAlignment = Alignment.Center
                    ) {
                        LazyColumn(modifier = modifier.padding(vertical = 5.dp)) {
                            items(DailyEvents) {DailyEvent ->
                                EventButton(event = DailyEvent, modifier = modifier, onSave = {}, onDelete = {})
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
                        contentAlignment = Alignment.Center
                    ) {
                        LazyColumn(modifier = modifier.padding(vertical = 5.dp)) {
                            items(WeeklyEvents) {WeeklyEvent ->
                                EventButton(event = WeeklyEvent, modifier = modifier, onSave = {}, onDelete = {})
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
                        contentAlignment = Alignment.Center
                    ) {
                        EventButton(event = MonthlyEvent, modifier = modifier, onSave = {}, onDelete = {})
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
                        contentAlignment = Alignment.Center
                    ) {
                        LazyColumn(modifier = modifier.padding(vertical = 5.dp)) {
                            items(YearlyEvents) {YearlyEvent ->
                                EventButton(event = YearlyEvent, modifier = modifier, onSave = {}, onDelete = {})
                            }
                        }
                    }
                }
                item {
                    Box (
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {}
                }
            }

        }
    }
    OptionButtons()

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    ASHTheme {
        Homescreen(schedule = Schedule.getInstance(),name = "Andrew")
    }
}