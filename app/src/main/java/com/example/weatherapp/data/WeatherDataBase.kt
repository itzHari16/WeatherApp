package com.example.weatherapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.model.Unit
import javax.sql.RowSetMetaData


@Database(entities = [Favorite::class,Unit::class], version = 2, exportSchema = false)
abstract class  WeatherDataBase:RoomDatabase() {
    abstract fun weatherDao(): WeatherDao


}