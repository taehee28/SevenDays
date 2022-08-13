package com.thk.data.repository

import com.thk.data.datasource.LocalDataSource
import com.thk.data.model.Stamp
import javax.inject.Inject

interface StampRepository {
    suspend fun getStamps(challengeId: Long): List<Stamp>
    suspend fun setStampChecked(stamp: Stamp)
}

class StampRepositoryImpl @Inject constructor(
    private val dataSource: LocalDataSource
) : StampRepository {
    override suspend fun getStamps(challengeId: Long) = dataSource.getStamps(challengeId)

    override suspend fun setStampChecked(stamp: Stamp) = dataSource.updateStampChecked(stamp)
}
