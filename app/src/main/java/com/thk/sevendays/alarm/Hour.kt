package com.thk.sevendays.alarm

@JvmInline
value class Hour(
    val hour: Int
) {
    companion object {
        fun of(hour: Int) = Hour(hour)
    }
}