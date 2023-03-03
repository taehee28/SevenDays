package com.thk.sevendays.alarm

import androidx.annotation.IntRange

@JvmInline
value class Hour private constructor(val hour: Int) {
    companion object {
        fun valueOf(
            @IntRange(from = 1, to = 12)
            hour: Int
        ) = Hour(hour)
    }
}