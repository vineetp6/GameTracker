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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext // Needed for storage!

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
    // 📱 Grab the Android context for storage
    val context = LocalContext.current

    // 📥 Load saved games immediately when the screen opens
    val games = remember {
        val savedGames = StorageManager.loadGames(context)
        mutableStateListOf(*savedGames.toTypedArray()) 
    }

    // 🧠 State to control dialog visibility
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
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

    if (showDialog) {
        AddGameDialog(
            onDismiss = { showDialog = false },
            onGameAdded = { newTitle, newPlatform ->
                games.add(Game(title = newTitle, platform = newPlatform))
                
                // 💾 Save the new list to the phone right after adding
                StorageManager.saveGames(context, games)
                
                showDialog = false
            }
        )
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


// package com.hackathon.gametracker:
   // In Kotlin, a package acts like a shared room. 
   // Because both files declare they belong to the exact same package, the Kotlin compiler automatically links them together behind the scenes. 
   // Any function (like our AddGameDialog) or data class (like our Game) created in one file is instantly visible to every other file in that same room.



// var showDialog by remember { mutableStateOf(false) } :
  // mutableStateOf(false): 
    // This creates a special "wired" box holding the value false.
    //  Because it is "State", Jetpack Compose constantly watches this box.
    // If the value inside changes to true, Compose says, "Ah! Something changed! I need to immediately redraw the screen to reflect this."
    // remember { ... }: 
       // When Compose redraws the screen, it basically runs your code from top to bottom again.
       // If you didn't have remember, the app would redraw the screen and instantly reset your box back to false. 
       // remember tells the app: "Keep this box in a safe place so you don't overwrite it when you refresh the screen."
    // by: This is just a handy Kotlin shortcut. 
       // Instead of forcing you to write showDialog.value = true every single time, by lets you interact with the box directly by simply writing showDialog = true.


// Lambda Expressions: 
  // A lambda expression is a small block of code that can be passed around and executed later. 
  // In Kotlin, we write them using curly braces {}. 
  // For example, onClick = { showDialog = true } is a lambda expression that says, "When the button is clicked, run this code to set showDialog to true."



// onGameAdded = { newTitle, newPlatform ->
                //games.add(0, Game(title = newTitle, platform = newPlatform))
                //showDialog = false // Hide after adding
            //}
   // Putting code inside curly braces {} with an arrow ->—is called a lambda expression.
   // newTitle, newPlatform are the packages being handed back to the Main Screen by the Dialog. 
      // The dialog is saying, "Here are the two specific text strings the user just finished typing."
   // -> (The Arrow) is the bridge. 
     // It separates what you receive from what you do.
     // It basically translates to: "Take these items AND THEN do the following with them..."


// items(items = games, key = { game -> game.id }) { game -> GameCard(game = game):
  // key = { game -> game.id }	"Take this game AND THEN hand back its id (so Compose can keep track of it)."
  // { game -> GameCard(game = game) }	"Take this game AND THEN draw a GameCard using its data."


//Important concept:
  // To get your saved data back onto the screen tomorrow, the app has to work in reverse. 
  // First, it reads the JSON text string from the phone's storage.
  // Second, it uses Gson to translate that text back into a Kotlin List<Game>. 
  // Finally, it puts that list into our Compose state so the screen redraws the cards.


// Best time to load the games is the exact moment our games state variable is created! 
// And we should save the games right after we add a new one to the list.

// context: Context is like your app's ID badge. 
  // The StorageManager needs to swipe this ID badge to get permission to open the phone's storage. 
  // To read and write files in Android, we need access to the phone's environment, which Android calls a Context.
    //  In Jetpack Compose, we grab this using LocalContext.current.


// mutableStateListOf works. This function expects you to hand it individual items separated by commas, like this:
   // mutableStateListOf(game1, game2, game3)
   // It does not accept a pre-packaged List object. 
   // If you try to hand it a whole list at once, Kotlin gets confused.

// mutableStateListOf(*savedGames.toTypedArray())
   // savedGames: This is your standard list of games loaded from the phone's memory.
   // .toTypedArray(): This converts your flexible Kotlin List into a rigid Array. 
   // We have to do this because the next step only works on Arrays.
   // * (The Spread Operator): This is the magic symbol. 
    // * It tells Kotlin to "unpack" or "spread out" the array into individual pieces.
 // Imagine mutableStateListOf is a vending machine 🎰 that only accepts individual coins. savedGames is a tightly wrapped paper roll of coins.
   // You can't shove the whole paper roll into the coin slot; it won't fit.
   //  * (spread operator) is the action of breaking open the paper roll and pouring the individual coins into the slot one by one.