package com.thk.data.repository

import com.thk.data.datasource.LocalDataSource
import com.thk.data.logd
import com.thk.data.model.Challenge
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ChallengeRepository {
    fun getChallenges(): Flow<List<Challenge>>
    suspend fun addChallenge(content: String)
    suspend fun removeChallenge(challengeId: Long)
}

class ChallengeRepositoryImpl @Inject constructor(
    private val dataSource: LocalDataSource
) : ChallengeRepository {
    override fun getChallenges() = dataSource.getChallenges()

    override suspend fun addChallenge(content: String) {
        val challenge = Challenge(title = content)
        val challengeId = dataSource.addChallenge(challenge)
        logd("challengeId = $challengeId")

        // TODO: 스탬프 추가
    }

    override suspend fun removeChallenge(challengeId: Long) = dataSource.removeChallenge(challengeId)
}