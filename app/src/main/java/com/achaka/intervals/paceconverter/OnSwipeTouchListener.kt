package com.achaka.intervals.paceconverter

import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

class OnSwipeTouchListener: View.OnTouchListener {

    private val gestureDetector: GestureDetector? = null

    private val LONG_PRESS_THRESHOLD_MS = 800L
    private val SWIPE_THRESHOLD = 100
    private val SWIPE_VELOCITY_THRESHOLD = 100

    override fun onTouch(p0: View?, ev: MotionEvent?): Boolean {
        return gestureDetector?.onTouchEvent(ev) ?: false
    }

    inner class OnGestureListener : GestureDetector.SimpleOnGestureListener() {
        // onlongpress - choose item
        override fun onLongPress(e: MotionEvent?) {
            val pressTime = e?.eventTime
            if (pressTime != null) {
                if (pressTime >= LONG_PRESS_THRESHOLD_MS)
                {
                    Log.d("LONGPRESS", "Detected")
                }
            }
            super.onLongPress(e)
        }

        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            var diffX: Float = 0F
            if (e1 != null && e2 != null) {
                diffX = e1.x.minus(e2.x)
            }

            if (diffX > SWIPE_THRESHOLD) {
                Log.d("FLING", "detected")
            }
            return super.onFling(e1, e2, velocityX, velocityY)
        }

        override fun onDoubleTap(e: MotionEvent?): Boolean {
            Log.d("DOUBLETAP", "detected")
            return super.onDoubleTap(e)
        }
    }
}