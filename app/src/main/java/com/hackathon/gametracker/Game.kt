package com.hackathon.gametracker

import java.util.UUID

enum class GameStatus(val displayName: String) {
    BACKLOG("Backlog ⏳"),
    PLAYING("Playing 🎮"),
    COMPLETED("Completed 🏆")
}

data class Game(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val platform: String,
    val status: GameStatus = GameStatus.BACKLOG,
    val rating: Int? = null
)


// In Android development, before we build UI components or connect a database,
  // we define how our information is structured in memory. 
  //In Kotlin, we do this using a data class, which is a lightweight class designed specifically to hold data without boilerplate code.

// In Kotlin, we represent structured data using a data class. 
    // Kotlin automatically generates useful functions behind the scenes for data classes, such as copying data or checking equality.