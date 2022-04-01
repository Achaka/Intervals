package com.achaka.intervals.timer

import android.app.Service
import android.content.Intent
import android.os.IBinder

class TimerService(private val intervals: Array<Int>): Service() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}