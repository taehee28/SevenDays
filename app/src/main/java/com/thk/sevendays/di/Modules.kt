package com.thk.sevendays.di

import android.content.Context
import com.thk.data.database.SevenDaysDatabase
import com.thk.data.datasource.DataStoreSource
import com.thk.data.datasource.DataStoreSourceImpl
import com.thk.data.datasource.LocalDataSource
import com.thk.data.datasource.LocalDataSourceImpl
import com.thk.data.repository.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindChallengeRepository(challengeRepositoryImpl: ChallengeRepositoryImpl): ChallengeRepository

    @Binds
    abstract fun bindStampRepository(stampRepositoryImpl: StampRepositoryImpl): StampRepository

    @Binds
    abstract fun bindSettingsRepository(settingsRepositoryImpl: SettingsRepositoryImpl): SettingsRepository
}


@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource

    @Binds
    abstract fun bindDataStoreSource(dataStoreSourceImpl: DataStoreSourceImpl): DataStoreSource
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
        SevenDaysDatabase.getInstance(appContext)
}