package com.achaka.intervals.interval.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.achaka.intervals.training.model.Training

@Entity (tableName = "intervals",
    foreignKeys = [ForeignKey(
    entity = Training::class,
    parentColumns = arrayOf("training_id"),
    childColumns = arrayOf("training_id"),
    onDelete = ForeignKey.CASCADE)]
        )
data class Interval (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "interval_id") val id: Long,
    @ColumnInfo(name = "number") var number: Int,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "seconds") var seconds: Int,
    @ColumnInfo(name = "is_completed") val isCompleted: Boolean,
    @ColumnInfo(name = "training_id") var trainingId: Long,
    @ColumnInfo(name = "suggested_pace") var suggestedPace: String,
    @ColumnInfo(name = "type") var type: Int,
    @ColumnInfo(name = "weight") var weight: Float,
    var progress: Int
    )

