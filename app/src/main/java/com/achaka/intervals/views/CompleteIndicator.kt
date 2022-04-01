package com.achaka.intervals.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.achaka.intervals.R

class CompleteIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr) {

    private var _state = CompleteIndicatorState.INACTIVE

    private val inactiveColor = resources.getColor(R.color.white)
    private val completedColor = resources.getColor(R.color.completed_green)
    private val notCompletedColor = resources.getColor(R.color.not_completed_red)

    var fillColor = inactiveColor

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var size = MeasureSpec.getSize(widthMeasureSpec)
        val desiredWidth = 56
        val desiredHeight = 56
        setMeasuredDimension(desiredWidth, desiredHeight)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                fillColor = resources.getColor(R.color.black)
                Log.d("action", "${event.action}")
            }
            MotionEvent.ACTION_UP -> {
                setNextState()
                setPaintFillColor()
                requestLayout()
                invalidate()
                performClick()
            }
        }
        return true
    }

    val paintStroke = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = 2f
        style = Paint.Style.STROKE
        color = resources.getColor(R.color.black)
    }

    val paintFill = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = fillColor
    }


    override fun onDraw(canvas: Canvas?) {
        canvas?.drawCircle(width/2f,height/2f,24f, paintStroke)
        canvas?.drawCircle(width/2f,height/2f,24f, paintFill)
        super.onDraw(canvas)
    }

    private fun setNextState() {
        _state = when (_state) {
            CompleteIndicatorState.INACTIVE -> {
                CompleteIndicatorState.COMPLETED
            }
            CompleteIndicatorState.COMPLETED -> {
                CompleteIndicatorState.NOT_COMPLETED
            }
            CompleteIndicatorState.NOT_COMPLETED -> {
                CompleteIndicatorState.COMPLETED
            }
        }
    }

    private fun setPaintFillColor() {
        paintFill.color = when(_state) {
            CompleteIndicatorState.COMPLETED -> completedColor
            CompleteIndicatorState.NOT_COMPLETED -> notCompletedColor
            CompleteIndicatorState.INACTIVE -> inactiveColor
        }
    }
}