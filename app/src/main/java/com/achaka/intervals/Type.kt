package com.achaka.intervals

enum class Type(private val type: Int) {
    REGULAR(0), INTERVAL(1), ROUND(2), LADDER(3), TIMED(4)
}

fun String.getTypeFromString(): Type {
    return when (this.uppercase()) {
        Type.REGULAR.name -> Type.REGULAR
        Type.INTERVAL.name -> Type.INTERVAL
        Type.ROUND.name -> Type.ROUND
        Type.LADDER.name -> Type.LADDER
        Type.TIMED.name -> Type.TIMED
        else -> {
            Type.INTERVAL
        }
    }
}