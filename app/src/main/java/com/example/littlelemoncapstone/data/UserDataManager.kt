package com.example.littlelemoncapstone.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Create a DataStore instance outside of any class
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class UserDataManager(private val context: Context) {

    // Define keys for each piece of data you want to store
    companion object {
        val FIRST_NAME_KEY = stringPreferencesKey("FIRST_NAME")
        val LAST_NAME_KEY = stringPreferencesKey("LAST_NAME")
        val EMAIL_KEY = stringPreferencesKey("EMAIL")
        val IS_REGISTERED_KEY = booleanPreferencesKey("IS_REGISTERED")
    }

    // Function to save all user data at once
    suspend fun saveUserData(firstName: String, lastName: String, email: String) {
        context.dataStore.edit { preferences ->
            preferences[FIRST_NAME_KEY] = firstName
            preferences[LAST_NAME_KEY] = lastName
            preferences[EMAIL_KEY] = email
            preferences[IS_REGISTERED_KEY] = true
        }
    }

    // Create a flow to read the first name
    val firstNameFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[FIRST_NAME_KEY]
        }

    val lastNameFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[LAST_NAME_KEY]
        }

    val emailFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[EMAIL_KEY]
        }
    // You can create similar flows for lastName and email if needed

    val isRegisteredFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            // If the key exists, return its value. Otherwise, return false.
            preferences[IS_REGISTERED_KEY] ?: false
        }

    suspend fun clearData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
