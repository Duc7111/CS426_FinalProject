package com.example.ash

import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.widget.TimePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.google.android.material.datepicker.MaterialDatePicker
import org.jetbrains.annotations.Nullable
import java.text.DateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.Calendar.HOUR
import java.util.Vector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDisplay(event: Event = Event(), isEditable: Boolean = false, onEventChange: (Event) -> Unit)
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
        //horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
    )
    {
        item {
            OutlinedTextField(
                value = summary,
                onValueChange = { summary = it },
                label = { Text(text = "Summary") },
                modifier = Modifier
                    .fillMaxWidth(),
                readOnly = !isEditable
            )
        }
        item {
            Box(
                 modifier = Modifier
                     .fillMaxWidth()
            )
            {

                OutlinedTextField(
                    value = frequency.toString(),
                    onValueChange = {  },
                    label = { Text(text = "Frequency") },
                    readOnly = true,
                    interactionSource = remember { MutableInteractionSource() }
                        .also { interactionSource ->
                            LaunchedEffect(interactionSource) {
                                interactionSource.interactions.collect {
                                    if (it is PressInteraction.Release) {
                                        freqExpanded = true
                                    }
                                }
                            }
                        },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { freqExpanded = true }
                        .onGloballyPositioned { coordinates ->
                            freqSize = coordinates.size.toSize()
                        },
                )
                if (isEditable) {
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
            }
        }
        item {
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text(text = "Description") },
                modifier = Modifier
                    .fillMaxWidth(),
                readOnly = !isEditable
            )
        }
        item {
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text(text = "Location") },
                modifier = Modifier
                    .fillMaxWidth(),
                readOnly = !isEditable
            )
        }
        item {
            DateDisplay(
                initialDate = date,
                onDateSelected = {
                    date = it
                },
                isEditable = isEditable
            )
        }
        item {

            TimeDisplay(
                time = startTime,
                onTimePick = {
                    startTime = it
                    date.set(Calendar.HOUR_OF_DAY, startTime[0])
                    date.set(Calendar.MINUTE, startTime[1])
                },
                label = "From",
                isEditable = isEditable
            )
            TimeDisplay(
                time = endTime,
                onTimePick = {
                    endTime = it
                },
                label = "To",
                isEditable = isEditable
            )
        }
        item {
            DisplayAttendee(attendees = attendees, isEditable = isEditable)
            }
        }
    var endtime: Calendar? = null
    if(!endTime.isEmpty())
    {
        endtime = Calendar.getInstance()
        endtime.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE), endTime[0], endTime[1])
    }
    onEventChange(Event(frequency = frequency, summary = summary, description = description, location = location, startTime = date, endTime = endtime, attendees = attendees))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateDisplay(
    onDateSelected: (Calendar) -> Unit,
    isEditable: Boolean = false,
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
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        Text(
            text = "Date: ${selectedDate.get(Calendar.YEAR)}, ${selectedDate.get(Calendar.MONTH) + 1}, ${selectedDate.get(Calendar.DATE)}"
        )

        Button(onClick = { showDatePicker = true }, modifier = Modifier.padding(10.dp), enabled = isEditable) {
            Text(text = "Change")
        }
    }
    
    if(showDatePicker)
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
fun TimeDisplay(time: List<Int>, onTimePick: (List<Int>) -> Unit, label: String, isEditable: Boolean = false)
{
    val initTime = if(time.size != 2) listOf(0, 0) else time
    val timePickerState = remember {
        TimePickerState(
            is24Hour = true,
            initialHour = initTime[0],
            initialMinute = initTime[1]
        )
    }
    /*
    var timePicked by remember { mutableStateOf(time) }
    TimePickCard(
        timePickerState = timePickerState,
        onTimePick = {timePicked = it},
        label = label
    )
    onTimePick(timePicked)
    */


    val context = LocalContext.current
    val hour = initTime[0]
    val minute = initTime[1]
    val timerSelectedStore = remember { mutableStateOf("${initTime[0]}:${initTime[1]}") }
    var hourSelect: Int by remember { mutableStateOf(initTime[0]) }
    var minuteSelect: Int by remember { mutableStateOf(initTime[1]) }

    val mTimePickerDialog = TimePickerDialog(
        context,
        {
            timePicker: TimePicker, hourSelected: Int, minuteSelected: Int ->
            hourSelect = hourSelected
            minuteSelect = minuteSelected
            timerSelectedStore.value = "$hourSelected:$minuteSelected"
        },
        hour, minute, true
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(text = label + ": ${timerSelectedStore.value}")
        Button(
            onClick = {
                mTimePickerDialog.show()
            },
            enabled = isEditable
        ) {
            Text(text = "Change")
        }
    }

    onTimePick(listOf(hourSelect, minuteSelect))
    println("$hourSelect:$minuteSelect")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickCard(timePickerState: TimePickerState, onTimePick: (List<Int>) -> Unit, label: String) {

    OutlinedCard(
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
    )
    {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 10.dp)
        )

        TimePicker(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            state = timePickerState,
            layoutType = TimePickerLayoutType.Vertical
        )
    }
    onTimePick(listOf(timePickerState.hour, timePickerState.minute))
}

@Composable
fun DisplayAttendee(attendees: Vector<Attendee>, isEditable: Boolean = false) {

    var showAddDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // Set the desired height here
    ) {
        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Attendees",
                        color = Color.Gray,
                        modifier = Modifier.padding(10.dp)
                    )
                    Button(
                        onClick = {
                            showAddDialog = true
                        },
                        enabled = isEditable,
                        modifier = Modifier
                            .padding(vertical = 5.dp, horizontal = 10.dp)
                            .height(30.dp)
                    ) {
                        Text ("Add attendee", fontSize = 10.sp)
                    }
                }

                if (attendees.size != 0) {
                    Column(
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    ) {
                        attendees.forEach { attendee ->
                            Text(
                                text = "${attendee.getName()} - ${attendee.getRole()} - ${attendee.getContact()} " ,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }

                } else {
                    Text(
                        text = "No attendees",
                        color = Color.Black,
                        modifier = Modifier.padding(10.dp)
                    )
                }

                Box(
                    modifier = Modifier.height(50.dp)
                )
            }
        }
    }

    if (showAddDialog) {
        AddAttendeeDialog(
            onAddNewAttendee = {attendees.addElement(it)},
            onClose = {
                showAddDialog = false
            }
        )
    }
}



@Composable
fun AddAttendeeDialog(onAddNewAttendee: (Attendee) -> Unit, onClose: () -> Unit) {

    var newAttendee by remember { mutableStateOf(Attendee()) }
    var newName by remember { mutableStateOf("") }
    var newRole by remember { mutableStateOf("") }
    var newContact by remember { mutableStateOf("") }

    AlertDialog(
        modifier = Modifier,
        onDismissRequest = {
        },
        title = { Text ( text = "New Attendee" ) },
        text = {
                Column (
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                        ) {
                    OutlinedTextField(
                        value = newName,
                        onValueChange = { newName = it },
                        label = { Text(text = "Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = newRole,
                        onValueChange = { newRole = it },
                        label = { Text(text = "Role") },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = newContact,
                        onValueChange = { newContact = it },
                        label = { Text(text = "Contact info") },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
        },
        dismissButton = {
            Row() {
                    Button( //Cancel
                        onClick = {
                            onClose()
                        },
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        Text("Cancel")
                    }
                    Button( //Save
                        onClick = {
                            onAddNewAttendee(Attendee(newName,newRole,newContact))
                            onClose()
                        },
                        modifier = Modifier.padding(horizontal = 5.dp)
                    ) {
                        Text("Save")
                    }
                }


        },
        confirmButton = {

        }

    )

}


@Preview
@Composable
private fun Test()
{
    var event by remember { mutableStateOf(Event()) }
    EventDisplay(event)
    {
        event = it
    }
}

@Preview
@Composable
private fun TestDate()
{
    var date by remember { mutableStateOf(Calendar.getInstance()) }
    DateDisplay(initialDate = date, onDateSelected = {date = it})
}
