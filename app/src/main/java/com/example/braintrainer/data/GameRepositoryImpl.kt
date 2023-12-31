package com.example.braintrainer.data

import com.example.braintrainer.domain.entities.GameLevel
import com.example.braintrainer.domain.entities.GameSettings
import com.example.braintrainer.domain.entities.Question
import com.example.braintrainer.domain.repository.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {
    private const val MIN_SUM_VALUE = 2
    private const val MIN_VISIBLE_NUMBER_VALUE = 1

    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_VISIBLE_NUMBER_VALUE, sum)
        val options = hashSetOf<Int>()
        val rightAnswer = sum - visibleNumber
        options.add(rightAnswer)
        val from = max(MIN_VISIBLE_NUMBER_VALUE, rightAnswer - countOfOptions)
        val to = min(rightAnswer + countOfOptions, maxSumValue)
        while (options.size < countOfOptions) {
            options.add(Random.nextInt(from, to))
        }
        return Question(sum, visibleNumber, options.toList())
    }

    override fun getGameSettings(gameLevel: GameLevel): GameSettings {
        return when (gameLevel) {
            GameLevel.TEST -> GameSettings(
                10,
                3,
                50,
                8
            )

            GameLevel.EASY -> GameSettings(
                10,
                10,
                70,
                60
            )

            GameLevel.NORMAL -> GameSettings(
                20,
                20,
                80,
                40
            )

            GameLevel.HARD -> GameSettings(
                30,
                30,
                90,
                40
            )

            GameLevel.IMPOSSIBLE -> GameSettings(
                100,
                30,
                90,
                30
            )
        }
    }
}