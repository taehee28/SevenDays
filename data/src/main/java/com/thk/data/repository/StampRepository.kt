package com.thk.data.repository

import com.thk.data.datasource.LocalDataSource
import com.thk.data.model.Challenge
import com.thk.data.model.Stamp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface StampRepository {
    suspend fun getChallenge(challengeId: Long): Challenge
    fun getStamps(challengeId: Long): Flow<List<Stamp>>
    suspend fun setStampChecked(stamp: Stamp)
}

class StampRepositoryImpl @Inject constructor(
    private val dataSource: LocalDataSource
) : StampRepository {
    override suspend fun getChallenge(challengeId: Long): Challenge = dataSource.getChallenge(challengeId)

    override fun getStamps(challengeId: Long) = dataSource.getStamps(challengeId)

    override suspend fun setStampChecked(stamp: Stamp) = dataSource.updateStampChecked(stamp)
}
