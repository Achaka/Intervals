package com.achaka.intervals

import androidx.room.Embedded
import androidx.room.Relation
import com.achaka.intervals.interval.Interval
import com.achaka.intervals.training.Training

data class TrainingWithIntervals (
    @Embedded val training: Training,
    @Relation(
                parentColumn = "training_id",
                entityColumn = "interval_id"
            )
            val intervals: List<Interval>
        )
