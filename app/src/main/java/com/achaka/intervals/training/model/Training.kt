package com.achaka.intervals.training.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.achaka.intervals.Type

@Entity(tableName = "trainings")
data class Training (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "training_id") val id: Long,
    @ColumnInfo(name = "training_name") val trainingName: String,
    @ColumnInfo(name = "type") val type: Type
    )