package com.thk.sevendays.utils

public val <T> List<T>.firstIndex: Int
    get() = if (this.isNotEmpty()) 0 else -1