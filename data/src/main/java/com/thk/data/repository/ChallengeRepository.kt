package com.thk.data.repository

import com.thk.data.datasource.LocalDataSource
import com.thk.data.model.Challenge
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ChallengeRepository {
    fun getChallenges(): Flow<List<Challenge>>
    suspend fun addChallenge(content: String)
    suspend fun removeChallenge(challengeId: Long)
}

class ChallengeRepositoryImpl @Inject constructor(
    dataSource: LocalDataSource
) : ChallengeRepository {
    override fun getChallenges(): Flow<List<Challenge>> {
        TODO("Not yet implemented")
    }

    override suspend fun addChallenge(content: String) {
        TODO("Not yet implemented")
    }

    override suspend fun removeChallenge(challengeId: Long) {
        TODO("Not yet implemented")
    }
}