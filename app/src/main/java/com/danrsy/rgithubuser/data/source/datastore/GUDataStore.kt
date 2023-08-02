package com.danrsy.rgithubuser.data.source.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GUDataStore(private val context: Context) {

    private val dataStoreName = "GU_DATASTORE"
    private val THEME_KEY = intPreferencesKey("theme_setting")

    private val Context.userPreferenceDataStore: DataStore<Preferences> by preferencesDataStore(
        name = dataStoreName
    )

    fun getThemeSetting(): Flow<Int> {
        return context.userPreferenceDataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: 2
        }
    }

    suspend fun saveThemeSetting(theme: Int) {
        context.userPreferenceDataStore.edit { preferences ->
            preferences[THEME_KEY] = theme
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var mInstance: GUDataStore? = null

        fun getInstance(context: Context): GUDataStore =
            mInstance?: synchronized(this) {
                val newInstance = mInstance?: GUDataStore(context).also { mInstance = it }
                newInstance
            }
    }
}