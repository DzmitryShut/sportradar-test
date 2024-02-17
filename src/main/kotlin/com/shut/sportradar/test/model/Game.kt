package com.shut.sportradar.test.model

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.UUID

/**
 * Class representing game domain object
 */
data class Game(
    var id: UUID = UUID.randomUUID(),
    var homeTeam: Team,
    var awayTeam: Team,
    var status: GameStatus = GameStatus.IN_PROGRESS,
    var createdAt: LocalDateTime = LocalDateTime.now(ZoneOffset.UTC)
)