package com.nirwashh.android.mycomposition.domain.usecases

import com.nirwashh.android.mycomposition.domain.entity.GameSettings
import com.nirwashh.android.mycomposition.domain.entity.Level
import com.nirwashh.android.mycomposition.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {

    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}