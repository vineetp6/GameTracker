package com.hackathon.gametracker

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
// 🧠 1. Added onStatusChange parameter
fun GameCard(game: Game, onDelete: () -> Unit, onStatusChange: (GameStatus) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = game.title,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = game.platform,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    
                    // 🔽 2. A Box to hold the button and the dropdown menu together
                    Box {
                        var expanded by remember { mutableStateOf(false) }
                        
                        // Clickable text to open the menu
                        TextButton(onClick = { expanded = true }) {
                            Text(text = game.status.displayName)
                        }
                        
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            // Loop through all possible statuses (Backlog, Playing, Completed)
                            GameStatus.entries.forEach { status ->
                                DropdownMenuItem(
                                    text = { Text(status.displayName) },
                                    onClick = {
                                        onStatusChange(status) // Tell the parent!
                                        expanded = false // Close the menu
                                    }
                                )
                            }
                        }
                    }
                }
            }
            
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete Game",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}


// Card: Surfaces the content in an elevated container.

// Column: Stacks items vertically (title on top, details below).

//Row: Arranges items horizontally (places platform on the left and status on the right using Arrangement.SpaceBetween).

// Modifier.padding & Spacer: Controls spacing without hardcoding coordinate positions.

// In Jetpack Compose, you nest layouts to build complex interfaces:
    // The outer Column stacks elements vertically: first the game title, then the info row beneath it.
    // Inner Row arranges items horizontally side-by-side: the platform name on the left and the status on the right.


// Modifier tells a composable how to look, size, or behave.
    // If a composable like Text or Card is the building block, the modifier is how you style it.
    // Examples from the code:
       // Modifier.fillMaxWidth() stretches the component across the full screen width.
       // Modifier.padding(16.dp) adds breathing room around the edges.


// State Hoisting.
  // State hoisting is a fancy way of saying: 
    // "The child component (the GameCard) shouldn't delete the data itself. 
    // It should just tell the parent component (the GameList) that the delete button was clicked, and let the parent handle the actual deletion."


// MainActivity.kt & GameCard.kt are tightly connected. 
  // MainActivity.kt is the "boss" that manages the list of games and handles saving to storage. 
  // GameCard.kt is a "worker" that displays individual game information and communicates user actions (like delete or status change) back to the boss.

   // MainActivity is the Boss. 
      // It holds your master list of games and manages saving them to the phone. 
   // The GameCard is just a Worker. 
     // Its only job is to display the data the Boss hands to it.

  // When you want to change a game from "Backlog" to "Completed", the Worker (GameCard) can't update the master list itself. 
    // So, we gave the GameCard a "walkie-talkie" command called onStatusChange.
  // Now, when you select a new status from the dropdown menu, the GameCard uses that walkie-talkie to radio the Boss: "Hey, update this game's status!" 
     // The Boss then updates the master list and saves it to the phone.

    