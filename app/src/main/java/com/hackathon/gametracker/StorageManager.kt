package com.hackathon.gametracker

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object StorageManager {
    private const val PREFS_NAME = "GameTrackerPrefs"
    private const val KEY_GAMES = "saved_games"

    // 📥 Convert list of games to JSON text and save it
    fun saveGames(context: Context, games: List<Game>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val jsonText = Gson().toJson(games)
        prefs.edit().putString(KEY_GAMES, jsonText).apply()
    }

    // 📤 Read JSON text and convert it back to a list of games
    fun loadGames(context: Context): List<Game> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val jsonText = prefs.getString(KEY_GAMES, null)
        
        return if (jsonText != null) {
            // TypeToken tells Gson exactly what shape of data to expect
            val type = object : TypeToken<List<Game>>() {}.type
            Gson().fromJson(jsonText, type)
        } else {
            emptyList() // Return an empty list if nothing is saved yet
        }
    }
}



// StorageManager Setup 
   // object StorageManager: In Kotlin, object means this is a "Singleton". 
     // There is only ever one StorageManager in the whole app. 
     // It acts as the manager of your filing cabinet.

   // PREFS_NAME & KEY_GAMES: These are just labels. 
      // PREFS_NAME is the label on the filing cabinet drawer. 
      // KEY_GAMES is the label on the specific folder inside that drawer where we keep the games.

   // Context: You'll see this passed into the functions. 
        // Context is like your app's ID badge. 
        // The StorageManager needs to swipe this ID badge to get permission to open the phone's storage.

// saveGames function
  // getSharedPreferences(..., MODE_PRIVATE): 
     // Opens the filing cabinet. MODE_PRIVATE means this cabinet is locked; no other apps on your phone can look inside it.
     // Gson().toJson(games): Hands the list of games to the translator to turn into plain text.
     // prefs.edit().putString(...).apply(): Opens the folder, erases whatever text was in there yesterday, writes down the new text, and slams the drawer shut (apply()).


// loadGames function
  // getSharedPreferences(..., MODE_PRIVATE): Opens the filing cabinet again.
  // prefs.getString(...): Looks inside the folder and reads the text string. 
     // If nothing is there yet, it returns null.
  // Gson().fromJson(jsonText, type): Hands the text to the translator to turn it back into a list of games.
  // TypeToken<List<Game>>() {}.type: This is a special trick to tell Gson exactly what kind of data to expect. 
     // Without this, Gson wouldn't know if it should make a list of games, a single game, or something else entirely.
  // emptyList(): If there was no text saved yet, we just hand the app a blank list so it doesn't crash.

