package com.thk.data.datasource

import com.thk.data.database.ChallengeDao
import com.thk.data.database.StampDao
import com.thk.data.model.Challenge
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface LocalDataSource {
    fun getChallenges(): Flow<List<Challenge>>
    suspend fun addChallenge(challenge: Challenge)
    suspend fun removeChallenge(challengeId: Long)
}

class LocalDataSourceImpl @Inject constructor(
    challengeDao: ChallengeDao,
    stampDao: StampDao
) : LocalDataSource {
    override fun getChallenges(): Flow<List<Challenge>> {
        TODO("Not yet implemented")
    }

    override suspend fun addChallenge(challenge: Challenge) {
        TODO("Not yet implemented")
    }

    override suspend fun removeChallenge(challengeId: Long) {
        TODO("Not yet implemented")
    }
}