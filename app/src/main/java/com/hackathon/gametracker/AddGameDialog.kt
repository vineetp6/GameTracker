package com.hackathon.gametracker

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddGameDialog(
    onDismiss: () -> Unit,
    onGameAdded: (title: String, platform: String) -> Unit
) {
    // 🧠 State to hold whatever the user types
    var title by remember { mutableStateOf("") }
    var platform by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add a New Game") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it }, // 'it' is the new text the user just typed
                    label = { Text("Game Title") }
                )
                OutlinedTextField(
                    value = platform,
                    onValueChange = { platform = it },
                    label = { Text("Platform (e.g., PC, Switch)") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // Only add if they actually typed something!
                    if (title.isNotBlank() && platform.isNotBlank()) {
                        onGameAdded(title, platform)
                    }
                }
            ) { Text("Add") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}


// In Kotlin, you can pass actions (functions) as variables, not just data like text or numbers.
  // () means "an action that requires no information to run".
  // -> Unit means "this action doesn't return an answer" (it just does something, like closing a screen).

// onDismiss: () -> Unit simply means: 
 // "When you use this Dialog in your app, you must tell it what action to run when the user wants to cancel.
 // " We trigger this action later when they click the "Cancel" button.

 // There are two OutlinedTextFields instead of one. 
  // An OutlinedTextField creates exactly one physical text box on the screen. 
  // Since our Game data requires both a title ("Elden Ring") and a platform ("PC"), we need to draw two separate boxes for the user to type those distinct pieces of information into.

  // In Kotlin and Jetpack Compose, curly braces { } usually mean one specific thing:
    //  "Here is a block of code (an action or a piece of UI) that I want you to run."


// label = { Text("Game Title") }?
 // In Compose, a label isn't restricted to being just a simple string of text; it's an open visual "slot." 
 // By requiring { }, Compose allows you to draw whatever you want in that space. 
 //You chose to draw simple Text, but because of the curly braces, you could easily draw an Icon and Text side-by-side inside that same label.


 // TextButton(onClick = onDismiss) { Text("Cancel") }?
    // This is a special Kotlin shortcut to make code easier to read. 
    // A TextButton needs two main pieces of information:
        // The Action: What happens when it's clicked? (This goes in the parentheses ()).
        // The Content: What does the button look like? (This goes in the curly braces { }).


// Parentheses () are for Configuration and Actions:
   // Anything inside () defines the properties or rules for that specific UI element.
   //  You are passing data to set it up. This includes:
      // Actions/Events: What should happen when it is clicked or typed in (e.g., onClick = onDismiss).
      // Styling: How big it is or what color it is (e.g., modifier = Modifier.padding(16.dp)).
      // State: The current data it holds (e.g., value = title).


    
// Curly Braces {} are for Content (Children) 
   // Anything inside {} defines what goes inside that UI element. 
   // If a component acts as a container, you use curly braces to put other UI elements inside it.
   // TextButton(onClick = onDismiss) { Text("Cancel") }, TextButton is the container, and Text("Cancel") is the visual child placed inside it.


// TextButton(onClick = onDismiss) { Text("Cancel") } : 
  // TextButton is the container, and Text("Cancel") is the visual child placed inside it.
  // This is a special Kotlin feature called a "trailing lambda." 
      // If the last setting a function asks for is "what UI should I draw inside myself?", Kotlin lets you write it outside the parentheses using {} to make the code cleaner.


// If Kotlin didn't have the trailing lambda feature: 
    // you would have to put the UI instructions inside the parentheses as a parameter (usually called content).
    // TextButton(onClick = onDismiss,content = { Text("Cancel") }) 


// value = title (Data): The title variable is just a raw piece of text (like "Halo" or "Mario"). 
   // You are handing the text field a static piece of data to display right now. 
   // No braces needed.


// onValueChange = { title = it }:
  // onValueChange doesn't want a word. 
  // It wants a plan of action for the future. 
  // By using { title = it }, we are giving it instructions: 
     // "Whenever the user types a new letter later on, take that new text (it) and save it into my title variable."



// parentheses () = The Settings ⚙️: Data you are giving it right now (colors, sizes, variables).
// braces {} = The Instructions 📝: A list of tasks to run later (what UI to draw inside, or what action to perform when clicked/typed).


// onValueChange = { title = it } => Jetpack Compose is strictly driven by state. 
// The text box doesn't manage its own text; it only displays whatever the title variable currently holds. If we don't actively update title = it with every keystroke, the text box will just stay blank no matter what you type!

// package com.hackathon.gametracker:
   // In Kotlin, a package acts like a shared room. 
   // Because both files declare they belong to the exact same package, the Kotlin compiler automatically links them together behind the scenes. 
   // Any function (like our AddGameDialog) or data class (like our Game) created in one file is instantly visible to every other file in that same room.

