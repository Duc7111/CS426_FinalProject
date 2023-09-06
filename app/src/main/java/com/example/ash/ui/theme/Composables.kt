package com.example.ash.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ash.R

@Preview
@Composable
fun FloatingWindowContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Set your desired background color
    ) {
        // Your content for the floating window
        Text("Event Details")
    }
}

@Composable
fun EventButton(/*event: Event ,*/ modifier: Modifier = Modifier) {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(LightBlue),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = "Dinner date" + " - " +
                    "Haidilao" + " - " +
                    "September 3rd, 2023" + " - " +
                    "7PM" + " - " +
                    "Bla bla bla bla bla bla bla bla bla bla bla bla bla",
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
                painter = painterResource(id = R.drawable.calendar), // Replace with your image resource
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
                painter = painterResource(id = R.drawable.plus), // Replace with your image resource
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .scale(0.7f)
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
