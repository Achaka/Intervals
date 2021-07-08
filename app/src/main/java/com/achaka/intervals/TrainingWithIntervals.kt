package com.achaka.intervals

import androidx.room.Embedded
import androidx.room.Relation

data class TrainingWithIntervals (
    @Embedded val training: Training,
            @Relation(
                parentColumn = "trainingId",
                entityColumn = "intervalId"
            )
            val intervals: List<Interval>
        )
