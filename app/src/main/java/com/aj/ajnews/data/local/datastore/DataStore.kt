package com.aj.ajnews.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class DataStore(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Settings Preference")

    /** Keys **/
    private val darkTheme = booleanPreferencesKey(name = "Is Dark Theme Enabled")
    private val categories = stringSetPreferencesKey(name = "User's Interest")
    private val countryCode = stringPreferencesKey(name = "Country Code")

    /** Function to save theme preferences **/
    suspend fun saveThemePreference(value: Boolean) {
        context.dataStore.edit { preference ->
            preference[darkTheme] = value
        }
    }

    /** Function to retrieve theme preference **/
    suspend fun getThemePreference() = context.dataStore.data.first()[darkTheme]

    /** Function to save category preference **/
    suspend fun saveCategoryPreference(value: Set<String>) {
        context.dataStore.edit { preference ->
            preference[categories] = value
        }
    }

    /** Function to retrieve user's interest **/
    suspend fun getUserInterest() = context.dataStore.data.first()[categories]

    /** Function to save country code **/
    suspend fun saveCountryCode(value: String) {
        context.dataStore.edit { preference ->
            preference[countryCode] = value
        }
    }

    /** Function to retrieve country code **/
    suspend fun getCountryCode() = context.dataStore.data.first()[countryCode]

}