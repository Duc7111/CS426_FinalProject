package com.example.ash

import android.os.Bundle
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
class MainActivity : ComponentActivity() {
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
                    Homescreen(name = "Phoenix")
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
fun Homescreen(name: String, modifier: Modifier = Modifier) {
    Box(modifier = Modifier
        .background(DeepBlue)
        .fillMaxSize()
        .padding(20.dp)
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
                        contentAlignment = Alignment.Center
                    ) {
                        LazyColumn(modifier = modifier) {
                            item {
                                EventButton(modifier = modifier)
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
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    ASHTheme {
        Homescreen(name = "Andrew")
    }
}