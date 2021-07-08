package com.achaka.intervals

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
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "intervalId") val id: Long,
    @ColumnInfo(name = "number") val number: Int,
    @ColumnInfo(name = "seconds") val seconds: Int,
    @ColumnInfo(name = "isCompleted") val isCompleted: Boolean
    )
