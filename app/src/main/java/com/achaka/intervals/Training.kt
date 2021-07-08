package com.achaka.intervals

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "trainings")
data class Training (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "trainingId") val id: Long,

    )