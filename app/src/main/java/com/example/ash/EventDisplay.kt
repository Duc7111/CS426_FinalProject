package com.example.ash

import android.icu.util.Calendar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.DateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.Vector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDisplay(event: Event = Event(), onEventChange: (Event) -> Unit)
{
    var frequency by remember { mutableStateOf(event.getFrequency()) }
    var summary by remember { mutableStateOf(event.getSummary()) }
    var description by remember { mutableStateOf(event.getDescription()) }
    var location by remember { mutableStateOf(event.getLocation()) }
    var date by remember { mutableStateOf(event.getStartTime()) }
    var startTime by remember { mutableStateOf(event.getTime(true)) }
    var endTime by remember { mutableStateOf(event.getTime(false)) }
    val attendees: Vector<Attendee> by remember { mutableStateOf(Vector(event.getAttendees())) }

    var freqExpanded by remember { mutableStateOf(false) }
    var freqSize by remember { mutableStateOf(Size.Zero) }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
    )
    {
        item {
            OutlinedTextField(
                value = summary,
                onValueChange = { summary = it },
                label = { Text(text = "Summary") },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        item {
            OutlinedTextField(
                value = summary,
                onValueChange = { summary = it },
                label = { Text(text = "Frequency") },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { freqExpanded = true }
                    .onGloballyPositioned { coordinates ->
                        freqSize = coordinates.size.toSize()
                    },
            )
            DropdownMenu(
                expanded = freqExpanded,
                onDismissRequest = { freqExpanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current){freqSize.width.toDp()})
            )
            {
                DropdownMenuItem(
                    text = { Text("Once") },
                    onClick = { frequency = Event.Frequency.ONCE },
                )
                DropdownMenuItem(
                    text = { Text("Yearly") },
                    onClick = { frequency = Event.Frequency.YEARLY },
                )
                DropdownMenuItem(
                    text = { Text("Monthly") },
                    onClick = { frequency = Event.Frequency.MONTHLY },
                )
                DropdownMenuItem(
                    text = { Text("Weekly") },
                    onClick = { frequency = Event.Frequency.WEEKLY },
                )
                DropdownMenuItem(
                    text = { Text("Daily") },
                    onClick = { frequency = Event.Frequency.DAILY },
                )

            }
        }
        item {
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text(text = "Description") },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        item {
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text(text = "Location") },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        item {
            DateDisplay(
                initialDate = date,
                onDateSelected = {
                    date = it
                }
            )
        }
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                TimeDisplay(
                    time = startTime,
                    onTimePick = { startTime = it },
                    label = "From"
                )
                TimeDisplay(
                    time = endTime,
                    onTimePick = { endTime = it },
                    label = "To"
                )
            }
        }
        items (attendees)
        {
            DisplayAttendee(it)
        }
    }



}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateDisplay(
    onDateSelected: (Calendar) -> Unit,
    initialDate: Calendar = Calendar.getInstance()
)
{

    val selectedDate by remember { mutableStateOf(initialDate) }
    var showDatePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(
        yearRange = (2023 .. 2024),
        initialSelectedDateMillis = selectedDate.timeInMillis,
        initialDisplayMode = DisplayMode.Picker,
        initialDisplayedMonthMillis = null
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        Text(
            text = "Date: ${DateFormat.getDateInstance().format(selectedDate)}"
        )

        Button(onClick = { showDatePicker = true }) {
            Text(text = "Change")
        }
    }
    

    DatePickerDialog(
        onDismissRequest = { showDatePicker = false },
        confirmButton = {
            TextButton(
                onClick = {
                    selectedDate.timeInMillis = datePickerState.selectedDateMillis!!
                    onDateSelected(selectedDate)
                    showDatePicker = false
                }
            )
            {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { showDatePicker = false })
            {
                Text(text = "Cancel")
            }
        }
    )
    {
        DatePicker(state = datePickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimeDisplay(time: List<Int>, onTimePick: (List<Int>) -> Unit, label: String)
{
    val initTime = if(time.size != 2) listOf(0, 0) else time
    var editing by remember { mutableStateOf(false) }
    val timePickerState = remember {
        TimePickerState(
            is24Hour = true,
            initialHour = initTime[0],
            initialMinute = initTime[1]
        )
    }

    OutlinedCard(
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .clickable {
                editing = true
            }
    )
    {
        Text(text = label)

        TimePicker(
            state = timePickerState,
            layoutType = TimePickerLayoutType.Horizontal,
            modifier = Modifier
                .fillMaxWidth()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            TextButton(
                enabled = editing,
                onClick = {

                    editing = false
                }
            )
            {
                Text(text = "Cancel")
            }
            TextButton(
                enabled = editing,
                onClick = {
                    onTimePick(listOf(timePickerState.hour, timePickerState.minute))
                    editing = false
                }
            )
            {
                Text(text = "Confirm")
            }
        }
    }
}

@Composable
fun DisplayAttendee(it: Attendee) {
    OutlinedCard() {

    }
}