package com.achaka.intervals.paceconverter

import android.util.Log
import java.lang.NumberFormatException
import kotlin.math.roundToInt

class PaceConverter {

    companion object {

        //KmPace
        fun convertKmPaceToMilePace(secondsOnKm: Int): Int {
            return (secondsOnKm * 1.60934F).roundToInt()
        }

        //MilePace
        fun convertMilePaceToKmPace(secondsOnMile: Int): Int {
            return (secondsOnMile / 1.60934F).roundToInt()
        }

        //Kph
        fun kphToMph(kph: Float): Float {
            return kph * 0.62137F
        }

        fun mphToKph(mph: Float): Float {
            return mph * 1.60934F
        }

        //speed-pace
        fun convertSpeedToPace(speed: Float): Int {
            Log.d("convertSpeedToPace", ((3600F/speed).roundToInt().toString()))
            return if (speed != 0F)
                (3600F/speed).roundToInt()
            else 0
        }

        fun convertPaceToSpeed(seconds: Int): Float {
            return  if (seconds!= 0)
                1/(seconds/3600F)
            else 0F
        }

        //output strings
        fun minutesStringToSeconds(timeString: String): Int {
            val delimiter = ":"
            return if (timeString.isNotEmpty() && (timeString.contains(":"))) {
                val beforeDelim = timeString.substringBefore(delimiter)
                val afterDelim = timeString.substringAfter(delimiter)
                var minutes = 0
                var seconds = 0
                if (beforeDelim.isNotBlank()) {
                    minutes = Integer.parseInt(beforeDelim)
                }
                if (afterDelim.isNotBlank()) {
                    seconds = Integer.parseInt(afterDelim)
                }
                minutes * 60 + seconds
            } else 0
        }

        fun secondsToMinutesString(seconds: Int): String {
            val minutes = seconds / 60
            val remainingSeconds = seconds % 60
            return "$minutes:$remainingSeconds"
        }

        fun speedToString(speed: Float): String {
            return "%.1f".format(speed)
        }

        fun mphToString(speed: Float): String {
            return "%.2f".format(speed)
        }

        fun calculateInKmPace(kmPace: String): List<String> {
            val seconds = minutesStringToSeconds(kmPace)

            val milePace = convertKmPaceToMilePace(seconds)
            val kph = convertPaceToSpeed(seconds)
            val mph = convertPaceToSpeed(milePace)

            return listOf(
                secondsToMinutesString(milePace),
                speedToString(kph),
                mphToString(mph)
            )
        }
        fun calculateInMilePace(milePace: String): List<String> {
            val seconds = minutesStringToSeconds(milePace)

            val kmPace = convertMilePaceToKmPace(seconds)
            val kph = convertPaceToSpeed(kmPace)
            val mph = convertPaceToSpeed(seconds)

            return listOf(
                secondsToMinutesString(kmPace),
                speedToString(kph),
                mphToString(mph)
            )
        }
        fun calculateInKph(kph: String): List<String> {
            var speed: Float
            try {
                speed = kph.toFloat()
            } catch (ex: NumberFormatException) {
                speed = 0F
            }

            val kmPace = convertSpeedToPace(speed)
            val milePace = convertKmPaceToMilePace(kmPace)
            val mph = kphToMph(speed)

            return listOf(
                secondsToMinutesString(kmPace),
                secondsToMinutesString(milePace),
                mphToString(mph)
            )
        }
        fun calculateInMph(mph: String): List<String> {
            var speed: Float
            try {
                speed = mph.toFloat()
                Log.d("speed", speed.toString())
            } catch (ex: NumberFormatException) {
                speed = 0F
            }

            val milePace = convertSpeedToPace(speed)
            val kmPace = convertMilePaceToKmPace(milePace)
            val kph = mphToKph(speed)

            return listOf(
                secondsToMinutesString(kmPace),
                secondsToMinutesString(milePace),
                speedToString(kph)
            )
        }
    }
}