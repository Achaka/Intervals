package com.achaka.intervals.interval.model

import javax.inject.Inject

class IntervalsRepositoryImpl @Inject constructor(val intervalsDao: IntervalDao): IntervalsRepository {
}