package com.thk.data.datasource

import com.thk.data.database.ChallengeDao
import com.thk.data.database.StampDao
import com.thk.data.model.Challenge
import com.thk.data.model.Stamp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface LocalDataSource {
    fun getChallenges(): Flow<List<Challenge>>
    suspend fun addChallenge(challenge: Challenge): Long
    suspend fun removeChallenge(challengeId: Long)
    fun getStamps(challengeId: Long): Flow<List<Stamp>>
    suspend fun addStamps(vararg stamp: Stamp): List<Long>
    suspend fun updateStampChecked(stamp: Stamp)
}

class LocalDataSourceImpl @Inject constructor(
    private val challengeDao: ChallengeDao,
    private val stampDao: StampDao
) : LocalDataSource {
    override fun getChallenges() = challengeDao.getChallenges()

    override suspend fun addChallenge(challenge: Challenge) = challengeDao.addChallenge(challenge)

    override suspend fun removeChallenge(challengeId: Long) = challengeDao.removeChallenge(challengeId)

    override fun getStamps(challengeId: Long) = stampDao.getStamps(challengeId)

    override suspend fun addStamps(vararg stamp: Stamp) = stampDao.addStamps(*stamp)

    override suspend fun updateStampChecked(stamp: Stamp) = stampDao.updateStampChecked(stamp)
}