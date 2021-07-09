package com.achaka.intervals

import android.app.Application

class IntervalsApp : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}