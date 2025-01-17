package com.example.weatherapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity(tableName = "Seting_tbl")
data class Unit(
    @Nonnull
    @PrimaryKey
    @ColumnInfo(name = "Unit")
    val unit:String
)
