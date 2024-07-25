package com.example.weatherapp.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.weatherapp.R
import com.example.weatherapp.model.Weather
import com.example.weatherapp.model.WeatherItem
import com.example.weatherapp.utils.farenheit
import com.example.weatherapp.utils.formateDate
import com.example.weatherapp.utils.formateTime

@Composable
fun HumidityWindPressureRow(weather: WeatherItem, isimperial: Boolean) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidityIcon",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${weather.humidity}%", style = MaterialTheme.typography.labelSmall)
        }
        Row {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "humidityIcon",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${weather.pressure} psi", style = MaterialTheme.typography.labelSmall)
        }
        Row {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "humidityIcon",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${weather.speed} "+if(isimperial) "mph" else "m/s", style = MaterialTheme.typography.labelSmall)
        }
    }

}

@Composable
fun TimesOfDay(weather: WeatherItem) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(5.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "sunriseIcon",
                modifier = Modifier.size(20.dp)
            )
            Text(text = (formateTime(weather.sunrise)))
        }
        Row {
            Icon(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "sunriseIcon",
                modifier = Modifier.size(20.dp)
            )
            Text(text = (formateTime(weather.sunset)))
        }
    }

}

@Composable
fun ThisWeek() {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "This Week", style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold, fontSize = 20.sp
        )
    }

}

@Composable
fun WeekData(data: Weather) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        //color = Color(0xFFEEF1EF),
        shape = RoundedCornerShape(14.dp)
    ) {
        LazyColumn(
            modifier = Modifier.padding(3.dp),
            contentPadding = PaddingValues(1.dp)
        ) {
            items(items = data.list) { item: WeatherItem ->
                WeatherDetailRow(weather = item)
            }
        }
    }

}

@Composable
fun WeatherDetailRow(weather: WeatherItem) {
    val imageUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}@2x.png"
    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                formateDate(weather.dt).split(",")[0],
                modifier = Modifier.padding(5.dp)
            )
            WeatherStateImage(imageUrl = imageUrl)
            Surface(
                modifier = Modifier.padding(1.dp),
                shape = CircleShape, color = Color(0xFFFFC400)
            ) {
                Text(weather.weather[0].description,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.labelSmall)
            }
            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(
                    color = Color.Blue.copy(alpha = 0.7f),
                    fontWeight = FontWeight.SemiBold
                )
                ){
                    append(farenheit(weather.temp.max.toString()) + "°")
                }
                withStyle(style = SpanStyle(
                    color = Color.LightGray
                )
                ){
                    append("/")
                    append(farenheit(weather.temp.min.toString()) + "°")
                }
            })
        }
    }
}


@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(
        painter = rememberImagePainter(imageUrl), contentDescription = "iconImage",
        modifier = Modifier.size(80.dp)
    )

}