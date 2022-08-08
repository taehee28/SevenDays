package com.thk.data.database

import androidx.room.TypeConverter
import java.time.LocalDate

class TypeConverters {
    @TypeConverter
    fun fromEpochDay(epochDay: Long?): LocalDate? {
        return epochDay?.let { LocalDate.ofEpochDay(it) }
    }

    @TypeConverter
    fun localDateToEpochDay(localDate: LocalDate?): Long? {
        return localDate?.toEpochDay()
    }
}