package com.example.ash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import java.io.FileInputStream
import java.io.FileOutputStream

class Attendee (
    private val name: String = "",
    private val role: String = "",
    private val contact: String = "",
)
{
    fun write(fout: FileOutputStream)
    {
        DataHandler.write(name, fout)
        DataHandler.write(role, fout)
        DataHandler.write(contact, fout)
    }

    companion object
    {
        fun read(fin: FileInputStream): Attendee
        {
            val name = DataHandler.readString(fin)
            val role = DataHandler.readString(fin)
            val contact = DataHandler.readString(fin)
            return Attendee(name, role, contact)
        }
    }

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