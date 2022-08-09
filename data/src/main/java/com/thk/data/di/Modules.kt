package com.thk.data.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.thk.data.database.ChallengeDao
import com.thk.data.database.DatabaseInfo
import com.thk.data.database.SevenDaysDatabase
import com.thk.data.database.StampDao
import com.thk.data.datasource.LocalDataSource
import com.thk.data.datasource.LocalDataSourceImpl
import com.thk.data.repository.ChallengeRepository
import com.thk.data.repository.ChallengeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideChallengeRepository(dataSource: LocalDataSource): ChallengeRepository =
        ChallengeRepositoryImpl(dataSource)
}


@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Singleton
    @Provides
    fun provideLocalDataSource(
        challengeDao: ChallengeDao,
        stampDao: StampDao
    ): LocalDataSource = LocalDataSourceImpl(challengeDao, stampDao)
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideChallengeDao(database: SevenDaysDatabase) = database.challengeDao()

    @Singleton
    @Provides
    fun provideStampDao(database: SevenDaysDatabase) = database.stampDao()

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            SevenDaysDatabase::class.java,
            DatabaseInfo.DB_NAME
        ).build()
}


