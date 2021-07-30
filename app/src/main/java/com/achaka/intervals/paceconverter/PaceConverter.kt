package com.achaka.intervals.paceconverter

class PaceConverter {

    companion object {

        fun convertMilePaceToKmPace(secondsOnMile: Int): Float {
            return secondsOnMile / 1.60934F
        }

        fun convertKmPaceToMilePace(secondsOnKm: Int): Float {
            return secondsOnKm * 0.62137F
        }

        fun minutesStringToSeconds(timeString: String): Int {
            val minutes = Integer.parseInt(timeString.substringBefore(":"))
            val seconds = Integer.parseInt(timeString.substringAfter(":"))
            return minutes * 60 + seconds
        }

        fun secondsToMinutesString(seconds: Int): String {
            val minutes = seconds / 60
            val remainingSeconds = seconds % 60
            return "$minutes:$remainingSeconds"
        }

    }
}