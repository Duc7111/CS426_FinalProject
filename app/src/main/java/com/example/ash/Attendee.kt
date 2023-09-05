package com.example.ash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import java.io.BufferedReader
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
        fun read(fbr: BufferedReader): Attendee
        {
            val name = DataHandler.readString(fbr)
            val role = DataHandler.readString(fbr)
            val contact = DataHandler.readString(fbr)
            return Attendee(name, role, contact)
        }
    }

    fun getName() = name
    fun getRole() = role
    fun getContact() = contact
}
