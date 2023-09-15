package com.example.ash.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ash.Event
import com.example.ash.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetails(event: Event, isEditable: Boolean = false ) {
    var summary by remember { mutableStateOf(event.getSummary()) }
    var location by remember { mutableStateOf(event.getLocation()) }
    var description by remember { mutableStateOf(event.getDescription()) }
    var startime by remember { mutableStateOf(event.getStartTime().getTime().toString()) }
    var isExpanded by remember { mutableStateOf(false) }
    var frequency by remember { mutableStateOf(event.getFrequency().toString()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Set your desired background color
    ) {
        // Your content for the floating window
        Column() {
            Text(
                text = "Frequency" ,
                color = Color.Gray,
                modifier = Modifier
                    .padding(10.dp)
                    .verticalScroll(rememberScrollState())
            )

            ExposedDropdownMenuBox(
                expanded = isExpanded,
                onExpandedChange = {
                        isExpanded = it
                }
            ) {
                TextField(
                    value = frequency,
                    onValueChange =  {
                      frequency = it
                    },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                        .padding(8.dp)
                        .menuAnchor(),
                    singleLine = true, // Adjust this as needed
                    textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black),
                    trailingIcon = {
                        if (isEditable)
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                if (isEditable) {
                    ExposedDropdownMenu(
                        expanded = isExpanded,
                        onDismissRequest = {isExpanded = false}
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = "ONCE") },
                            onClick = {
                                frequency = "ONCE"
                                isExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "DAILY") },
                            onClick = {
                                frequency = "DAILY"
                                isExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "WEEKLY") },
                            onClick = {
                                frequency = "WEEKLY"
                                isExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "MONTHLY") },
                            onClick = {
                                frequency = "MONTHLY"
                                isExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "YEARLY") },
                            onClick = {
                                frequency = "YEARLY"
                                isExpanded = false
                            }
                        )
                }

                }
            }

            Text(
                text = "Summary" ,
                color = Color.Gray,
                modifier = Modifier.padding(10.dp)
            )
            BasicTextField(
                value = summary,
                onValueChange =  {
                    summary = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp),
                singleLine = true, // Adjust this as needed
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black),
                readOnly = !isEditable
            )
            Text(
                text = "Location" /*event.getLocation()*/,
                color = Color.Gray,
                modifier = Modifier.padding(10.dp)
            )
            BasicTextField(
                value = location
                ,
                onValueChange =  {
                    location = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp),
                singleLine = true, // Adjust this as needed
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black),
                readOnly = !isEditable
            )
            Text(
                text = "Description",
                color = Color.Gray,
                modifier = Modifier.padding(10.dp)
            )
            BasicTextField(
                value = description,
                onValueChange =  {
                    description = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp),
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black),
                readOnly = !isEditable
            )
            Text(
                text = "Start Time",
                color = Color.Gray,
                modifier = Modifier.padding(10.dp)
            )
            BasicTextField(
                value = startime,
                onValueChange =  {
                    startime = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp),
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black),
                readOnly = !isEditable
            )
            Text(
                text = "Attendees",
                color = Color.Gray,
                modifier = Modifier.padding(10.dp)
            )

            LazyColumn(modifier = Modifier.padding(vertical = 5.dp)) {
                item {
                    /*Attendee button (show the information of the attendee)*/
                }
            }

        }
    }
}
@Composable
fun EventDetailsDialog(modifier: Modifier, event: Event, isViewOnly: Boolean, isNewEvent: Boolean, onClose: () -> Unit, onSave: () -> Unit, onDelete: () -> Unit) {

    var isEditable by remember { mutableStateOf(false) }
    if (isNewEvent) isEditable = true

    AlertDialog(
        onDismissRequest = {
            if (!isViewOnly) isEditable = false
            onClose()
        },
        title = {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                if (!isNewEvent)
                    Text( text = "Event Details" )
                else
                    Text ( text = "New Event" )

                if (!isViewOnly && !isNewEvent) {
                    if (!isEditable) {
                        Button(
                            onClick = {
                                isEditable = true
                            }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.edit_icon), // Replace with your image resource
                                    contentDescription = null, // Provide a suitable content description
                                    modifier = Modifier.size(24.dp) // Adjust the size as needed
                                )
                                Text(
                                    text = "Edit",
                                    modifier = Modifier.padding(start = 8.dp) // Add padding between the image and text if needed
                                )
                            }
                        }
                    }
                    else {
                        Text(
                            text = "Editing event mode",
                            fontSize = 20.sp
                        )
                    }
                }

            }

        },
        text = {
            Column() {
                EventDetails(event, isEditable)
            }

        },
        confirmButton = {
            Row() {
                //Cancel and Ok buttons when edit/add an event
                if (isEditable) {
                    Button( //Cancel
                        onClick = {
                            isEditable = false
                            if (isNewEvent) {
                                // Close without handling the data
                                onClose()
                            }
                        },
                        modifier = Modifier.padding(horizontal = 5.dp)
                    ) {
                        Text("Cancel")
                    }
                    Button( //Save
                        onClick = {
                            isEditable = false
                            onSave()
                            if (isNewEvent) onClose()
                        },
                        modifier = Modifier.padding(horizontal = 5.dp)
                    ) {
                        Text("Save")
                    }
                }
            }

        },
        dismissButton = {
            // The close button when viewing the event
            if (!isEditable && !isNewEvent)
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                // Delete and Close button
                    Button( //Delete event button
                        onClick = {
                            // Handle deleting the event here
                            onDelete()
                            // Raise the confirm dialog
                            onClose()
                        },
                        modifier = Modifier.padding(horizontal = 5.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.trash_icon), // Replace with your image resource
                                contentDescription = null, // Provide a suitable content description
                                modifier = Modifier.size(24.dp) // Adjust the size as needed
                            )
                            Text(
                                text = "Delete this event",
                                modifier = Modifier.padding(start = 8.dp) // Add padding between the image and text if needed
                            )
                        }
                    }
                    Button( //Close dialog button
                        onClick = {
                            onClose()
                        },
                        modifier = Modifier.padding(horizontal = 5.dp)
                    ) {
                        Text("Close")
                    }
                }

        }

    )
}

@Preview
@Composable
fun EventButton(/*event: Event ,*/ modifier: Modifier = Modifier) {
    var showEventDialog by remember { mutableStateOf(false) }


    var summary : String = "Dinner date"
    var location : String = "Haidilao"
    var date : String = "September 3rd, 2023"
    var startime : String = "7PM"
    var description : String = "Remember to buy a bouquet of flowers and bla bla bla" +
            "bla bla bla bla bla bla bla bla bla blabla bla bla bla blabla bla bla bla bla"

    var current_event = Event(summary = summary, location = location, description = description)


    Button(
        onClick = {
            showEventDialog = true
        },
        colors = ButtonDefaults.buttonColors(LightBlue),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = "$summary" + " - " +
                    "$location" + " - " +
                    "$date" + " - " +
                    "$startime" + " - " +
                    "$description",
            color = TextWhite,
            maxLines = 1, // Set the maximum number of lines
            overflow = TextOverflow.Ellipsis // Truncate with ellipsis when text overflows)
        )
        /*
        Text(text = event.getSummary() + " - ", color = TextWhite)
        Text(text = event.getLocation() + "\n", color = TextWhite)
        Text(text = event.getDate().toString() + "\n", color = TextWhite)
        Text(text = event.getStartTime().toString() + "\n", color = TextWhite)
        */
    }
    if (showEventDialog) {
        EventDetailsDialog(
            modifier = modifier,
            event = current_event,
            isViewOnly = false,
            isNewEvent = false,
            onClose = {
                showEventDialog = false
            },
            onSave = {},
            onDelete = {}
        )
    }
}

@Preview
@Composable
fun OptionButtons(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .background(color = Color.Transparent),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        FloatingActionButton(
            onClick = { /*Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show() */ },
            Modifier
                .background(color = Color.Transparent)
                .padding(vertical = 5.dp)
                .size(56.dp),
            contentColor = Color.White
        ) {
            Image(
                painter = painterResource(id = R.drawable.minimize), // Replace with your image resource
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .scale(0.7f)
            )
        }
        FloatingActionButton(
            onClick = { /*Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show() */ },
            Modifier
                .background(color = Color.Transparent)
                .padding(vertical = 5.dp)
                .size(56.dp),
            contentColor = Color.White
        ) {
            Image(
                painter = painterResource(id = R.drawable.calendar),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .scale(0.7f)
            )
        }
        var showEventDialog by remember { mutableStateOf(false) }
        FloatingActionButton(
            onClick = {
                showEventDialog = true
            },
            Modifier
                .background(color = Color.Transparent)
                .padding(vertical = 5.dp)
                .size(56.dp),
            contentColor = Color.White
        ) {
            Image(
                painter = painterResource(id = R.drawable.plus),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .scale(0.7f)
            )
        }
        if (showEventDialog) {
            var newEvent = Event()
            EventDetailsDialog(
                modifier = modifier,
                event = newEvent,
                isViewOnly = false,
                isNewEvent = true,
                onClose = {
                        showEventDialog = false
                },
                onSave = {},
                onDelete = {}
            )

        }

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Good morning, $name",
                color = TextWhite,
                //style = MaterialTheme.typography.h2
            )
            Text(
                text = "Let's check your schedule !",
                color = TextWhite,
                //style = MaterialTheme.typography.body1
            )

        }
    }
}
//------------------------------UI Elements----------------------------------
