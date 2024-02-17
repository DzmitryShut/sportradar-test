package com.shut.sportradar.test.repository

import com.shut.sportradar.test.model.Game
import com.shut.sportradar.test.model.GameStatus
import com.shut.sportradar.test.model.Team
import java.util.UUID

/**
 * Class for handling {@link com.shut.sportradar.test.model.Game} domain objects
 */
class GameRepository {

    /**
     * Should save and return {@link com.shut.sportradar.test.model.Game} or throw {@link com.shut.sportradar.test.exception.GameNotFoundException}
     */
    fun getById(gameId: UUID): Game = Game(homeTeam = Team("stub"), awayTeam = Team("stub"))

    /**
     * Should return {@link com.shut.sportradar.test.model.Game} or throw {@link com.shut.sportradar.test.exception.GameNotFoundException}
     */
    fun save(game: Game): Game = Game(homeTeam = Team("stub"), awayTeam = Team("stub"))


    /**
     * Should return all {@link com.shut.sportradar.test.model.Game} objects with specified {@link com.shut.sportradar.test.model.GameStatus}
     */
    fun findAllInStatus(gameStatus: GameStatus): List<Game> = listOf()


    /**
     * Should update and return {@link com.shut.sportradar.test.model.Game} or throw {@link com.shut.sportradar.test.exception.GameNotFoundException}
     */
    fun update(game: Game): Game = Game(homeTeam = Team("stub"), awayTeam = Team("stub"))

}