package com.example.weatherapp.screens.main

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.example.weatherapp.data.DataOrException
import com.example.weatherapp.model.Weather
import com.example.weatherapp.navigation.WeatherScreens
import com.example.weatherapp.screens.setting.SettingViewmodel
import com.example.weatherapp.utils.farenheit

import com.example.weatherapp.utils.formateDate
import com.example.weatherapp.widgets.HumidityWindPressureRow
import com.example.weatherapp.widgets.ThisWeek
import com.example.weatherapp.widgets.TimesOfDay
import com.example.weatherapp.widgets.WeatherAppBar
import com.example.weatherapp.widgets.WeatherStateImage
import com.example.weatherapp.widgets.WeekData


@OptIn(ExperimentalCoilApi::class)
@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingViewmodel: SettingViewmodel = hiltViewModel(),
    city: String?
) {
    val curCity:String=if(city!!.isBlank()) "Seattle" else city
    val unitFromDb = settingViewmodel.unitlist.collectAsState().value
    var unit by remember {
        mutableStateOf("imperial")
    }
    var isimperial by remember {
        mutableStateOf(false)
    }
    if (!unitFromDb.isNullOrEmpty()) {
        unit = unitFromDb[0].unit.split(" ")[0].lowercase()
        isimperial = unit == "imperial"
        val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)
        ) {
            value = mainViewModel.getWeatherData(city = city.toString(),units=unit)
        }.value
        if (weatherData.loading == true) {
            CircularProgressIndicator()
        } else if (weatherData.data != null) {
            MainScaffold(weather = weatherData.data!!, navController,isimperial= isimperial)
        }

    }

    Log.d("TAG", "MainScreen: $city")

}

@ExperimentalCoilApi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(weather: Weather, navController: NavController, isimperial: Boolean) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Column {


        Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {


                WeatherAppBar(
                    title = weather.city.name + ",${weather.city.country}",
                    // icon = Icons.Default.ArrowBack,
                    navController = navController,
                    elevation = 10.dp,
                    onAddActionClicked = {
                        navController.navigate(WeatherScreens.SearchScreen.name)
                    }
                )

            }) { innerPadding ->
            MainContent(data = weather, modifier = innerPadding,isimperial=isimperial)
        }

    }
}

//}

@Composable
fun MainContent(data: Weather, modifier: PaddingValues, isimperial: Boolean) {
    //Text(text = data.city.name)
    val weatherItem = data.list[0]
    val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}@2x.png"

    Column(
        modifier = Modifier
            .padding(top = 100.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = formateDate(weatherItem.dt),
            style = MaterialTheme.typography.bodyLarge,
            // color = Color.Blue,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(8.dp)
        )
        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp), shape = CircleShape,
            color = Color(0xFFFFC400)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(imageUrl = imageUrl)
                Text(
                    text = farenheit(weatherItem.temp.day.toString()) + "°",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = weatherItem.weather[0].main,
                    fontStyle = FontStyle.Italic
                )
            }

        }
        HumidityWindPressureRow(weather = weatherItem,isimperial=isimperial)
        Divider()
        TimesOfDay(weather = weatherItem)
        ThisWeek()
        WeekData(data = data)

    }
}


