package com.achaka.intervals

import androidx.room.Embedded
import androidx.room.Relation

data class TrainingWithIntervals (
    @Embedded val training: Training,
            @Relation(
                parentColumn = "training_id",
                entityColumn = "interval_id"
            )
            val intervals: List<Interval>
        )
