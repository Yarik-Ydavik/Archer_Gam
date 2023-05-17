package com.example.archer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.archer.ui.theme.ArcherTheme

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
data class Arch(
    val name : String,
    val hitChance : Int,
    @DrawableRes val image : Int = 0
)
val ArchersList = listOf<Arch>(
    Arch(name = "Робин Гуд", hitChance = 50),
    Arch(name = "Арагон", hitChance = 30),
    Arch(name = "Галион", hitChance = 60),
)
val ArrowList = listOf<Arch>(
    Arch(name = "Обычная", hitChance = -10, image = R.drawable.arr1),
    Arch(name = "Скоростная", hitChance = 30, image = R.drawable.arr2),
    Arch(name = "Среднеевропейская", hitChance = -12, image = R.drawable.arr3),
    Arch(name = "Острая", hitChance = 18, image = R.drawable.arr4),
    Arch(name = "Индейская", hitChance = -20, image = R.drawable.arr5),

)
val WeatherList = listOf<Arch>(
    Arch(name = "Обычные условия", hitChance = 0),
    Arch(name = "Пошёл дождь", hitChance = -20, image = R.drawable.rain),
    Arch(name = "Пошёл снег", hitChance = -10, image = R.drawable.winter),


)

@Preview
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Game(modifier: Modifier = Modifier){
    var align by remember { mutableStateOf(false) }
    var show by remember { mutableStateOf(false) }
    var nameArcher by remember { mutableStateOf("") }
    var hitChance by remember { mutableStateOf(0) }
    var arrowChance by remember { mutableStateOf(0) }
    var arrowImage by remember { mutableStateOf(R.drawable.arr1) }
    var arrowName by remember { mutableStateOf("") }


    var weather by remember { mutableStateOf(false) }
    var weathChance by remember { mutableStateOf(0) }
    var weathImage by remember { mutableStateOf(R.drawable.fone) }
    var weathName by remember { mutableStateOf("") }

    var randomNumber by remember { mutableStateOf(0) }

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
    Box(modifier = modifier.fillMaxSize()) {
        if (weather){
            Image(
                painter = painterResource(id = weathImage),
                contentDescription = "weather",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(
            modifier = modifier.fillMaxSize(),
        ) {
            Box(modifier = modifier
                .weight(1f)
                .fillMaxWidth(), contentAlignment = Alignment.Center) {
                if (align && randomNumber >= hitChance+arrowChance){
                    Column() {
                        if (weather){
                            Text(text = "$weathName, шанс попадания изменился на $weathChance%", modifier = modifier.background(color = Color.White) )
                        }
                        Text(text = "Выпавшая стрела: $arrowName с шансом $arrowChance%", modifier = modifier.background(color = Color.White))
                        Text(text = "$nameArcher попал с вероятностью: ${hitChance+arrowChance+weathChance}%", modifier = modifier.background(color = Color.White))
                    }
                }else if (align && randomNumber <= hitChance+arrowChance){
                    Column() {
                        if (weather){
                            Text(text = "$weathName, шанс попадания изменился на $weathChance% ", modifier = modifier.background(color = Color.White) )
                        }
                        Text(text = "Выпавшая стрела: $arrowName с шансом $arrowChance%", modifier = modifier.background(color = Color.White))
                        Text(text = "$nameArcher промахнулся с вероятностью: ${hitChance+arrowChance}%", modifier = modifier.background(color = Color.White))

                    }
                }

            }
            Row(modifier = modifier
                .fillMaxWidth()
                .weight(1f), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Column() {
                    Text(text = "Шанс попадания: ${hitChance+arrowChance}%", modifier = modifier.background(color = Color.White))
                    Spacer(modifier = modifier.height(10.dp))
                    Image(
                        painter = painterResource(id = R.drawable.archer),
                        contentDescription = "archer",
                        modifier = modifier
                            .size(120.dp)
                            .clickable {
                                show = !show
                            }
                    )
                    Spacer(modifier = modifier.height(10.dp))
                    Text(text = "Лучник: "+ nameArcher, modifier = modifier.background(color = Color.White))
                    DropdownMenu(expanded = show, onDismissRequest = { show = false }) {
                        ArchersList.forEach{it ->
                            Text(text = it.name, modifier = modifier.clickable {
                                nameArcher = it.name
                                hitChance = it.hitChance
                                show = false
                            })
                        }
                    }
                }
                if (randomNumber <= hitChance+arrowChance){
                    arrow( widthArr = arrowAlign, heightArr = arrowAlign2, boolean = align , arrowImage = arrowImage)
                }else if (randomNumber >= hitChance+arrowChance){
                    arrow( widthArr = arrowAlign, heightArr = 50, boolean = align , arrowImage = arrowImage)
                }
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

                    if (!align){
                        val arrow = ArrowList.random()
                        randomNumber = (0..100).random()
                        arrowImage = arrow.image
                        arrowChance = arrow.hitChance
                        arrowName = arrow.name

                        val weath = WeatherList.random()
                        if (weath.name != WeatherList[0].name){
                            weather = true
                        }
                        else if (weath.name == WeatherList[0].name){
                            weather = false
                        }
                        weathChance = weath.hitChance
                        weathImage = weath.image
                        weathName = weath.name

                    }else{
                        arrowChance = 0
                        hitChance = 50
                    }

                    align = !align
                }) {
                    Text(text = "Play")
                }
            }


        }


    }


}
@Composable
fun arrow(
    modifier: Modifier = Modifier,
    widthArr: Int,
    heightArr: Int,
    boolean: Boolean,
    arrowImage: Int,
){

    Box(modifier = modifier
        .height(heightArr.dp)) {
        Box(
            modifier = modifier.width(widthArr.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            if (boolean){
                Image(
                    painter = painterResource(id = arrowImage),
                    contentDescription = "arr1",
                    modifier = modifier
                        .size(50.dp)
                        .padding(bottom = 35.dp)
                )
            }

        }
    }
}
