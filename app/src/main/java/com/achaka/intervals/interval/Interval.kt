package com.achaka.intervals.interval

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


//Kotlin data class objects have some extra benefits,
// the compiler automatically generates utilities for comparing, printing and copying
// such as toString(), copy(), and equals().
// The primary constructor needs to have at least one parameter.
//All primary constructor parameters need to be marked as val or var.
//Data classes cannot be abstract, open, sealed or inner.

@Entity (tableName = "intervals")
data class Interval (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "interval_id") val id: Long,
    @ColumnInfo(name = "number") val number: Int,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "seconds") var seconds: Int,
    @ColumnInfo(name = "is_completed") val isCompleted: Boolean,
    @ColumnInfo(name = "training_id") val trainingId: Long,
    @ColumnInfo(name = "suggested_pace") val suggestedPace: String
    )
