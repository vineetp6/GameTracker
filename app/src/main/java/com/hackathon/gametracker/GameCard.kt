package com.hackathon.gametracker

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
// 🧠 1. We added 'onDelete', a function passed down from the parent
fun GameCard(game: Game, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        // We use a Row here to put the text on the left, and the button on the right
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
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = game.platform,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = game.status.displayName,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
            
            // 🗑️ 2. The Delete Button
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete Game",
                    tint = MaterialTheme.colorScheme.error // Makes the icon red!
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


