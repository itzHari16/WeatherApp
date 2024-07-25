package com.example.weatherapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.weatherapp.model.City
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.model.Unit
import kotlinx.coroutines.flow.Flow


@Dao
interface  WeatherDao {
    @Query("SELECT * FROM fav_table")
    fun getFavorites(): Flow<List<Favorite>>

    @Query("SELECT*FROM fav_table WHERE city = :city")
    suspend fun getFavoriteById(city: String): Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Update(onConflict =OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Query("DELETE FROM fav_table")
    suspend fun deleteAllFavorites()

    @Delete
    suspend fun deleteOneFavorite(favorite: Favorite)

    //unit table
    @Query("SELECT * FROM Seting_tbl")
    fun getUnits(): Flow<List<Unit>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit: Unit)

    @Update(onConflict =OnConflictStrategy.REPLACE)
    suspend fun updateUnit(unit: Unit)

    @Query("DELETE FROM fav_table")
    suspend fun deleteAllUnits()

    @Delete
    suspend fun deleteOneUnit(unit: Unit)
}
