package com.thk.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.thk.data.model.Challenge
import com.thk.data.model.Stamp

@Database(
    entities = [Challenge::class, Stamp::class],
    version = DatabaseInfo.DB_VERSION
)
@TypeConverters(com.thk.data.database.TypeConverters::class)
abstract class SevenDaysDatabase : RoomDatabase() {
    abstract fun challengeDao(): ChallengeDao
    abstract fun stampDao(): StampDao
}