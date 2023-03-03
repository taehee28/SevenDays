package com.thk.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    companion object {
        @Volatile
        private var instance: SevenDaysDatabase? = null

        fun getInstance(context: Context): SevenDaysDatabase {
            return instance ?: kotlin.run {
                synchronized(this) {
                    val tempInstance = Room.databaseBuilder(
                        context,
                        SevenDaysDatabase::class.java,
                        DatabaseInfo.DB_NAME
                    ).build()

                    instance = tempInstance
                    tempInstance
                }
            }
        }
    }
}