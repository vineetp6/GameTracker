import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
package com.hackathon.gametracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GameList(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun GameList(modifier: Modifier = Modifier) {
    // 🧠 1. Convert to a "State" list that remembers its data
    val games = remember {
        mutableStateListOf(
            Game(title = "Elden Ring", platform = "PC", status = GameStatus.PLAYING, rating = 10),
            Game(title = "Hollow Knight", platform = "Switch", status = GameStatus.COMPLETED, rating = 9)
        )
    }

    // 🏗️ 2. Scaffold allows us to easily add a Floating Action Button (FAB)
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                // ➕ 3. Add a placeholder game when the button is clicked!
                games.add(Game(title = "New Game", platform = "TBD"))
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Game")
            }
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(items = games, key = { game -> game.id }) { game ->
                GameCard(game = game)
            }
        }
    }
}








// In Jetpack Compose, we don't use a standard Column for dynamic lists. 
  // A regular Column renders every single item immediately, which can cause lag or out-of-memory crashes as your list grows. 
  // Instead, we use LazyColumn. It only renders the items currently visible on the screen, recycling them as the user scrolls (similar to RecyclerView in older Android development).


// LazyColumn(...) { ... } : This is the main scrollable container.
 // The word "Lazy" is key here—it means the app only draws the items currently visible on the screen. 
 //As you scroll, it recycles the off-screen UI components to save memory, which prevents the app from lagging.

 // items(items = sampleGames): This function acts like a loop specifically designed for lazy lists.
   // It takes your Kotlin list of games (sampleGames) and prepares to go through them one by one.

// { game -> GameCard(game = game) } : This is the visual blueprint. 
  // For every game in your list, it calls your GameCard layout and passes the specific game data to it to be rendered on the screen.

// key = { game -> game.id } : This tells Compose exactly how to uniquely identify each item in the list using the unique ID we created in the data class earlier.

// If we didn't provide this unique key, Compose would default to tracking the items merely by their position index in the list (0, 1, 2, etc.).

// To make it dynamic so we can add new games, we need to use Jetpack Compose State. 
  //In Compose, when "state" (data) changes, the UI automatically updates (recomposes) to show the new data.

// Unique key (like game.id) is crucial because it tells Jetpack Compose exactly which item is which.

// State = If we want to add new games to our backlog, we need a button, and we need to tell Compose to watch our list for changes so it can redraw the screen automatically.
  // In Compose, this is called State.

// Recompisition: Imagine Jetpack Compose is a very fast artist drawing your app on a whiteboard. 
  // Every time data changes, the artist wipes the board completely clean and redraws the whole screen from scratch. 
  // This wiping and redrawing is called Recomposition.


// mutableStateListOf (The Alarm Bell 🔔): 
 // A normal Kotlin list is silent. 
 // If you add a game to a normal list, the artist doesn't notice, so the screen never updates. 
 //A mutableStateListOf has a built-in alarm. Whenever you add, remove, or change an item, it rings the bell, telling the artist: "Hey! The data changed! Wipe the board and redraw the screen right now!"


 // remember (The Sticky Note 📝): 
  // Because the artist wipes the entire board during a redraw, any normal variables inside your code get completely erased and reset to their default starting values. 
  // remember acts like a permanent sticky note. It tells the artist: "When you wipe the board to redraw, do not erase this specific piece of data. Keep it exactly as it was."
  // f we didn't use remember, Jetpack Compose would completely recreate an empty list from scratch every single time the screen refreshed! 
     // Any games you added would vanish instantly. remember tells Compose to safely store that data across screen updates.




// mutableStateListOf forces the screen to update, and remember ensures your list of games actually survives that update instead of being wiped out.



// @Composable annotation tells the Kotlin compiler that a specific function is responsible for building a piece of the user interface (UI).
  // Normally, a Kotlin function takes in data and returns standard data (like numbers or text). 
  // But when you add @Composable above a function, you change its job. 
  //Instead of returning data, it "emits" visual elements onto the screen.
// Think of a @Composable function like a custom UI building block. 
  // When you wrote @Composable fun GameCard(game: Game), you created a custom block that takes in game data and draws a card.
  // A strict rule in Jetpack Compose is that you can only call a @Composable function from inside another @Composable function.


