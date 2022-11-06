package com.nirwashh.android.mycomposition.domain.repository

import com.nirwashh.android.mycomposition.domain.entity.GameSettings
import com.nirwashh.android.mycomposition.domain.entity.Level
import com.nirwashh.android.mycomposition.domain.entity.Question

interface GameRepository {

    fun generateQuestions(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question

    fun getGameSettings(level: Level): GameSettings
}