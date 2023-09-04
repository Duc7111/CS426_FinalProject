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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.ash.ui.theme.TextWhite


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
                    Greeting("Android")
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
    ){

        Column(modifier = modifier) {
            Greeting(name)
            Text(
                text = "One time events",
                color = TextWhite,
                //style = MaterialTheme.typography.body1
                modifier = Modifier
                    .padding(vertical = 16.dp)
            )
            Box (modifier = Modifier
                .size(width = 350.dp, height = 100.dp)
                .background(Color.Blue), // Set the background color
                contentAlignment = androidx.compose.ui.Alignment.Center){
            }
            Text(
                text = "Daily events",
                color = TextWhite,
                //style = MaterialTheme.typography.body1
                modifier = Modifier
                    .padding(vertical = 16.dp)
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    ASHTheme {
        Homescreen(name = "Andrew")
    }
}