package com.shut.sportradar.test.repository

import com.shut.sportradar.test.exception.GameNotFoundException
import com.shut.sportradar.test.model.Game
import com.shut.sportradar.test.model.GameStatus
import com.shut.sportradar.test.model.Team
import java.util.UUID
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GameRepositoryTest {

    private lateinit var gameRepository: GameRepository

    @BeforeEach
    fun setUp() {
        gameRepository = GameRepository()
    }

    @Test
    fun `getById should return game when exists`() {
        val game = Game(homeTeam = Team("homeTeam"), awayTeam = Team("awayTeam"))
        gameRepository.save(game)
        val result = gameRepository.getById(game.id)
        assertEquals(game, result)
    }

    @Test
    fun `getById should throw GameNotFoundException when game does not exist`() {
        assertThrows<GameNotFoundException> {
            gameRepository.getById(UUID.randomUUID())
        }
    }

    @Test
    fun `save should add game to repository`() {
        val game = Game(homeTeam = Team("homeTeam"), awayTeam = Team("awayTeam"))
        gameRepository.save(game)
        val result = gameRepository.getById(game.id)
        assertEquals(game, result)
    }

    @Test
    fun `findAllInStatus with IN_PROGRESS should return all games in progress`() {
        val inProgressGame1 = Game(homeTeam = Team("homeTeam1"), awayTeam = Team("awayTeam1"))
        val inProgressGame2 = Game(homeTeam = Team("homeTeam2"), awayTeam = Team("awayTeam2"))
        val inProgressGame3 = Game(homeTeam = Team("homeTeam3"), awayTeam = Team("awayTeam3"))
        val inProgressGames = listOf(inProgressGame1, inProgressGame2, inProgressGame3)
        val finishedGame1 =
            Game(homeTeam = Team("homeTeam4"), awayTeam = Team("awayTeam4"), status = GameStatus.FINISHED)
        val finishedGame2 =
            Game(homeTeam = Team("homeTeam5"), awayTeam = Team("awayTeam5"), status = GameStatus.FINISHED)
        val finishedGame3 =
            Game(homeTeam = Team("homeTeam6"), awayTeam = Team("awayTeam6"), status = GameStatus.FINISHED)
        gameRepository.save(inProgressGame1)
        gameRepository.save(inProgressGame2)
        gameRepository.save(inProgressGame3)
        gameRepository.save(finishedGame1)
        gameRepository.save(finishedGame2)
        gameRepository.save(finishedGame3)
        val results = gameRepository.findAllInStatus(GameStatus.IN_PROGRESS)
        assertContentEquals(results.sortedBy { it.id }, inProgressGames.sortedBy { it.id })
    }

    @Test
    fun `findAllInStatus with FINISHED should return all finished games`() {
        val inProgressGame1 = Game(homeTeam = Team("homeTeam1"), awayTeam = Team("awayTeam1"))
        val inProgressGame2 = Game(homeTeam = Team("homeTeam2"), awayTeam = Team("awayTeam2"))
        val inProgressGame3 = Game(homeTeam = Team("homeTeam3"), awayTeam = Team("awayTeam3"))
        val finishedGame1 =
            Game(homeTeam = Team("homeTeam4"), awayTeam = Team("awayTeam4"), status = GameStatus.FINISHED)
        val finishedGame2 =
            Game(homeTeam = Team("homeTeam5"), awayTeam = Team("awayTeam5"), status = GameStatus.FINISHED)
        val finishedGame3 =
            Game(homeTeam = Team("homeTeam6"), awayTeam = Team("awayTeam6"), status = GameStatus.FINISHED)
        val finishedGames = listOf(finishedGame1, finishedGame2, finishedGame3)
        gameRepository.save(inProgressGame1)
        gameRepository.save(inProgressGame2)
        gameRepository.save(inProgressGame3)
        gameRepository.save(finishedGame1)
        gameRepository.save(finishedGame2)
        gameRepository.save(finishedGame3)
        val results = gameRepository.findAllInStatus(GameStatus.FINISHED)
        assertContentEquals(results.sortedBy { it.id }, finishedGames.sortedBy { it.id })
    }

    @Test
    fun `findAllInStatus with IN_PROGRESS should return empty list if there are no games with this status`() {
        val finishedGame1 =
            Game(homeTeam = Team("homeTeam4"), awayTeam = Team("awayTeam4"), status = GameStatus.FINISHED)
        val finishedGame2 =
            Game(homeTeam = Team("homeTeam5"), awayTeam = Team("awayTeam5"), status = GameStatus.FINISHED)
        val finishedGame3 =
            Game(homeTeam = Team("homeTeam6"), awayTeam = Team("awayTeam6"), status = GameStatus.FINISHED)
        gameRepository.save(finishedGame1)
        gameRepository.save(finishedGame2)
        gameRepository.save(finishedGame3)
        val results = gameRepository.findAllInStatus(GameStatus.IN_PROGRESS)
        assertContentEquals(results, listOf())
    }

    @Test
    fun `update should successfully update game`() {
        val game = Game(homeTeam = Team("homeTeam1"), awayTeam = Team("awayTeam1"))
        gameRepository.save(game)
        val updatedHomeTeam = Team("updatedHomeTeam", 23)
        val updatedAwayTeam = Team("updatedAwayTeam", 123)
        val updatedGameStatus = GameStatus.FINISHED
        game.homeTeam = updatedHomeTeam
        game.awayTeam = updatedAwayTeam
        game.status = updatedGameStatus
        gameRepository.update(game)
        val result = gameRepository.getById(game.id)
        assertEquals(updatedHomeTeam, result.homeTeam)
        assertEquals(updatedAwayTeam, result.awayTeam)
        assertEquals(updatedGameStatus, result.status)
    }
}