package com.example.weatherapp.repository

import com.example.weatherapp.data.WeatherDao
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.model.Unit
import com.example.weatherapp.model.Weather
import kotlinx.coroutines.flow.Flow
import okhttp3.WebSocket
import javax.inject.Inject


class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao){
    fun getfavorites():Flow<List<Favorite>> = weatherDao.getFavorites()
    suspend fun insertfavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)
    suspend fun updateFavorite(favorite: Favorite)= weatherDao.updateFavorite(favorite)
    suspend fun deleteFavorite(favorite: Favorite)= weatherDao.deleteOneFavorite(favorite)
    suspend fun deleteAllFavorites()= weatherDao.deleteAllFavorites()
    suspend fun getFavbyId(city:String) :Favorite=weatherDao.getFavoriteById(city)


    fun getUnits():Flow<List<Unit>> = weatherDao.getUnits()
    suspend fun insertUnit(unit: Unit) = weatherDao.insertUnit(unit)
    suspend fun updateUnit(unit: Unit)= weatherDao.updateUnit(unit)
    suspend fun deleteUnit(unit: Unit)= weatherDao.deleteOneUnit(unit)
    suspend fun deleteAllUnits()= weatherDao.deleteAllUnits()



}