package com.shut.sportradar.test.service

import com.shut.sportradar.test.exception.GameNotFoundException
import com.shut.sportradar.test.model.Game
import com.shut.sportradar.test.model.GameStatus
import com.shut.sportradar.test.model.Team
import com.shut.sportradar.test.repository.GameRepository
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDateTime
import java.util.UUID
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ScoreboardServiceTest {

    private lateinit var gameRepository: GameRepository
    private lateinit var scoreboardService: ScoreboardService

    @BeforeEach
    fun setUp() {
        gameRepository = mockk()
        scoreboardService = ScoreboardService(gameRepository)
    }

    @Test
    fun `createGame should return created game`() {
        val game = Game(homeTeam = Team("homeTeam"), awayTeam = Team("awayTeam"))
        every { gameRepository.save(any()) } returns game
        val result = scoreboardService.createGame(game.homeTeam.name, game.awayTeam.name)
        assertEquals(game, result)
    }

    @Test
    fun `updateGameScore should return game with updated scores`() {
        val game = Game(homeTeam = Team("homeTeam"), awayTeam = Team("awayTeam"))
        every { gameRepository.getById(game.id) } returns game
        every { gameRepository.update(any()) } returnsArgument 0
        val newHomeTeamScore = 3424
        val newAwayTeamScore = 123
        val result = scoreboardService.updateGameScore(game.id, newHomeTeamScore, newAwayTeamScore)
        assertEquals(game.apply {
            homeTeam.score = newHomeTeamScore
            awayTeam.score = newAwayTeamScore
        }, result)
    }

    @Test
    fun `updateGameScore should throw GameNotFoundException if game not exists`() {
        val game = Game(homeTeam = Team("homeTeam"), awayTeam = Team("awayTeam"))
        every { gameRepository.getById(any()) } throws GameNotFoundException(UUID.randomUUID())
        assertThrows<GameNotFoundException> {
            scoreboardService.updateGameScore(game.id, 123, 321)
        }
    }

    @Test
    fun `finishGame should return game with finished status`() {
        val game = Game(homeTeam = Team("homeTeam"), awayTeam = Team("awayTeam"))
        every { gameRepository.getById(game.id) } returns game
        every { gameRepository.update(any()) } returnsArgument 0
        val result = scoreboardService.finishGame(game.id)
        assertEquals(game.apply {
            status = GameStatus.FINISHED
        }, result)
    }

    @Test
    fun `finishGame should throw GameNotFoundException if game not exists`() {
        val game = Game(homeTeam = Team("homeTeam"), awayTeam = Team("awayTeam"))
        every { gameRepository.getById(any()) } throws GameNotFoundException(UUID.randomUUID())
        assertThrows<GameNotFoundException> {
            scoreboardService.finishGame(game.id)
        }
    }

    @Test
    fun `getSummary should return empty list if `() {
        every { gameRepository.findAllInStatus(GameStatus.IN_PROGRESS) } returns listOf()
        val result = scoreboardService.getSummary()
        assertContentEquals(result, listOf())
    }

    @Test
    fun `getSummary should return sorted list`() {
        val game1 = Game(
            homeTeam = Team("Mexico", 0),
            awayTeam = Team("Canada", 5),
            createdAt = LocalDateTime.now().minusDays(1)
        )
        val game2 = Game(
            homeTeam = Team("Spain", 10),
            awayTeam = Team("Brazil", 2),
            createdAt = LocalDateTime.now().minusDays(2)
        )
        val game3 = Game(
            homeTeam = Team("Germany", 2),
            awayTeam = Team("France", 2),
            createdAt = LocalDateTime.now().minusDays(3)
        )
        val game4 = Game(
            homeTeam = Team("Uruguay", 6),
            awayTeam = Team("Italy", 6),
            createdAt = LocalDateTime.now().minusDays(4)
        )
        val game5 = Game(
            homeTeam = Team("Argentina", 3),
            awayTeam = Team("Australia", 1),
            createdAt = LocalDateTime.now().minusDays(5)
        )
        every { gameRepository.findAllInStatus(GameStatus.IN_PROGRESS) } returns listOf(
            game1,
            game2,
            game3,
            game4,
            game5
        )
        val result = scoreboardService.getSummary()
        assertContentEquals(result, listOf(game4, game2, game1, game5, game3))
    }

}