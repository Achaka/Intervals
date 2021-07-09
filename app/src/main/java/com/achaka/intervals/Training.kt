package com.achaka.intervals

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "trainings")
data class Training (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "training_id") val id: Long,
    @ColumnInfo(name = "training_name") val trainingName: String
    )