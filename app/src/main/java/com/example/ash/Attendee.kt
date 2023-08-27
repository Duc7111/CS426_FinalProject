package com.example.ash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview

class Attendee (
    private val name: String = "",
    private val role: String = "",
    private val contact: String = "",
)
{

    @Composable
    fun ComposeAttendee()
    {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        )
        {
            Text(
                text = "$name ($role)"
            )
            Text(
                text = contact
            )
        }
    }


}

@Preview(showBackground = true)
@Composable
private fun ComposeAttendee()
{
    val attendee = Attendee()
    attendee.ComposeAttendee()
}