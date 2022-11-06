package com.nirwashh.android.mycomposition.domain.usecases

import com.nirwashh.android.mycomposition.domain.entity.Question
import com.nirwashh.android.mycomposition.domain.repository.GameRepository

class GenerateQuestionUseCase(private val repository: GameRepository) {

    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestions(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object {
        private const val COUNT_OF_OPTIONS = 6
    }
}