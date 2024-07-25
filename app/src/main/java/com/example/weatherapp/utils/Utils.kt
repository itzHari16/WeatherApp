package com.example.weatherapp.utils

import okhttp3.internal.format
import java.text.SimpleDateFormat

fun formateDate(timestamp: Int): String {
    val sdf=SimpleDateFormat("EEE ,MMM d")
    val date=java.util.Date(timestamp.toLong()*1000)
return sdf.format(date)}

fun formateTime(timestamp: Int): String {
    val sdf=SimpleDateFormat("hh:mm a")
    val date=java.util.Date(timestamp.toLong()*1000)
return sdf.format(date)}

fun formatDecimals(item:String):String{
    return "%.0f".format(item)}

fun farenheit(item:String):String{
    val celsius=(item.toDouble()-32)*5/9
    return "%.1f".format(celsius)
}
