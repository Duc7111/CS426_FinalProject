package com.example.ash.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ash.Event
import java.lang.NullPointerException

@Preview
@Composable
fun EventButton(/*event: Event ,*/ modifier: Modifier = Modifier) {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(LightBlue),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        shape = RoundedCornerShape(5.dp),

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
