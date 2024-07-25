package com.example.weatherapp.screens.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weatherapp.model.Unit
import com.example.weatherapp.widgets.WeatherAppBar

@Composable
fun SettingScreen(
    navController: NavController,
    settingViewmodel: SettingViewmodel= hiltViewModel()
) {
    var unitToggleState by remember { mutableStateOf(false) }
    val measuremnetUnits = listOf("Metric (C)","Imperial (F)")
    val choiceFromDb = settingViewmodel.unitlist.collectAsState().value
    val choiceDef by remember {
        mutableStateOf(0)
    }
    val DefaultChoice = if (choiceFromDb.isEmpty()) measuremnetUnits[0] else choiceFromDb[0].unit
    var choiceState by remember { mutableStateOf(DefaultChoice) }

    Scaffold(topBar = {
        WeatherAppBar(
            title = "Settings", navController = navController,
            icon = Icons.Default.ArrowBack,
            ismainScreen = false
        ){
        navController.popBackStack()}
    }) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Change Units of Measurement",
                    modifier = Modifier.padding(bottom = 15.dp)
                )
                IconToggleButton(
                    checked = !unitToggleState,
                    onCheckedChange = {
                        unitToggleState = !it
                        if (unitToggleState) {
                            choiceState = "Imperial (F)"
                        } else {

                            choiceState = "Metric (C)"
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clip(shape = RectangleShape)
                        .padding(5.dp)
                        .background(
                            Color.Magenta.copy(alpha = 0.4f)
                        )
                ) {
                    Text(text = if (unitToggleState) "Fahrenheit F" else "Celsius C")
                }
                Button(
                    onClick = {
                        settingViewmodel.deleteAllUnits()
                        settingViewmodel.insertUnit(Unit(unit = choiceState))
                    },
                    modifier = Modifier
                        .padding(3.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xffefbe42)
                    )
                ) {
                    Text(
                        text = "SAVE", modifier = Modifier.padding(4.dp), color = Color.White,
                        fontWeight = FontWeight.SemiBold, fontSize = 20.sp
                    )
                }
            }

        }
    }

}
