package com.shut.sportradar.test.exception

import java.util.UUID

/**
 * Class should be thrown if there are no such game in data storage
 */
class GameNotFoundException(id: UUID) : RuntimeException("Game with id=$id not found")