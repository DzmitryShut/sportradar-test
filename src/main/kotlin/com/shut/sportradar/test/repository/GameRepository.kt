package com.shut.sportradar.test.repository

import com.shut.sportradar.test.exception.GameNotFoundException
import com.shut.sportradar.test.model.Game
import com.shut.sportradar.test.model.GameStatus
import java.util.UUID

/**
 * Class for handling {@link com.shut.sportradar.test.model.Game} domain objects
 */
class GameRepository {

    private val data = mutableListOf<Game>()

    /**
     * Should save and return {@link com.shut.sportradar.test.model.Game} or throw {@link com.shut.sportradar.test.exception.GameNotFoundException}
     */
    fun getById(gameId: UUID) =
        data.find { gameId == it.id } ?: throw GameNotFoundException(gameId)

    /**
     * Should return {@link com.shut.sportradar.test.model.Game} or throw {@link com.shut.sportradar.test.exception.GameNotFoundException}
     */
    fun save(game: Game) =
        data.add(game).let { game }


    /**
     * Should return all {@link com.shut.sportradar.test.model.Game} objects with specified {@link com.shut.sportradar.test.model.GameStatus}
     */
    fun findAllInStatus(gameStatus: GameStatus) =
        data.filter { it.status == gameStatus }


    /**
     * Should update and return {@link com.shut.sportradar.test.model.Game} or throw {@link com.shut.sportradar.test.exception.GameNotFoundException}
     */
    fun update(game: Game) =
        getById(game.id).apply {
            homeTeam = game.homeTeam
            awayTeam = game.awayTeam
            status = game.status
        }

}