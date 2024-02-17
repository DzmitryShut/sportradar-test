package com.shut.sportradar.test.service

import com.shut.sportradar.test.model.Game
import com.shut.sportradar.test.model.Team
import com.shut.sportradar.test.repository.GameRepository
import java.util.UUID

/**
 * Class representing manipulating {@link com.shut.sportradar.test.model.Game} objects in application
 */
class ScoreboardService(private val gameRepository: GameRepository) {

    /**
     * Should create and return {@link com.shut.sportradar.test.model.Game} with specified {@link com.shut.sportradar.test.model.Team} names
     */
    fun createGame(homeTeam: String, awayTeam: String): Game =
        Game(homeTeam = Team("stub"), awayTeam = Team("stub"))


    /**
     * Should update scores of home and away {@link com.shut.sportradar.test.model.Team} inside {@link com.shut.sportradar.test.model.Game} and return it
     */
    fun updateGameScore(gameId: UUID, newHomeTeamScore: Int, newAwayTeamScore: Int): Game =
        Game(homeTeam = Team("stub"), awayTeam = Team("stub"))


    /**
     * Should change {@link com.shut.sportradar.test.model.GameStatus} of {@link com.shut.sportradar.test.model.Game} to FINISHED and return it
     */
    fun finishGame(gameId: UUID): Game = Game(homeTeam = Team("stub"), awayTeam = Team("stub"))


    /**
     * Should return list of {@link com.shut.sportradar.test.model.Game} with in progress status ordered by their total score. The games with the same
     * total score will be returned ordered by the most recently started match in the scoreboard.
     */
    fun getSummary(): List<Game> = listOf()

}