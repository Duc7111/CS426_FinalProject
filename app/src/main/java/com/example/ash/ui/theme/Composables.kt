package com.example.ash.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
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
import com.example.ash.EventDisplay
import com.example.ash.R
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


@Composable
fun EventDetailsDialog(modifier: Modifier, event: Event, isViewOnly: Boolean, isNewEvent: Boolean, onClose: () -> Unit, onSave: (Event) -> Unit, onDelete: () -> Unit) {

    var changedEvent by remember { mutableStateOf(event) }
    var isEditable by remember { mutableStateOf(false) }
    if (isNewEvent) isEditable = true

    AlertDialog(
        modifier = modifier,
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
                            text = "Editing mode",
                            fontSize = 20.sp
                        )
                    }
                }

            }

        },
        text = {
                //EventDetails(event, isEditable)
            EventDisplay(changedEvent, isEditable, onEventChange = {changedEvent = it})

        },
        dismissButton = {
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
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        Text("Cancel")
                    }
                    Button( //Save
                        onClick = {
                            isEditable = false
                            onSave(changedEvent)
                            if (isNewEvent) onClose()
                        },
                        modifier = Modifier.padding(horizontal = 5.dp)
                    ) {
                        Text("Save")
                    }
                }
            }

        },
        confirmButton = {
            // The close button when viewing the event
            if (!isEditable && !isNewEvent)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                // Delete and Close button
                    Button( //Delete event button
                        onClick = {
                            // Handle deleting the event here
                            onDelete()
                            // Raise the confirm dialog
                            onClose()
                        }
                    ) {
                        Row() {
                            Image(
                                painter = painterResource(id = R.drawable.trash_icon), // Replace with your image resource
                                contentDescription = null, // Provide a suitable content description
                                modifier = Modifier.size(24.dp) // Adjust the size as needed
                            )
                            Text(
                                text = "Delete this event"
                            )
                        }
                    }
                    Button( //Close dialog button
                        onClick = {
                            onClose()
                        }
                    ) {
                        Text("Close")
                    }
                }

        }

    )
}

@Composable
fun EventButton(event: Event , modifier: Modifier = Modifier, onSave: (Event) -> Unit, onDelete: (Event) -> Unit) {
    var showEventDialog by remember { mutableStateOf(false) }
    var changedEvent by remember { mutableStateOf(event) }

    var summary : String = "Dinner date"
    var location : String = "Haidilao"
    var date : String = "September 3rd, 2023"
    var startime : String = "7PM"
    var description : String = "Remember to buy a bouquet of flowers and bla bla bla" +
            "bla bla bla bla bla bla bla bla bla blabla bla bla bla blabla bla bla bla bla"

    var current_event = Event(summary = summary, location = location, description = description)

    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    sdf.setTimeZone(TimeZone.getTimeZone("GMT+07")) // Set your desired timezone


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
            text = "${changedEvent.getSummary()}" + " - " +
                    "${changedEvent.getLocation()}" + " - " +
                    //"${changedEvent.}" + " - " +
                    "${sdf.format(changedEvent.getStartTime().getTime())}" + " - " +
                    "${changedEvent.getDescription()}",
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
            event = changedEvent,
            isViewOnly = false,
            isNewEvent = false,
            onClose = {
                showEventDialog = false
            },
            onSave = {
                current_event = it
                onSave(current_event)
            },
            onDelete = {
                onDelete(event)
            }
        )
    }
}

//@RequiresApi(Build.VERSION_CODES.Q)
@Preview
@Composable
fun OptionButtons(modifier: Modifier = Modifier, onAddNewEvent: (Event) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .background(color = Color.Transparent),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        //val context = LocalContext.current
        var isBubbleDisplay by remember { mutableStateOf(false) }
        FloatingActionButton(
            onClick = {
                //context.startActivity(Intent(context, BubbleDisplay::class.java))
                      isBubbleDisplay = true
            },
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
        if (isBubbleDisplay) {
            //bubbleDisplay()
        }
        if (showEventDialog) {
            EventDetailsDialog(
                modifier = modifier,
                event = Event(),
                isViewOnly = false,
                isNewEvent = true,
                onClose = {
                        showEventDialog = false
                },
                onSave = {
                         onAddNewEvent(it)
                },
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
