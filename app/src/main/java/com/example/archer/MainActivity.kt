package com.example.archer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.archer.ui.theme.ArcherTheme
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArcherTheme {
                Game()
            }
        }
    }
}
@Preview
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Game(modifier: Modifier = Modifier){
    var align by remember { mutableStateOf(false) }


    val arrowAlign by animateIntAsState(
        targetValue = if (align) 180 else 0,
        animationSpec = tween(
            durationMillis = 1000
        )
    )
    val arrowAlign2 by animateIntAsState(
        targetValue = if (align) 180 else 0,
        animationSpec = tween(
            durationMillis = 1000
        )
    )

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        Box(modifier = modifier.weight(1f)) {}
        Row(modifier = modifier
            .fillMaxWidth()
            .weight(1f), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {

            Image(
                painter = painterResource(id = R.drawable.archer),
                contentDescription = "archer",
                modifier = modifier.size(120.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.people),
                contentDescription = "people",
                modifier = modifier.size(120.dp)
            )
        }
        Box(modifier = modifier
            .fillMaxWidth()
            .weight(1f),
            contentAlignment = Alignment.Center) {
            Button(onClick = {
                align = !align
            }) {
                Text(text = "Play")
            }
        }


    }
}
@Composable
fun arrow(modifier: Modifier = Modifier){
    Box(modifier = modifier
        .weight(3f)
        .height(arrowAlign2.dp)) {
        Box(
            modifier = modifier.width(arrowAlign.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            if (align){
                Image(
                    painter = painterResource(id = R.drawable.arr1),
                    contentDescription = "arr1",
                    modifier = modifier
                        .size(50.dp)
                        .padding(bottom = 35.dp)
                )
            }

        }
    }
}
