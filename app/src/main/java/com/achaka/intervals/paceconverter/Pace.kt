package com.achaka.intervals.paceconverter

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pace_history")
data class Pace(
        @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "secKm")val secKm: Int,
        @ColumnInfo(name = "secMile")val secMile: Int,
        @ColumnInfo(name = "kph")val kph: Float,
        @ColumnInfo(name = "mph")val mph: Float
)
